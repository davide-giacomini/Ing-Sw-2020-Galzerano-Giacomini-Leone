package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.RequestPlayersNumber;

import java.util.ArrayList;

public abstract class ClientHandlerObservable {
    private final ArrayList<ClientHandlerListener> clientHandlerListeners = new ArrayList<>();
    
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
    
    public void notifyWin(ClientHandler clientHandler) {
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners) {
            clientHandlerListener.handleWinning(clientHandler);
        }
    }
    
    public void notifyLose(ClientHandler clientHandler){
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners) {
            clientHandlerListener.handleLosing(clientHandler);
        }
    }
}
