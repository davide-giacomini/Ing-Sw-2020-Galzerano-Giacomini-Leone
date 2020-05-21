package it.polimi.ingsw.PSP47.Enumerations;

import java.io.Serializable;

/**
 * Enumeration which represents the possible levels that can be built during the game.
 */
public enum Level implements Serializable {
    
    GROUND (0),
    LEVEL1 (1),
    LEVEL2 (2),
    LEVEL3 (3),
    DOME (4),
    ATLAS_DOME (0);
    
    private final int level;
    Level(int level) {
        this.level = level;
    }
    
}
