package Enumerations;

public enum Direction {

    LEFT (0),
    LEFTUP (1),
    UP (2),
    RIGHTUP (3),
    RIGHT (4),
    RIGHTDOWN (5),
    DOWN (6),
    LEFTDOWN (7),
    WRONGDIRECTION(8);

    private final int move;
    Direction(int move) {
        this.move = move;
    }

    /**
     * method used to convert strings into enum
     * @param name is the name as a string of the color/item
     * @return enum of the Direction
     */
    public static Direction getDirectionByName(String name)  {


        switch (name.toUpperCase()) {
            case "LEFT" :
                return LEFT;
            case "LEFTUP" :
                return LEFTUP;
            case "UP" :
                return UP ;
            case  "RIGHTUP" :
                return RIGHTUP ;
             case  "RIGHT" :
                return RIGHT ;
             case  "RIGHTDOWN" :
                return RIGHTDOWN ;
             case  "DOWN" :
                return DOWN ;
             case  "LEFTDOWN" :
                return LEFTDOWN ;
            default :
                return WRONGDIRECTION;

        }
    }

}
