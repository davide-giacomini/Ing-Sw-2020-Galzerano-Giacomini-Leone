package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;

public class NewPosition extends Message {
    private static final long serialVersionUID = 5940269946255707344L;
    private int row;
    private int column;

    public NewPosition(int row, int column) {
        this.row = row;
        this.column = column;
        this.messageType = MessageType.NEW_POSITION;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
