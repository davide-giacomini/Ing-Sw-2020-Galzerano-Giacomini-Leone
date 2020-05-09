package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableRowsAndColumns;

public class ChooseWorkerByPosition extends VisitableMessage {
    private static final long serialVersionUID = -4792198010811935743L;

    private VisitableRowsAndColumns rowsAndColumns;

    public ChooseWorkerByPosition(VisitableRowsAndColumns rowsAndColumns) {
        this.rowsAndColumns=rowsAndColumns;
        this.messageType=MessageType.CHOOSE_WORKER;
    }

    @Override
    public Visitable getContent() {
        return rowsAndColumns;
    }

}
