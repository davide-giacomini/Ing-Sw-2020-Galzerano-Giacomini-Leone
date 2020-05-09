package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Model.Gods.*;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Network.Server.VirtualViewListener;
import it.polimi.ingsw.PSP47.Visitor.ControllerVisitor;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class represents the controller of the game. It creates the instance of the game with all its elements
 * and contains all the methods used to update the model.
 */
public class GameController implements VirtualViewListener {
    private int numberOfPlayers;
    private static Game game;
    private ArrayList<VirtualView> views;
    private int indexOfCurrentPlayer;
    private TurnController turn;
    private ControllerVisitor controllerVisitor;

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
        controllerVisitor = new ControllerVisitor(this);
        for(Map.Entry<String,Color> entry : mapUserColor.entrySet()) {
            String username = entry.getKey();
            Color color = entry.getValue();
            game.addPlayer(new Player(username, color));

            VirtualView virtualView = mapUserVirtualView.get(username);
            setView(virtualView);
            virtualView.addVirtualViewListener(this);
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
    private void startController() {
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
        sendAnAdvice();
    }

    /**
     * This method sets the god that has been chosen by a player in his class.
     * It also delete this god from the list of available gods.
     * @param god the chosen god
     */
    public void setGod(GodName god) {
        if (!game.getGods().contains(god)) {
            String textError = "You cannot choose this god, it's not available";
            views.get(indexOfCurrentPlayer).sendError(textError);
            ArrayList<GodName> godsList = new ArrayList<>(game.getGods());
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
            return;
        }
        try {
            game.getPlayer(indexOfCurrentPlayer).setGod(chooseGod(god, game.getPlayer(indexOfCurrentPlayer)));
        } catch (IOException e) {
            views.get(indexOfCurrentPlayer).sendError("Try again, there were some troubles in the conversion.");
            ArrayList<GodName> godsList = new ArrayList<>(game.getGods());
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
        }
        game.getGods().remove(god);
        incrementIndex();
        if (indexOfCurrentPlayer == 0)
            startGame();
        else {
            ArrayList<GodName> godsList = new ArrayList<>(game.getGods());
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
            sendAnAdvice();
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
     * This method set both workers into their correspondent slots, checking if they're already occupied or
     * if the chosen slots are out of range.
     */
    public void setWorkers( int[] RowsAndColumns)  {
            int row1 = RowsAndColumns[0];
            int column1 = RowsAndColumns[1];
            int row2 = RowsAndColumns[2];
            int column2 = RowsAndColumns[3];
            if (row1 > 4 || row1 < 0 || row2 > 4 || row2 < 0 || column1 > 4 || column1 < 0 || column2 > 4 || column2 < 0) {
               String errorText = "One of the values you chose is out of range";
                views.get(indexOfCurrentPlayer).sendError(errorText);
                views.get(indexOfCurrentPlayer).sendSetWorkers();
                return;
            }
            Slot slot1 = game.getBoard().getSlot(row1, column1);
            Slot slot2 = game.getBoard().getSlot(row2, column2);
            if (slot1 == slot2) {
                String errorText = "You must choose two different slots";
                views.get(indexOfCurrentPlayer).sendError(errorText);
                views.get(indexOfCurrentPlayer).sendSetWorkers();
                return;
            }
            if (slot1.isOccupied() || slot2.isOccupied()) {
                String errorText = "One of these slots has been already chosen";
                views.get(indexOfCurrentPlayer).sendError(errorText);
                views.get(indexOfCurrentPlayer).sendSetWorkers();
                return;
            }
            Worker chosenWorkerMale = game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE);
            game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerMale, game.getBoard().getSlot(row1, column1));
            Worker chosenWorkerFemale = game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.FEMALE);
            game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerFemale, game.getBoard().getSlot(row2, column2));

            incrementIndex();
            if(indexOfCurrentPlayer == 0) {
                turn = new TurnController(views, game, indexOfCurrentPlayer, this);
                turn.startTurn();
            }
            else {
                views.get(indexOfCurrentPlayer).sendSetWorkers();
                sendAnAdvice();
            }
        }

    /**
     * This method creates a random order for the turn.
     * It must be called at the start of the game.
     */
    private void newRoundOrder() {
        game.createNewPlayersList();
        orderViews();
    }

    /**
     * This method creates the order of the round for the whole game and then it sends all the public information
     * to all the players.
     */
     private void startGame() {
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
        sendAnAdvice();
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
    private void orderViews() {
        VirtualView temp;
        for(int i=0; i<numberOfPlayers; i++) {
            if (!game.getPlayer(i).getUsername().equals(views.get(i).getUsername()))
                for(int j=0; j<numberOfPlayers; j++) {
                    if (views.get(j).getUsername().equals(game.getPlayer(i).getUsername())) {
                        temp = views.get(i);
                        views.set(i, views.get(j));
                        views.set(j, temp);
                    }
                }
        }
    }

    /**
     * This method create a new turn of the game and makes it start.
     */
    public void turn() {
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

    /**
     * This method is used just in the case when the third player lose the game.
     * It fixes the index of the other players and start a new turn.
     */
    private void fixIndexAndStart() {
        if (indexOfCurrentPlayer == 2)
            indexOfCurrentPlayer = 0;
        turn = new TurnController(views, game, indexOfCurrentPlayer, this);
        turn.startTurn();
    }

    public TurnController getTurn() {
        return turn;
    }

    /**
     * This method sends an advice to all the players, except for the one who is playing,
     * to inform them about who is playing in the current turn.
     */
    void sendAnAdvice() {
        for (VirtualView view : views) {
            if (!(view.getUsername().equals(views.get(indexOfCurrentPlayer).getUsername())))
                view.sendImportant("It's " + views.get(indexOfCurrentPlayer).getUsername() + "'s turn");
        }
    }

    /**
     * This method deletes a losing player from the game and notifies all the players.
     * If the players were just two, it also declares the winner and ends the game.
     */
    void removeLosingPlayer() {
        for (VirtualView view : views) {
            view.sendImportant("The player " + views.get(indexOfCurrentPlayer).getUsername() + "has just lost.");
        }

        Slot slot = game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE).getSlot();
        slot.setWorker(null);
        slot = game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE).getSlot();
        slot.setWorker(null);

        game.getPlayers().remove(game.getPlayer(indexOfCurrentPlayer));
        views.get(indexOfCurrentPlayer).sendLosingAdvice();

        views.remove(views.get(indexOfCurrentPlayer));

        if (game.getNumberOfPlayers() == 2) {
            endGame();
        }
        else {
            game.setNumberOfPlayers(2);
            fixIndexAndStart();
        }
    }

    /**
     * This method close the game when someone has won.
     */
    void endGame() {
        for (VirtualView view : views) {
            view.sendImportant("The player " + views.get(indexOfCurrentPlayer).getUsername() + "has just won.");
        }
        views.get(indexOfCurrentPlayer).sendWinningAdvice();
    }

    /**
     * This method implements the update of the Observer Pattern.
     * It's called every time the virtual view receives a message from the
     * client side, so its content is notified to the controller.
     * @param visitableObject the content of the message.
     */
    @Override
    public void update(Visitable visitableObject) {
        visitableObject.accept(controllerVisitor);
    }
}
