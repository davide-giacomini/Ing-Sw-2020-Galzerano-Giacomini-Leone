package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Slot;
import Model.Worker;

/**
 * This class is a skeleton to be implemented when gods are added.
 * When a god (as subclass) is created, every field MUST be initialized. Otherwise, the game may not work correctly.
 */
public abstract class God {
    protected int MIN_MOVEMENTS;
    protected int MIN_BUILDINGS;
    protected int MAX_MOVEMENTS;
    protected int MAX_BUILDINGS;
    protected boolean canAlwaysBuildDome;
    protected boolean canUseBothWorkers;
    protected Player player;
    protected String name;  // useless until now
    
    public God(Player player, String name) {
        this.player = player;
        this.name = name;
        player.setGod(this);
    }
    
    /**
     * @return the minimum amount of movements to be done by a god
     */
    public int getMIN_MOVEMENTS() {
        return MIN_MOVEMENTS;
    }
    /**
     * @return the minimum amount of buildings to be constructed by a god
     */
    public int getMIN_BUILDINGS() {
        return MIN_BUILDINGS;
    }
    /**
     * @return the maximum amount of movements to be done by a god
     */
    public int getMAX_MOVEMENTS() {
        return MAX_MOVEMENTS;
    }
    /**
     * @return the maximum amount of buildings to be constructed by a god
     */
    public int getMAX_BUILDINGS() {
        return MAX_BUILDINGS;
    }

    /**
     * @return if the god is allowed to build dome at any level
     */
    public boolean canAlwaysBuildDome() {
        return canAlwaysBuildDome;
    }
    
    /**
     * @return true if the god is allowed to use both the workers during a single turn.
     */
    public boolean canUseBothWorkers() {
        return canUseBothWorkers;
    }
    
    /**
     * This method moves a {@link Worker} from a {@link Slot} to another, towards the destination specified.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level, false otherwise
     * @throws SlotOccupiedException if the destination slot is occupied by a dome or another worker
     * @throws NotReachableLevelException if the level of the destination has at least 2 blocks more than the current
     * @throws InvalidDirectionException if the switch-else of getNearbySlot enters the default case. It shouldn't happen.
     * @throws IndexOutOfBoundsException if the destination {@link Slot} is outside the {@link Board}
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    public abstract boolean move(Direction direction, Worker worker)
            throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, WrongBuildOrMoveException;
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker} in the direction chosen.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the {@link Slot} where to build is outside the {@link Board}
     * @throws SlotOccupiedException if the slot where to build is occupied by a dome or another worker
     * @throws InvalidDirectionException if the switch-else of getNearbySlot enters the default case. It shouldn't happen.
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    public abstract void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException;
    
    /**
     * Reset all the additional eventual parameters of the god.
     * It has to be called inside the god and it's not necessary for every god.
     */
    public abstract void resetParameters();
    
    /**
     * @return true if it's possible to move,false otherwise.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @throws InvalidDirectionException if the default case in the choice of the direction is reached.
     */
    protected abstract boolean checkIfCanMove(Worker worker) throws InvalidDirectionException;
    
    /**
     * See {@link #checkIfCanGoOn(Worker)}
     *
     * @return true if it's possible to build, false otherwise.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     */
    protected abstract boolean checkIfCanBuild(Worker worker) throws InvalidDirectionException;
    
    /**
     * See {@link #checkIfCanGoOn(Worker)}
     *
     * This method is a way not to repeat for each god the same check
     * in case they don't modify the normal conditions.
     *
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if it's possible to move in normal conditions, false otherwise.
     * @throws InvalidDirectionException if the default case in the choice of the direction is reached.
     */
    protected boolean checkIfCanMoveInNormalConditions(Worker worker) throws InvalidDirectionException{
        for (Direction direction : Direction.values()) {
            try {
                // If the direction is out of the board, jump to the catch
                worker.checkDirection(direction);
                Slot destinationSlot = Board.getBoard().getNearbySlot(direction, worker.getSlot());
                // else, check if the worker can move to the destinationSlot
                if (!destinationSlot.isOccupied()){
                    // if the player can move up and the destinationSlot hasn't got too many levels, the player can move.
                    if (!player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal()+1)
                        return true;
                    // if the player cannot move up but the destinationSlot is equal or less high than the current slot, the player can move.
                    else if (player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal())
                        return true;
                }
            }
            catch (IndexOutOfBoundsException e){
                // just let the for continue
            }
        }
        
        return false;
    }
    
    /**
     * See {@link #checkIfCanGoOn(Worker)}
     *
     * This method is a way not to repeat for each god the same check,
     * in the case them don't modify the normal conditions.
     *
     * @return true if it's possible to build in normal conditions, false otherwise.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @throws InvalidDirectionException if the default case in the choice of the direction is reached.
     */
    protected boolean checkIfCanBuildInNormalConditions(Worker worker) throws InvalidDirectionException {
        for (Direction direction: Direction.values()){
            Slot destinationSlot = Board.getBoard().getNearbySlot(direction, worker.getSlot());
            try {
                // If the direction is out of the board, jump to the catch
                worker.checkDirection(direction);
                // else, check if the worker can build on the destinationSlot
                if (!destinationSlot.isOccupied())  return true;
            }
            catch (IndexOutOfBoundsException e) {
                // just let the for continue
            }
        }
        
        return false;
    }
    
    /**
     * This method checks, using {@link #checkIfCanBuild(Worker)}, {@link #checkIfCanMove(Worker)},
     * {@link #checkIfCanBuildInNormalConditions(Worker)} and {@link #checkIfCanMoveInNormalConditions(Worker)}, can
     * understand if the worker chosen can go on or they are eliminated.
     * @param worker the worker chosen to be checked.
     * @return true if the worker can go on, false otherwise.
     * @throws InvalidDirectionException if the default case in the choice of the direction is reached.
     */
    public abstract boolean checkIfCanGoOn (Worker worker) throws InvalidDirectionException;
    
    /**
     * This method control if the player can end his turn. If the player is winning, it returns true.
     * @return true if the player can end his turn.
     */
    public abstract boolean validateEndTurn();

    /**
     * @return the name of the god.
     */
    public String getName() {
        return name;
    }
}
