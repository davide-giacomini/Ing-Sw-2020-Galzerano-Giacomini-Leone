package Network.Message;

import Enumerations.MessageType;
import Network.Message.Message;

public class RequestNumberOfPlayers extends Message {
    private int numberOfPlayers;
    
    public RequestNumberOfPlayers() {
        super(MessageType.REQUEST_NUMBER_OF_PLAYERS);
    }
    
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
