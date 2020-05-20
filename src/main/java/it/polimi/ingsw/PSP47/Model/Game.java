package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.GodName;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains all the elements of a game.
 */
public class Game {
    private boolean isActive;
    private final Board board;
    private int numberOfPlayers;
    private Player randomPlayer;
    private ArrayList<GodName> gods;
    private ArrayList<Player> players ;

    public Game(int numberOfPlayers) {
        this.isActive = true;
        this.numberOfPlayers = numberOfPlayers;
        players = new ArrayList<>(numberOfPlayers);
        board = new Board();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<GodName> getGods() {
        return gods;
    }

    public void setGods(ArrayList<GodName> gods) {
        this.gods = gods;
    }

    public Player getRandomPlayer() {
        return randomPlayer;
    }

    public void setRandomPlayer(Player randomPlayer) {
        this.randomPlayer = randomPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Player getPlayer(int i) { return players.get(i); }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * This method put the randomPlayer in the last position.
     */
    public void putRandomAtLastPosition() {
        int index = 3;
        for (int i=0; i<numberOfPlayers; i++) {
            if (players.get(i) == randomPlayer)
                index = i;
        }
        if (index == 0 && numberOfPlayers == 2) {
            players.set(0, players.get(1));
            players.set(1, randomPlayer);
        }
        else if (index == 0 && numberOfPlayers == 3) {
            players.set(0, players.get(1));
            players.set(1, players.get(2));
            players.set(2, randomPlayer);
        }
        else if (index == 1 && numberOfPlayers == 3) {
            players.set(1,players.get(2));
            players.set(2,randomPlayer);
        }
    }

    /**
     * This method replaces the ArrayList players with a new ArrayList with the players
     * in the order chosen by the Challenger (he decided the first player, then the order has to
     * remains equal).
     * @param chosenPlayer the player chosen by the Challenger to be first.
     */
    public void createNewPlayersList(String chosenPlayer) {
        int index = -1;
        ArrayList<Player> newArray = new ArrayList<>(numberOfPlayers);
        for (int i=0; i<numberOfPlayers; i++) {
            if (players.get(i).getUsername().equals(chosenPlayer))
                index = i;
        }
        if (index == 0)
            newArray = players;
        else if (index == 1 && numberOfPlayers == 3) {
            newArray.add(players.get(index));
            newArray.add(players.get(2));
            newArray.add(players.get(0));
        }
        else if (index == numberOfPlayers - 1) {
            newArray.add(players.get(index));
            newArray.add(players.get(0));
            if (numberOfPlayers == 3)
                newArray.add(players.get(1));
        }
        players = newArray;
    }


    /**
     * This method returns the player who has a specific username.
     * @param username the username that you want to found the correspondent player.
     * @return the player instance.
     */
    public Player getPlayer (String username) {
        for(int i=0; i<numberOfPlayers; i++) {
            if (username.equals(players.get(i).getUsername()))
                return players.get(i);
        }
        return null;
    }

    public void removePlayer (Player player) {
        players.remove(player);
    }

}

