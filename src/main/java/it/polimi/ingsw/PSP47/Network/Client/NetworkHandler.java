package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
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
    private final Client client;    //TODO passare la view al posto del client.
    private Socket serverSocket;
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
        this.isConnected = true;
        this.networkHandlerVisitor= new NetworkHandlerVisitor(this);
        client.getView().addViewListener(this);
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
    
//            new Thread(() -> {
//                try {
//                    try {
//                        Thread.sleep(40000);
//                        outputServer.writeObject(new Ping());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }).start();
            
            dispatchMessages();
        }
        catch (IOException e){
            System.out.println("Connection failed.");
        }
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
                    case FIRST_CONNECTION:
                        handleFirstConnection();
                        break;
                    case REQUEST_PLAYERS_NUMBER:
                        client.getView().askNumberOfPlayers();
                        break;
                    case WRONG_PARAMETERS:
                        client.getView().showMessage(((WrongParameters) message).getErrorMessage());
                        handleFirstConnection();
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
                        client.getView().showMessage(((ConnectionFailed) message).getErrorMessage());
                        isConnected = false;
                        break;
                    case ERROR:
                        String errorText = ((ErrorMessage) message).getErrorText();
                        client.getView().showMessage(errorText);
                        break;
                    case LIST_OF_GODS:
                        VisitableListOfGods visitableGods =(VisitableListOfGods) message.getContent();
                        ArrayList<GodName> godNames =  visitableGods.getGodNames();
                        client.getView().chooseYourGod(godNames);
                        break;
                    case NUMBER_PLAYERS:
                        NumberOfPlayers messagePlayers = (NumberOfPlayers) message;
                        int number = messagePlayers.getNumberOfPlayers();
                        client.getView().getGameView().setNumberOfPlayers(number);
                        break;
                    case PUBLIC_INFORMATION:
                        PublicInformation messageInfo = (PublicInformation) message;

                        client.getView().getGameView().setUsernames(messageInfo.getUsernames());
                        client.getView().getGameView().setColors(messageInfo.getColors());
                        client.getView().getGameView().setGods(((PublicInformation) message).getGodNames());

                        client.getView().showPublicInformation();
                        break;
                    case UPDATE_SLOT:
                        UpdatedSlot messageSlot = (UpdatedSlot) message;
                        Slot slot = messageSlot.getUpdatedSlot();
                        client.getView().getGameView().getBoardView().setSlot(slot);
                        client.getView().showCurrentBoard();
                        break;
                    case CHALLENGER:
                        client.getView().challengerWillChooseThreeGods();
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
     * This method serializes the messages and sends them to the server.
     *
     * @param message the message that must be sent.
     */
    public void send(Message message) {
        try {
            outputServer.writeObject(message);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " + message.toString() + " message.");
            isConnected = false;
            e.printStackTrace();
        }
    }
    
    
    /**
     * This method handles the first connection with the server, asking to the user to choose their username and
     * the color they prefer for their workers.
     */
    public void handleFirstConnection() {
        client.getView().askFirstConnection();
    }

    @Override
    public void update (Visitable visitableObject){
        visitableObject.accept(networkHandlerVisitor);
    }
}

