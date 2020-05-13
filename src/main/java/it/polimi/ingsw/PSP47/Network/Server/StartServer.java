package it.polimi.ingsw.PSP47.Network.Server;

import java.io.IOException;

/**
 * This class instantiates a new server.
 */
public class StartServer {
    public static void main( String[] args )
    {
        Server server;
        try {
            server = new Server();
            server.listen();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
