package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Network.Message.ConnectionFailed;
import it.polimi.ingsw.PSP47.View.ViewObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class handles the transfer of messages between the client and the server.
 */
public class NetworkHandler implements Runnable, ViewObserver {
    private final Client client;
    private Socket serverSocket;
    private boolean firstConnection;
    private ObjectInputStream inputServer;
    private ObjectOutputStream outputServer;
    private boolean isConnected;
    
    /**
     * This constructor set up the management between the {@link Client} and the {@link it.polimi.ingsw.PSP47.Network.Server.Server}.
     *
     * @param client the {@link Client} to be handled.
     * @param serverSocket the socket of the {@link it.polimi.ingsw.PSP47.Network.Server.Server} the user wants to connect to.
     */
    public NetworkHandler(Client client, Socket serverSocket){
        this.client = client;
        this.serverSocket = serverSocket;
        firstConnection = true;
        this.isConnected = true;
    }
    
    /**
     * This method instantiates the {@link ObjectInputStream} and the {@link ObjectOutputStream} with
     * {@link java.io.InputStream} and {@link java.io.OutputStream} of the server's socket in order to
     * handle serialization.
     */
    @Override
    public void run() {
        try {
            outputServer = new ObjectOutputStream(serverSocket.getOutputStream());
            inputServer = new ObjectInputStream(serverSocket.getInputStream());
            
            dispatchMessages();
        }
        catch (IOException e){
            System.out.println("Connection failed.");
        }
    }
    
    /**
     * This method handles the first connection with the server, asking to the user to choose their username and
     * the color they prefer for their workers.
     */
    public void handleFirstConnection() {
        String username = client.getView().askUsername();
        Color color = client.getView().askColorWorkers();
        
        RequestConnection requestConnection = new RequestConnection(MessageType.REQUEST_CONNECTION);
        requestConnection.setColor(color);
        requestConnection.setUsername(username);
        try {
            outputServer.writeObject(requestConnection);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " + requestConnection.toString() + " message.");
            e.printStackTrace();
        }
        firstConnection = false;
    }
    
    /**
     * This method dispatches the messages coming from the the server and calls other methods useful to handle them.
     */
    public void dispatchMessages() {
        System.out.println("Started listening to the server.");
        
        while (isConnected){
            Message message;
            try {
                message = (Message) inputServer.readObject();
                switch (message.getMessageType()){
                    case REQUEST_CONNECTION:
                        handleFirstConnection();
                        break;
                    case CONNECTION_FAILED:
                        handleConnectionFailed((ConnectionFailed) message);
                        firstConnection = true;
                        break;
                    case ASK_WORKER_POSITION:
                        int[] rowsAndColumns;
                        client.getView().askWhereToPositionWorkers();


                    default:
                        message.handleClientSide(client, outputServer);
                        break;
                }
            }
            catch (IOException e){
                client.getView().showMessage("We are sorry: " +
                        "the server  at the address " + serverSocket.getInetAddress() + " disconnected.");
                isConnected = false;
                //e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                client.getView().showMessage("Error in casting during the readObject.");
                isConnected = false;
                //e.printStackTrace();
            }
        }
        
        client.getView().showMessage("Game closed.");
    }
    
    /**
     * Here a failure in the connection is analyzed.
     * If the user wrote the wrong username or the wrong color, this method proceeds for a reconnection, calling back
     * the {@link #handleFirstConnection()}.
     * Otherwise, if the game is already started, the connection closes.
     *
     * @param connectionFailedMessage it's the message with its parameters.
     */
    private void handleConnectionFailed(ConnectionFailed connectionFailedMessage) {
        connectionFailedMessage.handleClientSide(client, outputServer);
        
        if (connectionFailedMessage.getErrorMessage().equals("Somebody else has already taken this username. Try another.")         //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
                || connectionFailedMessage.getErrorMessage().equals("Somebody else has already taken this color. Try another.")){   //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
            handleFirstConnection();
        }
        else if (connectionFailedMessage.getErrorMessage().equals("The game is already started. Try later.")){         //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
            try {
                isConnected = false;
                serverSocket.close();
            }
            catch (IOException e){
                System.out.println("Unable to close server socket");
            }
        }
    }

    @Override
    public void update (int[] rowsAndColumns){
        AskWorkersPosition newMessage = new AskWorkersPosition(rowsAndColumns);
        try {
            outputServer.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " +this.toString()+" message.");
            e.printStackTrace();
        }
    }


}
