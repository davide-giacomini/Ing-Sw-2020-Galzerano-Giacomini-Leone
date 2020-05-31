package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If a {@link Player} has Hestia, their {@link Worker} can build a second time, but this
 * second construction cannot be on a perimeter slot.
 */
public class Hestia extends God {

    public Hestia(Player player, String name) {
        super(player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method allows a second build only if the chosen slot is a perimeter slot.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidDirectionException if there are problems with I/O
     * @throws InvalidBuildException if building is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {

        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements incorrect");

        if (player.getTurn().getNumberOfBuildings() == 1 && (player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot()).isPerimeterSlot()))
        throw new InvalidBuildException("You are not allowed to build a second time on a perimeter slot!");

        try {
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }

    }

    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions or
     * does a special check for the second build, controlling if the slots is a perimeter slot.
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
                    if ( (destinationSlot.isPerimeterSlot()) && !destinationSlot.isOccupied())
                        return true;
                }
                catch (IndexOutOfBoundsException e) {

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

    /**
     * This method checks if the player has completed a turn or if he still have to do some actions.
     * @return true if he can end his turn, false otherwise.
     */
    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfBuildings==1 && numberOfMovements==1 || numberOfBuildings==2 && numberOfMovements==1;
    }
}
