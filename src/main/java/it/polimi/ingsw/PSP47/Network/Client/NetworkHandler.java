package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.CurrentMoment;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.View.ViewListener;
import it.polimi.ingsw.PSP47.View.View;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;
import it.polimi.ingsw.PSP47.Visitor.VisitableListOfGods;

import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class handles the transfer of messages between the client and the server.
 */
public class NetworkHandler implements Runnable, ViewListener {
    private final View view;
    private final Socket serverSocket;
    private ObjectInputStream inputServer;
    private ObjectOutputStream outputServer;
    private boolean isConnected = true;
    private final NetworkHandlerVisitor networkHandlerVisitor = new NetworkHandlerVisitor(this);
    private final ExecutorService messageExecutor = Executors.newSingleThreadExecutor();
    private ClientTimer clientTimer;
    
    /**
     * This constructor set up the management between the {@link Client} and the {@link it.polimi.ingsw.PSP47.Network.Server.Server}.
     *
     * @param view the {@link View} passed by the Client.
     * @param serverSocket the socket of the {@link it.polimi.ingsw.PSP47.Network.Server.Server} the user wants to connect to.
     */
    public NetworkHandler(View view, Socket serverSocket){
        this.view = view;
        this.serverSocket = serverSocket;

        try {
            outputServer = new ObjectOutputStream(serverSocket.getOutputStream());
            inputServer = new ObjectInputStream(serverSocket.getInputStream());
        }
        catch (IOException e){
            System.out.println("Connection failed.");
            endConnection();
//            e.printStackTrace();
        }
    }
    
    /**
     * This method instantiates the {@link ObjectInputStream} and the {@link ObjectOutputStream} with
     * {@link InputStream} and {@link OutputStream} of the server's socket in order to
     * handle serialization.
     */
    @Override
    public void run() {
        clientTimer = new ClientTimer(this);
        new Thread(clientTimer).start();

        // Send a ping each 5 seconds.
        new Thread(() -> {
            while (isConnected) {
                try {
                    send(new Ping());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    endConnection();
//                    e.printStackTrace();
                }
            }
        }).start();

        dispatchMessages();
    }
    
    /**
     * This method dispatches the messages coming from the the server. If the message is a ping it is handled,
     * otherwise it forwards them to the runnable class {@link MessageHandler}.
     */
    public void dispatchMessages() {
        System.out.println("Started listening to the server.");
        
        while (isConnected){
            Message message;
            try {
                message = (Message) inputServer.readObject();
                switch (message.getMessageType()) {
                    case PING:
                        clientTimer.resetTime();
                        break;
                    default:
                        messageExecutor.submit(new MessageHandler(message, this));
                        break;
                }
            }
            catch (IOException e){
                if (isConnected)
                    endConnection();

                //e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                view.showErrorMessage("Error in casting during the readObject.");

                if (isConnected)
                    endConnection();

//                e.printStackTrace();
            }
        }

        view.showEnd();
//        System.out.println(messageExecutor.isShutdown());
//        System.out.println(messageExecutor.isTerminated());
    }

    /**
     * This Runnable class handles the messages living the networkHandler free to receive ping messages.
     */
    private class MessageHandler implements Runnable {
        Message message;
        NetworkHandler networkHandler;
        Visitable visitable;

        public MessageHandler(Message message, NetworkHandler networkHandler){
            this.message = message;
            this.networkHandler = networkHandler;
        }

