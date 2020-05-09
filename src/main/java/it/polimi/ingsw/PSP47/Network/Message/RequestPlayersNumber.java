package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInt;

/**
 * This message contains the number of players of the game.
 */
public class RequestPlayersNumber extends VisitableMessage {
    private static final long serialVersionUID = -2808360409198774148L;
    private final VisitableInt numberOfPlayers;

    public RequestPlayersNumber(VisitableInt numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        this.messageType = MessageType.REQUEST_PLAYERS_NUMBER;
    }
    
    @Override
    public Visitable getContent() {
        return numberOfPlayers;
    }
}
