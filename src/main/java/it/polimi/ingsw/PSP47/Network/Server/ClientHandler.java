package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Observable;
import it.polimi.ingsw.PSP47.Network.Message.ConnectionFailed;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableListOfGods;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Observable implements Runnable{
    private final Socket clientSocket;
    private ObjectInputStream inputClient;
    private ObjectOutputStream outputClient;
    private boolean isConnected;
    private boolean gameAlreadyStarted;
    private VirtualView virtualView;
    
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
                outputClient.writeObject(new FirstConnection(null));
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
                    default:
                        Visitable visitableObject = message.getContent();
                        virtualView.notifyVirtualViewListener(visitableObject);
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
        send(new ErrorMessage("Sei stato messo in coda."));
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
    
    void notifyOpponentClientDisconnected(){
        send(new ConnectionFailed("The first player disconnected and the game cannot be set.\n"+
                "Please try another time."));
        
        endConnection();
    }
    
    void notifyOpponentClientDisconnected(String username){
        send(new ConnectionFailed("The player" + username + " disconnected. Game over.\n"));
        
        endConnection();
    }

    /**
     * This method creates the virtual view of the player who is connected through a specific instance of the clientHandler.
     * @param username of the player
     * @param color of the player
     * @return the instance of the virtual view
     */
    VirtualView createVirtualView(String username, Color color){
        return (this.virtualView = new VirtualView(username, color, this));
    }

    /**
     * This method sends a message to the client with the number of players
     *
     * @param numberOfPlayers parameter that must be sent.
     */
    void sendNumberOfPlayers(int numberOfPlayers) {
        NumberOfPlayers message = new NumberOfPlayers(numberOfPlayers);
        send(message);
    }

    /**
     * This method send a message to the client to tell him that he is the Challenger.
     */
    void sendYouAreTheChallenger()  {
        send(new YouAreTheChallenger());
    }

    /**
     * This method sends a message to the client with the list of available gods he can choose from.
     *
     * @param gods list of available gods.
     */
    void sendGodsList(ArrayList<GodName> gods) {
        VisitableListOfGods listOfGods = new VisitableListOfGods();
        listOfGods.setGodNames(gods);
        ListOfGods message = new ListOfGods(listOfGods);
        send(message);
    }

    /**
     * This method sends a message to the client with all the information about the players of the current game.
     *
     * @param usernames the username of each client
     * @param colors the color of each client
     * @param godNames the god chose from each client
     */
    void sendPublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames)  {
        PublicInformation message = new PublicInformation(usernames,colors,godNames);
        send(message);
    }

    /**
     * This method sends a message to the client with an update of the model (a modified slot).
     *
     * @param updatedSlot the modified slot.
     */
    void sendUpdateSlot(Slot updatedSlot) {
        Slot newSlot = new Slot(updatedSlot.getRow(), updatedSlot.getColumn());
        newSlot.setWorker(updatedSlot.getWorker()) ;
        newSlot.setWorkerColor(updatedSlot.getWorkerColor());
        newSlot.setLevel(updatedSlot.getLevel());
        newSlot.setWorkerOn(updatedSlot.isWorkerOnSlot());
        UpdatedSlot message = new UpdatedSlot(newSlot);
        send(message);
    }

    /**
     * This method sends a message to the client to ask the initial position of his workers.
     */
    void sendAskWorkersPosition() {
        send(new AskWorkersPosition(null));
    }

    /**
     * This method sends a message to the client to warn him that he did something wrong.
     * @param errorText the errorString that explain what he did wrong.
     */
    void sendError(String errorText) {
        ErrorMessage message = new ErrorMessage(errorText);
        send(message);
    }

    /**
     * This method sends a message to the client to ask which worker he wants to use, asking the slot he is on.
     */
    void sendWhichWorker() {
        send(new ChooseWorkerByPosition(null));
    }

    /**
     * This method sends a message to the client to ask which action he wants to do next.
     */
    void sendAction() {
        send(new ChooseAction(null));
    }
}