        @Override
        public void run() {
            switch (message.getMessageType()) {
                case FIRST_CONNECTION:
                    view.getGameView().updateMoment(CurrentMoment.START);
                    handleFirstConnection();
                    break;
                case REQUEST_PLAYERS_NUMBER:
                    view.getGameView().updateMoment(CurrentMoment.CHOOSE_PLAYERS);
                    view.askNumberOfPlayers();
                    break;
                case WRONG_PARAMETERS:
                    view.getGameView().updateMoment(CurrentMoment.START);
                    view.showErrorMessage(((WrongParameters) message).getErrorMessage());
                    handleFirstConnection();
                    break;
                case CONNECTION_ACCEPTED:
                    visitable = ((VisitableMessage) message).getContent();
                    VisitableInformation visitableConnectionAccepted = (VisitableInformation) visitable;
                    String username = visitableConnectionAccepted.getUsername();
                    Color color = visitableConnectionAccepted.getColor();

                    view.getGameView().setMyUsername(username);
                    view.getGameView().setMyColor(color);
                    break;
                case CONNECTION_FAILED:
                    view.showErrorMessage(((ConnectionFailed) message).getErrorMessage());
                    endConnection();
                    break;
                case ERROR:
                    String errorText = ((ErrorMessage) message).getErrorText();
                    view.showErrorMessage(errorText);
                    break;
                case IMPORTANT:
                    String text = ((ImportantMessage) message).getText();
                    view.showImportantMessage(text);
                    break;
                case LIST_OF_GODS:
                    view.getGameView().updateMoment(CurrentMoment.CHOOSE_CARD);
                    visitable = ((VisitableMessage) message).getContent();
                    VisitableListOfGods visitableGods = (VisitableListOfGods) visitable;
                    ArrayList<GodName> godNames = visitableGods.getGodNames();
                    view.chooseYourGod(godNames);
                    break;
                case PLAYERS_NUMBER:
                    PlayersNumber messagePlayers = (PlayersNumber) message;
                    int number = messagePlayers.getNumberOfPlayers();
                    view.getGameView().setNumberOfPlayers(number);
                    break;
                case CHALLENGER:
                    view.getGameView().updateMoment(CurrentMoment.CHALLENGER);
                    YouAreTheChallenger messageNames = (YouAreTheChallenger) message;
                    ArrayList<String>usernames = messageNames.getUsernames();
                    view.challengerWillChooseThreeGods(usernames);
                    break;
                case LOSING:
                    view.getGameView().updateMoment(CurrentMoment.LOSE);
                    view.theLoserIs(((ImportantMessage) message).getText());
                    break;
                case WINNING:
                    view.getGameView().updateMoment(CurrentMoment.WIN);
                    view.theWinnerIs(((ImportantMessage) message).getText());
                    break;
                case START_GAME:
                    view.getGameView().setStart(true);
                    view.showGame();
                    break;
                case ASK_WORKER_POSITION:
                    view.askWhereToPositionWorkers();
                    break;
                case CHOOSE_ACTION:
                    view.askAction();
                    break;
                case NEW_POSITION:
                    NewPosition messageNewPosition = (NewPosition) message;
                    view.getGameView().setMyPosition(messageNewPosition.getRow(), messageNewPosition.getColumn());
                    break;
                case CHOOSE_WORKER:
                    view.getGameView().setMyTurn(true);
                    view.askWhichWorkerToUse();
                    break;
                case TURN:
                    view.getGameView().setMyTurn(false);
                    view.othersTurn(((ImportantMessage) message).getText());
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
                    view.showNewBoard(slot);
                    break;
                case DURING_TURN:
                    String changes = ((ImportantMessage) message).getText();
                    view.whileOnGame(changes);

            }
        }
    }
    
    /**
     * This method serializes the messages and sends them to the server.
     *
     * @param message the message that must be sent.
     */
    public void send(Message message) {
        try {
            synchronized (this) {
	            outputServer.reset();
	            outputServer.writeObject(message);
	            outputServer.flush();
	        }
        } catch (IOException e) {
            System.out.println("Error in the serialization of " + message.toString() + " message.");
            endConnection();
//            e.printStackTrace();
        }
    }

    /**
     * This method close the client process with no errors.
     */
    @Override
    public void endConnection(){
        isConnected = false;
        clientTimer.setIsConnectedFalse();
        messageExecutor.shutdownNow();

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Unable to close the socket of the server " + serverSocket.getInetAddress() + ".");
//            e.printStackTrace();
        }
    }

    /**
     * This method handles the first connection with the server, asking to the user to choose their username and
     * the color they prefer for their workers.
     */
    public void handleFirstConnection() {
        view.askFirstConnection();
    }

    /**
     * This method implements the update of the Observer Pattern.
     * It is called every time the view receives an input from the client,
     * so its content is notified to the Network Handler.
     * @param visitableObject the content of the input.
     */
    @Override
    public void update (Visitable visitableObject){
        visitableObject.accept(networkHandlerVisitor);
    }

}

