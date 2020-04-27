package Network.Server;

import Enumerations.Color;
import Enumerations.GodName;
import Enumerations.MessageType;
import Model.Slot;
import Network.Client.Client;
import Network.Message.*;
import Network.Message.ErrorMessages.ConnectionFailed;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private Server server;
    private VirtualView virtualView;
    private ObjectInputStream inputClient;
    private ObjectOutputStream outputClient;
    private boolean isConnected;
    
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
            System.out.println("client " + clientSocket.getInetAddress() + " connection dropped.");
            e.printStackTrace();
        }
    }
    
    public VirtualView getVirtualView() {
        return virtualView;
    }
    
    /**
     * It handles the first connection.
     * If the connection went well, the client is added and his {@link VirtualView} is instantiated.
     * If not, an error message is sent.
     *
     * @throws IOException if there are troubles in sending the message to the client.
     */
    private void handleFirstConnection(Message message) throws IOException {
        System.out.println("Handle first connection with the client at the address " + clientSocket.getInetAddress());
        
        RequestConnection requestConnection = (RequestConnection) message;
        String username = requestConnection.getUsername();
        Color color = requestConnection.getColor();
        
        //TODO gestire la concorrenza dei client con l'attributo numberOfPlayersRequestSent di Server. 
        
        for (ClientHandler clientHandler: server.getNumberOfPlayers()){
            if (server.getNumberOfPlayers().size()==server.getMaxNumberOfPlayers()){
                ConnectionFailed connectionFailed = new ConnectionFailed(MessageType.CONNECTION_FAILED);
                connectionFailed.setErrorMessage("The game is already started. Try later.");            //WARNING: this message MUST be equal to the one checked in handleConnectionFailed in the network handler
                outputClient.writeObject(connectionFailed);
                isConnected=false;
                System.out.println("I set false the connection.");
                return;
            }
            else if (clientHandler.getVirtualView().getUsername().equals(username)){
                ConnectionFailed connectionFailed = new ConnectionFailed(MessageType.CONNECTION_FAILED);
                connectionFailed.setErrorMessage("Somebody else has already taken this username.");     //WARNING: this message MUST be equal to the one checked in handleConnectionFailed in the network handler
                outputClient.writeObject(connectionFailed);
                return;
            }
            else if (clientHandler.getVirtualView().getColor().equals(color)){
                ConnectionFailed connectionFailed = new ConnectionFailed(MessageType.CONNECTION_FAILED);
                connectionFailed.setErrorMessage("Somebody else has already taken this color.");        //WARNING: this message MUST be equal to the one checked in handleConnectionFailed in the network handler
                outputClient.writeObject(connectionFailed);
                return;
            }
        }
        
        // the virtual view is added and it is added to the message listeners.
        virtualView = new VirtualView(username, color, this);
        server.addMessageListener(virtualView);
        
        // if the player is the first, he will decide the number of players
        if (server.getNumberOfPlayers().size()==0) {
            outputClient.writeObject(new RequestNumberOfPlayers(MessageType.REQUEST_NUMBER_OF_PLAYERS));
            server.setNumberOfPlayersRequestSent(true);
        }
        
        // the player is added to the list of players of the server
        server.addPlayer(this);
        server.addPlayerUsernameColorHashMap(username, color);
        server.addPlayerUsernameVirtualViewHashMap(username, virtualView);
        ConnectionAccepted connectionAccepted = new ConnectionAccepted(MessageType.CONNECTION_ACCEPTED);
        connectionAccepted.setUsername(username);
        connectionAccepted.setColor(color);
        outputClient.writeObject(connectionAccepted);
        
        // if the number of players is reached, the game is initialized.
        if (server.getNumberOfPlayers().size() == server.getMaxNumberOfPlayers())
            server.initGame();
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
                    case REQUEST_CONNECTION:
                        handleFirstConnection(message);
                        break;
                    default:
                        message.handleServerSide(server, virtualView, outputClient);
                        break;
                }
            } catch (ClassNotFoundException e) {
                System.out.println("The casting of the message was not good");
            } catch (IOException e) {
                if (e.getMessage().toUpperCase().equals("CONNECTION RESET")) {
                    isConnected = false;
                    System.out.println("Client " + clientSocket.getInetAddress() + " disconnected.");
                    //TODO pulire tutto
                    // se la partita è già iniziata bisogna far terminare il gioco, altrimenti bisogna semplicemente
                    // pulire il giocatore dal server e continuare con le connessioni
                }
                else {
                    e.printStackTrace();
                }
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
            System.out.println("Error in the serialization of " +message.toString()+ " message.");
            e.printStackTrace();
        }
    }

    /**
     * This method sends a message to the client with the number of players
     *
     * @param numberOfPlayers parameter that must be sent.
     */
    void sendNumberOfPlayers(int numberOfPlayers) {
        NumberOfPlayers message = new NumberOfPlayers(MessageType.NUMBER_PLAYERS);
        message.setNumberOfPlayers(numberOfPlayers);
        send(message);
    }

    /**
     * This method send a message to the client to tell him that he is the Challenger.
     */
    void sendYouAreTheChallenger()  {
        send(new YouAreTheChallenger(MessageType.RANDOM_PLAYER));
    }

    /**
     * This method sends a message to the client with the list of available gods he can choose from.
     *
     * @param gods list of available gods.
     */
    void sendGodsList(ArrayList<GodName> gods) {
        ListOfGods message = new ListOfGods(MessageType.LIST_OF_GODS);
        message.setGodsAvailable(gods);
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
        PublicInformation message = new PublicInformation(MessageType.PUBLIC_INFORMATION);
        message.setUsernames(usernames);
        message.setColors(colors);
        message.setGodNames(godNames);
        send(message);
    }

    /**
     * This method sends a message to the client with an update of the model (a modified slot).
     *
     * @param updatedSlot the modified slot.
     */
    void sendUpdateSlot(Slot updatedSlot) {
        UpdatedSlot message = new UpdatedSlot(MessageType.UPDATE_SLOT);
        message.setUpdatedSlot(updatedSlot);
        send(message);
    }

    /**
     * This method sends a message to the client to ask the initial position of his workers.
     */
    void sendAskWorkersPosition() {
        send(new AskWorkersPosition(MessageType.SET_WORKERS));
    }

    void sendError(String errorText) {
        ErrorMessage message = new ErrorMessage(MessageType.ERROR);
        message.setErrorText(errorText);
        send(message);
    }

    void sendWhichWorker() {
        send(new ChooseWorkerByPosition(MessageType.CHOOSE_WORKER));
    }
}
