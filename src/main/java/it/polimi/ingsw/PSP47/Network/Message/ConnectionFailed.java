package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

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

    @Override
    public Visitable getContent() {
        return null;
    }
}
