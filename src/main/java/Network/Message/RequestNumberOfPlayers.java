package Network.Message;

import Enumerations.MessageType;

public class RequestNumberOfPlayers extends Message {
    private static final long serialVersionUID = -2808360409198774148L;
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
