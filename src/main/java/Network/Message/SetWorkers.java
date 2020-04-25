package Network.Message;

import Enumerations.MessageType;

/**
 * This message contains an array of coordinates (row1, column1, row2, column2) that indicates the initial positions of
 * the worker of a specifical player. It is a C->S message.
 */
public class SetWorkers extends Message{

    private static final long serialVersionUID = 2612090580702427837L;

    private int[] RowsAndColumns;

    public SetWorkers(MessageType messageType) {
        super(messageType);
    }

    public int[] getRowsAndColumns() {
        return RowsAndColumns;
    }

    public void setRowsAndColumns(int[] rowsAndColumns) {
        RowsAndColumns = rowsAndColumns;
    }

}
