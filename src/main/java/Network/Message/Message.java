package Network.Message;

import java.util.ArrayList;

public abstract class Message {
    private ArrayList<MessageListener> messageListeners;

    public void notify_listeners() {
        for (MessageListener messageListener : messageListeners) {
            messageListener.update();
        }
    }
}
