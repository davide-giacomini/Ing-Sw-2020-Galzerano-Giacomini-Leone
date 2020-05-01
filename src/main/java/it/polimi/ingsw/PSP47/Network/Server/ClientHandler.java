package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Message.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private Server server;
    private VirtualView virtualView;
    private ObjectInputStream inputClient;
    private ObjectOutputStream outputClient;
    private boolean isConnected;
//    private Timer timer;
    private final static Object firstConnectionLock = new Object();
    
    public VirtualView getVirtualView() {
        return virtualView;
    }
    
    /**
     * This constructor set up the management between the {@link Client} and the {@link Server}.
     *
     * @param clientSocket the socket of the {@link Client} connected to the server.
     * @param server the server
     */
    public ClientHandler(Socket clientSocket, Server server){
        this.clientSocket = clientSocket;
        this.server = server;
        this.isConnected = true;
    }
    
    /**
     * This method instantiates the {@link ObjectInputStream} and the {@link ObjectOutputStream} with
     * {@link java.io.InputStream} and {@link java.io.OutputStream} of the client's socket in order to
     * handle serialization.
     */
    @Override
    public void run() {
        try {
            inputClient = new ObjectInputStream(clientSocket.getInputStream());
            outputClient = new ObjectOutputStream(clientSocket.getOutputStream());
            
            dispatchMessages();
        }
        catch (IOException e){
            System.out.println("Client " + clientSocket.getInetAddress() + " connection dropped.");
            e.printStackTrace();
        }
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
                    case REQUEST_DISCONNECTION:
//                        handleDisconnection();
                        // TODO non ancora testato questo caso
                    case FIRST_PLAYER_CONNECTION:
                        //TODO controlla che i parametri siano corretti
                    default:
                        server.notifyMessageListeners(message, virtualView);
                        break;
                }
            } catch (ClassNotFoundException e) {
                System.out.println("The casting of the message of the client " + clientSocket.getInetAddress() + " was not good.");
                try {
                    clientSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                finally {
                    isConnected = false;
                }
                System.out.println("Client " + clientSocket.getInetAddress() + " disconnected.");
                
//                handleDisconnection();
                
                //TODO scollegamento:
                // scollegamento di rete: boh.
                
                //e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error in the I/O of the client " + clientSocket.getInetAddress() + ":" +
                        " client " + clientSocket.getInetAddress() + " disconnected.");
                
//                handleDisconnection();
                
                //TODO scollegamento:
                // scollegamento di rete: boh.
                
                //e.printStackTrace();
            }
        }
    }

    /**
     * It handles the first player connection.
     * If the connection went well, the client is added and his {@link VirtualView} is instantiated.
     */
    private void handleFirstPlayerConnection(FirstPlayerConnection message) {
        System.out.println("Handle first connection with the client at the address " + clientSocket.getInetAddress());

        // Variables to be used in this method.
        String username = message.getUsername();
        Color color = message.getColor();
        int numberPlayers = message.getNumberPlayers();
        VirtualView virtualView = new VirtualView(username, color, this);

        server.addPlayer(this);
        server.addUsernameAndColorToMap(username, color);
        server.addUsernameAndVirtualViewToMap(username, virtualView);
        server.setMaxNumberOfPlayers(numberPlayers);
        Server.setGameParametersChosen(new AtomicBoolean(true));
        // Notify all the waiting players that the first player chose game's parameters.
        firstConnectionLock.notifyAll();
    }

    /**
     * This method disconnects the server from this client telling him who disconnected first.
     * @param message it tells this client who disconnected first.
     */
    public void disconnectFromClient(OpponentPlayerDisconnection message){
        send(message);
        isConnected = false;
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Unable to close the socket of this client: " + clientSocket.getInetAddress());
            e.printStackTrace();
        }
    }

    /**
     * This method handles the disconnection of the client. If it is the first client to disconnect from the game,
     * it will make others disconnect as well. Otherwise, the method is ignored.
     */
