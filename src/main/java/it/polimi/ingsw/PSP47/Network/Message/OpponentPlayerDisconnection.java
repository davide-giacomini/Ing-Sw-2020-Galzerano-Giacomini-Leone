package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.ObjectOutputStream;

//TODO THIS MESSAGE CAN BE DELETED AND YOU CAN DO THIS IN ERROR MESSAGE
/**
 * This message has the username of the player who disconnected.
 * It's used to advise the other players.
 */
public class OpponentPlayerDisconnection extends Message{

    private static final long serialVersionUID = -6561561295860715699L;

    private String username;
    
    public OpponentPlayerDisconnection(String username) {
        this.username=username;
        this.messageType=MessageType.OPPONENT_PLAYER_DISCONNECTION;
    }

}
