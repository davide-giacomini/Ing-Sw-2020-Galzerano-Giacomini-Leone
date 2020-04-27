package Network.Message;

import Enumerations.Action;
import Enumerations.Direction;
import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

public class ChooseWorkerByPosition extends Message {
    private static final long serialVersionUID = 6986806777661212457L;
    int[] coordinates;

    public ChooseWorkerByPosition(MessageType messageType) {
        super(messageType);
    }

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {

    }

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
        virtualView.receiveWhichWorker(getCoordinates());
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
}
