package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If a {@link Player} has Hera, the opponents' {@link Worker} cannot win by moving into a perimeter slot.
 */
public class Hera extends God  {

    public Hera(Player player, String name) {

        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
    }

    /**
     * This method calls the standard move of a worker:
     * Hera doesn't modify the moving rules.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level or if moves down
     * two or more levels, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidMoveException if the move is not permitted.
     */
    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidMoveException {
        try {
            return worker.move(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }
    }

    /**
     * This method calls the standard build of a worker:
     * Hera doesn't modify the building rules.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidBuildException if the build is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException {
        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements incorrect");

        try {
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }
    }

    @Override
    public void resetParameters() {

    }

    /**
     * This method directly calls the God's method checkIfCanMoveInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can move, false otherwise
     */
    @Override
    public boolean checkIfCanMove(Worker worker) { return checkIfCanMoveInNormalConditions(worker); }

    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can build, false otherwise.
     */
    @Override
    public boolean checkIfCanBuild(Worker worker) {
        return checkIfCanBuildInNormalConditions(worker);
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

        return numberOfMovements==1 && numberOfBuildings==1 || player.isWinning();
    }
}
