package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.RequestPlayersNumber;

import java.util.ArrayList;

/**
 * This class permits the {@link ClientHandler} to be observable.
 */
public abstract class ClientHandlerObservable {
    private final ArrayList<ClientHandlerListener> clientHandlerListeners = new ArrayList<>();
    
    /**
     * It adds a {@link ClientHandlerListener} to the list of listeners.
     *
     * @param clientHandlerListener the {@link ClientHandlerListener} to be added.
     */
    public void addClientHandlerListener(ClientHandlerListener clientHandlerListener) {
        clientHandlerListeners.add(clientHandlerListener);
    }
    
    /**
     * It notifies that the message {@link FirstConnection} came to the server.
     *
     * @param message the message which contains player's username and password.
     * @param clientHandler the {@link ClientHandler} which handles the client which sent the message.
     */
    public void notifyFirstConnection(FirstConnection message, ClientHandler clientHandler){
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners){
            clientHandlerListener.handleFirstConnection(message, clientHandler);
        }
    }
    
    /**
     * It notifies that the message {@link RequestPlayersNumber} came to the server.
     *
     * @param message the message which contains the max number of players chosen by the player for the game.
     */
    public void notifyPlayersNumber(RequestPlayersNumber message) {
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners){
            clientHandlerListener.setPlayersNumber(message);
        }
    }
    
    /**
     * It notifies that the client specified disconnected.
     *
     * @param clientHandler handles the client which disconnected.
     */
    public void notifyDisconnection(ClientHandler clientHandler) {
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners) {
            clientHandlerListener.handleDisconnection(clientHandler);
        }
    }
    
    /**
     * It notifies that a client won.
     */
    public void notifyWin() {
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners) {
            clientHandlerListener.handleWinning();
        }
    }
    
    /**
     * It notifies that the client specified lost.
     * 
     * @param clientHandler handles the client which lost.
     */
    public void notifyLoss(ClientHandler clientHandler){
        for (ClientHandlerListener clientHandlerListener : clientHandlerListeners) {
            clientHandlerListener.handleLoosing(clientHandler);
        }
    }
}
