package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Observable;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Observable implements Runnable{
    private final Socket clientSocket;
    private ObjectInputStream inputClient;
    private ObjectOutputStream outputClient;
    private boolean isConnected;
    private boolean gameAlreadyStarted;
//    private Timer timer;
    private final static Object firstConnectionLock = new Object();
    
    /**
     * This constructor set up the management between the {@link Client} and the {@link Server}.
     *
     * @param clientSocket the socket of the {@link Client} connected to the server.
     * @param gameAlreadyStarted if the game is already started.
     */
    public ClientHandler(Socket clientSocket, boolean gameAlreadyStarted){
        this.clientSocket = clientSocket;
        this.isConnected = true;
        this.gameAlreadyStarted = gameAlreadyStarted;
    
        try {
            inputClient = new ObjectInputStream(clientSocket.getInputStream());
            outputClient = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Creation of the client " + clientSocket.getInetAddress() + " input and output streams failed.");
            this.isConnected = false;
            e.printStackTrace();
        }
    }
    
    /**
     * This method instantiates the {@link ObjectInputStream} and the {@link ObjectOutputStream} with
     * {@link java.io.InputStream} and {@link java.io.OutputStream} of the client's socket in order to
     * handle serialization.
     */
    @Override
    public void run() {
        try {
            if (gameAlreadyStarted) {
                send(new ConnectionFailed("The game is already started.\nTry another time."));
    
                endConnection();
            }
            else
                outputClient.writeObject(new FirstConnection(null, null));
        } catch (IOException e) {
            System.out.println("Failed to send the first connection request to the client" + clientSocket.getInetAddress() +".");
            isConnected = false;
            e.printStackTrace();
        }
    
        dispatchMessages();
    }
    
    /**
     * This method handle the messages that come from the client.
     * Each different message is handled by a method of this class, method which is called within this method.
     */
    public void dispatchMessages() {
        System.out.println("Started listening the client at the address" + clientSocket.getInetAddress());
        
        while (isConnected) {
            Message message;
            try {
                message = (Message) inputClient.readObject();
                switch (message.getMessageType()) {
                    case FIRST_CONNECTION:
                        notifyFirstConnection((FirstConnection) message, this);
                        break;
                    case REQUEST_PLAYERS_NUMBER:
                        notifyPlayersNumber((RequestPlayersNumber) message);
                        break;
                    case REQUEST_DISCONNECTION:
//                        handleDisconnection();
                        // TODO non ancora testato questo caso
                    default:
                        break;
                }
            } catch (ClassNotFoundException e) {
                System.out.println("The casting of the message of the client " + clientSocket.getInetAddress() + " was not good.");
    
                if (isConnected)
                    notifyDisconnection(this);
                
                try {
                    clientSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                finally {
                    isConnected = false;
                }
                System.out.println("Client " + clientSocket.getInetAddress() + " disconnected.");
                
                //TODO scollegamento:
                // scollegamento di rete: boh.
                
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error in the I/O of the client " + clientSocket.getInetAddress() + ":" +
                        " client " + clientSocket.getInetAddress() + " disconnected.");
    
                if (isConnected)
                    notifyDisconnection(this);
    
                try {
                    clientSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                finally {
                    isConnected = false;
                }
                System.out.println("Client " + clientSocket.getInetAddress() + " disconnected.");
                
                //TODO scollegamento:
                // scollegamento di rete: boh.
                
                e.printStackTrace();
            }
        }
    }
    
    /**
    * This method serializes the messages and sends them to the client.
    *
    * @param message the message that must be sent.
    */
    private void send(Message message) {
        try {
            outputClient.writeObject(message);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " + message.toString() + " message.");
            isConnected = false;
            e.printStackTrace();
        }
    }
    
    private void endConnection(){
        isConnected = false;
        notifyDisconnection(this);
    
        try {
            outputClient.close();
        } catch (IOException e) {
            System.out.println("Unable to close the socket of the client " + clientSocket.getInetAddress() + ".");
            e.printStackTrace();
        }
    }
    
    void askMaxPlayersNumber(){
        send(new RequestPlayersNumber(null));
    }
    
    void warnFirstPlayerIsChoosing(){
        send(new WaitConnectionOpponentPlayer("Sei stato messo in coda."));
    }
    
    void askAgainParameters(String wrongParameter){
        send(new WrongParameters("An other players chose your " + wrongParameter + ".\n" +
                "Please try with another."));
    }
    
    void notifyGameStartedWithoutYou(){
        gameAlreadyStarted = true;
        send(new ConnectionFailed("The game is already started.\nTry another time."));
        
        endConnection();
    }
    
    void notifyFirstClientHandlerDropped(){
        send(new ConnectionFailed("The first player disconnected and the game cannot be set.\n"+
                "Please try another time."));
        
        endConnection();
    }
}
