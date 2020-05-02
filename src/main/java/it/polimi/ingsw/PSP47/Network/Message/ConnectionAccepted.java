package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;

import java.io.ObjectOutputStream;

public class ConnectionAccepted extends Message{
    private static final long serialVersionUID = -7614194884802773262L;

    private VisitableInformation usernameAndColor;

    public ConnectionAccepted(VisitableInformation usernameAndColor) {
        this.usernameAndColor=usernameAndColor;
        this.messageType=MessageType.CONNECTION_ACCEPTED;
    }

    @Override
    public Visitable getContent() {
        return usernameAndColor;
    }
    

    /*
     * Client's username and color are set.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().getGameView().setMyUsername(username);
        client.getView().getGameView().setMyColor(color);
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
}
