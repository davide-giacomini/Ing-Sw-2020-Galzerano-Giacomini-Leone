package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

public class OpponentWinning extends Message{
    private static final long serialVersionUID = 2521590617642271626L;
    private final String username;
    
    public OpponentWinning(String username){
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
