package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableRowsAndColumns;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This message contains an array of coordinates (row1, column1, row2, column2) that indicates the initial positions of
 * the worker of a specifical player. It is a C->S message.
 */
public class AskWorkersPosition extends Message{

    private static final long serialVersionUID = 2612090580702427837L;


    private VisitableRowsAndColumns rowsAndColumns;

    public AskWorkersPosition(VisitableRowsAndColumns rowsAndColumns) {
        this.rowsAndColumns = rowsAndColumns;
        this.messageType = MessageType.ASK_WORKER_POSITION;
    }

    @Override
    public Visitable getContent() {
        return rowsAndColumns;
    }
    

    /*
     * This method asks the player which position they want to put the worker on and sends it to the server creating
     * an array with rows and columns.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        int[] rowsAndColumns;
        rowsAndColumns = client.getView().askWhereToPositionWorkers();
    
        AskWorkersPosition newMessage = new AskWorkersPosition(MessageType.ASK_WORKER_POSITION);
        newMessage.setRowsAndColumns(rowsAndColumns);
        try {
            outputServer.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " +this.toString()+" message.");
            e.printStackTrace();
        }
    }*/
    
    /*
     * This method receives a list of coordinates (row1, column1, row2, column2) and calls the view to set the player's workers
     * into these positions and update the model.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
        virtualView.receiveSetWorkers(rowsAndColumns);
    }*/


}
