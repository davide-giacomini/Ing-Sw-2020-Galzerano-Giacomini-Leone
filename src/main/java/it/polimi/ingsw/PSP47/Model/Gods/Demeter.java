package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * {@link Player}'s {@link Worker} may build one additional time, but not on the same space.
 */
public class Demeter extends God {
    // the slot where Demeter builds the first time
    private Slot previousSlot;
    
    public Demeter(Player player, String name) {
        super(player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method allows a second build only if the new slot where to build differs from the old one
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidDirectionException if there are problems with I/O
     * @throws InvalidBuildException if building is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {
    
        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements incorrect");
    
        if (player.getTurn().getNumberOfBuildings() == 0)
            previousSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());
        else if (player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot()).equals(previousSlot))
            throw new InvalidBuildException("You are trying to build on the same slot as the previous one");

        worker.build(direction);
    
    }

    /**
     * At the end of the turn the previousSlot must be reset, as it won't be the same on the next turn.
     */
    @Override
    public void resetParameters() {
        previousSlot = null;
    }


    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions or
     * does a special check for the second build
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can build, false otherwise.
     */
    @Override
    public boolean checkIfCanBuild(Worker worker) {
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfBuildings==0)
            return checkIfCanBuildInNormalConditions(worker);
        if (numberOfBuildings==1) {
            for (Direction direction: Direction.values()){
                try {
                    // If the direction is out of the board, jump to the catch
                    worker.checkDirection(direction);
                    Slot destinationSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());
                    // else, check if the worker can build on the destinationSlot
                    if (!destinationSlot.equals(previousSlot) && !destinationSlot.isOccupied())
                        return true;
                }
                catch (IndexOutOfBoundsException e) {
                    // just let the for continue
                } catch (InvalidDirectionException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * This method checks if the worker is paralyzed or not.
     * @param worker the worker chosen to be checked.
     * @return true if the worker can go on, false otherwise.
     */
    @Override
    public boolean checkIfCanGoOn(Worker worker) {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();
        
        if (numberOfMovements==0 && numberOfBuildings==0)
            return checkIfCanMove(worker);
        if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanBuild(worker);
        if (numberOfMovements==1 && numberOfBuildings==1)
            return checkIfCanBuild(worker);
        return false;
    }

}
