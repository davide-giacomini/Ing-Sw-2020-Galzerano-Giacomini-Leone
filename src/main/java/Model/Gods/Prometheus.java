package Model.Gods;

import Model.Board;
import Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Slot;
import Model.Worker;

/**
 * If {@link Player}'s {@link Worker} does not move up, it may build both before and after moving.
 */
public class Prometheus extends God {
    // true if the player began with a move. In this case it's impossible to build two times
    private boolean moveThenBuild = false;
    
    public Prometheus(Player player, String name) {
        super(player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canAlwaysBuildDome = false;
        canUseBothWorkers = false;
    }
    
    /**
     * This method moves a {@link Worker} from a {@link Slot} to another, towards the destination specified.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level, false otherwise
     * @throws SlotOccupiedException if the destination slot is occupied by a dome or another worker
     * @throws NotReachableLevelException if the level of the destination has at least 2 blocks more than the current
     * @throws InvalidDirectionException if the switch-else of {@link Board#getNearbySlot(Direction, Slot)} enters
     * the default case. It shouldn't happen.
     * @throws IndexOutOfBoundsException if the destination {@link Slot} is outside the {@link Board}
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    @Override
    public boolean move(Direction direction, Worker worker)
            throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, WrongBuildOrMoveException {
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfBuildings==0) {
            moveThenBuild = true;
            return worker.move(direction);
        }
        else if (numberOfBuildings==1){
            moveThenBuild = false;
            // if the destination slot is higher than the current slot
            if (worker.getSlot().getLevel().ordinal() < Board.getNearbySlot(direction, worker.getSlot()).getLevel().ordinal())
                throw new NotReachableLevelException();
            else
                return worker.move(direction);
        }
        else throw new WrongBuildOrMoveException();
    }
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker} in the direction chosen.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the {@link Slot} where to build is outside the {@link Board}
     * @throws SlotOccupiedException if the slot where to build is occupied by a dome or another worker
     * @throws InvalidDirectionException if the switch-else of getNearbySlot enters the default case. It shouldn't happen.
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    @Override
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();
        
        if (numberOfMovements==0 && numberOfBuildings==1) throw new WrongBuildOrMoveException();
        if (numberOfBuildings==1 && moveThenBuild)  throw new WrongBuildOrMoveException();
        
        worker.build(direction);
    }
    
    @Override
    public void resetParameters() {
        moveThenBuild = false;
    }
    
    @Override
    protected boolean checkIfCanMove(Worker worker) throws InvalidDirectionException {
        return checkIfCanMoveInNormalConditions(worker);
    }
    
    @Override
    protected boolean checkIfCanBuild(Worker worker) throws InvalidDirectionException {
        return checkIfCanBuildInNormalConditions(worker);
    }
    
    @Override
    public boolean checkIfCanGoOn(Worker worker) throws InvalidDirectionException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();
        
        if (numberOfMovements==0 && numberOfBuildings==0)
            return checkIfCanMove(worker) || checkIfCanBuild(worker);
        if (numberOfMovements==1 && numberOfBuildings==0 && moveThenBuild || numberOfMovements==1 && numberOfBuildings==1 && !moveThenBuild)
            return checkIfCanBuild(worker);
        if (numberOfMovements==0 && numberOfBuildings==1 && !moveThenBuild)
            return checkIfCanMove(worker);
        
        return false;
    }
    
    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();
        
        return numberOfMovements==1 && numberOfBuildings==1 && moveThenBuild
                || numberOfMovements==1 && numberOfBuildings==2 && !moveThenBuild
                || player.isWinning();
    }
    
    /**
     * This method return the status of Prometheus.
     * It isn't callable by the interface {@link God}, because it's a Prometheus' personal field.
     * @return true if Prometheus is obligated to do a normal turn (because he moved before having built).
     */
    public boolean moveThenBuild() {
        return moveThenBuild;
    }
}
