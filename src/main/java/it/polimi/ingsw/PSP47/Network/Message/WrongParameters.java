package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

public class WrongParameters extends Message {
    private static final long serialVersionUID = -6181235425571580535L;
    private final String errorMessage;
    
    public WrongParameters(String errorMessage){
        this.errorMessage = errorMessage;
        this.messageType = MessageType.WRONG_PARAMETERS;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    @Override
    public Visitable getContent() {
        return null;
    }
}
