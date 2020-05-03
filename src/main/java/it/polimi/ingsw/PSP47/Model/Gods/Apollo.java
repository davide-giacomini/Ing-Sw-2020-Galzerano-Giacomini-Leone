package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

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

    /**
     * This method allows a movement not only if the chosen slot is free but also if in
     * the chosen slot there is an enemy worker, switching the two workers
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the winning condition has been verified, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidDirectionException if there are some troubles of I/O.
     * @throws InvalidMoveException if the move is invalid.
     */
    @Override
    public boolean move(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidDirectionException, InvalidMoveException {

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
                opponentWorkerSlot.setWorker(null);
                worker.setSlot(opponentWorkerSlot);
                previousSlot.setWorker(null);
                opponentWorker.setSlot(previousSlot);
                int nextLevel = worker.getSlot().getLevel().ordinal();
                return nextLevel-previousLevel>0 && worker.getSlot().getLevel()==Level.LEVEL3;
            }
            // if there is a dome or a player's worker, the slot is occupied for Apollo too
            else
                throw new InvalidMoveException("Slot occupied");
        }
    }

    /**
     * This method calls the standard build of a worker:
     * Apollo doesn't modify the building rules.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidBuildException if building is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidBuildException {
        
        if (player.getTurn().getNumberOfMovements() == 0)
            throw new InvalidBuildException("Order of movements incorrect");
    
        try {
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }
    }

    /**
     * It does nothing.
     */
    @Override
    public void resetParameters() {
        // nothing is necessary
    }

    /**
     * This methods does what checkIfCanMoveInNormalCondition does together with another verification,
     * it checks the availability of a slot by checking if it's free or if there is an enemy worker on it
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can move, false otherwise
     */
    @Override
    public boolean checkIfCanMove(Worker worker) {
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
            } catch (InvalidDirectionException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can build, false otherwise.
     */
    @Override
    public boolean checkIfCanBuild(Worker worker) {
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

        if (numberOfMovements==0 && numberOfBuildings==0)
            return checkIfCanMove(worker);
        if (numberOfMovements==1 && numberOfBuildings==0)
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

        return numberOfMovements==1 && numberOfBuildings==1 || player.isWinning();
    }
    
}
