package Network.Message;

import Enumerations.MessageType;
import Model.Slot;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

/**
 * This message contains an update from the model (a modified slot).
 * It's a S->C message.
 */
public class UpdatedSlot extends Message{
    private static final long serialVersionUID = 709094366320013507L;
    private Slot updatedSlot ;

    public UpdatedSlot(MessageType messageType) {
        super(messageType);

    }
    
    /**
     * This method calls the view to update a modified slot and to print the updated board into the screen.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().getViewDatabase().getBoardView().setSlot(updatedSlot);

    }
    
    /**
     * @deprecated
     * This method doesn't do anything for now.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
     */
    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {}
    
    public Slot getUpdatedSlot() {
        return updatedSlot;
    }

    public void setUpdatedSlot(Slot updatedSlot) {
        this.updatedSlot = updatedSlot;
    }


}
