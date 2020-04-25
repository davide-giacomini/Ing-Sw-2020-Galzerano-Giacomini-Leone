package Network.Server;

import Model.Slot;
import Model.SlotListener;
import Network.Message.Message;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Observable interface of pattern observer.
 */
public abstract class Observable {
    private ArrayList<ServerListener> messageListeners = new ArrayList<>();
    private ArrayList<SlotListener> slotListeners = new ArrayList<>();
    
    public void notifyMessageListeners(Message message) {
        for (ServerListener messageListener : messageListeners) {
            messageListener.update(message);
        }
    }
    
    public void notifySlotListeners(Slot slot) {
        for (SlotListener slotListener : slotListeners) {
            slotListener.update(slot);
        }
    }
    
    public void addMessageListener(ServerListener listener){
        messageListeners.add(listener);
    }
    
    public void addSlotListener(SlotListener slotListener){
        slotListeners.add(slotListener);
    }

    public void removeSlotListener(SlotListener slotListener){
        slotListeners.remove(slotListener);
    }
}
