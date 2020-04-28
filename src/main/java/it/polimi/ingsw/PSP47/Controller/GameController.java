package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import it.polimi.ingsw.PSP47.Model.Gods.*;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

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
    private TurnController turn;

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
     * This method is the first method which is launched in the constructor to make start the game.
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
        ArrayList<GodName> godsList = new ArrayList<>(gods);
        views.get(indexOfCurrentPlayer).sendGodsList(godsList);
    }

    /**
     * This method sets the god that has been chosen by a player in his class.
     * It also delete this god from the list of available gods.
     * @param god the chosen god
     * @throws IOException if the god is not one of the enumeration.
     */
    public void setGod(GodName god) throws IOException {
        if (!game.getGods().contains(god)) {
            String textError = "You cannot choose this god, it's not available in this game";
            views.get(indexOfCurrentPlayer).sendError(textError);
            ArrayList<GodName> godsList = new ArrayList<>(game.getGods());
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
            return;
        }
        Game.getPlayer(indexOfCurrentPlayer).setGod(chooseGod(god, Game.getPlayer(indexOfCurrentPlayer)));
        game.getGods().remove(god);
        incrementIndex();
        if (indexOfCurrentPlayer == 0)
            startGame();
        else {
            ArrayList<GodName> godsList = new ArrayList<>(game.getGods());
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
        }
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
    public void setWorkers( int[] RowsAndColumns)  {
        try {
            int row1 = RowsAndColumns[0];
            int column1 = RowsAndColumns[1];
            int row2 = RowsAndColumns[2];
            int column2 = RowsAndColumns[3];
            if (row1 > 4 || row1 < 0 || row2 > 4 || row2 < 0 || column1 > 4 || column1 < 0 || column2 > 4 || column2 < 0)
                throw new IndexOutOfBoundsException("One of the values you chose is out of range");

            Slot slot1 = game.getBoard().getSlot(row1, column1);
            Slot slot2 = game.getBoard().getSlot(row2, column2);
            if (slot1.getIsOccupied() || slot2.getIsOccupied())
                throw new SlotOccupiedException();

            Worker chosenWorkerMale = Game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE);
            Game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerMale, game.getBoard().getSlot(row1, column1));
            Worker chosenWorkerFemale = Game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.FEMALE);
            Game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerFemale, game.getBoard().getSlot(row2, column2));

            incrementIndex();
            if(indexOfCurrentPlayer == 0) {
                firstTurn();
            }
            else {
                views.get(indexOfCurrentPlayer).sendSetWorkers();
            }
        }catch (IndexOutOfBoundsException | SlotOccupiedException e) {
            String errorText = e.getMessage();
            views.get(indexOfCurrentPlayer).sendError(errorText);
            views.get(indexOfCurrentPlayer).sendSetWorkers();
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
    public void startGame() {
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
            view.sendPublicInformation(usernames, colors, godNames);        //TODO passare degli ArrayList seri
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

    public void firstTurn() {
        turn = new TurnController(views, game, indexOfCurrentPlayer, this);
        turn.startTurn();
    }

    public void turn()  {
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

    public TurnController getTurn() {
        return turn;
    }
}
