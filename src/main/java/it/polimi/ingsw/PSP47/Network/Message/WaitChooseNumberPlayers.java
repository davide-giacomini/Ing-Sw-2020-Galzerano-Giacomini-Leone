package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.ObjectOutputStream;

public class WaitChooseNumberPlayers extends Message {
    private static final long serialVersionUID = -7646087950588922689L;

    public WaitChooseNumberPlayers() {
        this.messageType=MessageType.WAIT_CHOOSE_NUMBER_PLAYERS;
    }

    /*
     * This methods is sent when the first player is choosing the number of players and the client has to wait
     * to know if they can be added to the list of players.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().showMessage("Please wait. The first player is choosing the number of players right now.");
        //TODO Arianna: se ti serve cambiare questo metodo cambialo pure.
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

    @Override
    public Visitable getContent() {
        return null;
    }
}
