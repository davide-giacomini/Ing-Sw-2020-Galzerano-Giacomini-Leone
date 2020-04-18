package Network.Client;

import java.io.IOException;
import java.net.Socket;

public class NetworkHandler implements Runnable{
    private String ipServer;
    private int port;
    private String username;
    private String color;

    public NetworkHandler (String ipServer, int port, String username, String color){
        this.ipServer = ipServer;
        this.port = port;
        this.username = username;
        this.color = color;

    }

    @Override
    public void run() {
        /* open a connection to the server */
        Socket server;
        try {
            server = new Socket(ipServer, port);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }

        System.out.println("Connected");
    }
}
