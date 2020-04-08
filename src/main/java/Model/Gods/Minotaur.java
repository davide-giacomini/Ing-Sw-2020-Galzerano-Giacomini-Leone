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
        canUseBothWorkers = false;
    }
    
    @Override
    public boolean move(Direction direction, Worker worker)
            throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException {

        int previousLevel = worker.getSlot().getLevel().ordinal();
        try {
            return worker.move(direction);
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
        
            // if the slot next to the opponent worker is free and the destination slot is actually occupied by an opponent worker
            if (opponentWorker!=null && opponentWorker.getColor()!=worker.getColor() && !slotNearOpponentSlot.isOccupied()) {
                // manually move player's worker in the destination slot
                opponentWorker.updatePosition(slotNearOpponentSlot);
                return worker.updatePosition(opponentSLot);
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
    
    @Override
    protected boolean checkIfCanMove(Worker worker) throws InvalidDirectionException {
        for (Direction direction : Direction.values()) {
            try {
                // If the direction is out of the board, jump to the catch
                worker.checkDirection(direction);
                Slot destinationSlot = Board.getBoard().getNearbySlot(direction, worker.getSlot());
                Slot slotNearOpponentSlot = Board.getBoard().getNearbySlot(direction, destinationSlot);
                // else, check if the worker can move to the destinationSlot
                if (!destinationSlot.isOccupied()){
                    // if the player can move up and the destinationSlot hasn't got too many levels, the player can move.
                    if (!player.cannotMoveUp() && destinationSlot.getLevel().ordinal() < worker.getSlot().getLevel().ordinal()+1)
                        return true;
                        // if the player cannot move up but the destinationSlot is equal or less high than the current slot, the player can move.
                    else if (player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal())
                        return true;
                }
                else if (slotNearOpponentSlot!=null && !slotNearOpponentSlot.isOccupied())
                    return true;
            }
            catch (IndexOutOfBoundsException e){
                // just let the for continue
            }
        }
    
        return false;
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
            return checkIfCanMove(worker);
        if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanBuild(worker);
        
        return false;
    }
    
    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();
        
        return numberOfMovements==1 && numberOfBuildings==1 || player.isWinning();
    }
}
