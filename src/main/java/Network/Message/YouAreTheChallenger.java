package Network.Message;

import Enumerations.GodName;
import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This message is sent only to the player who has been chosen to be the Challenger to inform him.
 */
public class YouAreTheChallenger extends Message{
    private static final long serialVersionUID = -2204871958919107480L;

    public YouAreTheChallenger(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * This methods calls the view to ask for the gods that must be used in the game and send a message
     * to the server with this information.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        ArrayList<GodName> gods = client.getView().challengerWillChooseThreeGods();
        ListOfGods message = new ListOfGods(MessageType.LIST_OF_GODS);
        message.setGodsAvailable(gods);
        try {
            outputServer.writeObject(message);
        } catch (IOException e) {
            client.getView().print("Error in the serialization of " +this.toString()+" message.");
            //e.printStackTrace();
        }
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
