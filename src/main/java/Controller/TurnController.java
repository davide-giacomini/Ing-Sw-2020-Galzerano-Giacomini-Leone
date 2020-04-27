package Controller;

import Enumerations.Action;
import Enumerations.Direction;
import Enumerations.Gender;
import Model.Exceptions.GodNotSetException;
import Model.Exceptions.InvalidBuildException;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.InvalidMoveException;
import Model.Game;
import Model.Player;
import Model.Slot;
import Model.Turn;
import Network.Server.VirtualView;

import java.util.ArrayList;

public class TurnController {

    private GameController controller;
    private ArrayList<VirtualView> views;
    private Game game;
    private int indexOfCurrentPlayer;
    private Turn turn;
    private Player player;
    private Gender workerGender;

    //TODO ma l'eccezione GodNotSet serve? Io la leverei
    //TODO io catcherei anche l'invalidDirection perchè mi pare inutile
    public TurnController(ArrayList<VirtualView> views, Game game, int indexOfCurrentPlayer, GameController controller) {
        this.views = views;
        this.game = game;
        this.indexOfCurrentPlayer = indexOfCurrentPlayer;
        this.player = Game.getPlayer(indexOfCurrentPlayer);
        this.turn = new Turn(player);
        this.controller = controller;
    }

    void startTurn() {
        if (player.isLoosing())
            removeLosingPlayer();
        views.get(indexOfCurrentPlayer).sendWhichWorker();
    }

    public void setWorkerGender(int[] position) {
        int row = position[0];
        int column = position[1];
        if (game.getBoard().getSlot(row,column).getWorker() == null || game.getBoard().getSlot(row,column).getWorker().getColor() != player.getColor()) {
            String textError = "Your worker is not there!";
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichWorker();
            return;
        }
        try {
            workerGender = game.getBoard().getSlot(row,column).getWorker().getGender();
            if (!player.getGod().checkIfCanGoOn(player.getWorker(workerGender))) {
                if (workerGender == Gender.MALE)
                    workerGender = Gender.FEMALE;
                if (workerGender == Gender.FEMALE)
                    workerGender = Gender.MALE;
                String textError = "Your worker is blocked. You are forced to use the other one!";
                views.get(indexOfCurrentPlayer).sendError(textError);
            }
            turn.setWorkerGender(workerGender);
        } catch (InvalidMoveException e) {
            String textError = e.getMessage();
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichWorker();
        }
        views.get(indexOfCurrentPlayer).sendWhichAction();
    }

    public void executeAction(Action action, Direction direction) {
        switch (action) {
            case MOVE:
                if (player.isLoosing()) {
                    removeLosingPlayer();
                    break;
                }
                try {
                    turn.executeMove(direction);
                } catch (InvalidDirectionException | InvalidMoveException e) {
                    String textError = e.getMessage();
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                }
            case BUILD:
                if (player.isLoosing()) {
                    removeLosingPlayer();
                    break;
                }
                try {
                    turn.executeBuild(direction);
                } catch (InvalidDirectionException | InvalidBuildException e) {
                    String textError = e.getMessage();
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                }
            case BUILDDOME:
                if (player.isLoosing()) {
                    removeLosingPlayer();
                    break;
                }
                try {
                    turn.setWantsToBuildDome(true);
                    turn.executeBuild(direction);
                    } catch (InvalidDirectionException | InvalidBuildException e) {
                    String textError = e.getMessage();
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                     }
            case END:
                if (!turn.validateEndTurn()) {
                    String textError = "You cannot end your turn, you must do another action!";
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                }
                controller.turn();
        }
    }

    /**
     * This method deletes a losing player from the game and notifies all the players.
     * If the players were just two, it also declares the winner and ends the game.
     */
    private void removeLosingPlayer() {
        for(VirtualView view : views) {
            view.sendLosingPlayer(player.getUsername());
        }
        views.remove(views.get(indexOfCurrentPlayer));

        Slot slot = Game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE).getSlot();
        slot.setWorker(null);
        slot = Game.getPlayer(indexOfCurrentPlayer).getWorker(Gender.MALE).getSlot();
        slot.setWorker(null);

        game.getPlayers().remove(Game.getPlayer(indexOfCurrentPlayer));
        //TODO chiudere tutto il suo processo
        if (Game.getNumberOfPlayers() == 2) {
            //views.get(0).sendYouAreTheWinner()
            //TODO chiudere tutto il gioco
        }
        else {
            Game.setNumberOfPlayers(2);
            controller.turn();
        }
    }

    // Ad ogni inizio turno il Turn controlla in automatico se uno dei due giocatori non si può più muovere.
    // Nel caso entrambi i giocatori non si possano muovere, il giocatore perde e i suoi worker vengono rimossi.
    // Ora inizia il Controller.
    // Il giocatore inizia a giocare selezionando il gender di un worker.
    // Appena seleziona il Gender deve essere fatto un controllo.
    // Se il worker può fare qualcosa, allora non succede nulla e il giocatore gioca senza problemi.
    // Se il worker è paralizzato, viene avvisato il giocatore e viene costretto a scegliere l'altro giocatore.
    // La prima mossa ovviamente la può fare tranquillamente senza problemi.
    // Una volta fatta la prima mossa, il controller chiama la validateEndTurn() di God.
    // Questo metodo controllerà se il turno può essere terminato.
    // Se il turno non può essere terminato
    // Il controller dovrà controllare se l'ultimo worker usato dal player si può ancora muovere/costruire, chiamando
    // la checkIfCanGoOn() di God.
    // NB: se il player può usare entrambi i giocatori, allora il controllo viene fatto su entrambi.
    // nel caso solo uno dei due worker si può muovere, si procederà come 10 righe sopra.
    // se entrambi non si possono muovere... beh è fottuto e si procede come di seguito.
    // Se il worker è paralizzato, il giocatore perde istantaneamente il gioco e i suoi worker vengono eliminati dalla board.
    // Altrimenti, il giocatore può ancora muovere.
    // Altrimenti, se il turno può essere terminato
    // Il controller verifica comunque se l'ultimo worker usato dal player può essere mosso oppure no.
    // Se il worker non può essere mosso (Anche qui considerare il caso di prima del NB)
    // Il controller termina automaticamente il turno dal player con un avviso e passa ad un altro player.
    // Altrimenti
    // Il controller permette al player di fare quello che vuole, e poi sono cazzi del giocatore se si incastra.
    // Quando il giocatore decide di terminare il turno, il controller controlla se il giocatore ha le facoltà di
    // terminarlo. Se non può, gli manda un avviso e gli dice di fare la prossima mossa.
    
    // Nel caso il turno si può terminare e viene terminato, il controllo verrà fatto all'inizio del turno successivo.
    // Questo poiché gli dei possono modificare particolari condizioni.
    
}
