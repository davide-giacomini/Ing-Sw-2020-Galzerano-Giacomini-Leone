package Network.Message;

import Enumerations.MessageType;

/**
 * This message is sent only to the player who has been chosen to be the Challenger to inform him.
 */
public class YouAreTheRandomPlayer extends Message{

    private static final long serialVersionUID = -2204871958919107480L;

    public YouAreTheRandomPlayer(MessageType messageType) {
        super(messageType);
    }
}
