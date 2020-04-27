package Network.Message;

import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

public class WaitChooseNumberPlayers extends Message {
    private static final long serialVersionUID = -7646087950588922689L;
    
    public WaitChooseNumberPlayers(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * This methods is sent when the first player is choosing the number of players and the client has to wait
     * to know if they can be added to the list of players.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().print("Please wait. The first player is choosing the number of players right now.");
        //TODO Arianna: se ti serve cambiare questo metodo cambialo pure.
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
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {}
}
