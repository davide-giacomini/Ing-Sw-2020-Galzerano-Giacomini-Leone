package Network.Message;

import Enumerations.MessageType;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Message implements Serializable {
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private MessageType messageType;

    public Message(MessageType messageType){
        this.messageType = messageType;
    }
    
    public void notifyListeners() {
        for (MessageListener messageListener : messageListeners) {
            messageListener.update(this);
        }
    }
    
    public void addListener(MessageListener listener){
        messageListeners.add(listener);
    }
    
    public MessageType getMessageType(){
        return messageType;
    }
}
