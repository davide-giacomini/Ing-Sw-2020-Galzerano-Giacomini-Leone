package Network.Message;

import Enumerations.Color;
import Enumerations.MessageType;

public class ConnectionAccepted extends Message{
    private String userName;
    private Color color;
    
    public ConnectionAccepted(MessageType messageType) {
        super(messageType);
    }
    
    public String getUserName() {
        return userName;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
}
