package Network.Server;

import Controller.GameController;
import Enumerations.Color;
import Enumerations.GodName;
import Enumerations.MessageType;
import Model.Slot;
import Model.SlotListener;
import Network.Message.Message;
import Network.Message.YouAreTheRandomPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class VirtualView implements ServerListener, SlotListener {
    private GameController controller;
    private ClientHandler clientHandler;
    private final String username;
    private final Enumerations.Color color;
    
    public VirtualView (String username, Color color, ClientHandler clientHandler){
        this.username = username;
        this.color = color;
        this.clientHandler = clientHandler;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Enumerations.Color getColor() {
        return color;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void update(Message message) {
        switch (message.getMessageType()){
        }
    }
    
    @Override
    public void update(Slot slot)  {
        clientHandler.manageUpdateSlot(slot);
        //TODO implementare l'update a seconda di cosa serve. Nel caso cambiare la signature di update per passare i parametri che si vogliono.

    }

    /**
     * This method go to the clientHandler to send the needed message.
     * @param numberOfPlayers the parameter that must be sent to the client.
     * @throws IOException if there are some IO troubles.
     */
    public void sendNumberOfPlayers(int numberOfPlayers) throws IOException {
        clientHandler.manageNumberOfPlayers(numberOfPlayers);
    }

    /**
     * This method go to the clientHandler to send if he is the Challenger or not.
     * @throws IOException if there are some IO troubles.
     */
    public void sendChallenger() throws IOException {
        clientHandler.manageChallenger();
    }

    /**
     * This method receive the list of god that can be used in the game and calls the controller.
     * @param gods list of gods.
     * @throws IOException if there are some IO troubles.
     */
    public void receiveListOfGods(ArrayList<GodName> gods) throws IOException {
        controller.setGods(gods);
    }

    /**
     * This method calls the clientHandler to send the list of available gods.
     * @param gods list of gods.
     * @throws IOException if there are some IO troubles.
     */
    public void sendGodsList(ArrayList<GodName> gods) throws IOException {
        clientHandler.manageGodsList(gods);
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
     * @throws IOException if there are some IO troubles.
     */
    public void sendPublicInformation(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> godNames) throws IOException {
        clientHandler.managePublicInformation(usernames, colors, godNames);
    }

    public void sendSetWorkers()  {
        clientHandler.manageSetWorkers();
    }

    public void receiveSetWorkers(int[] RowsAndColumns) {

            controller.setWorkers(RowsAndColumns);

    }

}
