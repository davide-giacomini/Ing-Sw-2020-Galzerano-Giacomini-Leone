package Network.Server;

import Controller.GameController;
import Enumerations.Color;
import Enumerations.GodName;
import Enumerations.MessageType;
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
    public void update() {
        //TODO implementare l'update a seconda di cosa serve. Nel caso cambiare la signature di update per passare i parametri che si vogliono.
    }

    public void sendChallenger() throws IOException {
        clientHandler.manageChallenger();
    }

    public void receiveListOfGods(ArrayList<GodName> gods) throws IOException {
        controller.setGods(gods);
    }

    public void sendGodsList(ArrayList<GodName> gods) throws IOException {
        clientHandler.manageGodsList(gods);
    }

    public void receiveChosenGod(GodName god) throws IOException {
        controller.setGod(god);
    }

}
