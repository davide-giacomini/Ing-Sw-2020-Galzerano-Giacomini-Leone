package Network.Server;

import Network.Message.Message;

public interface ServerListener {
    void update(Message message);
}