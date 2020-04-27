package Network.Server;

import Network.Message.Message;

/**
 * This interface is implemented by who listens to the server.
 */
public interface ServerListener {
    /**
     * This update handle what to do when a message is sent.
     * The addressee is the client who owns the parameter {@link VirtualView}.
     * @param message the message sent.
     * @param virtualView the {@link VirtualView} of the message's addressee.
     */
    void update(Message message, VirtualView virtualView);
}