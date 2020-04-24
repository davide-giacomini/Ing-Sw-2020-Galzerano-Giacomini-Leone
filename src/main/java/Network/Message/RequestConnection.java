package Network.Message;

import Enumerations.MessageType;

import java.awt.*;

public class RequestConnection extends Message {
    private String username;
    private Color color;

    public RequestConnection(){
        super(MessageType.REQUEST_CONNECTION);
    }

    public String getUsername(){
        return username;
    }

    public Color getColor(){
        return color;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
}
