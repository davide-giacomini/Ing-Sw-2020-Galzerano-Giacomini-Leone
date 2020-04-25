package Network.Message;

import Enumerations.MessageType;

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
