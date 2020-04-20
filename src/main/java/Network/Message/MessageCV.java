package Network.Message;

import Network.Client.NetworkHandler;

import java.io.IOException;
import java.io.Serializable;

public interface MessageCV extends Serializable {

    public void accept(NetworkHandler networkHandler) throws IOException;

}
