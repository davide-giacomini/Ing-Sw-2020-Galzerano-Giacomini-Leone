package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.SlotListener;
import it.polimi.ingsw.PSP47.Network.Message.Message;
import it.polimi.ingsw.PSP47.Network.Message.OpponentPlayerDisconnection;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class implements a virtualView, which basically represents the connection between the network and the controller.
 * There is one for each player.
 */
public class VirtualView implements ServerListener, SlotListener {
    private GameController controller;
    private ClientHandler clientHandler;
    private final String username;
    private final it.polimi.ingsw.PSP47.Enumerations.Color color;
    
    public VirtualView (String username, Color color, ClientHandler clientHandler){
        this.username = username;
        this.color = color;
        this.clientHandler = clientHandler;
    }
    
    public String getUsername() {
        return username;
    }
    
    public it.polimi.ingsw.PSP47.Enumerations.Color getColor() {
        return color;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    /**
     * //TODO completare
     * @param message the message sent.
     * @param virtualView the {@link VirtualView} of the message's addressee.
     */
    @Override
    public void update(Message message, VirtualView virtualView) {
        switch (message.getMessageType()){
            case OPPONENT_PLAYER_DISCONNECTION:
                if (!virtualView.equals(this))
                    clientHandler.disconnectFromClient((OpponentPlayerDisconnection) message);
            //TODO implementare l'update a seconda di cosa serve
        }
    }
    
    @Override
    public void update(Slot slot) {
        clientHandler.sendUpdateSlot(slot);
        //TODO implementare l'update a seconda di cosa serve. Nel caso cambiare la signature di update per passare i parametri che si vogliono.
    }

    /**
     * This method go to the clientHandler to send the needed message.
     * @param numberOfPlayers the parameter that must be sent to the client.
     */
    public void sendNumberOfPlayers(int numberOfPlayers) {
        clientHandler.sendNumberOfPlayers(numberOfPlayers);
    }

    /**
     * This method go to the clientHandler to send if he is the Challenger or not.
     */
    public void sendChallenger() {
        clientHandler.sendYouAreTheChallenger();
    }

    /**
     * This method receive the list of god that can be used in the game and calls the controller.
     * @param gods list of gods.
     */
    public void receiveListOfGods(ArrayList<GodName> gods) {
        controller.setGods(gods);
    }

    /**
     * This method calls the clientHandler to send the list of available gods.
     * @param gods list of gods.
     */
    public void sendGodsList(ArrayList<GodName> gods) {
        clientHandler.sendGodsList(gods);
    }

    /**
     * This method receive the chosen god and calls the controller.
     * @param god chosen god.
     * @throws IOException if there are some IO troubles.
     */
    public void receiveChosenGod(GodName god) throws IOException {
        controller.setGod(god);
    }

    /**
     * This method send the information about all the players of the game.
     * @param usernames list of usernames
     * @param colors list of colors
     * @param godNames list of gods
     */
    public void sendPublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames) {
        clientHandler.sendPublicInformation(usernames, colors, godNames);
    }

    /**
     * This method send the request of the initial position of the workers.
     */
    public void sendSetWorkers()  {
        clientHandler.sendAskWorkersPosition();
    }

    /**
     * This method receive a list of coordinates (row1,column1,row2,column2) and calls the controller.
     * @param RowsAndColumns the list of coordinates.
     */
    public void receiveSetWorkers(int[] RowsAndColumns) {
            controller.setWorkers(RowsAndColumns);
    }


    public void sendLosingPlayer(String username) {
        //clientHandler.manageSendLosingPlayer(username)
    }

    /**
     * This method send the request of the choice about which worker use in a turn.
     */
    public void sendWhichWorker() {
        clientHandler.sendWhichWorker();
    }

    /**
     * This method send the request of the choice about what to do next:
     * A player can move, build, build a dome or end his turn.
     */
    public void sendWhichAction() {
        clientHandler.sendAction();
    }

    /**
     * This method send an error to the client.
     * It contains an errorString which will be printed to inform him about what he did wrong.
     * @param errorText the errorString that must be sent to the client.
     */
    public void sendError(String errorText) {
        clientHandler.sendError(errorText);
    }

    /**
     * This method receives the coordinates about the worker that has been chosen and send them to the controller.
     * @param coordinates row and column where the chosen worker is on.
     */
    public void receiveWhichWorker(int[] coordinates) {
        controller.getTurn().setWorkerGender(coordinates);
    }

    /**
     * This method receives the action chosen by the player during his turn.
     * @param action the type of action he chose.
     * @param direction the chosen direction.
     */
    public void receiveWhichAction(Action action, Direction direction) {
        controller.getTurn().executeAction(action, direction);
    }
}