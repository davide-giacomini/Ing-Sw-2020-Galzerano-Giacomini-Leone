package Network.Message;

import Enumerations.MessageType;

public class RequestNumberOfPlayers extends Message {
    private int numberOfPlayers;
    
    public RequestNumberOfPlayers(MessageType messageType) {
        super(messageType);
    }
    
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
