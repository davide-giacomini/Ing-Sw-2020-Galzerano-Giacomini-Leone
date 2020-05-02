package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.ObjectOutputStream;

/**
 * This message is sent to the server when a client wants to disconnect.
 */
public class RequestDisconnection extends Message {
    private static final long serialVersionUID = 8209837022517336261L;

    public RequestDisconnection() {
        this.messageType=MessageType.REQUEST_DISCONNECTION;
    }

    @Override
    public Visitable getContent() {
        return null;
    }
    

    /*
     * @deprecated
     * This method is special, is handled inside the {@link it.polimi.ingsw.PSP47.Network.Client.NetworkHandler}. It has not to be used.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {}
    
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
