package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Enumerations.Level;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Slot;
import Model.Worker;

/**
 * If a {@link Player} has Apollo, their {@link Worker} can move into an opponent worker's space (using normal movements
 * rules) and swap their position.
 */
public class Apollo extends God {
    
    public Apollo(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canBuildDome = false;
        canUseMoreWorkers = false;
    }
    

    @Override
    public boolean move(Direction direction, Worker worker)
            throws NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, SlotOccupiedException {

        int previousLevel = worker.getSlot().getLevel().ordinal();
        try {
            return worker.move(direction);
        } catch (SlotOccupiedException e) {
            // the worker set in the destination slot
            Worker opponentWorker = Board.getNearbySlot(direction, worker.getSlot()).getWorker();
            Slot previousSlot = worker.getSlot();
            
            // if there is actually an opponent worker on the destination slot
            if (opponentWorker!=null && opponentWorker.getColor()!=worker.getColor()) {
                // manually move player's worker in the destination slot
                opponentWorker.getSlot().setWorker(worker);
                previousSlot.setWorker(opponentWorker);
                int nextLevel = worker.getSlot().getLevel().ordinal();
                return nextLevel-previousLevel>0 && worker.getSlot().getLevel()==Level.LEVEL3;
            }
            // if there is a dome or a player's worker, the slot is occupied for Apollo too
            else
                throw new SlotOccupiedException();
        }
    }
    
    @Override
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        
        if (player.getTurn().getNumberOfMovements() == 0)  throw new WrongBuildOrMoveException();
        
        worker.build(direction);
    }
    
    @Override
    public void resetParameters() {
        // nothing is necessary
    }
    
}
