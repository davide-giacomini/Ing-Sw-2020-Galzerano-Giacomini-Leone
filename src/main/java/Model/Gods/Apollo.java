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
        canAlwaysBuildDome = false;
        canUseBothWorkers = false;
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
                Slot opponentWorkerSlot = opponentWorker.getSlot();
                opponentWorker.setSlot(previousSlot);
                worker.setSlot(opponentWorkerSlot);
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
    
    @Override
    protected boolean checkIfCanMove(Worker worker) throws InvalidDirectionException {
        for (Direction direction : Direction.values()) {
            try {

                worker.checkDirection(direction);
                Slot destinationSlot = Board.getNearbySlot(direction, worker.getSlot());

                // else, check if the worker can move to the destinationSlot
                //if it is occupied do the special check
                if (destinationSlot!=null && destinationSlot.isOccupied() ){
                    //Since there has to be a switch between workers following the rules, I have to consider that
                    //in both movements the difference of levels has to be max 1
                    if (!player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal()+1)
                        return true;
                        // if the player cannot move up but the destinationSlot has the same level, the player can move.
                    else if (player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal()) {
                        return true;
                    }
                }
                else //if the dest slot is free do the normal check
                    return checkIfCanMoveInNormalConditions(worker);
            }
            catch (IndexOutOfBoundsException e){
                // just let the "for" continue
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
