package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.io.ObjectOutputStream;

public class ConnectionAccepted extends Message{
    private static final long serialVersionUID = -7614194884802773262L;
    private String username;
    private Color color;

    public ConnectionAccepted(MessageType messageType) {
        super(messageType);
    }
    
    public String getUsername() {
        return username;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Client's username and color are set.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        client.getView().getGameView().setMyUsername(username);
        client.getView().getGameView().setMyColor(color);
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
}
