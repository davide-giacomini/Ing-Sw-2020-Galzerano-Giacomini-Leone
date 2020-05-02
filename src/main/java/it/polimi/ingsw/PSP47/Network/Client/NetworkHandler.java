package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Network.Message.ConnectionFailed;
import it.polimi.ingsw.PSP47.View.ViewListener;
import it.polimi.ingsw.PSP47.Visitor.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class handles the transfer of messages between the client and the server.
 */
public class NetworkHandler implements Runnable, ViewListener {
    private final Client client;
    private Socket serverSocket;
    private boolean firstConnection;
    private ObjectInputStream inputServer;
    private ObjectOutputStream outputServer;
    private boolean isConnected;
    private NetworkHandlerVisitor networkHandlerVisitor;
    
    /**
     * This constructor set up the management between the {@link Client} and the {@link it.polimi.ingsw.PSP47.Network.Server.Server}.
     *
     * @param client the {@link Client} to be handled.
     * @param serverSocket the socket of the {@link it.polimi.ingsw.PSP47.Network.Server.Server} the user wants to connect to.
     */
    public NetworkHandler(Client client, Socket serverSocket){
        this.client = client;
        this.serverSocket = serverSocket;
        firstConnection = true;
        this.isConnected = true;
        this.networkHandlerVisitor= new NetworkHandlerVisitor(this);
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
            
            dispatchMessages();
        }
        catch (IOException e){
            System.out.println("Connection failed.");
        }
    }
    
    /**
     * This method handles the first connection with the server, asking to the user to choose their username and
     * the color they prefer for their workers.
     */
    public void handleFirstConnection() {
        client.getView().askFirstConnection();

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
                    case ASK_WORKER_POSITION:
                        client.getView().askWhereToPositionWorkers();
                        break;
                    case  CHOOSE_ACTION:
                        client.getView().askAction();
                        break;
                    case CHOOSE_WORKER:
                        client.getView().askWhichWorkerToUse();
                        break;
                    case CONNECTION_ACCEPTED:
                        VisitableInformation visitableConnectionAccepted = (VisitableInformation)  message.getContent();
                        String username = visitableConnectionAccepted.getUsername();
                        Color color = visitableConnectionAccepted.getColor();

                        client.getView().getGameView().setMyUsername(username);
                        client.getView().getGameView().setMyColor(color);
                        break;
                    case CONNECTION_FAILED:
                        handleConnectionFailed((ConnectionFailed) message);
                        firstConnection = true;
                        break;
                    case ERROR:
                        ErrorMessage messageError = (ErrorMessage) message;
                        String errorText = messageError.getErrorText();
                        client.getView().showMessage(errorText);
                        //TODO ADD FIRST PLAYEr CoNNECTION
                        //godchosen is never recived by the network handler but only sent
                    case LIST_OF_GODS:
                        VisitableListOfGods visitableGods =(VisitableListOfGods) message.getContent();
                        ArrayList<GodName> godnames =  visitableGods.getGodNames();
                        client.getView().chooseYourGod(godnames);
                        break;
                    case NUMBER_PLAYERS:
                        NumberOfPlayers messagePlayers = (NumberOfPlayers) message;
                        int number = messagePlayers.getNumberOfPlayers();
                        client.getView().getGameView().setNumberOfPlayers(number);
                        break;
                        //TODO CHaNGE OPPONENT DISC MESSAGE
                    case PUBLIC_INFORMATION:
                        PublicInformation messageInfo = (PublicInformation) message;

                        client.getView().getGameView().setUsernames(messageInfo.getUsernames());
                        client.getView().getGameView().setColors(messageInfo.getColors());
                        client.getView().getGameView().setGods(((PublicInformation) message).getGodNames());

                        client.getView().showPublicInformation();
                        break;
                    case REQUEST_CONNECTION:
                        handleFirstConnection();
                        break;
                        //request disconnection is never received but only sent
                    case REQUEST_NUMBER_OF_PLAYERS:
                        client.getView().askNumberOfPlayers();
                        break;
                        //TODO ADD TIMERUPDATE MESSAHE
                    case UPDATE_SLOT:
                        UpdatedSlot messageSlot = (UpdatedSlot) message;
                        Slot slot = messageSlot.getUpdatedSlot();
                        client.getView().getGameView().getBoardView().setSlot(slot);
                        client.getView().showCurrentBoard();
                        break;
                        //TODO DELETE WAIT CHOOSE MESSAGE
                    case WAIT_CHOOSE_NUMBER_PLAYERS:
                        client.getView().showMessage("Please wait. The first player is choosing the number of players right now.");
                        break;
                    case CHALLENGER:
                        client.getView().challengerWillChooseThreeGods();
                    default:
                        //message.handleClientSide(client, outputServer);
                        break;
                }
            }
            catch (IOException e){
                client.getView().showMessage("We are sorry: " +
                        "the server  at the address " + serverSocket.getInetAddress() + " disconnected.");
                isConnected = false;
                //e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                client.getView().showMessage("Error in casting during the readObject.");
                isConnected = false;
                //e.printStackTrace();
            }
        }
        
        client.getView().showMessage("Game closed.");
    }
    
    /**
     * Here a failure in the connection is analyzed.
     * If the user wrote the wrong username or the wrong color, this method proceeds for a reconnection, calling back
     * the {@link #handleFirstConnection()}.
     * Otherwise, if the game is already started, the connection closes.
     *
     * @param connectionFailedMessage it's the message with its parameters.
     */
    private void handleConnectionFailed(ConnectionFailed connectionFailedMessage) {
        VisitableString visitableString = (VisitableString) connectionFailedMessage.getContent();
        String text = visitableString.getString();
        client.getView().showMessage(text);
        
        if (connectionFailedMessage.getErrorMessage().equals("Somebody else has already taken this username. Try another.")         //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
                || connectionFailedMessage.getErrorMessage().equals("Somebody else has already taken this color. Try another.")){   //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
            handleFirstConnection();
        }
        else if (connectionFailedMessage.getErrorMessage().equals("The game is already started. Try later.")){         //WARNING: this message MUST be equal to the one checked in handleFirstConnection in the client handler of the server
            try {
                isConnected = false;
                serverSocket.close();
            }
            catch (IOException e){
                System.out.println("Unable to close server socket");
            }
        }
    }

    @Override
    public void update (Visitable visitableObject){

        visitableObject.accept(networkHandlerVisitor);
    }


    public void send(Message newMessage){
        try {
            outputServer.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " +this.toString()+" message.");
            e.printStackTrace();
        }
    }
}
