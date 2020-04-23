package Network.Client;

import Network.Message.ErrorMessages.ConnectionFailed;
import Network.Message.RequestNumberOfPlayers;
import Network.Message.Message;
import Network.Message.RequestConnection;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkHandler implements Runnable{
    private final Client client;
    private Socket serverSocket;
    private boolean firstConnection;
    ObjectInputStream inputServer;
    ObjectOutputStream outputServer;
    
    public NetworkHandler(Client client, Socket serverSocket){
        this.client = client;
        firstConnection = true;
        this.serverSocket = serverSocket;
    }
    
    
    @Override
    public void run() {
        try {
            if (firstConnection)
                handleFirstConnection();
            dispatchMessages();
        }
        catch (IOException e){
            System.out.println("Connection dropped.");
        }
        catch (ClassNotFoundException e){
            System.out.println("Error in casting from abstract Message to one of its subclasses.");
        }
    }
    
    public void handleFirstConnection() throws IOException, ClassNotFoundException {
        String username = client.getView().askUsername();
        Color color = client.getView().askColorWorkers();
    
        outputServer = new ObjectOutputStream(serverSocket.getOutputStream());
    
        RequestConnection requestConnection = new RequestConnection();
        requestConnection.setColor(color);
        requestConnection.setUsername(username);
        outputServer.writeObject(requestConnection);
        firstConnection = false;
    }
    
    public void dispatchMessages() throws IOException, ClassNotFoundException {
        while (true){
            System.out.println("Started listening");
            inputServer = new ObjectInputStream(serverSocket.getInputStream());
            Message message = (Message) inputServer.readObject();
            
            try {
                switch (message.getMessageType()){
                    case CONNECTION_FAILED:
                        handleConnectionFailed((ConnectionFailed) message);
                        firstConnection = true;
                    case REQUEST_NUMBER_OF_PLAYERS:
                        handleRequestNumberOfPlayers();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private void handleConnectionFailed(ConnectionFailed connectionFailed) throws IOException, ClassNotFoundException {
        if (connectionFailed.getErrorMessage().equals("Somebody else has already taken this username.")
                || connectionFailed.getErrorMessage().equals("Somebody else has already taken this color.")){
            client.getView().print(connectionFailed.getErrorMessage());
            handleFirstConnection();
        }
        else if (connectionFailed.getErrorMessage().equals("The game is already started.")){
            try {
                serverSocket.close();
            }
            catch (IOException e){
                System.out.println("Unable to close server socket");
            }
        }
    }
    
    private void handleRequestNumberOfPlayers() throws IOException {
        System.out.println("Entrato in handleRequestNumberOfPlayers");
        int numberOfPlayers = client.getView().askNumberOfPlayers();
        RequestNumberOfPlayers requestNumberOfPlayers = new RequestNumberOfPlayers();
        requestNumberOfPlayers.setNumberOfPlayers(numberOfPlayers);
        outputServer.writeObject(requestNumberOfPlayers);
    }
}
