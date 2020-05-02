package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

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

    public PublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames) {
        this.usernames = usernames;
        this.colors = colors;
        this.godNames = godNames;
        this.messageType=MessageType.PUBLIC_INFORMATION;
    }


    /*
     * This method calls the view to set all the informations about the players into the ViewDatabase and to
     * print this in the screen.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().getGameView().setUsernames(usernames);
        client.getView().getGameView().setColors(colors);
        client.getView().getGameView().setGods(godNames);
    
        client.getView().showPublicInformation();
    }
    
    /**
     * @deprecated
     * This method doesn't do anything for now.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {}
    */
    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public void setGodNames(ArrayList<GodName> godNames) {
        this.godNames = godNames;
    }

    @Override
    public Visitable getContent() {
        return null;
    }
}
