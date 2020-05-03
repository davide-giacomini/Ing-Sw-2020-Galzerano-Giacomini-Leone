package it.polimi.ingsw.PSP47.Network.Server;


import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.RequestPlayersNumber;
import it.polimi.ingsw.PSP47.Observable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class instantiates a new server and wait for connections with clients.
 */
public class Server extends Observable implements ClientHandlerListener {
    // Class for the pair username-color of the client
    private class PlayerParameters {
        private final String username;
        private final Color color;
        
        private PlayerParameters(String username, Color color) {
            this.username = username;
            this.color = color;
        }
    
        public String getUsername() {
            return username;
        }
    
        public Color getColor() {
            return color;
        }
    }
    
    /**
     * The socket's port to connect to from the client.
     */
    public final static int SOCKET_PORT = 7777;
    private final ServerSocket serverSocket;
    private volatile boolean firstPlayerConnected = false;
    private volatile boolean gameStarted = false;
    private int maxPlayersNumber = -1;
    private ClientHandler firstClientHandler;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Map<ClientHandler, PlayerParameters> connectionsAccepted = new HashMap<>();
    
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(SOCKET_PORT);
    }
    
    public void listen() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                
                synchronized (this) {
                    ClientHandler clientHandler = new ClientHandler(clientSocket, gameStarted);
                    clientHandlers.add(clientHandler);
                    clientHandler.addClientHandlerListener(this);
                    new Thread(clientHandler).start();
                }
                
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
    
    @Override
    public synchronized void handleFirstConnection(FirstConnection message, ClientHandler clientHandler) {
        String username = message.getUsername();
        Color color = message.getColor();
        
        if (!firstPlayerConnected) {
            firstPlayerConnected = true;
            firstClientHandler = clientHandler;
            connectionsAccepted.put(clientHandler, new PlayerParameters(username, color));
            clientHandlers.remove(clientHandler);
            clientHandler.askMaxPlayersNumber();
            return;
        } else if (maxPlayersNumber < 0) {
            clientHandler.warnFirstPlayerIsChoosing();
            
            while (maxPlayersNumber < 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            if (!firstPlayerConnected) {
                clientHandler.notifyFirstClientHandlerDropped();
                clientHandlers.remove(clientHandler);
                notifyAll(); //TODO non so se serva
                return;
            }
        }
    
        String wrongParameter;
        if ((wrongParameter = checkParameters(username, color)) != null) {
            clientHandler.askAgainParameters(wrongParameter);
            notifyAll(); //TODO non so se serva
            return;
        }
        
        connectionsAccepted.put(clientHandler, new PlayerParameters(username, color));
        clientHandlers.remove(clientHandler);
        
        if (connectionsAccepted.size() == maxPlayersNumber)
            initGame();
        
        notifyAll(); //TODO non so se serva
    }
    
    @Override
    public synchronized void setPlayersNumber(RequestPlayersNumber message) {
        maxPlayersNumber = message.getNumberOfPlayers();
        
        notifyAll();
    }
    
    @Override
    public synchronized void handleDisconnection(ClientHandler clientHandler) {
        // If the clientHandler isn't in the game yet
        if (!connectionsAccepted.containsKey(clientHandler)) {
            clientHandlers.remove(clientHandler);
        }
        // If the clientHandler is the first and the game is not started yet, the game is reset and others players
        // are notified. A message of the dropped connection of the first client will be sent to them.
        else if (!gameStarted && clientHandler.equals(firstClientHandler)) {
            connectionsAccepted = new HashMap<>();
            maxPlayersNumber = -1;
            firstClientHandler = null;
            firstPlayerConnected = false;
            notifyAll();
        }
        // If clientHandler is in the connections accepted, they are not the first clientHandler and the game is not
        // started yet, this clientHandler can exit silently, without affecting other players.
        else if (!gameStarted) {
            connectionsAccepted.remove(clientHandler);
        }
        // If the game is started and you are among the connected players.
        else {
            connectionsAccepted = new HashMap<>();
            maxPlayersNumber = -1;
            firstClientHandler = null;
            firstPlayerConnected = false;
        }
    }
    
    private String checkParameters(String username, Color color) {
        PlayerParameters opponentsParameters;
    
        for (ClientHandler clientHandler : connectionsAccepted.keySet()) {
            opponentsParameters = connectionsAccepted.get(clientHandler);
        
            if (opponentsParameters.getUsername().equals(username) && opponentsParameters.getColor().equals(color))
                return "username and color";
            if (opponentsParameters.getUsername().equals(username))
                return "username";
            else if (opponentsParameters.getColor().equals(color))
                return "color";
        }
        
        return null;
    }
    
    private void initGame() {
        System.out.println("Gioco iniziato."); //TODO inizia il gioco
        gameStarted = true;
        
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.notifyGameStartedWithoutYou();
        }
    }
}