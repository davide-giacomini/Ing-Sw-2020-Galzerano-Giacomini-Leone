package Network.Message;

import Enumerations.Color;
import Enumerations.MessageType;

public class ConnectionAccepted extends Message{
    private static final long serialVersionUID = -7614194884802773262L;
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
