package Network.Server;

import Enumerations.Color;
import Model.SlotListener;
import Network.Message.Message;

public class VirtualView implements ServerListener, SlotListener {
    private final String username;
    private final Enumerations.Color color;
    
    public VirtualView (String username, Color color){
        this.username = username;
        this.color = color;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Enumerations.Color getColor() {
        return color;
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
}
