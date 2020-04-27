package Network.Message;

import Enumerations.Action;
import Enumerations.Direction;
import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ChooseWorkerByPosition extends Message {
    private static final long serialVersionUID = -4792198010811935743L;

    private int[] rowsAndColumns;

    public ChooseWorkerByPosition(MessageType messageType) {
        super(messageType);
    }

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        int[] rowsAndColumns;
        rowsAndColumns = client.getView().askWhichWorkerToUse();

        ChooseWorkerByPosition newMessage = new ChooseWorkerByPosition(MessageType.CHOOSE_WORKER);
        newMessage.setRowsAndColumns(rowsAndColumns);
        try {
            outputServer.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " +this.toString()+" message.");
            e.printStackTrace();
        }
    }

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
        virtualView.receiveWhichWorker(getRowsAndColumns());
    }



    public int[] getRowsAndColumns() {
        return rowsAndColumns;
    }

    public void setRowsAndColumns(int[] rowsAndColumns) {
        this.rowsAndColumns = rowsAndColumns;
    }
}
