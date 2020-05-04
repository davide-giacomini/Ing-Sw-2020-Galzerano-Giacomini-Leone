package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Message.ConnectionFailed;
import it.polimi.ingsw.PSP47.View.View;
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
    private View view;
    private Socket serverSocket;
    private ObjectInputStream inputServer;
    private ObjectOutputStream outputServer;
    private boolean isConnected;
    private NetworkHandlerVisitor networkHandlerVisitor;
    
    /**
     * This constructor set up the management between the {@link Client} and the {@link it.polimi.ingsw.PSP47.Network.Server.Server}.
     *
     * @param view the {@link View} passed by the Client.
     * @param serverSocket the socket of the {@link it.polimi.ingsw.PSP47.Network.Server.Server} the user wants to connect to.
     */
    public NetworkHandler(View view, Socket serverSocket){
        this.view = view;
        this.serverSocket = serverSocket;
        this.isConnected = true;
        this.networkHandlerVisitor= new NetworkHandlerVisitor(this);
        view.addViewListener(this);
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
                        view.askNumberOfPlayers();
                        break;
                    case WRONG_PARAMETERS:
                        view.showMessage(((WrongParameters) message).getErrorMessage());
                        handleFirstConnection();
                        break;
                    case ASK_WORKER_POSITION:
                        view.askWhereToPositionWorkers();
                        break;
                    case  CHOOSE_ACTION:
                        view.askAction();
                        break;
                    case CHOOSE_WORKER:
                        view.askWhichWorkerToUse();
                        break;
                    case CONNECTION_ACCEPTED:
                        VisitableInformation visitableConnectionAccepted = (VisitableInformation)  message.getContent();
                        String username = visitableConnectionAccepted.getUsername();
                        Color color = visitableConnectionAccepted.getColor();

                        view.getGameView().setMyUsername(username);
                        view.getGameView().setMyColor(color);
                        break;
                    case CONNECTION_FAILED:
                        view.showMessage(((ConnectionFailed) message).getErrorMessage());
                        isConnected = false;
                        break;
                    case ERROR:
                        String errorText = ((ErrorMessage) message).getErrorText();
                        view.showMessage(errorText);
                        break;
                    case LIST_OF_GODS:
                        VisitableListOfGods visitableGods =(VisitableListOfGods) message.getContent();
                        ArrayList<GodName> godNames =  visitableGods.getGodNames();
                        view.chooseYourGod(godNames);
                        break;
                    case NUMBER_PLAYERS:
                        NumberOfPlayers messagePlayers = (NumberOfPlayers) message;
                        int number = messagePlayers.getNumberOfPlayers();
                        view.getGameView().setNumberOfPlayers(number);
                        break;
                    case PUBLIC_INFORMATION:
                        PublicInformation messageInfo = (PublicInformation) message;

                        view.getGameView().setUsernames(messageInfo.getUsernames());
                        view.getGameView().setColors(messageInfo.getColors());
                        view.getGameView().setGods(((PublicInformation) message).getGodNames());

                        view.showPublicInformation();
                        break;
                    case UPDATE_SLOT:
                        UpdatedSlot messageSlot = (UpdatedSlot) message;
                        Slot slot = messageSlot.getUpdatedSlot();
                        view.getGameView().getBoardView().setSlot(slot);
                        view.showCurrentBoard();
                        break;
                    case CHALLENGER:
                        view.challengerWillChooseThreeGods();
                        break;
                }
            }
            catch (IOException e){
                view.showMessage("We are sorry: " +
                        "the server  at the address " + serverSocket.getInetAddress() + " disconnected.");
                isConnected = false;
                //e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                view.showMessage("Error in casting during the readObject.");
                isConnected = false;
                //e.printStackTrace();
            }
        }

        view.showMessage("Game closed.");
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
        view.askFirstConnection();
    }

    @Override
    public void update (Visitable visitableObject){
        visitableObject.accept(networkHandlerVisitor);
    }
}

