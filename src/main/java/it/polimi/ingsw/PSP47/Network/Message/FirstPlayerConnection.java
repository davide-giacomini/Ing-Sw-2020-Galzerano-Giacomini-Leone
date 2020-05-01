package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;

import java.io.Serializable;

public class FirstPlayerConnection extends Message implements Serializable {
    private static final long serialVersionUID = 7416497312772957279L;
    private final MessageType messageType = MessageType.FIRST_PLAYER_CONNECTION;
    private final String username;
    private final Color color;
    private final Integer playersNumber;
    
    public FirstPlayerConnection(String username, Integer playersNumber, Color color){
        this.username = username;
        this.color = color;
        this.playersNumber = playersNumber;
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
    
    public int getPlayersNumber() {
        return playersNumber;
    }
}
