package Network.Message;

import Enumerations.MessageType;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private MessageType messageType;

    public Message(MessageType messageType){
        this.messageType = messageType;
    }
    
    public MessageType getMessageType(){
        return messageType;
    }
}
