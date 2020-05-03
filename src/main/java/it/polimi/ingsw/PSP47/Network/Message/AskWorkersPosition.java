package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInitialPositions;
import it.polimi.ingsw.PSP47.Visitor.VisitableRowsAndColumns;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This message contains an array of coordinates (row1, column1, row2, column2) that indicates the initial positions of
 * the worker of a specifical player. It is a C->S message.
 */
public class AskWorkersPosition extends Message{
    private static final long serialVersionUID = 2612090580702427837L;
    private VisitableInitialPositions rowsAndColumns;

    public AskWorkersPosition(VisitableInitialPositions rowsAndColumns) {
        this.rowsAndColumns = rowsAndColumns;
        this.messageType = MessageType.ASK_WORKER_POSITION;
    }

    @Override
    public Visitable getContent() {
        return rowsAndColumns;
    }
}
