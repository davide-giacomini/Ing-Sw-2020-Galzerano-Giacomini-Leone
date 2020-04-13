package Model.Gods;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Worker;

/**
 * {@link Player}'s {@link Worker} may build a dome instead of the "following rules" level.
 */
public class Atlas extends God {

    
    public Atlas(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = true;
        canUseBothWorkers = false;
    }

    /**
     * This method calls the standard move of a worker:
     * Atlas doesn't modify the moving rules.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the winning condition has been verified, false otherwise
     * @throws SlotOccupiedException if the worker try to move in an occupied slot
     * @throws NotReachableLevelException if the worker try to move in an unreachable slot
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidDirectionException if there are some troubles of I/O.
     */
    @Override
    public boolean move(Direction direction, Worker worker) throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException {
        return worker.move(direction);
    }

    /**
     * This method calls two different worker's methods, depending on the setting of WantsToBuildDome.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws SlotOccupiedException if the worker try to build in an occupied slot
     * @throws InvalidDirectionException if there are some troubles of I/O.
     * @throws WrongBuildOrMoveException if the worker try to build but he still hasn't moved.
     */
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        if (player.getTurn().getNumberOfMovements() == 0) {
            throw new WrongBuildOrMoveException();
        }

        if (player.getTurn().wantsToBuildDome() && player.CanBuildDome()) {
            worker.buildDome(direction);
        }
        else if (!player.getTurn().wantsToBuildDome()) {
            worker.build(direction);
        }
        
    }

    /**
     * It does nothing.
     */
    @Override
    public void resetParameters() {
    }

    /**
     * This method directly calls the God's method checkIfCanMoveInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can move, false otherwise
     * @throws InvalidDirectionException if there are some I/O troubles.
     */
    @Override
    protected boolean checkIfCanMove(Worker worker) throws InvalidDirectionException {
        return checkIfCanMoveInNormalConditions(worker);
    }

    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can build, false otherwise.
     * @throws InvalidDirectionException if there are some I/O troubles.
     */
    @Override
    protected boolean checkIfCanBuild(Worker worker) throws InvalidDirectionException {
        return checkIfCanBuildInNormalConditions(worker);
    }

    /**
     * This method checks if the worker is paralyzed or not.
     * @param worker the worker chosen to be checked.
     * @return true if the worker can go on, false otherwise.
     * @throws InvalidDirectionException if there are some I/O troubles.
     */
    @Override
    public boolean checkIfCanGoOn(Worker worker) throws InvalidDirectionException {
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
