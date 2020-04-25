package Network.Message;

import Enumerations.MessageType;

public class YouAreTheRandomPlayer extends Message{

    private static final long serialVersionUID = -2204871958919107480L;

    public YouAreTheRandomPlayer(MessageType messageType) {
        super(messageType);
    }
}
