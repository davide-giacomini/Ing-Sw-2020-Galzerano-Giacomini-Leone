package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

public class OpponentLoosing extends Message{
    private static final long serialVersionUID = 9322710552312487L;
    private final String username;
    
    public OpponentLoosing (String username){
        this.username = username;
        this.messageType = MessageType.OPPONENT_WINNING;
    }
    
    public String getUsername() {
        return username;
    }
    
    @Override
    public Visitable getContent() {
        return null;
    }
}
