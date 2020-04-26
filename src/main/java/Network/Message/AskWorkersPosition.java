package Network.Message;

import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This message contains an array of coordinates (row1, column1, row2, column2) that indicates the initial positions of
 * the worker of a specifical player. It is a C->S message.
 */
public class AskWorkersPosition extends Message{

    private static final long serialVersionUID = 2612090580702427837L;

    private int[] rowsAndColumns;

    public AskWorkersPosition(MessageType messageType) {
        super(messageType);
    }
    
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        int[] rowsAndColumns;
        rowsAndColumns = client.getView().askWhereToPositionWorkers();
    
        AskWorkersPosition newMessage = new AskWorkersPosition(MessageType.SET_WORKERS);
        newMessage.setRowsAndColumns(rowsAndColumns);
        try {
            outputServer.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " +this.toString()+" message.");
            e.printStackTrace();
        }
    }
    
    /**
     * This method receives a list of coordinates (row1, column1, row2, column2) and calls the view to set the player's workers
     * into these positions and update the model.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
     */
    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
        virtualView.receiveSetWorkers(rowsAndColumns);
    }
    
    public int[] getRowsAndColumns() {
        return rowsAndColumns;
        //TODO rendere questo settaggio safe
    }

    public void setRowsAndColumns(int[] rowsAndColumns) {
        this.rowsAndColumns = rowsAndColumns;
        //TODO rendere questo settaggio safe
    }

}
