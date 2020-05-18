package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.View.View;

import java.io.IOException;
import java.net.Socket;

public class NetworkConnectionUtil {
    public static NetworkHandler setConnection(View view, String ipAddress) throws IOException{
        NetworkHandler networkHandler;
        
        // open a connection with the server
        Socket serverSocket;
        serverSocket = new Socket(ipAddress, Server.SOCKET_PORT);
    
        networkHandler = new NetworkHandler(view , serverSocket);
        
        new Thread(networkHandler).start();
        
        return networkHandler;
    }
}
