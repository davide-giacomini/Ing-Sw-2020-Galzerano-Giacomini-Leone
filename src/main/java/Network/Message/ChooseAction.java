package Network.Message;

import Enumerations.Action;
import Enumerations.Direction;
import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ChooseAction extends Message {

    private static final long serialVersionUID = 7394766692151121241L;

    private Action action;

    private Direction direction;

    public ChooseAction(MessageType messageType) {
        super(messageType);
    }

    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().showCurrentBoard();
        ArrayList<Enum> ActionAndDirection;
        ActionAndDirection = client.getView().askAction();

        ChooseAction newMessage = new ChooseAction(MessageType.CHOOSE_ACTION);
        newMessage.setAction((Action) ActionAndDirection.get(0));
        newMessage.setDirection((Direction) ActionAndDirection.get(1));

        try {
            outputServer.writeObject(newMessage);
        } catch (IOException e) {
            System.out.println("Error in the serialization of " +this.toString()+" message.");
            e.printStackTrace();
        }
    }

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
            virtualView.receiveWhichAction(action, direction);
    }


    public Action getAction() {
        return action;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
