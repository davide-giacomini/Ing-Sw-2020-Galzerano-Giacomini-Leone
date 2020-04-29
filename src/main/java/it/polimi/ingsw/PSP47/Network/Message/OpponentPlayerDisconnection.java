package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.io.ObjectOutputStream;

/**
 * This message has the username of the player who disconnected.
 * It's used to advise the other players.
 */
public class OpponentPlayerDisconnection extends Message{
    private static final long serialVersionUID = -6561561295860715699L;
    private String username;
    
    public OpponentPlayerDisconnection(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * This client is told that an opponent player disconnected.
     * The game ends.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().showMessage("We are sorry: "+username+" has just disconnected.\nThe game is going to end.");
    }
    
    /**
     * @deprecated
     * This method doesn't do anything for now.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
     */
    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
    
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
