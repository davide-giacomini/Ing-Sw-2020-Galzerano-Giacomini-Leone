package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

public class Ping extends Message {
    private static final long serialVersionUID = 3318381794069351953L;
    
    public Ping(){
        this.messageType = MessageType.PING;
    }
    
}
