package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.Serializable;

public class FirstPlayerConnection extends Message implements Serializable {
    private static final long serialVersionUID = 7416497312772957279L;
    private final String username;
    private final Color color;
    private final Integer numberPlayers;
    
    public FirstPlayerConnection(String username, Integer numberPlayers, Color color){
        this.username = username;
        this.color = color;
        this.numberPlayers = numberPlayers;
        messageType = MessageType.FIRST_PLAYER_CONNECTION;
    }
    
    public MessageType getMessageType() {
        return messageType;
    }


    public String getUsername() {
        return username;
    }
    
    public Color getColor() {
        return color;
    }
    
    public int getNumberPlayers() {
        return numberPlayers;
    }

    @Override
    public Visitable getContent() {
        return null;
    }
}
