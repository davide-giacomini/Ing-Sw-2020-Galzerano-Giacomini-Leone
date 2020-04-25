package Controller;

import Enumerations.Color;
import Enumerations.Gender;
import Enumerations.GodName;
import Model.Game;
import Model.Gods.*;
import Model.Player;
import Model.Worker;
import Network.Server.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameController {
    private int numberOfPlayers;
    private static Game game;
    private ArrayList<VirtualView> views;
    private int indexOfCurrentPlayer;

    /**
     * This is the constructor of the GameController which creates the game and set the random player who will
     * choose the gods that can be used in this game.
     * @param numberOfPlayers the number of player of the game which is chosen by the first player who connect.
     * @param map usernames and colors of each player.
     */
    public GameController(int numberOfPlayers, HashMap<String,Color> map){
        this.numberOfPlayers = numberOfPlayers;
        this.views = new ArrayList<>(numberOfPlayers);
        game = new Game(numberOfPlayers);
        for(Map.Entry<String,Color> entry : map.entrySet()) {
            String username = entry.getKey();
            Color color = entry.getValue();
            game.addPlayer(new Player(username, color));
        }
        game.setRandomPlayer(chooseRandomPlayer());
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

    public void tellChallenger() throws IOException {
         orderViews();
         int index = game.getPlayers().indexOf(game.getPlayer(game.getRandomPlayer().getUsername()));
         views.get(index).sendChallenger();
    }

    /**
     * Update the model with the gods that will be used in the game.
     * @param gods list of chosen gods.
     */
    public void setGods(ArrayList<GodName> gods) throws IOException {
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
        /*for (int i=0; i<numberOfPlayers; i++) {
            if (game.getGods().get(i).equals(god))
                game.getGods().remove(i);
        } */
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
     * @param numPlayer number of the Player who has chosen the god
     * @param workerGender gender of the selected worker
     * @param row row of the selected slot
     * @param column column of the selected slot
     */
    public void setWorker(int numPlayer, Gender workerGender, int row, int column) {
        Worker chosenWorker = Game.getPlayer(numPlayer).getWorker(workerGender);
        Game.getPlayer(numPlayer).putWorkerOnSlot(chosenWorker, game.getBoard().getSlot(row,column));
    }

    /**
     * This method creates a random order for the turn.
     * It must be called at the start of the game.
     */
    public void newRoundOrder() {
        game.createNewPlayersList();
        orderViews();
    }

    public void startGame() {
        newRoundOrder();
        // view.sendSetWorker
    }

    public void run() {
        // setta la divinità e currentPlayer++;
        // quando currentPlayer == numberOfPlayer fai startGame() che manda il turno di settaggio
        // ora che è tutto settato inizia il gioco vero
    }

    public void setView(VirtualView view) {
        this.views.add(view);
    }

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

    private void incrementIndex() {
        if (indexOfCurrentPlayer<numberOfPlayers-1)
            indexOfCurrentPlayer++;
        else
            indexOfCurrentPlayer=0;
    }

    // Start the game calling setStart of Game.
    // When the game is started, a random player is chosen as the beginner player.
    // La scelta del giocatore è inoltrata alla view, che mostrerà a tutti i giocatori quale
    // giocatore è stato scelto come Challenger.
    // Nel frattempo, GameController aggiornerà il proprio Challenger e chiamerà il metodo chooseGods(), metodo che servirà per
    // scegliere tre dei tra quelli disponibili.
    // chooseGods() chiamerà il metodo della view che chiede al challenger di scegliere gli dei. Una volta scelti gli dei,
    // viene notificato al GameController che gli dei sono stati scelti e quindi GameController provvederà a
    // chiamare il metodo che si fa il giro dei giocatori e gli chiede quale dio vogliono tra quei tre.
    // Il metodo in questione chiamerà una chooseGod(Player player) della view e
    // La VirtualView implementerà MessageListener e non appena gli dei saranno scelti, notificherà il GameController.
    // Il GameController viene notificato e inserisce gli dei corrispondenti nei giocatori.
    // A questo punto GameController chiede al Challenger, tramite VirtualView, di scegliere il giocatore iniziale.
}
