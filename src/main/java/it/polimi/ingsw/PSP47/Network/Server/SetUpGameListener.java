package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.FirstPlayerConnection;

import java.net.Socket;

public interface SetUpGameListener {
    
    void setFirstPlayerParameters(FirstPlayerConnection message, Socket clientSocket);
    
    void setFirstConnectionParameters(FirstConnection message, Socket clientSocket);
}
