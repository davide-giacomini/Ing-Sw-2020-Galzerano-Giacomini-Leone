package Network.Message;

import Enumerations.Color;
import Enumerations.MessageType;

public class ConnectionAccepted extends Message{
    private String userName;
    private Color color;
    private int numberOfPlayers;
    
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
