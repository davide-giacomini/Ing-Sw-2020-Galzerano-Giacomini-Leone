package Network.Server;

import Enumerations.MessageType;
import Network.Message.ErrorMessages.ConnectionFailed;
import Network.Message.RequestNumberOfPlayers;
import Network.Message.Message;
import Network.Message.RequestConnection;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private Server server;
    private boolean firstConnection;
    VirtualView virtualView;
    ObjectInputStream inputClient;
    ObjectOutputStream outputClient;
    
    public ClientHandler(Socket clientSocket, Server server){
        this.clientSocket = clientSocket;
        this.server = server;
        this.firstConnection = true;
    }
    
    @Override
    public void run() {
        try {
            if (firstConnection)
                handleFirstConnection();
            dispatchMessages();
            //TODO sistemare l'ordine con cui viene fatto dispatchMessages
        }
        catch (IOException e){
            System.out.println("client " + clientSocket.getInetAddress() + " connection dropped.");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            System.out.println("Serialization failed.");
        }
    }
    
    public VirtualView getVirtualView() {
        return virtualView;
    }
    
    /**
     * Handle the first connection.
     * If the connection went well, the client is added and his {@link VirtualView} is instantiated.
     * If not, an error message is sent.
     * @throws IOException if an I/O problem is found
     * @throws ClassNotFoundException if the cast from {@link Message} to the subclass is incorrect.
     */
    public void handleFirstConnection() throws IOException, ClassNotFoundException {
        System.out.println("handle first connection");
        inputClient = new ObjectInputStream(clientSocket.getInputStream());
        outputClient = new ObjectOutputStream(clientSocket.getOutputStream());
        
        Message message = (Message) inputClient.readObject();
        if (message.getMessageType()== MessageType.REQUEST_CONNECTION) {
            RequestConnection requestConnection = (RequestConnection) message;
            String username = requestConnection.getUsername();
            Color color = requestConnection.getColor();
            
            for (ClientHandler clientHandler: server.getPlayers()){
                if (clientHandler.getVirtualView().getUsername().equals(username)){
                    ConnectionFailed connectionFailed = new ConnectionFailed();
                    connectionFailed.setErrorMessage("Somebody else has already taken this username.");
                    outputClient.writeObject(connectionFailed);
                    return;
                }
                else if (clientHandler.getVirtualView().getColor().equals(color)){
                    ConnectionFailed connectionFailed = new ConnectionFailed();
                    connectionFailed.setErrorMessage("Somebody else has already taken this color.");
                    outputClient.writeObject(connectionFailed);
                    return;
                }
                else if (server.getPlayers().size()==server.getMaxNumberOfPlayers()){
                    ConnectionFailed connectionFailed = new ConnectionFailed();
                    connectionFailed.setErrorMessage("The game is already started.");
                    outputClient.writeObject(connectionFailed);
                    return;
                }
            }
            
            // the virtual view is added and it is added to the message listeners.
            virtualView = new VirtualView(username, color);
            requestConnection.addListener(virtualView);
            // if the player is the first, he will decide the number of players
            if (server.getPlayers().size()==0)
                outputClient.writeObject(new RequestNumberOfPlayers());
            // if the number of players is reached, the game is initialized.
            else if (server.getPlayers().size()== server.getMaxNumberOfPlayers())
                server.initGame();
            
            server.addClient(this);
            firstConnection = false;
        }
        else
            System.out.println("The type of the message is incorrect.");
    }
    
    public void dispatchMessages() throws IOException{
        while (true){
            System.out.println("Started listening");
            inputClient = new ObjectInputStream(clientSocket.getInputStream());
            Message message;
            try {
                message = (Message) inputClient.readObject();
                message.notifyListeners();
            } catch (ClassNotFoundException e) {
                System.out.println("The casting of the message was not good");
            }
            catch (IOException e){
                e.printStackTrace();
            }
            
        }
    }
}
