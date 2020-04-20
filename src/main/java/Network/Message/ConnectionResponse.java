package Network.Message;

import Network.Client.NetworkHandler;

import java.io.IOException;

public class ConnectionResponse implements MessageCV {

    String text;

    public ConnectionResponse(String text) {
        this.text=text;
    }

    @Override
    public void accept(NetworkHandler networkHandler) throws IOException {
        networkHandler.print(text);
    }
}
