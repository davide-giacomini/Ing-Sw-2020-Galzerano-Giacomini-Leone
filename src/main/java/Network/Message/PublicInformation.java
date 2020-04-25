package Network.Message;
import Enumerations.Color;
import Enumerations.GodName;
import Enumerations.MessageType;
import java.util.ArrayList;

public class PublicInformation extends Message{
    private static final long serialVersionUID = 2261428338933766010L;
    ArrayList<String> usernames;
    ArrayList<Color> colors;
    ArrayList<GodName> godNames;

    public PublicInformation(MessageType messageType) {
        super(messageType);
    }
    public ArrayList<String> getUsernames() {
        return usernames;
    }
    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }
    public ArrayList<Color> getColors() {
        return colors;
    }
    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }
    public ArrayList<GodName> getGodNames() {
        return godNames;
    }
    public void setGodNames(ArrayList<GodName> godNames) {
        this.godNames = godNames;
    }
}

