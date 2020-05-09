package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Visitor.Visitable;

/**
 * This abstract class contains the method getContent which must be present in each
 * Message that contains a Visitable attribute.
 */
public abstract class VisitableMessage extends Message {
    private static final long serialVersionUID = -481470833825933533L;

    public abstract Visitable getContent();
}
