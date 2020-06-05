package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Network.Server.VirtualViewListener;
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
    private Game game;
    private String chosenPlayer;
    private ArrayList<GodName> availableGods;
    private ArrayList<VirtualView> views;
    private int indexOfCurrentPlayer;
    private int indexOfChallenger;
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
        game.setChallenger(chooseChallenger());
        startController();
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * This method choose a random player who will decide which gods can be used in the game and the order of the players in a round.
     * @return the instance of the chosen player
     */
     private Player chooseChallenger(){
        ArrayList<Player> players = game.getPlayers();
        Player challenger;
        Random rand = new Random();

        challenger = players.get(rand.nextInt(numberOfPlayers));
        game.setChallenger(challenger);
        game.putChallengerAtLastPosition();
        return challenger;
    }

    /**
     * This method is the first method which is launched in the constructor to make the game start.
     */
    private void startController() {
        orderViews();
        indexOfChallenger = game.getPlayers().indexOf(game.getPlayer(game.getChallenger().getUsername()));
        for (VirtualView view : views) {
            // This sending is useful to set in the client the number of players (it doesn't advice the players)
            view.sendNumberOfPlayers(numberOfPlayers);
        }
        ArrayList<String> usernames = new ArrayList<>();
        for (VirtualView view : views)
            usernames.add(view.getUsername());
        
        // Here the interaction between controller and client begins.
        // The challenger is sent the list of usernames of the other players, to choose who will begin
        views.get(indexOfChallenger).sendChallenger(usernames);
    }

    /**
     * Update the model with the gods that will be used in the game.
     * @param gods list of chosen gods.
     */
    public void setGods(ArrayList<GodName> gods, String chosenPlayer) {
        for (GodName god : gods) {
            try {
                if (game.getNumberOfPlayers() == 3 && !god.chooseGod(god, getGame().getPlayer(indexOfCurrentPlayer)).threePlayersGame()) {
                    String textError = "You cannot choose a god which is not available in a three players game";
                    views.get(indexOfChallenger).sendError(textError);
                    ArrayList<String> usernames = new ArrayList<>();
                    for (VirtualView view : views)
                        usernames.add(view.getUsername());
                    // Here the challenger is sent the list of usernames of the other players, to choose who will begin
                    views.get(indexOfChallenger).sendChallenger(usernames);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.chosenPlayer = chosenPlayer;
        availableGods = gods;
        game.putChallengerAtLastPosition();
        orderViews();
        ArrayList<GodName> godsList = new ArrayList<>(gods);
        views.get(indexOfCurrentPlayer).sendGodsList(godsList);
        sendWhoseIsTheTurn();
    }

    /**
     * This method sets the god that has been chosen by a player in his class.
     * It also delete this god from the list of available gods.
     * @param god the chosen god
     */
    public void setGod(GodName god) {
        if (!availableGods.contains(god)) {
            String textError = "You cannot choose this god, it's not available";
            views.get(indexOfCurrentPlayer).sendError(textError);
            ArrayList<GodName> godsList = new ArrayList<>(availableGods);
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
            return;
        }
        try {
            game.getPlayer(indexOfCurrentPlayer).setGod(god.chooseGod(god, game.getPlayer(indexOfCurrentPlayer)));
        } catch (IOException e) {
            views.get(indexOfCurrentPlayer).sendError("Try again, there were some troubles in the conversion.");
            ArrayList<GodName> godsList = new ArrayList<>(availableGods);
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
        }
        availableGods.remove(god);
        incrementIndex();
        if (indexOfCurrentPlayer == 0) {
            for (VirtualView view : views)
            view.sendImportant("The game is started!", MessageType.START_GAME);
            startGame();
        }
        else {
            ArrayList<GodName> godsList = new ArrayList<>(availableGods);
            views.get(indexOfCurrentPlayer).sendGodsList(godsList);
            sendWhoseIsTheTurn();
        }
    }

    /**
     * This method set both workers into their correspondent slots, checking if they're already occupied,
     * if they are two different slots or if they are out of range. If all these checks are negative
     * the slots are setted, otherwise the method sends an error and asks again.
     */
    public void setWorkers( int[] RowsAndColumns)  {
            int row1 = RowsAndColumns[0];
            int column1 = RowsAndColumns[1];
            int row2 = RowsAndColumns[2];
            int column2 = RowsAndColumns[3];
            if (row1 > 4 || row1 < 0 || row2 > 4 || row2 < 0 || column1 > 4 || column1 < 0 || column2 > 4 || column2 < 0) {
               String errorText = "One of the values you chose is out of range";
                views.get(indexOfCurrentPlayer).sendError(errorText);
                views.get(indexOfCurrentPlayer).sendAskWorkersPosition();
                return;
            }
            Slot slot1 = game.getBoard().getSlot(row1, column1);
            Slot slot2 = game.getBoard().getSlot(row2, column2);
            if (slot1 == slot2) {
                String errorText = "You must choose two different slots";
                views.get(indexOfCurrentPlayer).sendError(errorText);
                views.get(indexOfCurrentPlayer).sendAskWorkersPosition();
                return;
            }
            if (slot1.isOccupied() || slot2.isOccupied()) {
                String errorText = "One of these slots has been already chosen";
                views.get(indexOfCurrentPlayer).sendError(errorText);
                views.get(indexOfCurrentPlayer).sendAskWorkersPosition();
                return;
            }
            Worker chosenWorkerMale = game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE);
            game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerMale, game.getBoard().getSlot(row1, column1));
            Worker chosenWorkerFemale = game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.FEMALE);
            game.getPlayer(indexOfCurrentPlayer).putWorkerOnSlot(chosenWorkerFemale, game.getBoard().getSlot(row2, column2));
            sendAnAdviceDuringGame("The workers have been initially set");
            incrementIndex();
            if(indexOfCurrentPlayer == 0) {
                turn = new TurnController(views, game, indexOfCurrentPlayer, this);
                turn.startTurn();
            }
            else {
                views.get(indexOfCurrentPlayer).sendAskWorkersPosition();
                sendWhoseIsTheTurn();
            }
        }

    /**
     * This method creates the order that will be follow during the game.
     * The first player has been chosen by the Challenger, then the order remains the same.
     * It must be called at the start of the game.
     */
    private void newRoundOrder() {
        game.createNewPlayersList(chosenPlayer);
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

        views.get(indexOfCurrentPlayer).sendAskWorkersPosition();
        sendWhoseIsTheTurn();
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
        for(int i=0; i<game.getNumberOfPlayers(); i++) {
            if (!game.getPlayer(i).getUsername().equals(views.get(i).getUsername()))
                for(int j=0; j<game.getNumberOfPlayers(); j++) {
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
    public void changeTurn() {
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
    void sendWhoseIsTheTurn() {
        for (VirtualView view : views) {
            if (!(view.getUsername().equals(views.get(indexOfCurrentPlayer).getUsername())))
                view.sendImportant( views.get(indexOfCurrentPlayer).getUsername() , MessageType.TURN);
        }
    }

    /**
     * This method deletes a losing player from the game and notifies all the players.
     * If the players were just two, it also declares the winner and ends the game.
     */
    void removeLosingPlayer(String username) {

        if (game.getNumberOfPlayers() == 2) {
            incrementIndex();
            endGame(game.getPlayer(indexOfCurrentPlayer).getUsername());
        }
        else {
            for (VirtualView view : views) {
                view.sendImportant(username, MessageType.LOSING);
            }

            game.checkWhenPlayerIsDeleted(game.getPlayer(indexOfCurrentPlayer).getGodName());

            game.getPlayer(indexOfCurrentPlayer).deleteWorkers();
            game.removePlayer(game.getPlayer(indexOfCurrentPlayer));
            views.get(indexOfCurrentPlayer).sendYouAreOutOfTheGame(false);
            views.get(indexOfCurrentPlayer).removeVirtualViewListener(this);
            views.remove(views.get(indexOfCurrentPlayer));

            setNumberOfPlayers(2);
            game.setNumberOfPlayers(2);
            orderViews();
            fixIndexAndStart();
        }
    }

    /**
     * This method close the game when someone has won.
     */
    void endGame(String username) {
        game.setActive(false);
        for (VirtualView view : views) {
            view.sendImportant(username, MessageType.WINNING);
            view.sendYouAreOutOfTheGame(true);
        }
    }

    /**
     * This method sends to all the players the information about the evolution of the game,
     * for example which player has just build, or which player has chosen the worker to use.
     * @param changes the action which has to be communicated.
     */
    void sendAnAdviceDuringGame(String changes){
        for (VirtualView view : views) {
            view.sendImportant( changes , MessageType.DURING_TURN);
        }
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

    /**
     * This method sets all the elements to null.
     * It's used in the tests to clear the game.
     */
    void clearController() {
        this.numberOfPlayers = 0;
        this.views = null;
        this.game = null;
        this.indexOfCurrentPlayer = 0;
        this.turn = null;
        this.controllerVisitor = null;
    }

    ArrayList<VirtualView> getViews() {
        return views;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Game getGame() {
        return game;
    }

    int getIndexOfCurrentPlayer() {
        return indexOfCurrentPlayer;
    }

    public void setIndexOfCurrentPlayer(int indexOfCurrentPlayer) {
        this.indexOfCurrentPlayer = indexOfCurrentPlayer;
    }

    public ArrayList<GodName> getAvailableGods() {
        return availableGods;
    }
}
