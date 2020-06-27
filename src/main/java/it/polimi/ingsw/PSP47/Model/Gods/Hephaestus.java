package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;


/**
 * {@link Player}'s {@link Worker} may build one additional block (not dome) on top of your first block.
 */
public class Hephaestus extends God {

    // the slot where Hephaestus builds the first time
    // and where it can build for a second time
    private Slot doubleBuildSlot;

    public Hephaestus(Player player, String name){
        super (player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method allows a second build only if the new slot where to build is the same as the old one
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidBuildException if the build is not permitted.
     * @throws InvalidDirectionException if the default case in the choice of the direction is reached.
     */
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException{

        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException(" Order of movements not correct");

        if (player.getTurn().getNumberOfBuildings() == 0)
            doubleBuildSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());
        else if (!player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot()).equals(doubleBuildSlot) || (worker.getSlot().getLevel().ordinal()== 3) )
            throw new InvalidBuildException("The second build cannot be permitted on a different slot");

        worker.build(direction);

    }

    /**
     * It reset the value of doubleBuildSlot as this slot change in every turn.
     */
    @Override
    public void resetParameters() {
        doubleBuildSlot = null;
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
        ///THIS PART HERE IS TO CHECK AGAIN AND MAYBE DELETE
        if (numberOfBuildings==1) {
            for (Direction direction: Direction.values()){
                try {
                    // If the direction is out of the board, jump to the catch
                    worker.checkDirection(direction);
                    Slot destinationSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());
                    // else, check if the worker can build on the destinationSlot
                    if (destinationSlot.equals(doubleBuildSlot) && !destinationSlot.isOccupied())
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
