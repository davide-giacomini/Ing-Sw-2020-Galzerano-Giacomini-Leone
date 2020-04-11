package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class has to be completed yet.
 */
public class Game {
    private static int numberOfPlayers;
    private boolean start;
    private boolean end;
    private static ArrayList<Player> players;

    public static final Random rand = new Random();

    public Game() {
        //this.players = new Player[3];
        this.start = false;
        this.end = false;
        numberOfPlayers = 0;
        players = new ArrayList<>();
    }

    public void addPlayer (String username, Color c, int i) {
        players.set(i, new Player(username, c));
    }

    public void addPlayer(Player player) {
        if (start) {
            //throw exception because game has already started and
            //it is not possible to add a player
        }
        if (players.size() >= 4) {
            //throw exception because it's not
            //possible to have more than 3 players
        }
        players.add(player);
        numberOfPlayers++;
    }

    public Slot getSlot(int row, int column) {
        return Board.getBoard().getSlot(row, column);
    }
    
    public boolean isGameEnded() {
        return end;
    }

    public boolean isGameStarted() {
        return start;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public static Player getPlayer(int i) { return players.get(i); }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        Game.numberOfPlayers = numberOfPlayers;
    }

}

