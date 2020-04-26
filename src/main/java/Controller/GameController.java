package Controller;

import Enumerations.Color;
import Enumerations.Gender;
import Enumerations.GodName;
import Model.Game;
import Model.Gods.*;
import Model.Player;
import Model.Slot;
import Model.Worker;
import Network.Server.VirtualView;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class represents the controller of the game. It creates the instance of the game with all its elements
 * and contains all the methods used to update the model.
 */
public class GameController {
    private int numberOfPlayers;
    private static Game game;
    private ArrayList<VirtualView> views;
    private int indexOfCurrentPlayer;

    /**
     * This is the constructor of the GameController which creates the game and set the random player who will
     * choose the gods that can be used in this game.
     * @param numberOfPlayers the number of player of the game which is chosen by the first player who connect.
     * @param mapUserColor usernames and colors of each player.
     */
    public GameController(int numberOfPlayers, HashMap<String,Color> mapUserColor, HashMap<String, VirtualView> mapUserVirtualView){
        this.numberOfPlayers = numberOfPlayers;
        this.views = new ArrayList<>(numberOfPlayers);
        game = new Game(numberOfPlayers);
        for(Map.Entry<String,Color> entry : mapUserColor.entrySet()) {
            String username = entry.getKey();
            Color color = entry.getValue();
            game.addPlayer(new Player(username, color));

            VirtualView virtualView = mapUserVirtualView.get(username);
            setView(virtualView);
            virtualView.setController(this);
        }
        game.setRandomPlayer(chooseRandomPlayer());
        startController();
    }

    /**
     * This method choose a random player who will decide which gods can be used in the game and the order of the players in a round.
     * @return the instance of the chosen player
     */
     private Player chooseRandomPlayer(){
        ArrayList<Player> players = game.getPlayers();
        Player randomPlayer;
        Random rand = new Random();

        randomPlayer = players.get(rand.nextInt(numberOfPlayers));
        game.setRandomPlayer(randomPlayer);
        game.putRandomAtLastPosition();
        return randomPlayer;
    }

    /**
     * This method is the first method which is launched by the server.
     */
    public void startController() {
         orderViews();
         int index = game.getPlayers().indexOf(game.getPlayer(game.getRandomPlayer().getUsername()));
        for (VirtualView view : views) {
            view.sendNumberOfPlayers(numberOfPlayers);
        }
         views.get(index).sendChallenger();
    }

    /**
     * Update the model with the gods that will be used in the game.
     * @param gods list of chosen gods.
     */
    public void setGods(ArrayList<GodName> gods) {
        game.setGods(gods);
        game.putRandomAtLastPosition();
        orderViews();
        views.get(indexOfCurrentPlayer).sendGodsList(game.getGods());
    }

    /**
     * This method sets the god that has been chosen by a player in his class.
     * It also delete this god from the list of available gods.
     * @param god the chosen god
     * @throws IOException if the god is not one of the enumeration.
     */
    public void setGod(GodName god) throws IOException {
        Game.getPlayer(indexOfCurrentPlayer).setGod(chooseGod(god, Game.getPlayer(indexOfCurrentPlayer)));
        game.getGods().remove(god);
        incrementIndex();
        if (indexOfCurrentPlayer == 0)
            startGame();
        else
            views.get(indexOfCurrentPlayer).sendGodsList(game.getGods());
    }

    /**
     * This method convert the enumeration into the corrispondent class.
     * @param god the god that has to be converted
     * @param player the player who chose this god
     * @return the converted god
     * @throws IOException if the god is not one of the enumeration.
     */
    private God chooseGod(GodName god, Player player) throws IOException {
        switch (god) {
            case PAN: return new Pan(player, "Pan");
            case ATLAS: return new Atlas(player, "Atlas");
            case APOLLO: return new Apollo(player, "Apollo");
            case ATHENA: return new Athena(player, "Athena");
            case ARTEMIS: return new Artemis(player, "Artemis");
            case DEMETER: return new Demeter(player, "Demeter");
            case MINOTAUR: return new Minotaur(player, "Minotaur");
            case HEPHAESTUS: return new Hephaestus(player, "Hephaestus");
            case PROMETHEUS: return new Prometheus(player, "Prometheus");
            default: throw new IOException();
        }
    }

