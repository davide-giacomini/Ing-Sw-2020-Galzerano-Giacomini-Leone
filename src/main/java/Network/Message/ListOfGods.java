package Network.Message;

import Enumerations.GodName;
import Enumerations.MessageType;

import java.util.ArrayList;

public class ListOfGods extends Message {
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
