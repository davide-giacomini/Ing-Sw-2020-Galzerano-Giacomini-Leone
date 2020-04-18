package Network.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

///ip server è sempre 127.0.0.1 ???????

public class SantoriniServer
{
    public final static int SOCKET_PORT = 7777;
    private static int numberOfThread = 0;

    public static void main(String[] args)
    {
        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client,numberOfThread);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
            numberOfThread++;
        }
    }
}

