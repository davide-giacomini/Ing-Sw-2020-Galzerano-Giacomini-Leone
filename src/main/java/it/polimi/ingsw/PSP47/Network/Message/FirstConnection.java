package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;

import java.io.ObjectOutputStream;

public class FirstConnection extends Message {
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
