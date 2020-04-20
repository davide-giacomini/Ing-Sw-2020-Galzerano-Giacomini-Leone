package Network.Message;

import Controller.GameController;

import java.io.Serializable;

/**
 * Class which implements messages from View to Controller (Client to Server)
 */
public interface MessageVC extends Serializable {

    /**
     * method that accept this SocketMessage server side
     */
    void accept(GameController controller);

}
