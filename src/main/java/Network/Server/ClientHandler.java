package Network.Server;

import Enumerations.Color;
import Enumerations.GodName;
import Enumerations.MessageType;
import Model.Gods.God;
import Model.Slot;
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
    
    public ClientHandler(Socket clientSocket, Server server){
        this.clientSocket = clientSocket;
        this.server = server;
        this.isConnected = true;
    }
    
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
    
    public void dispatchMessages() {
        while (isConnected){
            System.out.println("Started listening 1");
            Message message;
            try {
                message = (Message) inputClient.readObject();
                switch (message.getMessageType()){
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
            }
            catch (IOException e){
                e.printStackTrace();

            }
        }
    }
    
    /**
     * Handle the first connection.
     * If the connection went well, the client is added and his {@link VirtualView} is instantiated.
     * If not, an error message is sent.
     * @throws IOException if an I/O problem is found
     */
    private void handleFirstConnection(Message message) throws IOException {
        System.out.println("handle first connection");
        
        RequestConnection requestConnection = (RequestConnection) message;
        String username = requestConnection.getUsername();
        Color color = requestConnection.getColor();
        
        for (ClientHandler clientHandler: server.getNumberOfPlayers()){
            if (server.getNumberOfPlayers().size()==server.getMaxNumberOfPlayers()){
                ConnectionFailed connectionFailed = new ConnectionFailed(MessageType.CONNECTION_FAILED);
                connectionFailed.setErrorMessage("The game is already started.");
                outputClient.writeObject(connectionFailed);
                isConnected=false;
                System.out.println("I set false the connection");
                return;
            }
            else if (clientHandler.getVirtualView().getUsername().equals(username)){
                ConnectionFailed connectionFailed = new ConnectionFailed(MessageType.CONNECTION_FAILED);
                connectionFailed.setErrorMessage("Somebody else has already taken this username.");
                outputClient.writeObject(connectionFailed);
                return;
            }
            else if (clientHandler.getVirtualView().getColor().equals(color)){
                ConnectionFailed connectionFailed = new ConnectionFailed(MessageType.CONNECTION_FAILED);
                connectionFailed.setErrorMessage("Somebody else has already taken this color.");
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
            // if the number of players is reached, the game is initialized.
        else if (server.getNumberOfPlayers().size() == server.getMaxNumberOfPlayers()-1) {
            server.addPlayer(this);
            server.addPlayerUsernameColorHashMap(username, color);
            ConnectionAccepted connectionAccepted = new ConnectionAccepted(MessageType.CONNECTION_ACCEPTED);
            connectionAccepted.setUserName(username);
            connectionAccepted.setColor(color);
            outputClient.writeObject(connectionAccepted);
            server.initGame();
            return;
        }
        // the player is added to the list of players of the server
        server.addPlayer(this);
        server.addPlayerUsernameColorHashMap(username, color);
        ConnectionAccepted connectionAccepted = new ConnectionAccepted(MessageType.CONNECTION_ACCEPTED);
        connectionAccepted.setUserName(username);
        connectionAccepted.setColor(color);
        outputClient.writeObject(connectionAccepted);
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
     * @throws IOException if there are some IO troubles.
     */
    void manageNumberOfPlayers(int numberOfPlayers) throws IOException {
        NumberOfPlayers message = new NumberOfPlayers(MessageType.NUMBER_PLAYERS);
        message.setNumberOfPlayers(numberOfPlayers);
        send(message);
    }

    /**
     * This method send a message to the client to tell him that he is the Challenger.
     * @throws IOException if there are some IO troubles.
     */
    void manageChallenger() throws IOException {
        YouAreTheRandomPlayer message = new YouAreTheRandomPlayer(MessageType.RANDOM_PLAYER);
        send(message);
    }

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

    void manageGodsList(ArrayList<GodName> gods) throws IOException {
        ListOfGods message = new ListOfGods(MessageType.LIST_OF_GODS);
        message.setGodsAvailable(gods);
        send(message);
    }

    void managePublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames) throws IOException {
        PublicInformation message = new PublicInformation(MessageType.PUBLIC_INFORMATION);
        message.setUsernames(usernames);
        message.setColors(colors);
        message.setGodNames(godNames);
        send(message);
    }

    //TODO ask david if clone is usable to send the changed slot
    void manageUpdateSlot(Slot slot) {
        try {
            Slot updatedSlot = new Slot(slot.getRow(), slot.getColumn());
            updatedSlot.setLevel(slot.getLevel()) ;
            updatedSlot.setOccupied(slot.getIsOccupied()) ;
            updatedSlot.setWorkerColor(slot.getWorkerColor());
            UpdatedSlot message = new UpdatedSlot(MessageType.UPDATE_SLOT);
            message.setUpdatedSlot(updatedSlot);
            send(message);
        }catch( IOException e ) {
            e.printStackTrace();
        }
        //TODO there has been and error message



    }

    void manageSetWorkers() {
        try {
            SetWorkers message = new SetWorkers(MessageType.SET_WORKERS);
            send(message);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    void handleSetWorkers(SetWorkers message ){
        virtualView.receiveSetWorkers(message.getRowsAndColumns());
    }

}
