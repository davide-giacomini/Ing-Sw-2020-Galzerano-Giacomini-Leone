package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.util.ArrayList;

public abstract class VirtualViewObservable {
    private final ArrayList<VirtualViewListener> virtualViewListeners = new ArrayList<>();
    
    public void addVirtualViewListener(VirtualViewListener virtualViewListener) {
        virtualViewListeners.add(virtualViewListener);
    }
    
    public void notifyVirtualViewListener(Visitable visitableObject) {
        for (VirtualViewListener slotListener : virtualViewListeners) {
            slotListener.update(visitableObject);
        }
    }
}
