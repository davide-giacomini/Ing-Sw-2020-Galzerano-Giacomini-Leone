package it.polimi.ingsw.PSP47.Enumerations;

/**
 * Enumeration for the type of available actions.
 * **/
public enum Action {
    MOVE,
    BUILD,
    BUILDDOME,
    END,
    WRONGACTION;

    /**
     * method used to convert strings into enum
     * @param name is the name as a string of the color/item
     * @return enum of the Action
     */
    public static Action getActionByName(String name)  {


        switch (name.toUpperCase()) {
            case "MOVE" :
                return MOVE;
            case "BUILD" :
                return BUILD;
            case "BUILDDOME" :
                return BUILDDOME ;
            case  "END" :
                return END ;
            default :
                return WRONGACTION;

        }
    }
}
