package Model;

import Model.Exceptions.GameAlreadyStartedException;

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

    //when a player is added it goes at the end of the arraylist
    /*public void addPlayer (String username, Color c, int i) {
        players.set(i, new Player(username, c));
    }*/

    public void addPlayer(Player player) throws GameAlreadyStartedException {
        if (start) {
            throw new GameAlreadyStartedException( "A new player cannot be added because the game is started");
            //throw exception because game has already started and
            //it is not possible to add a player
        }
        if (players.size() >= 3) {
            throw new GameAlreadyStartedException( "A new player cannot be added because the number of player is already MAX");
            //throw exception because it's not
            //possible to have more than 3 players
        }
        players.add(player);
        numberOfPlayers++;
    }

   /*public Slot getSlot(int row, int column) {
        return Board.getBoard().getSlot(row, column);
    }*/
    
    public boolean isGameEnded() {
        return end;
    }

    public boolean isGameStarted() {
        return start;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(Game.players);
    }

    public static Player getPlayer(int i) { return players.get(i); }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        Game.numberOfPlayers = numberOfPlayers;
    }

    public void setStart(){ start = true; }

    public void setEnd(){ end = true; }

}

