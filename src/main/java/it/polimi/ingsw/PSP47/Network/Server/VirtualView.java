package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
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
     * This method calls the clientHandler to send the list of available gods.
     * @param gods list of gods.
     */
    public void sendGodsList(ArrayList<GodName> gods) {
        clientHandler.sendGodsList(gods);
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


    public void sendLosingPlayer(String username) {
//        clientHandler.manageSendLosingPlayer(username)
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

}
