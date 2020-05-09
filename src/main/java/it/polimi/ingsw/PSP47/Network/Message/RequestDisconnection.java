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

}
