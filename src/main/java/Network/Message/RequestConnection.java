package Network.Message;

import Enumerations.Color;
import Enumerations.MessageType;
import Network.Client.Client;
import Network.Server.Server;
import Network.Server.VirtualView;

import java.io.ObjectOutputStream;

public class RequestConnection extends Message {
    private static final long serialVersionUID = -8037367122696029080L;
    private String username;
    private Enumerations.Color color;
    
    public RequestConnection(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * @deprecated
     * This method is special, is handled inside the {@link Network.Client.NetworkHandler}. It has not to be used.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer){}
    
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
    
    public String getUsername(){
        return username;
    }

    public Color getColor(){
        return color;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setColor(Enumerations.Color color) {
        this.color = color;
    }
}
