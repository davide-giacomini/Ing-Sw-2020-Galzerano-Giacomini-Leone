package Network.Message;

import Enumerations.MessageType;

/**
 * This message contains the numberOfPlayers of the game.
 * It is a S->C message.
 */
public class NumberOfPlayers extends Message {
    private static final long serialVersionUID = 7757520416341164027L;
    private int numberOfPlayers;

    public NumberOfPlayers(MessageType messageType) {
        super(messageType);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
