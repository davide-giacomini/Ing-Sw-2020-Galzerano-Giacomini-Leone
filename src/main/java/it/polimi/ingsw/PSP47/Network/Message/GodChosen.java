package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableGod;

/**
 * This message contains the god chosen by a player.
 */
public class GodChosen extends VisitableMessage{
    private static final long serialVersionUID = 3437666900454385113L;

    VisitableGod god;

    public GodChosen(VisitableGod god) {
        this.god = god;
        this.messageType= MessageType.GOD_CHOSEN;
    }

    @Override
    public Visitable getContent() {
        return god;
    }
}
