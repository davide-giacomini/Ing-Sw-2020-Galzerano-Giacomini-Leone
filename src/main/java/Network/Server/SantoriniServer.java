package Network.Server;


import Network.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.ArrayList;

///ip server è sempre 127.0.0.1 ???????

public class SantoriniServer
{
    public final static int SOCKET_PORT = 7777;
    private static int numberOfThread = 0;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();


    public static void main(String[] args)
    {
        SantoriniServer santoriniServer = new SantoriniServer();
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
                System.out.println("I've received a request");
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                MessageVC message = (MessageVC) in.readObject();
                if (message instanceof RequestConnection) {
                    ClientHandler clientHandler = new ClientHandler(client, socket, numberOfThread,((RequestConnection) message).getUsername(),
                            ((RequestConnection) message).getColor(), in, out, santoriniServer);
                    addConnection(clientHandler, ((RequestConnection) message).getUsername(), ((RequestConnection) message).getColor());
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("connection dropped");
            }
            numberOfThread++;
        }
    }

    private static boolean addConnection(ClientHandler clientHandler, String username, String color) throws IOException {
        if (clients.isEmpty()) {
            clients.add(clientHandler);
            ConnectionResponse connectionResponse = new ConnectionResponse("Successfully connected");
            MessageContainer message = new MessageContainer(MessageContainer.CV_EVENT, connectionResponse);
            clientHandler.send(message);
            return true;
        } else {
            if (checkUsername(username) && checkColor(color)) {
                clients.add(clientHandler);
                System.out.println(username + " è stato aggiunto all'elenco\n");
                ConnectionResponse connectionResponse = new ConnectionResponse("Successfully connected");
                MessageContainer message = new MessageContainer(MessageContainer.CV_EVENT, connectionResponse);
                clientHandler.send(message);
                return true;
            } else {
                ConnectionResponse connectionResponse = new ConnectionResponse("Tentativo di connessione non riuscito per username e colore invalidi");
                MessageContainer message = new MessageContainer(MessageContainer.CV_EVENT, connectionResponse);
                clientHandler.send(message);
                clientHandler.disconnect();
                return false;
            }
        }
    }

    private static boolean checkUsername(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equalsIgnoreCase(username))
            {
                System.out.println("Username già scelto");
                return false;
            }
        }
        return true;
    }

    private static boolean checkColor(String color) {
        for (ClientHandler client : clients) {
          if (client.getColor().equalsIgnoreCase(color))
          {
            System.out.println("Colore già scelto");
            return false;
          }
        }
        return true;
    }

    /**
     * Called when a player disconnects
     *
     * @param clientHandler connection of the player that just disconnected
     */
    void onDisconnect(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

}

