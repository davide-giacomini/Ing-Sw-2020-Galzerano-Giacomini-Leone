package it.polimi.ingsw.PSP47;

import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.SlotListener;
import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.FirstPlayerConnection;
import it.polimi.ingsw.PSP47.Network.Message.Message;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.ServerListener;
import it.polimi.ingsw.PSP47.Network.Server.SetUpGameListener;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Observable interface of pattern observer.
 */
public abstract class Observable {
    private ArrayList<ServerListener> messageListeners = new ArrayList<>();
    private ArrayList<SlotListener> slotListeners = new ArrayList<>();
    private ArrayList<SetUpGameListener> setUpGameListeners = new ArrayList<>();
    
    public void notifyMessageListeners(Message message, VirtualView virtualView) {
        for (ServerListener messageListener : messageListeners) {
            messageListener.update(message, virtualView);
        }
    }
    
    public void notifySlotListeners(Slot slot) {
        for (SlotListener slotListener : slotListeners) {
            slotListener.update(slot);
        }
    }
    
    public void addMessageListener(ServerListener listener){
        messageListeners.add(listener);
    }
    
    public void addSlotListener(SlotListener slotListener){
        slotListeners.add(slotListener);
    }
    
    public void addSetUpGameListener(SetUpGameListener setUpGameListener) {
        setUpGameListeners.add(setUpGameListener);
    }
    
    public void removeAll() {
        slotListeners = new ArrayList<>();
        messageListeners = new ArrayList<>();
    }
    
    public void notifyFirstPlayerParameters(FirstPlayerConnection message, Socket clientSocket){
        for (SetUpGameListener setUpGameListener: setUpGameListeners){
            setUpGameListener.setFirstPlayerParameters(message, clientSocket);
        }
    }
    
    public void notifyFirstConnection(FirstConnection message, Socket clientSocket){
        for (SetUpGameListener setUpGameListener: setUpGameListeners){
            setUpGameListener.setFirstConnectionParameters(message, clientSocket);
        }
    }
}
