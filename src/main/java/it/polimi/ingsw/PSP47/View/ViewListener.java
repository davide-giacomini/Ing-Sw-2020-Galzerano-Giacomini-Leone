package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Visitor.Visitable;

/**
 * Observer interface of the Pattern observer.
 */
public interface ViewListener {

    void update(Visitable visitableObject);
    
    
    void endConnection();
}
