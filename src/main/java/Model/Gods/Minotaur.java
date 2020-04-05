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
 * {@link Player}'s {@link Worker} may move into an opponent worker's {@link Slot} (using normal movements rules), if
 * the next slot in the same direction is unoccupied. Their worker is forced into that slot (regardless of it's level).
 */
public class Minotaur extends God {
    
    public Minotaur(Player player, String name) {
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
            throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException {

        int previousLevel = worker.getSlot().getLevel().ordinal();
        try {
            worker.move(direction);
            int nextLevel = worker.getSlot().getLevel().ordinal();
            return nextLevel-previousLevel>0 && worker.getSlot().getLevel()==Level.LEVEL3;
        } catch (SlotOccupiedException e) {
            Slot opponentSLot = Board.getNearbySlot(direction, worker.getSlot());
            // the slot in the same direction of the worker. If there is not a slot, the move is not available.
            Slot slotNearOpponentSlot;
            try {
                slotNearOpponentSlot = Board.getNearbySlot(direction, opponentSLot);
            } catch (IndexOutOfBoundsException er){
                // this exception advises the caller that the slot is occupied and the opponent worker cannot move.
                throw new SlotOccupiedException();
            }
            // the worker set in the destination slot
            Worker opponentWorker = opponentSLot.getWorker();
            Slot previousSlot = worker.getSlot();
        
            // if the slot next to the opponent worker is free and the destination slot is actually occupied by an opponent worker
            if (opponentWorker!=null && opponentWorker.getColor()!=worker.getColor() && !slotNearOpponentSlot.isOccupied()) {
                // manually move player's worker in the destination slot
                opponentWorker.updatePosition(slotNearOpponentSlot);
                worker.updatePosition(opponentSLot);
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
        
        if (player.getTurn().getNumberOfMovements() == 0) throw new WrongBuildOrMoveException();
        
        worker.build(direction);
    }
    
    @Override
    public void resetParameters() {
        // nothing is necessary
    }
}
