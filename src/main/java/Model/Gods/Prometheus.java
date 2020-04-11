package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
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
}
