package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.RequestPlayersNumber;

public interface ClientHandlerListener {
    
    void handleFirstConnection(FirstConnection message, ClientHandler clientHandler);
    
    void setPlayersNumber(RequestPlayersNumber message);
    
    void handleDisconnection(ClientHandler clientHandler);
    
    void handleWinning(ClientHandler clientHandler);
    
    void handleLosing(ClientHandler clientHandler);
}