//    void handleDisconnection() {
//        // If the virtualView is set to null, it means that the player didn't enter the handleFirstConnection,
//        // hence nothing has to be done.
//        if (virtualView==null){
//            isConnected = false;
//            return;
//        }
//
//        // This is synchronized in the case there is an attempt of connection by another client
//        // during the disconnection of this client.
//        synchronized (firstConnectionLock) {
//            // If isConnected is true, it means that this method hasn't been called by other clients. This means
//            // that the client which has to advise the others is this.
//            // Hence, for each client this method sends to them a message of incoming disconnection.
//            if (isConnected) {
//                OpponentPlayerDisconnection message = new OpponentPlayerDisconnection(MessageType.OPPONENT_PLAYER_DISCONNECTION);
//                message.setUsername(virtualView.getUsername());
//                server.notifyMessageListeners(message, virtualView);
//                server.cleanServer();
//                try {
//                    clientSocket.close();
//                } catch (IOException e) {
//                    System.out.println("Unable to close the socket of the client " + clientSocket.getInetAddress() + ".");
//                    e.printStackTrace();
//                }
//            }
//
//            isConnected = false;
//        }
//    }

    /**
     * This method serializes the messages and sends them to the client.
     *
     * @param message the message that must be sent.
     */
    void send(Message message) {
//        try {
//            outputClient.writeObject(message);
//        } catch (IOException e) {
//            System.out.println("Error in the serialization of " +message.toString()+ " message.");
//            e.printStackTrace();
//        }
//
//        if (message.getMessageType() == MessageType.TIMER_UPDATER) return;
//
//        timer = new Timer(this);
//        new Thread(timer).start();
    }

    /**
     * This method sends a message to the client with the number of players
     *
     * @param numberOfPlayers parameter that must be sent.
     */
    void sendNumberOfPlayers(int numberOfPlayers) {
//        NumberOfPlayers message = new NumberOfPlayers(MessageType.NUMBER_PLAYERS);
//        message.setNumberOfPlayers(numberOfPlayers);
//        send(message);
    }

    /**
     * This method send a message to the client to tell him that he is the Challenger.
     */
    void sendYouAreTheChallenger()  {
//        send(new YouAreTheChallenger(MessageType.RANDOM_PLAYER));
    }

    /**
     * This method sends a message to the client with the list of available gods he can choose from.
     *
     * @param gods list of available gods.
     */
    void sendGodsList(ArrayList<GodName> gods) {
//        ListOfGods message = new ListOfGods(MessageType.LIST_OF_GODS);
//        message.setGodsAvailable(gods);
//        send(message);
    }

    /**
     * This method sends a message to the client with all the information about the players of the current game.
     *
     * @param usernames the username of each client
     * @param colors the color of each client
     * @param godNames the god chose from each client
     */
    void sendPublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames)  {
//        PublicInformation message = new PublicInformation(MessageType.PUBLIC_INFORMATION);
//        message.setUsernames(usernames);
//        message.setColors(colors);
//        message.setGodNames(godNames);
//        send(message);
//    }

    /**
     * This method sends a message to the client with an update of the model (a modified slot).
     *
     * @param updatedSlot the modified slot.
     */
//    void sendUpdateSlot(Slot updatedSlot) {
//        Slot newSlot = new Slot(updatedSlot.getRow(), updatedSlot.getColumn());
//        newSlot.setWorker(updatedSlot.getWorker()) ;
//        newSlot.setWorkerColor(updatedSlot.getWorkerColor());
//        newSlot.setLevel(updatedSlot.getLevel());
//        newSlot.setWorkerOn(updatedSlot.isWorkerOnSlot());
//        UpdatedSlot message = new UpdatedSlot(MessageType.UPDATE_SLOT);
//        message.setUpdatedSlot(newSlot);
//        send(message);
    }

    /**
     * This method sends a message to the client to ask the initial position of his workers.
     */
    void sendAskWorkersPosition() {
//        send(new AskWorkersPosition(MessageType.ASK_WORKER_POSITION));
    }

    /**
     * This method sends a message to the client to warn him that he did something wrong.
     * @param errorText the errorString that explain what he did wrong.
     */
    void sendError(String errorText) {
//        ErrorMessage message = new ErrorMessage(MessageType.ERROR);
//        message.setErrorText(errorText);
//        send(message);
    }

    /**
     * This method sends a message to the client to ask which worker he wants to use, asking the slot he is on.
     */
    void sendWhichWorker() {
//        send(new ChooseWorkerByPosition(MessageType.CHOOSE_WORKER));
    }

    /**
     * This method sends a message to the client to ask which action he wants to do next.
     */
    void sendAction() {
//        send(new ChooseAction(MessageType.CHOOSE_ACTION));
    }
}