    /**
     * This method set a worker into a slot, checking if it's already occupied.
     */
    public void setWorkers( int[] RowsAndColumns) throws InvalidDirectionException, GodNotSetException {
        try {
            int row1 = RowsAndColumns[0];
            int column1 = RowsAndColumns[1];
            int row2 = RowsAndColumns[2];
            int column2 = RowsAndColumns[3];
            if (row1 > 4 || row2 > 4 || column1 > 4 || column2 > 4)
                throw new IndexOutOfBoundsException();
            boolean result;
            Worker chosenWorkerMale = Game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE);
            result = Game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerMale, game.getBoard().getSlot(row1, column1));
            if (!result) {
                throw new SlotOccupiedException();
            }
            Worker chosenWorkerFemale = Game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.FEMALE);
            result = Game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerFemale, game.getBoard().getSlot(row2, column2));
            if (!result) {
                throw new SlotOccupiedException();
            }
            incrementIndex();
            if(indexOfCurrentPlayer == 0) {
                firstTurn();
            }
            else {
                views.get(indexOfCurrentPlayer).sendSetWorkers();
            }
        }catch (IndexOutOfBoundsException e) {
            // views.get(indexOfCurrentPlayer).sendError()
        }catch (SlotOccupiedException e) {
            // views.get(indexOfCurrentPlayer).sendError()
        }
    }

    /**
     * This method creates a random order for the turn.
     * It must be called at the start of the game.
     */
    public void newRoundOrder() {
        game.createNewPlayersList();
        orderViews();
    }

    /**
     * This method creates the order of the round for the whole game and then it sends all the public information
     * to all the players.
     */
    public void startGame() throws IOException {
        newRoundOrder();
        ArrayList<String> usernames = new ArrayList<>(numberOfPlayers);
        ArrayList<Color> colors = new ArrayList<>(numberOfPlayers);
        ArrayList<GodName> godNames = new ArrayList<>(numberOfPlayers);
        for (Player player : game.getPlayers() ) {
            usernames.add(player.getUsername());
            colors.add(player.getColor());
            godNames.add(player.getGodName());
        }
        for (VirtualView view : views) {
            view.sendPublicInformation(usernames, colors, godNames);
        }

        views.get(indexOfCurrentPlayer).sendSetWorkers();

    }

    /**
     * Add a view to the ArrayList views.
     * @param view the view that has to be added.
     */
    private void setView(VirtualView view) {
        this.views.add(view);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                game.getBoard().getSlot(i, j).addSlotListener(view);
            }
        }

    }

    /**
     * This method order the ArrayList of Virtual Views the same as the players in the Game class.
     * This is because the indexOfCurrentPlayer must refers to the player and to the VirtualView at the same time.
     */
    public void orderViews() {
        VirtualView temp;
        for(int i=0; i<numberOfPlayers; i++) {
            if (!Game.getPlayer(i).getUsername().equals(views.get(i).getUsername()))
                for(int j=0; j<numberOfPlayers; j++) {
                    if (views.get(j).getUsername().equals(Game.getPlayer(i).getUsername())) {
                        temp = views.get(i);
                        views.set(i, views.get(j));
                        views.set(j, temp);
                    }
                }
        }
    }

    public void firstTurn() throws InvalidDirectionException, GodNotSetException {
        turn = new TurnController(views, game, indexOfCurrentPlayer, this);
        turn.startTurn();
    }

    public void turn() throws InvalidDirectionException, GodNotSetException {
        incrementIndex();
        turn = new TurnController(views, game, indexOfCurrentPlayer, this);
        turn.startTurn();
    }

    /**
     * This method increments the index of the current player. If it is equal to the number of player,
     * a new round is starting.
     */
    private void incrementIndex() {
        if (indexOfCurrentPlayer<numberOfPlayers-1)
            indexOfCurrentPlayer++;
        else
            indexOfCurrentPlayer=0;
    }

}
