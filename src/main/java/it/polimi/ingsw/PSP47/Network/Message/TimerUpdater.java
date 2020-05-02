package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.Timer;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.ObjectOutputStream;
import java.sql.Time;

public class TimerUpdater extends Message{
    private static final long serialVersionUID = 3711431311549795837L;
    private boolean expired = false;
    private int secondsWaited;

    public TimerUpdater(boolean expired, int secondsWaited) {
        this.expired = expired;
        this.secondsWaited = secondsWaited;
        this.messageType=MessageType.TIMER_UPDATER;
    }

    public void setExpired(){
        expired = true;
    }
    
    public void setSecondsWaited(int secondsWaited) {
        this.secondsWaited = secondsWaited;
    }
    
    /*@Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        if (expired)
            client.getView().print("The time expired: you lose.");
        else
            client.getView().print("You have " + (Timer.MAX_MILLISECONDS_PER_PLAYER-secondsWaited) + " to continue.");
    }
    
    /**
     * @deprecated
     * This method doesn't do anything for now.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.

    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {}
*/
    @Override
    public Visitable getContent() {
        return null;
    }
}
