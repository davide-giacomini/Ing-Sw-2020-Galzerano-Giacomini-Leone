package it.polimi.ingsw.PSP47.Network.Server;


import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
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

//TODO chiedere per synchronized

/**
 * This class wait for connections with clients and handles connections and disconnections, creating or deleting
 * the game.
 */
public class Server implements ClientHandlerListener {
    /**
     * The socket's port to connect to from the client.
     */
    public final static int SOCKET_PORT = 7777;
    private final ServerSocket serverSocket;
    private final ArrayList<GameServer> games = new ArrayList<>();
    
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
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.addClientHandlerListener(this);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
    
    /**
     * This method handles the first connection of each player. It checks if username and color are correct end then,
     * if the {@link GameServer} is ready, the method starts it.
     *
     * @param message it contains the username and the color chosen by the player.
     * @param clientHandler the handler of the client connected to the server.
     */
    @Override
    public void handleFirstConnection(FirstConnection message, ClientHandler clientHandler) {
        GameServer game;
        String wrongParameter;
    
        synchronized (this) {
            VisitableInformation firstConnectionMessage = (VisitableInformation) message.getContent();
            String username = firstConnectionMessage.getUsername();
            Color color = firstConnectionMessage.getColor();
            game = null;
        
            // Get the GameServer which contains the clientHandler
            for (GameServer localGame : games) {
                if (localGame.containsClientHandler(clientHandler))
                    game = localGame;
            }
        
            // If game is null it's because the clientHandler disconnected before the synchronized block
            if (game==null) return;
            
            wrongParameter = game.addParametersIfDifferent(username, color, clientHandler);
        }
    
        if (wrongParameter != null) {
            clientHandler.askAgainParameters(wrongParameter);
            return;
        }
        
        boolean gameReady;
        synchronized (this) {
            if (gameReady=(game.isReady() && !game.isStarted()))
                game.setStarted(true);
        }
    
        if (!gameReady) {
            String waitMessage = "Wait for the other players to connect.\n" +
                    "The game will start as soon as there are " + game.getPlayersNumber() + " players.";
            
            clientHandler.sendImportant(waitMessage, MessageType.IMPORTANT);
            return;
        }
        
        initGame(game);
    }
    
    /**
     * This method searches for a free game with the number of players specified bu the message. If there isn't such a
     * game, it creates a new game with the number of players mentioned above.
     *
     * @param message it contains the max number of players.
     */
    @Override
    public void setPlayersNumber(RequestPlayersNumber message, ClientHandler clientHandler) {
        int playersNumberChosen = ((VisitableInt) message.getContent()).getNumber();
        boolean clientHandlerAdded = false;
    
        synchronized (this) {
            for (GameServer game : games) {
                if (!game.isFull() && !game.isStarted() && playersNumberChosen == game.getPlayersNumber()) {
                    game.addClientHandler(clientHandler);
                    clientHandlerAdded = true;
                }
            }
        
            if (!clientHandlerAdded) {
                GameServer game = new GameServer(playersNumberChosen);
                game.addClientHandler(clientHandler);
                games.add(game);
            }
        }
    
        clientHandler.askParameters();
    }
    
    /**
     * It handles an illegal disconnection of a client. Illegal means that the client doesn't disconnect volountarly
     * or because the game ended.
     * The server, with this method, takes care of notifying and deleting all the other players, if necessary.
     *
     * @param clientHandler the client who disconnected.
     */
    @Override
    public void handleDisconnection(ClientHandler clientHandler) {
        String usernameDisconnected = null;
        ArrayList<ClientHandler> clientHandlersToNotify = new ArrayList<>();
    
        synchronized (this) {
            // Iterates over the game to find the clientHandler
            for (GameServer game: games) {
                if (game.containsClientHandler(clientHandler)) {
                    // If the game isn't started yet, the clientHandler can exit silently
                    if (!game.isStarted())
                        game.removeClientHandler(clientHandler);
                    // If the game is already started, the username is saved and the clientHandler is removed.
                    // Then, outside the synchronized block, the other players will be notified
                    else {
                        usernameDisconnected = game.getClientHandlers().get(clientHandler).getUsername();
                        game.removeClientHandler(clientHandler);
                        
                        // Store the opponentClientHandlers to notify them outside the loop.
                        clientHandlersToNotify.addAll(game.getClientHandlers().keySet());
                        
                        // Remove the opponentClientHandlers
                        for (ClientHandler opponentClientHandler: clientHandlersToNotify) {
                            game.removeClientHandler(opponentClientHandler);
                        }
                    }
                    
                    break;
                }
            }
        }
        
        if (usernameDisconnected!=null) {
            for (ClientHandler opponentClientHandler: clientHandlersToNotify) {
                opponentClientHandler.notifyOpponentClientDisconnected(usernameDisconnected);
            }
        }
    }
    
    /**
     * This method removes a client handler when the game is over.
     *
     * @param clientHandler game over for this clientHandler.
     */
    public synchronized void clientHandlerGameOver(ClientHandler clientHandler) {
        for (GameServer game: games) {
            if (game.containsClientHandler(clientHandler)) {
                game.removeClientHandler(clientHandler);
                break;
            }
        }
    }
    
    /**
     * It starts the game specified.
     *
     * @param game the game to be started.
     */
    private void initGame(GameServer game) {
        // These maps have to be passed to the gameController.
        HashMap<String,Color> mapUserColor = new HashMap<>();
        HashMap<String, VirtualView> mapUserVirtualView = new HashMap<>();
        HashMap<ClientHandler, PlayerParameters> clientHandlers = game.getClientHandlers();
        
        for (Map.Entry<ClientHandler, PlayerParameters> entry : clientHandlers.entrySet()){
            ClientHandler clientHandler = entry.getKey();
            String username = entry.getValue().getUsername();
            Color color = entry.getValue().getColor();
            
            VirtualView virtualView = clientHandler.createVirtualView(username, color);
            
            mapUserColor.put(username, color);
            mapUserVirtualView.put(username, virtualView);
        }
        
        System.out.println("Game " + game.toString() + " started.");
        new GameController(game.getPlayersNumber(), mapUserColor, mapUserVirtualView);
    }
}