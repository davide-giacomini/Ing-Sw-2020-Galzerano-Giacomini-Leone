package it.polimi.ingsw.PSP47.Enumerations;

import java.io.Serializable;

/**
 * Enumeration which represent the available colors.
 */
public enum Color implements Serializable {

    RED(0),
    GREEN(1),
    YELLOW(2),
    BLUE(3),
    PURPLE(4),
    CYAN(5),
    WHITE(6),
    WRONGCOLOR(7);

    private final int color;
    Color(int color) {
        this.color=color;
    }


    /**
     * method used to convert strings into enum
     * @param name is the name as a string of the color/item
     * @return enum of the color
     */
    public static Color getColorByName(String name)  {


        switch (name.toUpperCase()) {
            case "RED" :
                return RED;
            case "GREEN" :
                return GREEN;
            case "YELLOW" :
                return YELLOW ;
            case  "BLUE" :
                return BLUE ;
            case "PURPLE" :
                return PURPLE;
            case "CYAN" :
                return CYAN ;
            case "WHITE" :
                return WHITE ;
            default :
                return WRONGCOLOR;

        }
    }
}
