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
        
        //TODO questo pezzo non è thread safe: se arriva una richiesta di un giocatore da un client e prima
        // che il client viene aggiunto al numero dei client dentro al server il codice entra nel foreach, beh
        // a questo punto entrambi i client risultano i primi giocatori.
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
        if (server.getNumberOfPlayers().size()==0)
            outputClient.writeObject(new RequestNumberOfPlayers(MessageType.REQUEST_NUMBER_OF_PLAYERS));
        
        // the player is added to the list of players of the server
        server.addPlayer(this);
        server.addPlayerUsernameColorHashMap(username, color);
        server.addPlayerUsernameVirtualViewHashMap(username, virtualView);
        ConnectionAccepted connectionAccepted = new ConnectionAccepted(MessageType.CONNECTION_ACCEPTED);
        connectionAccepted.setUserName(username);
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
                    case REQUEST_NUMBER_OF_PLAYERS:
                        handleRequestNumberOfPlayers((RequestNumberOfPlayers) message);
                        break;
                    case LIST_OF_GODS:
                        handleListOfGods((ListOfGods) message);
                        break;
                    case SET_WORKERS:
                        handleSetWorkers((SetWorkers) message);
                        break;
                    default:
                        server.notifyMessageListeners(message);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("The casting of the message was not good");
            } catch (IOException e) {
                if (e.getMessage().toUpperCase().equals("CONNECTION RESET")) {
                    isConnected = false;
                    System.out.println("Client " + clientSocket.getInetAddress() + " disconnected.");
                    //TODO pulire tutto
                }
                else {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void handleRequestNumberOfPlayers(RequestNumberOfPlayers message){
        server.setMaxNumberOfPlayers(message.getNumberOfPlayers());
        //TODO rendere questo settaggio safe
    }

    /**
     * This method sends a message to the client.
     * @param message the message that must be sent.
     * @throws IOException if there are some IO troubles.
     */
    private void send(Message message) throws IOException {
        outputClient.writeObject(message);
        /*outputClient.flush();
        outputClient.reset();*/
    }

    /**
     * This method send a message to the client with the number of players
     * @param numberOfPlayers parameter that must be sent.
     */
    void manageNumberOfPlayers(int numberOfPlayers) {
        NumberOfPlayers message = new NumberOfPlayers(MessageType.NUMBER_PLAYERS);
        message.setNumberOfPlayers(numberOfPlayers);
        //send(message);
        try {
            outputClient.writeObject(message);
        } catch (IOException e) {
            System.out.println();   //TODO scrivere
            e.printStackTrace();
        }
    }

    /**
     * This method send a message to the client to tell him that he is the Challenger.
     */
    void manageChallenger()  {
        YouAreTheRandomPlayer message = new YouAreTheRandomPlayer(MessageType.RANDOM_PLAYER);
        try {
            outputClient.writeObject(message);
        } catch (IOException e) {
            System.out.println();   //TODO scrivere
            e.printStackTrace();
        }
    }

    /**
     * This method receive a list of available gods and calls the view to set this into the model.
     * @param message contains list of available gods.
     * @throws IOException if there are some IO troubles.
     */
    private void handleListOfGods(ListOfGods message) throws IOException {
        ArrayList<GodName> godsAvailable = message.getGodsAvailable();
        GodName chosenGod = message.getChosenGod();
        if (godsAvailable != null) {
            virtualView.receiveListOfGods(godsAvailable);
        }
        else if (chosenGod != null) {
            virtualView.receiveChosenGod(chosenGod);
        }
    }

    /**
     * This method sends a message to the client with the list of available gods he can choose from.
     * @param gods list of available gods.
     * @throws IOException if there are some IO troubles.
     */
    void manageGodsList(ArrayList<GodName> gods) throws IOException {
        ListOfGods message = new ListOfGods(MessageType.LIST_OF_GODS);
        message.setGodsAvailable(gods);
        //send(message);
        outputClient.writeObject(message);
    }

    /**
     * This method sends a message to the client with all the information about the players of the current game.
     * @param usernames the username of each client
     * @param colors the color of each client
     * @param godNames the god chose from each client
     * @throws IOException if there are some IO troubles.
     */
    void managePublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames) throws IOException {
        PublicInformation message = new PublicInformation(MessageType.PUBLIC_INFORMATION);
        message.setUsernames(usernames);
        message.setColors(colors);
        message.setGodNames(godNames);
        //send(message);
        outputClient.writeObject(message);
    }

    /**
     * This method sends a message to the client with an update of the model (a modified slot).
     * @param slot the modified slot.
     */
    void manageUpdateSlot(Slot slot) {
        try {
            Slot updatedSlot = new Slot(slot.getRow(), slot.getColumn());
            updatedSlot.setLevel(slot.getLevel()) ;
            updatedSlot.setOccupied(slot.getIsOccupied()) ;
            updatedSlot.setWorkerColor(slot.getWorkerColor());
            UpdatedSlot message = new UpdatedSlot(MessageType.UPDATE_SLOT);
            message.setUpdatedSlot(updatedSlot);
            outputClient.writeObject(message);
        }catch( IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * This method sends a message to the client to ask the initial position of his workers.
     */
    void manageSetWorkers() {
        try {
            SetWorkers message = new SetWorkers(MessageType.SET_WORKERS);
            //send(message);
            outputClient.writeObject(message);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method receives a list of coordinates (row1, column1, row2, column2) and calls the view to set the player's workers
     * into these posiions and update the model.
     * @param message contains the coordinates.
     */
    void handleSetWorkers(SetWorkers message){
        virtualView.receiveSetWorkers(message.getRowsAndColumns());
    }

}
