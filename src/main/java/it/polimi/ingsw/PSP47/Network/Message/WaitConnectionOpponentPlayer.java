package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;

public class WaitConnectionOpponentPlayer extends Message {
    private static final long serialVersionUID = -7646087950588922689L;
    
    public WaitConnectionOpponentPlayer(){
        messageType = MessageType.WAIT_CONNECTION_OPPONENT_PLAYER;
    }
}
