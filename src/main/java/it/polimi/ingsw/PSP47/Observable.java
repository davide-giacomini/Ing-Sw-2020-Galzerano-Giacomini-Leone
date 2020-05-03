package it.polimi.ingsw.PSP47;

import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.SlotListener;
import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.Message;
import it.polimi.ingsw.PSP47.Network.Message.RequestPlayersNumber;
import it.polimi.ingsw.PSP47.Network.Server.ClientHandler;
import it.polimi.ingsw.PSP47.Network.Server.ServerListener;
import it.polimi.ingsw.PSP47.Network.Server.ClientHandlerListener;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Network.Server.VirtualViewListener;
import it.polimi.ingsw.PSP47.View.ViewObserver;
import it.polimi.ingsw.PSP47.Visitor.Visitable;
import it.polimi.ingsw.PSP47.View.ViewListener;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.util.ArrayList;

/**
 * Observable interface of pattern observer.
 */
public abstract class Observable {
    private ArrayList<SlotListener> slotListeners = new ArrayList<>();
    private final ArrayList<ClientHandlerListener> clientHandlerListeners = new ArrayList<>();
    private ArrayList<ServerListener> messageListeners = new ArrayList<>();
    private ViewListener viewListener;
    private VirtualViewListener virtualViewListener;
    
    public void notifySlotListeners(Slot slot) {
        for (SlotListener slotListener : slotListeners) {
            slotListener.update(slot);
        }
    }

    public void notifyViewListener(Visitable visitableObject){
        viewListener.update(visitableObject);
    }


    public void notifyVirtualViewListener(Visitable visitableObject) {
        virtualViewListener.update(visitableObject);
    }
    
    public void addSlotListener(SlotListener slotListener){
        slotListeners.add(slotListener);
    }
    
    public void addClientHandlerListener(ClientHandlerListener clientHandlerListener) {
        clientHandlerListeners.add(clientHandlerListener);
    }
    
    public void notifyFirstConnection(FirstConnection message, ClientHandler clientHandler){
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners){
            clientHandlerListener.handleFirstConnection(message, clientHandler);
        }
    }
    
    public void notifyPlayersNumber(RequestPlayersNumber message) {
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners){
            clientHandlerListener.setPlayersNumber(message);
        }
    }
    
    public void notifyDisconnection(ClientHandler clientHandler) {
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners) {
            clientHandlerListener.handleDisconnection(clientHandler);
        }
    }

    public void addViewListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }

    public void addVirtualViewListener(VirtualViewListener virtualViewListener) {
        this.virtualViewListener = virtualViewListener;
    }

    public void removeAll() {
        slotListeners = new ArrayList<>();
        messageListeners = new ArrayList<>();
    }
}