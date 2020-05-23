package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Enumerations.Color;

import java.util.*;

/**
 * TODO javadoc
 */
class GameServer {
    private final int playersNumber;
    private final HashMap<ClientHandler, PlayerParameters> clientHandlers = new HashMap<>();
    private boolean started;
    
    GameServer(int playersNumber) {
        this.playersNumber = playersNumber;
    }
    
    int getPlayersNumber() {
        return playersNumber;
    }
    
    public boolean isStarted() {
        return started;
    }
    
    public void setStarted(boolean started) {
        this.started = started;
    }
    
    HashMap<ClientHandler, PlayerParameters> getClientHandlers(){
        return new HashMap<>(clientHandlers);
    }
    
    /**
     * This method add a {@link ClientHandler} to the {@link HashMap}.
     *
     * @param clientHandler the clientHandler to be added.
     */
    void addClientHandler(ClientHandler clientHandler){
        clientHandlers.put(clientHandler, null);
    }
    
    /**
     * This method checks if the {@link ClientHandler} specified is present.
     *
     * @param clientHandler the clientHandler to check.
     * @return true if the clientHandler is present, false otherwise.
     */
    boolean containsClientHandler(ClientHandler clientHandler){
        return clientHandlers.containsKey(clientHandler);
    }
    
    /**
     * It checks if the game is already full of players.
     *
     * @return true if it is full, false otherwise.
     */
    boolean isFull(){
        return clientHandlers.size()==playersNumber;
    }
    
    /**
     * It checks if the username and the color chosen by the client are different by the ones chosen by the others.
     *
     * @param username player's username.
     * @param color player's color.
     * @return what is equal to the others players. If username and color are different it returns null. Indeed, a
     * null return means that the client can play with these parameters.
     */
    String addParametersIfDifferent(String username, Color color, ClientHandler clientHandler) {
        
        for (PlayerParameters playerParameters : clientHandlers.values()) {
            
            if (playerParameters==null)
                continue;
            if (playerParameters.getUsername().equals(username) && playerParameters.getColor().equals(color))
                return "username and color";
            if (playerParameters.getUsername().equals(username))
                return "username";
            if (playerParameters.getColor().equals(color))
                return "color";
        }
        
        clientHandlers.put(clientHandler, new PlayerParameters(username, color));
        return null;
    }
    
    /**
     * It checks if this game can start.
     *
     * @return true if the game can start, false otherwise.
     */
    boolean isReady(){
        for (PlayerParameters playerParameters : clientHandlers.values()){
            if (playerParameters==null)
                return false;
        }
        
        return isFull();
    }
    
    /**
     * Removes the clientHandler specified.
     *
     * @param clientHandler the {@link ClientHandler} to be removed.
     */
    void removeClientHandler(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
    }
}

/**
 * This class is a tuple with a username and a color.
 */
class PlayerParameters {
    private final String username;
    private final Color color;
    
    PlayerParameters(String username, Color color) {
        this.username = username;
        this.color = color;
    }
    
    String getUsername() {
        return username;
    }
    
    Color getColor() {
        return color;
    }
}