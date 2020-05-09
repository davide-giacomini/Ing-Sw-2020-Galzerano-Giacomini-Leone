package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Visitor.Visitable;

/**
 * This class implements the Observer interface of the Observer Pattern.
 */
public interface VirtualViewListener {
    
    /**
     * This method implements the update of the Observer Pattern.
     * It's called every time the virtual view receives a message from the
     * client side, so its content is notified to the controller.
     *
     * @param visitableObject the content of the message.
     */
    void update(Visitable visitableObject);
}
