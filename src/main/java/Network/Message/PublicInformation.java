package Network.Message;

import Enumerations.Color;
import Enumerations.GodName;
import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This message contains all the informations about the players of the game: their usernames, their colors and the gods they had chosen.
 * It is a S->C message.
 */
public class PublicInformation extends Message{

    private static final long serialVersionUID = 2261428338933766010L;
    private ArrayList<String> usernames;
    private ArrayList<Color> colors;
    private ArrayList<GodName> godNames;


    public PublicInformation(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * This method calls the view to set all the informations about the players into the ViewDatabase and to
     * print this in the screen.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().getViewDatabase().setUsernames(usernames);
        client.getView().getViewDatabase().setColors(colors);
        client.getView().getViewDatabase().setGods(godNames);
    
        client.getView().showPublicInformation();
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
    
    public ArrayList<String> getUsernames() {
        return new ArrayList<>(usernames);
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public ArrayList<Color> getColors() {
        return new ArrayList<>(colors);
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public ArrayList<GodName> getGodNames() {
        return new ArrayList<>(godNames);
    }

    public void setGodNames(ArrayList<GodName> godNames) {
        this.godNames = godNames;
    }
}
