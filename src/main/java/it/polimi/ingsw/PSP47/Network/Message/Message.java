package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = -609055340671121579L;
    private final MessageType messageType;

    public Message(MessageType messageType){
        this.messageType = messageType;
    }
    
    public MessageType getMessageType(){
        return messageType;
    }


    /**
     * This method handles what to do if the message is sent client-side.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    public abstract void handleClientSide(Client client, ObjectOutputStream outputServer);
    
    /**
     * This method handles what to do if the message is sent server-side.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
     */
    public abstract void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient);
    
    @Override
    public String toString(){
        return messageType.toString();
    }
}
