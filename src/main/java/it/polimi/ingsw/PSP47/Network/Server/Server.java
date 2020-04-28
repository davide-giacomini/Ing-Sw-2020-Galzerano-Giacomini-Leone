package it.polimi.ingsw.PSP47.Network.Server;


import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Model.Board;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class instantiates a new server and wait for connections with clients.
 */
public class Server extends Observable {
    /**
     * The socket's port to connect to from the client.
     */
    public final static int SOCKET_PORT = 7777;
    private ArrayList<ClientHandler> connections = new ArrayList<>();
    private HashMap<String, Color> mapUsernameColor = new HashMap<>();
    private HashMap<String, VirtualView> mapUsernameVirtualView = new HashMap<>();
    private static Server server;
    private int maxNumberOfPlayers = 0;
    
    /**
     * This method creates a connection to be caught by clients. As the server catches a connection, it
     * instantiates a {@link ClientHandler} as a thread. It will handle the connection between that client and the
     * server itself.
     *
     * @param args they are not used.
     */
    public static void main(String[] args)
    {
        server = new Server();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
        
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, server);
                Thread thread = new Thread(clientHandler, "server_" + clientSocket.getInetAddress());
                thread.start();
                System.out.println("Socket from the client " + clientSocket.getInetAddress() + " connected.");
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
    
    public synchronized void cleanServer(){
        Board.getBoard().clearBoard();
        connections = new ArrayList<>();
        mapUsernameColor = new HashMap<>();
        mapUsernameVirtualView = new HashMap<>();
        maxNumberOfPlayers = 0;
    }
    
    public ArrayList<ClientHandler> getPlayers(){
        return new ArrayList<>(connections);
    }
    
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
    
    public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }
    
    public void addPlayer(ClientHandler clientHandler){
        connections.add(clientHandler);
    }
    
    public void addUsernameAndColorToMap(String username, Color color){
        mapUsernameColor.put(username, color);
    }
    
    public void addUsernameAndVirtualViewToMap(String username, VirtualView virtualView){
        mapUsernameVirtualView.put(username, virtualView);
    }
    
    /**
     * This methods makes the game start, instantiating the controller and setting it into the virtualView.
     * If {@link #cleanServer()} has been called before this method, it returns immediately.
     */
    public synchronized void initGame() {
        if (maxNumberOfPlayers==0 || mapUsernameVirtualView==null || mapUsernameColor==null)
            return;
        
        new GameController(maxNumberOfPlayers, mapUsernameColor, mapUsernameVirtualView);
    }
    
}

