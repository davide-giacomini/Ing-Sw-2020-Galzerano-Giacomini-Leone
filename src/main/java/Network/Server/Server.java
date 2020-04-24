package Network.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Server {
    public final static int SOCKET_PORT = 7777;
    private static int numberOfThread = 0;
    private static ArrayList<ClientHandler> players = new ArrayList<>();
    private static Server server;
    private int maxNumberOfPlayers;
    
    public static void main(String[] args)
    {
        server = new Server();
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
                Socket clientSocket = socket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, server);
                Thread thread = new Thread(clientHandler, "server_" + clientSocket.getInetAddress());
                thread.start();
                System.out.println("I've received a request");
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
            numberOfThread++;
        }
    }
    
    public ArrayList<ClientHandler> getPlayers(){
        return players; //TODO rendere safe questa chiamata
    }
    
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
    
    public void addClient(ClientHandler clientHandler){
        players.add(clientHandler);
    }
    
    public void initGame(){
        //TODO istanzia tutto
    }
    
}

