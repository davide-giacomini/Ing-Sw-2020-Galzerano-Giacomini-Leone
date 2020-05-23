package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.RequestPlayersNumber;

/**
 * This interface is implemented by the classes which want to listen to the {@link ClientHandler}.
 */
public interface ClientHandlerListener {
    
    /**
     * This method handles the first connection of each player.
     *
     * @param message it contains the username and the color chosen by the player.
     * @param clientHandler the handler of the client connected to the server.
     */
    void handleFirstConnection(FirstConnection message, ClientHandler clientHandler);
    
    /**
     * This message sets the max number of players chosen by the first player.
     *
     * @param message it contains the max number of players.
     */
    void setPlayersNumber(RequestPlayersNumber message, ClientHandler clientHandler);
    
    /**
     * It handles an illegal disconnection of a client. Illegal means that the client disconnects after being
     * added to the game's players but when the game isn't finished yet.
     *
     * @param clientHandler the client who disconnected.
     */
    void handleDisconnection(ClientHandler clientHandler);
    
    /**
     * This method handles the end of the game of the {@link ClientHandler specified}.
     *
     * @param clientHandler the clientHandler
     */
    void clientHandlerGameOver(ClientHandler clientHandler);
}
