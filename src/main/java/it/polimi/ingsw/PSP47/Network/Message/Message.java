package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = -609055340671121579L;
    private MessageType messageType;
    
    public MessageType getMessageType(){
        return messageType;
    }
    
    @Override
    public String toString(){
        return messageType.toString();
    }
}
