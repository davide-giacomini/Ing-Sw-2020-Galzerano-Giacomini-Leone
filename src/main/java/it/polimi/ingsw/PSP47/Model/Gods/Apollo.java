package it.polimi.ingsw.PSP47.Model.Gods;

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
        threePlayersGame = true;
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
        Worker opponentWorker = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot()).getWorker();
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
        else if (opponentWorker != null && opponentWorker.getColor() == worker.getColor()) {
            throw new InvalidMoveException("You cannot switch with yourself, you must choose another player's worker.");
        }
        else
            return worker.move(direction);
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
                Slot destinationSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());

                // else, check if the worker can move to the destinationSlot
                //if it is occupied do the special check
                if (destinationSlot!=null && destinationSlot.isWorkerOnSlot() && !destinationSlot.getWorker().getColor().equals(worker.getColor()) ){
                    //Since there has to be a switch between workers following the rules, I have to consider that
                    //in both movements the difference of levels has to be max 1
                    if (!player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal()+1)
                        return true;
                        // if the player cannot move up but the destinationSlot has the same level, the player can move.
                    else if (player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal()) {
                        return true;
                    }
                }
                else { //if the dest slot is free do the normal check
                    boolean result = checkIfCanMoveInNormalConditions(worker);
                    if (result)
                        return true;
                }
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
     * This method checks if the slot is occupied by a worker.
     * It always return false as even if there is a worker on the slot, Apollo
     * can switch with him, so there is no need to check.
     * @param slot the slot that has to be checked
     * @return always false.
     */
    @Override
    public boolean checkIfAWorkerIsOnSlot(Slot slot) {
        return false;
    }
    
}
