package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;

import java.io.Serializable;

public class FirstConnection extends Message implements Serializable {
    private static final long serialVersionUID = 7416497312772957279L;
    private final String username;
    private final Color color;
    
    public FirstConnection(String username, Color color) {
        this.username = username;
        this.color = color;
        messageType = MessageType.FIRST_CONNECTION;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Color getColor() {
        return color;
    }
}
