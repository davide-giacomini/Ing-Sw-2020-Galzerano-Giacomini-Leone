package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;

/**
 * This message is sent only to the player who has been chosen to be the Challenger to inform him.
 */
public class YouAreTheChallenger extends Message{

    private static final long serialVersionUID = -2204871958919107480L;

    public YouAreTheChallenger() {
    this.messageType=MessageType.CHALLENGER;
    }

}
