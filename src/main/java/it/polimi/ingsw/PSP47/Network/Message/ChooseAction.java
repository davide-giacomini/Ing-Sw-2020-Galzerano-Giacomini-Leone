package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableActionAndDirection;

/**
 * This message contains the action and the direction which have been chosen by the player during the turn.
 */
public class ChooseAction extends VisitableMessage {

    private static final long serialVersionUID = 7394766692151121241L;

    private VisitableActionAndDirection actionAndDirection;
    
    public ChooseAction(VisitableActionAndDirection actionAndDirection) {
        this.actionAndDirection = actionAndDirection;
        this.messageType=MessageType.CHOOSE_ACTION;
    }
    
    public ChooseAction(){
        this.messageType=MessageType.CHOOSE_ACTION;
    }

    @Override
    public Visitable getContent() {
        return actionAndDirection;
    }
}
