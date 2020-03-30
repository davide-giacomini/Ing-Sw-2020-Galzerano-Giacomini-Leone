package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    protected static Board gameBoard;

    private boolean start;
    private boolean end;
    private ArrayList<Player> players;

    public static final Random rand = new Random();
    // Array dichiarato da tre giocatori, ma se fossero due?
    public Game() {
        //this.players = new Player[3];
        this.gameBoard = Board.getInstance();
        this.start = false;
        this.end = false;
        players = new ArrayList<>();
    }


    public void addPlayer (String username, Color c, int i) {
        players.set(i, new Player(username, c));
    }
    public void addPlayer(Player player) {
        if (start == true )
            //throw exception because game has already started and
            //it is not possible to add a player

            if (players.size() >= 4) //throw exception because it's not
                //possible to have more than 3 players
                players.add(player);
    }

    public Slot getSlot(int row, int column) {
        return gameBoard.getSlot(row, column);
    }


    public static void main(String[] args) {
        Game prova = new Game();
        prova.addPlayer("monica", Color.BLUE, 0);
        prova.players.get(0).putWorker(prova.players.get(0).chooseWorker(0), gameBoard.getSlot(2,2));
        prova.players.get(0).move(gameBoard.getSlot(2,3), prova.players.get(0).chooseWorker(0));

    }

    public static Board getGameBoard() {
        return gameBoard;
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
}
