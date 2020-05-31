package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Gods.Athena;

import java.util.ArrayList;

/**
 * This class contains all the elements of a game.
 */
public class Game {
    private boolean isActive;
    private final Board board;
    private int numberOfPlayers;
    private Player challenger;
    private ArrayList<GodName> gods;
    private ArrayList<Player> players;

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

    public Player getChallenger() {
        return challenger;
    }

    public void setChallenger(Player challenger) {
        this.challenger = challenger;
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
     * This method put the challenger in the last position.
     */
    public void putChallengerAtLastPosition() {
        int index = 3;
        for (int i=0; i<numberOfPlayers; i++) {
            if (players.get(i) == challenger)
                index = i;
        }
        if (index == 0 && numberOfPlayers == 2) {
            players.set(0, players.get(1));
            players.set(1, challenger);
        }
        else if (index == 0 && numberOfPlayers == 3) {
            players.set(0, players.get(1));
            players.set(1, players.get(2));
            players.set(2, challenger);
        }
        else if (index == 1 && numberOfPlayers == 3) {
            players.set(1,players.get(2));
            players.set(2, challenger);
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

    /**
     * This method checks if the player who has just moved is Athena.
     * If he is and if he has just moved up, he makes move up impossible to all the other players.
     * If he hasn't moved up, the other players can.
     * Otherwise, it does nothing.
     * @param player the player who has just moved.
     */
    public void checkIfPlayersCanMoveUp(Player player) {
        if (player.getGod().getName().equals("Athena")) {
            boolean moveUp = ((Athena)player.getGod()).isMoveUp();
            for (int i = 0; i<numberOfPlayers; i++) {
                if (getPlayer(i) != null && getPlayer(i) != player) {
                    getPlayer(i).setCannotMoveUp(moveUp);
                }
            }
        }
    }

    /**
     * This method is called after every move.
     * It checks if a player has just won the game.
     * There are two cases: if there is a player with the Hera card there is another control in addition to
     * check if the player has just move up in a third level.
     * In fact, to win he must also not be on a perimeter slot of be Hera.
     * @param currentWorker the worker that do the winning move.
     * @param currentPlayer the player who has just moved.
     * @return true if the player has won, false otherwise.
     */
    public boolean checkWinningCondition(Worker currentWorker, Player currentPlayer) {
        Player heraPlayer = checkIfTheGodIsPresent(GodName.HERA);
        if (heraPlayer != null)
            return currentPlayer.isWinning() && !(currentWorker.getSlot().isPerimeterSlot() && !(currentPlayer == heraPlayer));
        else
            return currentPlayer.isWinning();
    }
    //TODO davide -> questo secondo me bisognerebbe spostarlo nella classe Player, o addirittura nel Worker

    /**
     * This method is called after every build.
     * It checks if a player has just won the game.
     * It happens if there is a player with the Chronus card and if there are five complete towers on the board.
     * @return the player who has won, null if no one won.
     */
    public Player checkWinningCondition() {
        Player chronusPlayer = checkIfTheGodIsPresent(GodName.CHRONUS);
        if (chronusPlayer != null && board.getCountDomes() == 5)
            return chronusPlayer;
        else
            return null;
    }

    /**
     * This method checks if a god is present in the game and returns its player.
     * Otherwise, it returns null.
     * @param godName the god that must be checked
     * @return the player who has the god, null if there isn't.
     */
    private Player checkIfTheGodIsPresent(GodName godName) {
        for(Player playerToCheck : players){
            if (playerToCheck.getGod().getName().equals(godName.getGod())) {
                return playerToCheck;
            }
        }
        return null;
    }
}

