package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class handles the transfer of messages between the client and the server.
 */
public class NetworkHandler implements Runnable{
    private final Client client;
    private Socket serverSocket;
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
    
//            new Thread(() -> {
//                try {
//                    try {
//                        Thread.sleep(40000);
//                        outputServer.writeObject(new Ping());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }).start();
            
            dispatchMessages();
        }
        catch (IOException e){
            System.out.println("Connection failed.");
        }
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
                    case FIRST_CONNECTION:
                        handleFirstConnection();
                        break;
                    case REQUEST_PLAYERS_NUMBER:
                        handlePlayersRequestNumber();
                        break;
                    case WRONG_PARAMETERS:
                        client.getView().showMessage(((WrongParameters) message).getErrorMessage());
                        handleFirstConnection();
                        break;
                    case CONNECTION_FAILED:
                        client.getView().showMessage(((ConnectionFailed) message).getErrorMessage());
                        isConnected = false;
                        break;
                    case WAIT_CONNECTION_OPPONENT_PLAYER:
                        client.getView().showMessage(((WaitConnectionOpponentPlayer) message).getErrorMessage());
                        break;
                    default:
//                        message.handleClientSide(client, outputServer);
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
     * This method serializes the messages and sends them to the server.
     *
     * @param message the message that must be sent.
     */
    private void send(Message message) {
        try {
            outputServer.writeObject(message);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " + message.toString() + " message.");
            isConnected = false;
            e.printStackTrace();
        }
    }
    
    private void handleFirstConnection(){
        String username = client.getView().askUsername();
        Color color = client.getView().askColorWorkers();
        
        send(new FirstConnection(username, color));
    }
    
    private void handlePlayersRequestNumber(){
        int playersNumber = client.getView().askNumberOfPlayers();
    
        send((new RequestPlayersNumber(playersNumber)));
    }
}

