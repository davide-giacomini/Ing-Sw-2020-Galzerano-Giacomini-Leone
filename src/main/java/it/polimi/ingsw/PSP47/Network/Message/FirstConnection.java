package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;


public class FirstConnection extends VisitableMessage {
    private static final long serialVersionUID = -8037367122696029080L;

    private VisitableInformation usernameAndColor;

    public FirstConnection(VisitableInformation usernameAndColor) {
        this.usernameAndColor = usernameAndColor;
        this.messageType=MessageType.FIRST_CONNECTION;
    }

    @Override
    public Visitable getContent() {
        return usernameAndColor;
    }
}
