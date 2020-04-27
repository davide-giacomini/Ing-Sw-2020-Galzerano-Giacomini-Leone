package Network.Message;

import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

public class ChooseAction extends Message {
    public ChooseAction(MessageType messageType) {
        super(messageType);
    }

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {

    }

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {

    }
}
