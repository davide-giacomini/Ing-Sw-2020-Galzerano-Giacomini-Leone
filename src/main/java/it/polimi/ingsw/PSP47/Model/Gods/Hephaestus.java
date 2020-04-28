package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.Board;
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
        canUseBothWorkers = false;
    }

    /**
     * This method calls the standard move of a worker:
     * Hephaestus doesn't modify the moving rules.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the winning condition has been verified, false otherwise
     * @throws InvalidMoveException if the move is not permitted.
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     */
    @Override
    public boolean move(Direction direction, Worker worker)  throws IndexOutOfBoundsException, InvalidMoveException {

        try {
            return worker.move(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }
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
            doubleBuildSlot = Board.getNearbySlot(direction, worker.getSlot());
        else if (!Board.getNearbySlot(direction, worker.getSlot()).equals(doubleBuildSlot) || (worker.getSlot().getLevel().ordinal()== 3) )
            throw new InvalidBuildException("The second build cannot be permitted on a different slot");

        try {
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }

    }

    /**
     * It does nothing.
     */
    @Override
    public void resetParameters() {
        doubleBuildSlot = null;
    }

    /**
     * This method directly calls the God's method checkIfCanMoveInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can move, false otherwise
     */
    @Override
    protected boolean checkIfCanMove(Worker worker) {
        return checkIfCanMoveInNormalConditions(worker);
    }

    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions or
     * does a special check for the second build
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can build, false otherwise.
     */
    @Override
    protected boolean checkIfCanBuild(Worker worker) {
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfBuildings==0)
            return checkIfCanBuildInNormalConditions(worker);
        ///THIS PART HERE IS TO CHECK AGAIN AND MAYBE DELETE
        if (numberOfBuildings==1) {
            for (Direction direction: Direction.values()){
                try {
                    // If the direction is out of the board, jump to the catch
                    worker.checkDirection(direction);
                    Slot destinationSlot = Board.getBoard().getNearbySlot(direction, worker.getSlot());
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
     * @throws InvalidDirectionException if there are some I/O troubles.
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
