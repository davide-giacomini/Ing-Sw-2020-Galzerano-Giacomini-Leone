package it.polimi.ingsw.PSP47.Enumerations;

/**
 * Enumeration for the type of available actions.
 * **/
public enum Action {
    MOVE,
    BUILD,
    BUILDDOME,
    END,
    QUIT,
    //added actions //TODO distingush between actions that have to be sent to the server and the ones that indicate the moment in the game for the client
    CHOOSEACT,
    ASK_INITIAL_POSITION,
    ASK_WHICH_WORKER,
    WAIT,
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
            case "QUIT" :
                return QUIT ;
            default :
                return WRONGACTION;

        }
    }
}
