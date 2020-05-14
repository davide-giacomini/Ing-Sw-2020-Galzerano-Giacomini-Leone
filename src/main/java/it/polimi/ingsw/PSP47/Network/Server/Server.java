package it.polimi.ingsw.PSP47.Network.Server;


import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.RequestPlayersNumber;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;
import it.polimi.ingsw.PSP47.Visitor.VisitableInt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class wait for connections with clients and handles connections and disconnections, creating or deleting
 * the game.
 */
public class Server implements ClientHandlerListener {
    /**
     * This class is a tuple with a username and a color.
     */
    private class PlayerParameters {
        private final String username;
        private final Color color;
        
        private PlayerParameters(String username, Color color) {
            this.username = username;
            this.color = color;
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
    private final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private final Map<ClientHandler, PlayerParameters> connectionsAccepted = new HashMap<>();
    private GameController gameController;
    
    /**
     * It creates the server socket to connect with the clients.
     *
     * @throws IOException if the {@link #serverSocket} can't be created.
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(SOCKET_PORT);
    }
    
    /**
     * This method listens to new connections to the server.
     */
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
    
    /**
     * This method handles the first connection of each player. The players are added to the game's queue to wait for
     * the creation of the game.
     *
     * @param message it contains the username and the color chosen by the player.
     * @param clientHandler the handler of the client connected to the server.
     */
    @Override
    public synchronized void handleFirstConnection(FirstConnection message, ClientHandler clientHandler) {
        VisitableInformation firstConnectionMessage = (VisitableInformation) message.getContent();
        String username = firstConnectionMessage.getUsername();
        Color color = firstConnectionMessage.getColor();
        
        // If the first player isn't connected yet, you become the first player
        if (!firstPlayerConnected) {
            firstPlayerConnected = true;
            firstClientHandler = clientHandler;
            connectionsAccepted.put(clientHandler, new PlayerParameters(username, color));
            clientHandlers.remove(clientHandler);
            clientHandler.sendConnectionAccepted(username, color);
            clientHandler.askMaxPlayersNumber();
            return;
        }
        // If the first player is already connected and they didn't choose a max number of player, you have to wait.
        if (maxPlayersNumber < 0) {
            clientHandler.warnFirstPlayerIsChoosing();
            
            while (maxPlayersNumber < 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // If the first player is no more connected, the game is corrupted and it closes.
            if (!firstPlayerConnected) {
                clientHandler.notifyFirstClientDisconnected();
                notifyAll();
                return;
            }
            // If the game is already started, you are out.
            if (gameStarted) {
                clientHandler.notifyGameStartedWithoutYou();
                notifyAll();
                return;
            }
        }
    
        // It checks if your color and username are corrected.
        String wrongParameter;
        if ((wrongParameter = checkParameters(username, color)) != null) {
            clientHandler.askAgainParameters(wrongParameter);
            notifyAll();
            return;
        }
        
        // At this point you can enter the game and wait for other players
        connectionsAccepted.put(clientHandler, new PlayerParameters(username, color));
        clientHandler.sendConnectionAccepted(username, color);
        clientHandlers.remove(clientHandler);

        
        // With enough players, the game starts.
        if (connectionsAccepted.size() == maxPlayersNumber)
            initGame();
        
        notifyAll();
    }
    
    /**
     * This message sets the max number of players chosen by the first player.
     * @param message it contains the max number of players.
     */
    @Override
    public synchronized void setPlayersNumber(RequestPlayersNumber message) {
        maxPlayersNumber = ((VisitableInt) message.getContent()).getNumber();
        
        notifyAll();
    }
    
    /**
     * It handles an illegal disconnection of a client. Illegal means that the client disconnects after being
     * added to the game's players but when the game isn't finished yet.
     * The server, with this method, takes care of notifying and deleting all the other players, if necessary.
     *
     * @param clientHandler the client who disconnected.
     */
    @Override
    public synchronized void handleDisconnection(ClientHandler clientHandler) {
        // If the clientHandler isn't in the game yet
        if (!connectionsAccepted.containsKey(clientHandler)) {
            clientHandlers.remove(clientHandler);
        }
        // If the clientHandler is the first and the game is not started yet, the game is reset and others players
        // are notified. A message of the dropped connection of the first client will be sent to them.
        else if (!gameStarted && clientHandler.equals(firstClientHandler)) {
            String username = connectionsAccepted.get(clientHandler).username;
            connectionsAccepted.remove(clientHandler);
            
            for (ClientHandler client : connectionsAccepted.keySet()) {
                client.notifyOpponentClientDisconnected(username);
            }
            
            cleanServer();
            notifyAll();
        }
        // If clientHandler is in the connections accepted, they are not the first clientHandler and the game is not
        // started yet, this clientHandler can exit silently, without affecting other players.
        else if (!gameStarted) {
            connectionsAccepted.remove(clientHandler);
        }
        // If the game is started and you are among the connected players.
        else {
            String username = connectionsAccepted.get(clientHandler).username;
            connectionsAccepted.remove(clientHandler);
    
            // The iteration is in this way because, otherwise, a remove inside a for loop gives troubles.
            while (connectionsAccepted.size()!=0) {
                Iterator<ClientHandler> iterator = connectionsAccepted.keySet().iterator();
                ClientHandler client = iterator.next();
                connectionsAccepted.remove(client);
                client.notifyOpponentClientDisconnected(username);
            }
            
            cleanServer();
        }
    }
    
    /**
     * This methods deletes the clients from the game, because one of them won.
     */
    @Override
    public synchronized void handleWinning() {
        // The iteration is in this way because, otherwise, a remove inside a for loop gives troubles.
        while (connectionsAccepted.size()!=0) {
            Iterator<ClientHandler> iterator = connectionsAccepted.keySet().iterator();
            ClientHandler client = iterator.next();
            connectionsAccepted.remove(client);
            client.endConnection();
        }
        
        cleanServer();
    }
    
    /**
     * This method deletes the loosing client.
     *
     * @param clientHandler the client who lost.
     */
    @Override
    public synchronized void handleLoosing(ClientHandler clientHandler) {
        connectionsAccepted.remove(clientHandler);
        
        if (connectionsAccepted.size()==0)
            cleanServer();
    }
    
    /**
     * It cleans the server.
     */
    private void cleanServer(){
        maxPlayersNumber = -1;
        firstClientHandler = null;
        firstPlayerConnected = false;
        gameStarted = false;
    }
    
    /**
     * It checks if the username and the color chosen by the client are different by the ones chosen by the others.
     *
     * @param username player's username.
     * @param color player's color.
     * @return what is equal to the others players. If username and color are different it returns null. Indeed, a
     * null return means that the client can play with these parameters.
     */
    private String checkParameters(String username, Color color) {
        PlayerParameters opponentsParameters;
    
        for (ClientHandler clientHandler : connectionsAccepted.keySet()) {
            opponentsParameters = connectionsAccepted.get(clientHandler);
        
            if (opponentsParameters.username.equals(username) && opponentsParameters.color.equals(color))
                return "username and color";
            if (opponentsParameters.username.equals(username))
                return "username";
            if (opponentsParameters.color.equals(color))
                return "color";
        }
        
        return null;
    }
    
    /**
     * It starts the game and sets {@link #gameStarted} = true, notifying all the players waiting inside
     * {@link #handleFirstConnection}.
     */
    private void initGame() {
        // These maps have to be passed to the gameController.
        HashMap<String,Color> mapUserColor = new HashMap<>();
        HashMap<String, VirtualView> mapUserVirtualView = new HashMap<>();
        
        for (ClientHandler clientHandler : connectionsAccepted.keySet()){
            PlayerParameters clientHandlerParameters = connectionsAccepted.get(clientHandler);
            String username = clientHandlerParameters.username;
            Color color = clientHandlerParameters.color;
            VirtualView virtualView = clientHandler.createVirtualView(username, color);
            
            mapUserColor.put(username, color);
            mapUserVirtualView.put(username, virtualView);
        }
        
        System.out.println("Game started.");
        gameStarted = true;
        gameController = new GameController(maxPlayersNumber, mapUserColor, mapUserVirtualView);
        
        notifyAll();
    }
}