package Network.Server;


import Controller.GameController;
import Enumerations.Color;
import Network.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server extends Observable {
    public final static int SOCKET_PORT = 7777;
    private ArrayList<ClientHandler> players = new ArrayList<>();
    private HashMap<String, Color> playerUsernameColorHashMap = new HashMap<>();
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
        }
    }
    
    public ArrayList<ClientHandler> getNumberOfPlayers(){
        return players; //TODO rendere safe questa chiamata
    }
    
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
    
    public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }
    
    public void addPlayer(ClientHandler clientHandler){
        players.add(clientHandler);
    }
    
    public void addPlayerUsernameColorHashMap(String username, Color color){
        playerUsernameColorHashMap.put(username, color);
    }
    
    public void initGame(){
        
       new GameController(maxNumberOfPlayers, playerUsernameColorHashMap);
    }
    
}

