package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;

/**
 * This message contains an error of connection.
 */
public class ConnectionFailed extends Message{
    private static final long serialVersionUID = 2482945733715396646L;
    private final String errorMessage;
    
    public ConnectionFailed(String errorMessage){
        this.errorMessage = errorMessage;
        this.messageType = MessageType.CONNECTION_FAILED;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
