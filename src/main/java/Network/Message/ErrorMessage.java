package Network.Message;

import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

public class ErrorMessage extends Message {
    String errorText;

    public ErrorMessage(MessageType messageType) {
        super(messageType);
    }

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().print(getErrorText());
    }

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {

    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
