package Network.Message;

import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

/**
 * This message contains the numberOfPlayers of the game.
 * It is a S->C message.
 */
public class NumberOfPlayers extends Message {
    private static final long serialVersionUID = 7757520416341164027L;
    private int numberOfPlayers;

    public NumberOfPlayers(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * This method calls the view to set the number of players into the viewDatabase.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().getViewDatabase().setNumberOfPlayers(numberOfPlayers);
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
    
    public int getNumberOfPlayers() {
        return numberOfPlayers;
        //TODO rendere thread safe
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        //TODO rendere thread safe
    }
}
