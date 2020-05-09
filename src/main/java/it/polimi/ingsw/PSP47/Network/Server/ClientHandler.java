package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Network.Message.ConnectionFailed;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableListOfGods;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class handles the connection and the communication between the server and a client.
 */
public class ClientHandler extends ClientHandlerObservable implements Runnable{
    private final Socket clientSocket;
    private ObjectInputStream inputClient;
    private ObjectOutputStream outputClient;
    private boolean isConnected;
    private boolean gameAlreadyStarted;
    private VirtualView virtualView;
    private ServerTimer serverTimer;
    private final ExecutorService messageExecutor = Executors.newSingleThreadExecutor();
    
    /**
     * This constructor initializes the input stream and output stream of the sockets.
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
     * This method makes the server listen to the ping from the client and checks if the game is already started.
     * If so, the client handler immediately closes the connection. Otherwise, it asks to the client the username
     * and the color and then it begins to listen to the messages from the client.
     */
    @Override
    public void run() {
        // start to listen to the ping
        serverTimer = new ServerTimer(this);
        new Thread(serverTimer).start();
    
        // Send a ping each 5 seconds.
        new Thread(() -> {
            while (isConnected) {
                try {
                    send(new Ping());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    endConnection();
                    e.printStackTrace();
                }
            }
        }).start();
        
        // start the game
        if (gameAlreadyStarted) {
            send(new ConnectionFailed("The game is already started.\nTry another time."));
            endConnection();
        }
        else {
            send(new FirstConnection(null));
        }
    
        dispatchMessages();
    }
    
    /**
     * This method dispatches the messages coming from the client. If the message is a ping it is handled, otherwise
     * it forwards them to the runnable class {@link MessageHandler}.
     */
    private void dispatchMessages() {
        System.out.println("Started listening the client at the address" + clientSocket.getInetAddress());
        while (isConnected) {
            Message message;
            try {
                message = (Message) inputClient.readObject();
                switch (message.getMessageType()) {
                    case PING:
                        serverTimer.resetTime();
                        break;
                    default:
                        messageExecutor.submit(new MessageHandler(message, this));
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
                
                e.printStackTrace();
            }
        }
    }
    
    /**
     * This Runnable class handles the messages living the clientHandler free to receive ping messages. The messages
     * which handle the connection and disconnection of the client are forwarded to the server, otherwise they are
     * forwarded to the virtual view.
     */
    private class MessageHandler implements Runnable {
        Message message;
        ClientHandler clientHandler;
        
        public MessageHandler(Message message, ClientHandler clientHandler){
            this.message = message;
            this.clientHandler = clientHandler;
        }
        
        @Override
        public void run() {
            switch (message.getMessageType()) {
                case FIRST_CONNECTION:
                    notifyFirstConnection((FirstConnection) message, clientHandler);
                    break;
                case REQUEST_PLAYERS_NUMBER:
                    notifyPlayersNumber((RequestPlayersNumber) message);
                    break;
                //TODO disconnessione volontaria del client.
                default:
                    Visitable visitableObject = ((VisitableMessage) message).getContent();
                    virtualView.notifyVirtualViewListener(visitableObject);
                    break;
            }
        }
    }
    
    /**
    * This method serializes the messages and sends them to the client.
    *
    * @param message the message that must be sent.
    */
    private synchronized void send(Message message) {
        try {
            outputClient.writeObject(message);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " + message.toString() + " message.");
            endConnection();
            e.printStackTrace();
        }
    }
    
    /**
     * It ends the connection of this client and notifies the server to handle it.
     */
    void endConnection(){
        isConnected = false;
        notifyDisconnection(this);
        serverTimer.setIsConnectedFalse();
        messageExecutor.shutdownNow();
    
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Unable to close the socket of the client " + clientSocket.getInetAddress() + ".");
            e.printStackTrace();
        }
    }
    
    /**
     * It sends a message to the client to ask them the max number of players they want.
     */
    void askMaxPlayersNumber(){
        send(new RequestPlayersNumber(null));
    }
    
    /**
     * It sends a message to the client to warn them that the first player hasn't chosen the game's parameters yet
     * and so they have to wait.
     */
    void warnFirstPlayerIsChoosing(){
        send(new ErrorMessage("Wait for the first player to choose the game's parameters."));
    }
    
    /**
     * It sends a message to the client to ask them again the username and color, because the previous ones
     * submitted were not different from the other players already added to the game.
     *
     * @param wrongParameter it can be username, color or both.
     */
    void askAgainParameters(String wrongParameter){
        send(new WrongParameters("An other players chose your " + wrongParameter + ".\n" +
                "Please try with another."));
    }
    
    /**
     * It sends a message to the client to warn them that the game is already started because other players have
     * been added before them.
     * It ends the game and the connection.
     *
     */
    void notifyGameStartedWithoutYou(){
        gameAlreadyStarted = true;
        send(new ConnectionFailed("The game is already started.\nTry another time."));
        
        endConnection();
    }
    
    /**
     * It sends a message to the client to advise them that the first player, who had to choose the game's parameters,
     * suddenly disconnected and the game cannot be set.
     * It ends the game and the connection.
     *
     */
    void notifyFirstClientDisconnected(){
        send(new ConnectionFailed("The first player disconnected and the game cannot be set.\n"+
                "Please try another time."));
    
        endConnection();
    }
    
    /**
     * It sends a message to the client to warn them that the player with the username specified disconnected.
     * It ends the game and the connection.
     *
     * @param username the player's username who disconnected.
     */
    void notifyOpponentClientDisconnected(String username){
        send(new ConnectionFailed("The player" + username + " disconnected. Game over.\n"));
        
        endConnection();
    }
    
    /**
     * It creates the virtual view when the game starts.
     *
     * @param username virtualView's player's username
     * @param color virtualView's player's color.
     * @return the virtual view just created.
     */
    VirtualView createVirtualView(String username, Color color){
        return (this.virtualView = new VirtualView(username, color, this));
    }
    
    /**
     * It sends a message to the client telling them that they won.
     */
    void showYouWon(){
        notifyWin();
        endConnection();
    }
    
    /**
     * It sends a message to the client telling them that they lost.
     */
    void showYouLost(){
        notifyLoss(this);
        endConnection();
    }

    /**
     * This method sends a message to the client with the number of players
     *
     * @param numberOfPlayers parameter that must be sent.
     */
    void sendNumberOfPlayers(int numberOfPlayers) {
        PlayersNumber message = new PlayersNumber(numberOfPlayers);
        send(message);
    }

    /**
     * This method send a message to the client to tell them that they are the Challenger.
     */
    void sendYouAreTheChallenger()  {
        send(new YouAreTheChallenger());
    }

    /**
     * This method sends a message to the client with the list of available gods they can choose from.
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
        //TODO passare lo slot senza settarne uno nuovo
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
     * This method sends a message to the client with a general information.
     * @param text the String with the advice that must be sent.
     */
    void sendImportant(String text) {
        ImportantMessage message = new ImportantMessage(text);
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
