package it.polimi.ingsw.PSP47;

import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.SlotListener;
import it.polimi.ingsw.PSP47.Network.Message.Message;
import it.polimi.ingsw.PSP47.Network.Server.ServerListener;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.View.ViewListener;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.util.ArrayList;

/**
 * Observable interface of pattern observer.
 */
public abstract class Observable {
    private ArrayList<ServerListener> messageListeners = new ArrayList<>();
    private ArrayList<SlotListener> slotListeners = new ArrayList<>();
    private ViewListener viewListener;
    
    public void notifyMessageListeners(Message message, VirtualView virtualView) {
        for (ServerListener messageListener : messageListeners) {
            messageListener.update(message, virtualView);
        }
    }
    
    public void notifySlotListeners(Slot slot) {
        for (SlotListener slotListener : slotListeners) {
            slotListener.update(slot);
        }
    }

    public void notifyViewListener(Visitable visitableObject){
        viewListener.update(visitableObject);
    }

    
    public void addMessageListener(ServerListener listener){
        messageListeners.add(listener);
    }
    
    public void addSlotListener(SlotListener slotListener){
        slotListeners.add(slotListener);
    }

    public void addViewListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }

    public void removeAll() {
        slotListeners = new ArrayList<>();
        messageListeners = new ArrayList<>();
    }
}
