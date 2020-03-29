package Enumerations;

public enum Level {
    
    GROUND (0),
    LEVEL1 (1),
    LEVEL2 (2),
    LEVEL3 (3),
    DOME (4);
    
    private final int level;
    Level(int level) {
        this.level = level;
    }
    
}
