package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;

public class ConnectionAccepted extends VisitableMessage{
    private static final long serialVersionUID = -7614194884802773262L;

    private VisitableInformation usernameAndColor;

    public ConnectionAccepted(VisitableInformation usernameAndColor) {
        this.usernameAndColor=usernameAndColor;
        this.messageType=MessageType.CONNECTION_ACCEPTED;
    }

    @Override
    public Visitable getContent() {
        return usernameAndColor;
    }

}
