package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;

public class RequestPlayersNumber extends Message {
    private static final long serialVersionUID = -2808360409198774148L;
    private final Integer numberOfPlayers;

    public RequestPlayersNumber(Integer numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        this.messageType = MessageType.REQUEST_PLAYERS_NUMBER;
    }
    
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
