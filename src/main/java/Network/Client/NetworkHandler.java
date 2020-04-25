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

public class NetworkHandler implements Runnable{
    private final Client client;
    private Socket serverSocket;
    private boolean firstConnection;
    private ObjectInputStream inputServer;
    private ObjectOutputStream outputServer;
    private boolean isConnected;
    
    public NetworkHandler(Client client, Socket serverSocket){
        this.client = client;
        firstConnection = true;
        this.serverSocket = serverSocket;
        isConnected = true;
    }
    
    
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
            System.out.println("Connection dropped.");
        }
    }
    
    public void handleFirstConnection() throws IOException {
        String username = client.getView().askUsername();
        Color color = client.getView().askColorWorkers();
        
        RequestConnection requestConnection = new RequestConnection(MessageType.REQUEST_CONNECTION);
        requestConnection.setColor(color);
        requestConnection.setUsername(username);
        outputServer.writeObject(requestConnection);
        firstConnection = false;
    }
    
    public void dispatchMessages() {
        while (isConnected){
            System.out.println("Started listening 2");
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
                }
            }
            catch (IOException e){
                e.printStackTrace();
                isConnected = false;
            }
            catch (ClassNotFoundException e){
                System.out.println("Error in casting from abstract Message to one of its subclasses.");
                e.printStackTrace();
            }
        }
    }

    private void handleRandomPlayer() throws IOException {
        ArrayList<GodName> gods = client.getView().challengerWillChooseThreeGods();
        ListOfGods message = new ListOfGods(MessageType.LIST_OF_GODS);
        message.setGodsAvailable(gods);
        send(message);
    }

    private void handleConnectionFailed(ConnectionFailed connectionFailed) throws IOException, ClassNotFoundException {
        if (connectionFailed.getErrorMessage().equals("Somebody else has already taken this username.")
                || connectionFailed.getErrorMessage().equals("Somebody else has already taken this color.")){
            client.getView().print(connectionFailed.getErrorMessage());
            handleFirstConnection();
        }
        else if (connectionFailed.getErrorMessage().equals("The game is already started.")){
            try {
                System.out.println("Debugging = the game is already started");
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

    private void send(Message message) throws IOException {
        outputServer.writeObject(message);
        outputServer.flush();
        outputServer.reset();
    }

    public void handleListOfGods(ListOfGods message) throws IOException {
       GodName chosenGod = client.getView().chooseYourGod(message.getGodsAvailable());
       ListOfGods mess = new ListOfGods(MessageType.LIST_OF_GODS);
       mess.setChosenGod(chosenGod);
       send(mess);
    }

    public void handlePublicInformation(PublicInformation message ) throws IOException{

        client.getView().getViewDatabase().setUsernames(message.getUsernames());
        client.getView().getViewDatabase().setColors(message.getColors());
        client.getView().getViewDatabase().setGods(message.getGodNames());

        client.getView().showPublicInformation();
    }

    public void handleNumberOfPlayers(NumberOfPlayers message) {
        client.getView().getViewDatabase().setNumberOfPlayers(message.getNumberOfPlayers());
    }
}
