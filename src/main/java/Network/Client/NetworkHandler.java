package Network.Client;

import Enumerations.Color;
import Enumerations.GodName;
import Enumerations.MessageType;
import Network.Message.*;
import Network.Message.ErrorMessages.ConnectionFailed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class handles the transfer of messages between the client and the server.
 */
public class NetworkHandler implements Runnable{
    private final Client client;
    private Socket serverSocket;
    private boolean firstConnection;
    private ObjectInputStream inputServer;
    private ObjectOutputStream outputServer;
    private boolean isConnected;
    
    /**
     * This constructor set up the management between the {@link Client} and the {@link Network.Server.Server}.
     *
     * @param client the {@link Client} to be handled.
     * @param serverSocket the socket of the {@link Network.Server.Server} the user wants to connect to.
     */
    public NetworkHandler(Client client, Socket serverSocket){
        this.client = client;
        this.serverSocket = serverSocket;
        firstConnection = true;
        isConnected = true;
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
            
            if (firstConnection)
                handleFirstConnection();
            dispatchMessages();
        }
        catch (IOException e){
            System.out.println("Connection failed.");
        }
    }
    
    /**
     * This method handles the first connection with the server, asking to the user to choose their username and
     * the color they prefer for their workers.
     *
     * @throws IOException if there are troubles in sending the message to the server.
     */
    public void handleFirstConnection() throws IOException {
        String username = client.getView().askUsername();
        Color color = client.getView().askColorWorkers();
        
        RequestConnection requestConnection = new RequestConnection(MessageType.REQUEST_CONNECTION);
        requestConnection.setColor(color);
        requestConnection.setUsername(username);
        outputServer.writeObject(requestConnection);
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
                    case REQUEST_NUMBER_OF_PLAYERS:
                        handleRequestNumberOfPlayers();
                        break;
                    case CONNECTION_ACCEPTED:
                        handleConnectionAccepted((ConnectionAccepted) message);
                        break;
                    case RANDOM_PLAYER:
                        handleRandomPlayer();
                        break;
                    case LIST_OF_GODS:
                        handleListOfGods((ListOfGods) message);
                        break;
                    case PUBLIC_INFORMATION:
                        handlePublicInformation ((PublicInformation) message);
                        break;
                    case NUMBER_PLAYERS:
                        handleNumberOfPlayers((NumberOfPlayers) message);
                        break;
                    case UPDATE_SLOT:
                        handleUpdatedSlot((UpdatedSlot) message);
                        break;
                    case SET_WORKERS:
                        handleSetWorkers((SetWorkers) message);
                        break;

                }
            }
            catch (IOException e){
                e.printStackTrace();
                isConnected = false;
            }
            catch (ClassNotFoundException e){
                System.out.println("Error in casting from abstract Message to one of its subclasses.");
                if (e.getMessage().toUpperCase().equals("CONNECTION RESET")) {
                    isConnected = false;
                    System.out.println("Client " + serverSocket.getInetAddress() + " disconnected.");
                    //TODO pulire (se serve)
                }
                else {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This methods calls the view to ask for the gods that must be used in the game and send a message
     * to the server with this information.
     * @throws IOException if there are some IO troubles.
     */
    private void handleRandomPlayer() throws IOException {
        ArrayList<GodName> gods = client.getView().challengerWillChooseThreeGods();
        ListOfGods message = new ListOfGods(MessageType.LIST_OF_GODS);
        message.setGodsAvailable(gods);
        //send(message);
        outputServer.writeObject(message);
    }


    private void handleConnectionFailed(ConnectionFailed connectionFailed) throws IOException, ClassNotFoundException {
        if (connectionFailed.getErrorMessage().equals("Somebody else has already taken this username.")         //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
                || connectionFailed.getErrorMessage().equals("Somebody else has already taken this color.")){   //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
            client.getView().print(connectionFailed.getErrorMessage());
            handleFirstConnection();
        }
        else if (connectionFailed.getErrorMessage().equals("The game is already started. Try later.")){         //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
            try {
                isConnected = false;
                serverSocket.close();
            }
            catch (IOException e){
                System.out.println("Unable to close server socket");
            }
        }
    }
    
    private void handleRequestNumberOfPlayers() throws IOException {
        System.out.println("Entrato in handleRequestNumberOfPlayers");
        int numberOfPlayers = 0;
        while (numberOfPlayers<2 || numberOfPlayers>3)
            numberOfPlayers = client.getView().askNumberOfPlayers();
        RequestNumberOfPlayers requestNumberOfPlayers = new RequestNumberOfPlayers(MessageType.REQUEST_NUMBER_OF_PLAYERS);
        requestNumberOfPlayers.setNumberOfPlayers(numberOfPlayers);
        outputServer.writeObject(requestNumberOfPlayers);
    }
    
    public void handleConnectionAccepted(ConnectionAccepted message){
        String username = message.getUserName();
        Color color = message.getColor();

        client.getView().getViewDatabase().setMyUsername(username);
        client.getView().getViewDatabase().setMyColor(color);
    }

    /**
     * This method calls the view to ask for the god that the player has been chosed and send a message
     * to the server with this information.
     * @param message contains the list of available gods.
     * @throws IOException if there are some IO troubles.
     */
    public void handleListOfGods(ListOfGods message) throws IOException {
       GodName chosenGod = client.getView().chooseYourGod(message.getGodsAvailable());
       ListOfGods newMessage = new ListOfGods(MessageType.LIST_OF_GODS);
       newMessage.setChosenGod(chosenGod);
       outputServer.writeObject(newMessage);
    }

    /**
     * This method calls the view to set all the informations about the players into the ViewDatabase and to
     * print this in the screen.
     * @param message contains all these informations.
     */
    public void handlePublicInformation(PublicInformation message) {

        client.getView().getViewDatabase().setUsernames(message.getUsernames());
        client.getView().getViewDatabase().setColors(message.getColors());
        client.getView().getViewDatabase().setGods(message.getGodNames());

        client.getView().showPublicInformation();
    }

    /**
     * This method calls the view to set the number of players into the viewDatabase.
     * @param message contains the number of players.
     */
    public void handleNumberOfPlayers(NumberOfPlayers message) {
        client.getView().getViewDatabase().setNumberOfPlayers(message.getNumberOfPlayers());
    }

    /**
     * This method calls the view to update a modified slot and to print the updated board into the screen.
     * @param message contains the modified slot.
     */
    public void handleUpdatedSlot(UpdatedSlot message){
        client.getView().getViewDatabase().getBoardView().setSlot(message.getUpdatedSlot());
        client.getView().showCurrentBoard();
    }

    /**
     * This methods calls the view to ask for the initial position of the workers.
     * @param message doesn't contain anything.
     * @throws IOException if there are some IO troubles.
     */
    public void handleSetWorkers(SetWorkers message) throws IOException {
        int[] rowsAndColumns;
        rowsAndColumns = client.getView().askWhereToPositionWorkers();

        SetWorkers newMessage = new SetWorkers(MessageType.SET_WORKERS);
        newMessage.setRowsAndColumns(rowsAndColumns);
        outputServer.writeObject(newMessage);
    }
}
