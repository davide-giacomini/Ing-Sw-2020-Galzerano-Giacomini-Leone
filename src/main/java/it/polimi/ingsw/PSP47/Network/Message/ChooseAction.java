package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

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

    /**
     * This method print to the client the request about what action he wants to do next and send to the server
     * a message with the type of action he chose and the relative direction.
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {

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

    /**
     * This method sends to the virtual view the content of the message.
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
     */
    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
            virtualView.receiveWhichAction(action, direction);
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
