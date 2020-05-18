package it.polimi.ingsw.PSP47.Enumerations;

import it.polimi.ingsw.PSP47.Model.Slot;

/**
 * Enumeration which represents the possible direction that can be chosen by a player.
 */
public enum Direction {

    LEFT (0),
    LEFTUP (1),
    UP (2),
    RIGHTUP (3),
    RIGHT (4),
    RIGHTDOWN (5),
    DOWN (6),
    LEFTDOWN (7),
    HERE(8), //only available for zeus
    WRONGDIRECTION(9);

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
             case  "HERE" :
                return HERE ;
            default :
                return WRONGDIRECTION;

        }
    }

    public static Direction getDirectionGivenSlots( int originRow, int originColumn ,int destinationRow, int destinationColumn ){

        if (originRow-destinationRow == 0 && originColumn - destinationColumn == 0)
            return HERE;
        else if (originRow-destinationRow == 1 && originColumn - destinationColumn == 1)
            return LEFTUP;
        else if (originRow-destinationRow == 0 && originColumn - destinationColumn == 1)
            return LEFT;
        else if (originRow-destinationRow == 1 && originColumn - destinationColumn == -1)
            return RIGHTUP;
        else if (originRow-destinationRow == 0 && originColumn - destinationColumn == -1)
            return RIGHT;
        else if (originRow-destinationRow == -1 && originColumn - destinationColumn == 1)
            return LEFTDOWN;
        else if (originRow-destinationRow == -1 && originColumn - destinationColumn == 0)
            return DOWN;
        else if (originRow-destinationRow == -1 && originColumn - destinationColumn == -1)
            return RIGHTDOWN;
        else
            return WRONGDIRECTION;
    }

}
