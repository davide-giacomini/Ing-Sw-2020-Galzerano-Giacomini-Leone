package Model;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidActionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;

public abstract class God {
    final int MIN_MOVEMENTS;
    final int MIN_BUILDINGS;
    final int MAX_MOVEMENTS;
    final int MAX_BUILDINGS;
    protected Player player;
    protected String name;
    
    public God(Player player, God god, int minMovements, int minBuildings, int maxMovements, int maxBuildings) {
        this.player = player;
        this.name = god.getClass().toString();
        MIN_MOVEMENTS = minMovements;
        MIN_BUILDINGS = minBuildings;
        MAX_MOVEMENTS = maxMovements;
        MAX_BUILDINGS = maxBuildings;
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
    
    public abstract boolean move(Direction direction, Worker worker) throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidActionException;
    public abstract boolean build(Direction direction);
    
}
