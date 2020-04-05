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
 *
 */
public abstract class God {
    protected int MIN_MOVEMENTS;
    protected int MIN_BUILDINGS;
    protected int MAX_MOVEMENTS;
    protected int MAX_BUILDINGS;
    protected boolean canBuildDome;
    protected boolean canUseMoreWorkers;
    protected Player player;
    protected String name;  // useless until now
    
    public God(Player player, String name) {
        this.player = player;
        this.name = getClass().toString();
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
    public boolean canBuildDome() {
        return canBuildDome; }

    public boolean canUseMoreWorkers() {
        return canUseMoreWorkers;
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
    
}
