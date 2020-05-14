package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.SlotListener;

import java.util.ArrayList;

/**
 * This class implements a virtualView, which basically represents the connection between the network and the controller.
 * There is one for each player.
 */
public class VirtualView extends VirtualViewObservable implements SlotListener {
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


    /**
     * This method sends a model update client side.
     * @param slot the slot that has been updated.
     */
    @Override
    public void update(Slot slot) {
        clientHandler.sendUpdateSlot(slot);
    }

    /**
     * This method sends the number of players of the game.
     * @param numberOfPlayers the parameter that must be sent to the client.
     */
    public void sendNumberOfPlayers(int numberOfPlayers) {
        clientHandler.sendNumberOfPlayers(numberOfPlayers);
    }

    /**
     * This method sends a notify who tells who is the challenger.
     */
    public void sendChallenger() {
        clientHandler.sendYouAreTheChallenger();
    }

    /**
     * This method calls the clientHandler to send the list of available gods.
     * @param gods list of gods.
     */
    public void sendGodsList(ArrayList<GodName> gods) {
        clientHandler.sendGodsList(gods);
    }


    /**
     * This method sends the information about all the players of the game.
     * @param usernames list of usernames
     * @param colors list of colors
     * @param godNames list of gods
     */
    public void sendPublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames) {
        clientHandler.sendPublicInformation(usernames, colors, godNames);
    }

    /**
     * This method sends the request of the initial position of the workers.
     */
    public void sendSetWorkers()  {
        clientHandler.sendAskWorkersPosition();
    }

    /**
     * This method sends the request of the choice about which worker use in a turn.
     */
    public void sendWhichWorker() {
        clientHandler.sendWhichWorker();
    }

    /**
     * This method sends the request of the choice about what to do next:
     * A player can move, build, build a dome or end his turn.
     */
    public void sendWhichAction() {
        clientHandler.sendAction();
    }

    /**
     * This method sends an error to the client.
     * It contains an errorString which will be printed to inform him about what he did wrong.
     * @param errorText the errorString that must be sent to the client.
     */
    public void sendError(String errorText) {
        clientHandler.sendError(errorText);
    }

    /**
     * This method sends an advice to the client.
     * @param text the String that contains the advice.
     */
    public void sendImportant(String text, MessageType messageType) {
        clientHandler.sendImportant(text, messageType);
    }

    /**
     * This method sends the player who has lost.
     */
    public void sendLosingAdvice() {
        clientHandler.showYouLost();
    }

    /**
     * This method sends the player who has win.
     */
    public void sendWinningAdvice() {
        clientHandler.showYouWon();
    }
}
