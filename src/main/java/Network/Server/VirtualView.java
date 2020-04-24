package Network.Server;

import Enumerations.Color;
import Network.Message.Message;
import Network.Message.MessageListener;

import java.awt.*;

public class VirtualView implements MessageListener {
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
            case FIRST_GAMER:
                //TODO chiama il metodo del controller che setta il challenger a this.username
        }
    }
}
