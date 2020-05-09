package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.util.ArrayList;

/**
 * This class permits the {@link VirtualView} to be observable.
 */
public abstract class VirtualViewObservable {
    private final ArrayList<VirtualViewListener> virtualViewListeners = new ArrayList<>();
    
    /**
     * This method adds a virtualViewListener to the list of listeners.
     *
     * @param virtualViewListener the virtualViewListener to be added.
     */
    public void addVirtualViewListener(VirtualViewListener virtualViewListener) {
        virtualViewListeners.add(virtualViewListener);
    }

    public void removeVirtualViewListener(VirtualViewListener virtualViewListener) {
        virtualViewListeners.remove(virtualViewListener);
    }
    
    /**
     * This method notifies every listener that a message from the client arrived to the server.
     *
     * @param visitableObject it contains of the message.
     */
    public void notifyVirtualViewListener(Visitable visitableObject) {
        for (VirtualViewListener slotListener : virtualViewListeners) {
            slotListener.update(visitableObject);
        }
    }
}
