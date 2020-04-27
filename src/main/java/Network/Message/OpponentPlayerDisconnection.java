package Network.Message;

import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

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
        client.getView().print("We are sorry: "+username+" has just disconnected.\nThe game is going to end.");
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
