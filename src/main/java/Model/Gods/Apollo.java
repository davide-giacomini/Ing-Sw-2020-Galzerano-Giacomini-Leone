package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Exceptions.InvalidActionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.God;
import Model.Player;
import Model.Worker;

public class Apollo extends God {
    
    public Apollo(Player player, God god, int minMovements, int minBuildings, int maxMovements, int maxBuildings) {
        super(player, god, minMovements, minBuildings, maxMovements, maxBuildings);
    }
    
    @Override
    public boolean move(Direction direction, Worker worker) throws NotReachableLevelException, IndexOutOfBoundsException, InvalidActionException {
        try {
            worker.move(direction);
        } catch (SlotOccupiedException e) {
            // the worker set in the destination slot
            Worker opponentWorker = Board.getNearbySlot(direction, worker.getSlot()).getWorker();
            
            // if the opponentWorker is null or there is the other worker of the current player, jump in the else case
            if (opponentWorker!=null && opponentWorker.getPlayer()!=worker.getPlayer()) {
                // manually move player's worker in the destination slot
                opponentWorker.getSlot().setWorker(worker);
                
            }
        }
    
        return false;
    }
    
    @Override
    public boolean build(Direction direction) {
        return false;
    }
    
}
