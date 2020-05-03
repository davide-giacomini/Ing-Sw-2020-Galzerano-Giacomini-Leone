package it.polimi.ingsw.PSP47.Model;

import java.util.ArrayList;

public abstract class SlotObservable {
    private ArrayList<SlotListener> slotListeners = new ArrayList<>();
    
    public void notifySlotListeners(Slot slot) {
        for (SlotListener slotListener : slotListeners) {
            slotListener.update(slot);
        }
    }
    
    public void addSlotListener(SlotListener slotListener){
        slotListeners.add(slotListener);
    }
    
    public void removeAll() {
        slotListeners = new ArrayList<>();
    }
}
