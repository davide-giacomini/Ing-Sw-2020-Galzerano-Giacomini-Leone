package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;

/**
 * This method contains the username and the color chosen by the player who connected first.
 */
public class FirstConnection extends VisitableMessage {
    private static final long serialVersionUID = -8037367122696029080L;

    private VisitableInformation usernameAndColor;
    
    /**
     * This is the constructor to be used by the client, to set username and color
     *
     * @param usernameAndColor the information of username and color.
     */
    public FirstConnection(VisitableInformation usernameAndColor) {
        this.usernameAndColor = usernameAndColor;
        this.messageType=MessageType.FIRST_CONNECTION;
    }
    
    /**
     * This is the constructor to be used by th Server, to ask for the information.
     */
    public FirstConnection(){
        this.messageType = MessageType.FIRST_CONNECTION;
    }

    @Override
    public Visitable getContent() {
        return usernameAndColor;
    }
}
