package Model;

import java.awt.*;

public class Game {
    protected static Board gameBoard;
    private Player[] players;

    // Array dichiarato da tre giocatori, ma se fossero due?
    public Game() {
        this.players = new Player[3];
        gameBoard = new Board();
    }

    public void addPlayer (String username, Color c, int i) {
        players[i] = new Player(username,c);
    }


    public Slot getSlot(int row, int column) {
        return gameBoard.getSlot(row, column);
    }


    public static void main(String[] args) {
        Game prova = new Game();
        prova.addPlayer("monica", Color.BLUE, 0);
        prova.players[0].putWorker(prova.players[0].chooseWorker(0), gameBoard.getSlot(2,2));
        prova.players[0].move(gameBoard.getSlot(2,3), prova.players[0].chooseWorker(0));

    }
}