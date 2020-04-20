package Network.Message;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Message{
    private ArrayList<MessageListener> messageListeners;

    public void notifyListeners() {
        for (MessageListener messageListener : messageListeners) {
            messageListener.update();
        }
    }
}
