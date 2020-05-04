package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

/**
 * This message contains the numberOfPlayers of the game.
 * It is a S->C message.
 */
public class PlayersNumber extends Message {
    private static final long serialVersionUID = 7757520416341164027L;
    private int numberOfPlayers;

    public PlayersNumber(int numberOfPlayers) {
        this.numberOfPlayers=numberOfPlayers;
        this.messageType=MessageType.PLAYERS_NUMBER;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public Visitable getContent() {
        return null;
    }
}
