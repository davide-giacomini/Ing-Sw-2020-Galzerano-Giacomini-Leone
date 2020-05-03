package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Visitor.Visitable;

/**
 * This class implements the Observer interface of the Observer Pattern.
 */
public interface VirtualViewListener {

    void update(Visitable visitableObject);
}
