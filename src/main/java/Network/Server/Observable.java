package Network.Server;

import Model.Slot;
import Model.SlotListener;
import Network.Message.Message;

import java.util.ArrayList;

public abstract class Observable {
    private ArrayList<ServerListener> messageListeners = new ArrayList<>();
    private ArrayList<SlotListener> slotListeners = new ArrayList<>();
    
    public void notifyMessageListeners(Message message) {
        for (ServerListener messageListener : messageListeners) {
            messageListener.update(message);
        }
    }
    
    public void notifySlotListeners() {
        for (SlotListener slotListener : slotListeners) {
            slotListener.update();
        }
    }
    
    public void addMessageListener(ServerListener listener){
        messageListeners.add(listener);
    }
    
    public void addSlotListener(SlotListener slotListener){
        slotListeners.add(slotListener);
    }
}
