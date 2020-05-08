package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableListOfGods;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This message contains two elements as it is sent both from server to client and from client to server.
 * When it is S->C it's setted the ArrayList of godsAvailable, with all of the god chosen by the Challenger.
 * When it is C->S it's setted the GodName chosenGod with the kind of god chosen by a player.
 */
public class ListOfGods extends Message {
    private static final long serialVersionUID = 5244974574544564271L;
    private VisitableListOfGods listOfGods;

    public ListOfGods(VisitableListOfGods listOfGods) {
        this.listOfGods=listOfGods;
        this.messageType=MessageType.LIST_OF_GODS;
    }

    @Override
    public Visitable getContent() {
        return listOfGods;
    }

}
