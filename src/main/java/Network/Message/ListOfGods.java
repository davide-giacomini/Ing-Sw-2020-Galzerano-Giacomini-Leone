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
 * This message contains two elements as it is sent both from server to client and from client to server.
 * When it is S->C it's setted the ArrayList of godsAvailable, with all of the god chosen by the Challenger.
 * When it is C->S it's setted the GodName chosenGod with the kind of god chosen by a player.
 */
public class ListOfGods extends Message {
    private static final long serialVersionUID = 5244974574544564271L;
    private ArrayList<GodName> godsAvailable;
    private GodName chosenGod;

    public ListOfGods(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * This method calls the view to ask for the god that the player has been chosed and send a message
     * to the server with this information.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        GodName chosenGod = client.getView().chooseYourGod(godsAvailable);
        ListOfGods newMessage = new ListOfGods(MessageType.LIST_OF_GODS);
        newMessage.setChosenGod(chosenGod);
        try {
            outputServer.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " +this.toString()+" message.");
            e.printStackTrace();
        }
    }
    
    /**
     * This method receives a list of available gods and calls the view to set this into the model.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
     */
    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
        if (godsAvailable != null) {
            virtualView.receiveListOfGods(godsAvailable);
        }
        else if (chosenGod != null) {
            try {
                virtualView.receiveChosenGod(chosenGod);
            } catch (IOException e) {
                //TODO fare qualcosa in caso viene lanciata questa eccezione
                // chiedere a Monica
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<GodName> getGodsAvailable() {
        return new ArrayList<>(godsAvailable);
    }

    public void setGodsAvailable(ArrayList<GodName> gods) {
        this.godsAvailable = gods;
    }

    public GodName getChosenGod() {
        return chosenGod;
    }

    public void setChosenGod(GodName chosenGod) {
        this.chosenGod = chosenGod;
    }
}
