package Model.Stubs;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Gods.God;
import Model.Player;
import Model.Worker;

/**
 * @deprecated
 * This class is a stub. It's only used for testing.
 */
public class TestingGod extends God {
    
    public TestingGod(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canBuildDome = false;
        canUseBothWorkers = false;
    }
    
    @Override
    public boolean move(Direction direction, Worker worker) throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, WrongBuildOrMoveException {
        return worker.move(direction);
    }
    
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        worker.build(direction);
    }
    
    @Override
    public void resetParameters() {
    
    }
    
    
    public void setCanUseBothWorkers (boolean canUseBothWorkers) {
        this.canUseBothWorkers = canUseBothWorkers;
    }
}
