package it.polimi.ingsw.PSP47.Network.Server;


import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.FirstPlayerConnection;
import it.polimi.ingsw.PSP47.Network.Message.WaitConnectionOpponentPlayer;
import it.polimi.ingsw.PSP47.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class instantiates a new server and wait for connections with clients.
 */
public class Server extends Observable implements SetUpGameListener{
    /**
     * The socket's port to connect to from the client.
     */
    public final static int SOCKET_PORT = 7777;
    private static ArrayList<ClientHandler> connections = new ArrayList<>();
    private HashMap<String, Color> mapUsernameColor = new HashMap<>();
    private HashMap<String, VirtualView> mapUsernameVirtualView = new HashMap<>();
    private static Server server;
    private int maxNumberOfPlayers = 0;
    private GameController gameController;
    private static AtomicBoolean firstPlayerConnected = new AtomicBoolean(false);
    private static AtomicBoolean gameParametersChosen = new AtomicBoolean(false);
    private static LinkedList<Socket> clientSockets = new LinkedList<>();
    private static AtomicBoolean connectionSettingFree = new AtomicBoolean(false);
    private static final ReentrantLock firstConnectionLock = new ReentrantLock();
    private static AtomicBoolean gameStarted = new AtomicBoolean(false);
    
    public static void setFirstPlayerConnected(AtomicBoolean firstPlayerConnected) {
        Server.firstPlayerConnected = firstPlayerConnected;
    }
    
    public static void setGameParametersChosen(AtomicBoolean gameParametersChosen) {
        Server.gameParametersChosen = gameParametersChosen;

        Socket proxClientSocket;
        if ((proxClientSocket = clientSockets.poll()) != null)
            createSetUpGame(proxClientSocket, false);
        else
            connectionSettingFree.set(true);
    }
    
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
            System.out.println("Cannot open server socket.");
            System.exit(1);
            return;
        }
        
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                
                synchronized (firstConnectionLock) {
                    if (gameStarted.get()){
                        //TODO manda un messaggio in cui dici che il gioco è partito e arrivederci
                        clientSocket.close();
                        continue;
                    }
                    
                    clientSockets.add(clientSocket);
                    
                    // If the first player hasn't connected yet, you are the first.
                    if (!firstPlayerConnected.get()) {
                        firstPlayerConnected.set(true);
                        createSetUpGame(clientSockets.poll(), true);
                    }
                    // If the first player isn't connected and the connection setting isn't free, wait.
                    else if (!gameParametersChosen.get() || !connectionSettingFree.get()) {
                        new ObjectOutputStream(clientSocket.getOutputStream()).writeObject(new WaitConnectionOpponentPlayer());
                    } else {
                        createSetUpGame(clientSockets.poll(), false);
                    }
                }
                
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
    
    public synchronized void cleanServer(){
        removeAll();
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
    public void initGame() {
        //gameController = new GameController(maxNumberOfPlayers, mapUsernameColor, mapUsernameVirtualView);
        gameStarted.set(true);
        System.out.println("Gioco iniziato.");
    }
    
    @Override
    public void setFirstPlayerParameters(FirstPlayerConnection message, Socket clientSocket) {
        synchronized (firstConnectionLock) {
            System.out.println("Setting up parameters of the first client: address" + clientSocket.getInetAddress() + ".");
    
            // Variables to be used in this method.
            String username = message.getUsername();
            Color color = message.getColor();
            int numberPlayers = message.getNumberPlayers();
    
            mapUsernameColor.put(username, color);
            maxNumberOfPlayers = numberPlayers;
            Server.setGameParametersChosen(new AtomicBoolean(true));
            createClientHandler(clientSocket);
        }
    }
    
    @Override
    public void setFirstConnectionParameters(FirstConnection message, Socket clientSocket) {
        synchronized (firstConnectionLock) {
            System.out.println("Setting up parameters the client" + clientSocket.getInetAddress() + ".");
        
            String username = message.getUsername();
            Color color = message.getColor();
    
            mapUsernameColor.put(username, color);
            createClientHandler(clientSocket);
            
            if (connections.size() == maxNumberOfPlayers){
                initGame();
                return;
            }
    
            if (clientSockets.getFirst()==null)
                connectionSettingFree.set(true);
            else
                connectionSettingFree.set(false);
        }
    }
    
    private void createClientHandler(Socket clientSocket){
        ClientHandler clientHandler = new ClientHandler(clientSocket, server);
        connections.add(clientHandler);
        new Thread(clientHandler, "server_" + clientSocket.getInetAddress()).start();
        System.out.println("Socket from the client " + clientSocket.getInetAddress() + " successfully connected.");
    }
    
    private static void createSetUpGame(Socket clientSocket, boolean isTheFirstPlayer){
        SetUpGame setUpGame = new SetUpGame(clientSocket, isTheFirstPlayer);
        setUpGame.addSetUpGameListener(server);
        new Thread(setUpGame).start();
    }
}

