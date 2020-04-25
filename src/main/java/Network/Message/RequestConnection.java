package Network.Message;

import Enumerations.Color;
import Enumerations.MessageType;

public class RequestConnection extends Message {
    private static final long serialVersionUID = -8037367122696029080L;
    private String username;
    private Enumerations.Color color;
    
    public RequestConnection(MessageType messageType) {
        super(messageType);
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
    
    public void setColor(Enumerations.Color color) {
        this.color = color;
    }
}
