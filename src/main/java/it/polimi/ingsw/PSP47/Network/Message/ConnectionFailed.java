package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.ObjectOutputStream;

//TODO THIS MESSAGE CAN BE DELETED AND YOU CAN DO THIS IN ERROR MESSAGE
public class ConnectionFailed extends Message {
    String errorMessage;
    
    public ConnectionFailed(String errorMessage) {
        this.errorMessage=errorMessage;
        this.messageType=MessageType.ERROR;
    }
    
    /*
     * This method tells the client what went wrong in the connection.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer){
        client.getView().showMessage(errorMessage);
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

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public Visitable getContent() {
        return null;
    }
}
