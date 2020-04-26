package Model.Gods;

import Enumerations.Direction;
import Model.Exceptions.*;
import Model.Player;
import Model.Worker;

/**
 * {@link Player} can win also if his {@link Worker} moves down two or more levels.
 */
public class Pan extends God{
    public Pan(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        canUseBothWorkers = false;
    }

    /**
     * This method calls the standard move of a worker:
     * Atlas doesn't modify the moving rules.
     * The only difference is in the return value.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level or if moves down
     * two or more levels, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidMoveException if the move is not permitted.
     */
    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException,  InvalidMoveException {
        int previousLevel = worker.getSlot().getLevel().ordinal();
        boolean winCondition = false;
        try {
            winCondition = worker.move(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }
        int actualLevel = worker.getSlot().getLevel().ordinal();
        return winCondition || (actualLevel - previousLevel < -1);
    }

    /**
     * This method calls the standard build of a worker:
     * Athena doesn't modify the building rules.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidDirectionException if there are some troubles of I/O.
     * @throws InvalidBuildException if the build is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException,InvalidBuildException{
        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements not correct");

        try {
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }
    }

    /**
     * In does nothing.
     */
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
    protected boolean checkIfCanMove(Worker worker) {
        return checkIfCanMoveInNormalConditions(worker);
    }

    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can build, false otherwise.
     */
    @Override
    protected boolean checkIfCanBuild(Worker worker) {
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

        if (numberOfMovements==0)
            return checkIfCanMove(worker);
        else if (numberOfMovements==1 && numberOfBuildings==0)
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

        return numberOfBuildings >= MIN_BUILDINGS && numberOfMovements >= MIN_MOVEMENTS
                || numberOfMovements >= MIN_MOVEMENTS && player.isWinning();
    }
}
