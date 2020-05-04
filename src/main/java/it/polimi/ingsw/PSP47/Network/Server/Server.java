package it.polimi.ingsw.PSP47.Network.Server;


import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Network.Client.Client;
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
 * This class instantiates a new server and wait for connections with clients.
 */
public class Server implements ClientHandlerListener {
    // Class for the pair username-color of the client
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
        VisitableInformation firstConnectionMessage = (VisitableInformation) message.getContent();
        String username = firstConnectionMessage.getUsername();
        Color color = firstConnectionMessage.getColor();
        
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
                clientHandler.notifyFirstClientDisconnected();
                notifyAll();
                return;
            }
            if (gameStarted) {
                clientHandler.notifyGameStartedWithoutYou();
                notifyAll();
                return;
            }
        }
    
        String wrongParameter;
        if ((wrongParameter = checkParameters(username, color)) != null) {
            clientHandler.askAgainParameters(wrongParameter);
            notifyAll();
            return;
        }
        
        connectionsAccepted.put(clientHandler, new PlayerParameters(username, color));
        clientHandlers.remove(clientHandler);
        
        if (connectionsAccepted.size() == maxPlayersNumber)
            initGame();
        
        notifyAll();
    }
    
    @Override
    public synchronized void setPlayersNumber(RequestPlayersNumber message) {
        maxPlayersNumber = ((VisitableInt) message.getContent()).getNumber();
        
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
    
    private void cleanServer(){
        Board.getBoard().clearBoard();
        maxPlayersNumber = -1;
        firstClientHandler = null;
        firstPlayerConnected = false;
        gameStarted = false;
    }
    
    private String checkParameters(String username, Color color) {
        PlayerParameters opponentsParameters;
    
        for (ClientHandler clientHandler : connectionsAccepted.keySet()) {
            opponentsParameters = connectionsAccepted.get(clientHandler);
        
            if (opponentsParameters.username.equals(username) && opponentsParameters.color.equals(color))
                return "username and color";
            if (opponentsParameters.username.equals(username))
                return "username";
            else if (opponentsParameters.color.equals(color))
                return "color";
        }
        
        return null;
    }
    
    private void initGame() {
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
        
        System.out.println("Gioco iniziato."); //TODO inizia il gioco
        gameStarted = true;
        gameController = new GameController(maxPlayersNumber, mapUserColor, mapUserVirtualView);
        
        notifyAll();
    }
}