package Network.Server;

import Enumerations.Color;
import Enumerations.MessageType;
import Network.Message.ConnectionAccepted;
import Network.Message.ErrorMessages.ConnectionFailed;
import Network.Message.RequestNumberOfPlayers;
import Network.Message.Message;
import Network.Message.RequestConnection;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private Server server;
    VirtualView virtualView;
    ObjectInputStream inputClient;
    ObjectOutputStream outputClient;
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
        virtualView = new VirtualView(username, color);
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
}
