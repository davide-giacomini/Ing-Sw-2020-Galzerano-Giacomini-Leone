package Network.Message;

import Enumerations.GodName;
import Enumerations.MessageType;

import java.util.ArrayList;

/**
 * This message contains two elements as it is sent both from server to client and from client to server.
 * When it is S->C it's setted the ArrayList of godsAvailable, with all of the god chosen by the Challenger.
 * When it is C->S it's setted the GodName chosenGod with the kind of god chosen by a player.
 */
public class ListOfGods extends Message {
    private static final long serialVersionUID = 5244974574544564271L;
    private ArrayList<GodName> godsAvailable;
    private GodName chosenGod;

    public ListOfGods(MessageType messageType) {
        super(messageType);
    }

    public ArrayList<GodName> getGodsAvailable() {
        return godsAvailable;
    }

    public void setGodsAvailable(ArrayList<GodName> gods) {
        this.godsAvailable = gods;
    }

    public GodName getChosenGod() {
        return chosenGod;
    }

    public void setChosenGod(GodName chosenGod) {
        this.chosenGod = chosenGod;
    }
}
