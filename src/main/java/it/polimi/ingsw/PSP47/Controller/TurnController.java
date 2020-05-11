package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Gods.Athena;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.util.ArrayList;

public class TurnController {

    private GameController controller;
    private ArrayList<VirtualView> views;
    private Game game;
    private int indexOfCurrentPlayer;
    private Turn turn;
    private Player player;
    private Gender workerGender;

    public TurnController(ArrayList<VirtualView> views, Game game, int indexOfCurrentPlayer, GameController controller) {
        this.views = views;
        this.game = game;
        this.indexOfCurrentPlayer = indexOfCurrentPlayer;
        this.player = game.getPlayer(indexOfCurrentPlayer);
        this.turn = new Turn(player, game.getBoard());
        this.controller = controller;
    }

    /**
     * This method is called by the GameController to start a turn game.
     */
    void startTurn() {
        if (player.isLoosing()) {
            controller.removeLosingPlayer();
            return;
        }
        views.get(indexOfCurrentPlayer).sendWhichWorker();
        controller.sendAnAdvice();
    }

    /**
     * This method set the workerGender that the player chose to use in this turn.
     * If he selected a slot where there isn't one of his workers, the controller asks again.
     * If he selected a paralyzed worker, the controller forced him to use the other one sending him an advice.
     * @param position the coordinates of the slot where is located the chosen worker.
     */
    public void setWorkerGender(int[] position) {
        int row = position[0];
        int column = position[1];
        if (game.getBoard().getSlot(row,column).getWorker() == null || game.getBoard().getSlot(row,column).getWorker().getColor() != player.getColor()) {
            String textError = "Your worker is not there";
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichWorker();
            return;
        }
        workerGender = game.getBoard().getSlot(row,column).getWorker().getGender();
        if (!player.getGod().checkIfCanGoOn(player.getWorker(workerGender))) {
            if (workerGender == Gender.MALE)
                workerGender = Gender.FEMALE;
            else
                workerGender = Gender.MALE;
            String textError = "Your worker is blocked. You are forced to use the other one";
            views.get(indexOfCurrentPlayer).sendError(textError);
        }
        turn.setWorkerGender(workerGender);
        views.get(indexOfCurrentPlayer).sendWhichAction();
    }

    /**
     * This method is the main actor in a turn.
     * It sets which action has been selected and if is the player is allowed to, it executes it;
     * otherwise, it sends an error and ask again.
     * @param action the action chosen by the player
     * @param direction the direction chosen by the player
     */
    public void executeAction(Action action, Direction direction) {
        if (!(player.getGod().checkIfCanGoOn(player.getWorker(workerGender))) && !(player.getGod().validateEndTurn()) ) {
            player.setLoosing(true);
        }
        if (player.isLoosing()) {
            controller.removeLosingPlayer();
            return;
        }
        switch (action) {
            case MOVE:
                move(direction);
                break;
            case BUILD:
                build(direction);
                break;
            case BUILDDOME:
                buildDome(direction);
                break;
            case END:
                if (!turn.validateEndTurn()) {
                    String textError = "You're not allowed to end your turn. You have to choose another action";
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                }
                controller.turn();
        }
    }

    /**
     * This method executes a move. There are several check before the model's method
     * is called: if all of them are negative, the action is executed.
     * Otherwise, it sends an error and asks again.
     * @param direction the direction where the worker is going to move
     */
    private void move(Direction direction) {
        try {
            Slot actualSlot = player.getWorkerPosition(player.getWorker(workerGender));
            Slot destinationSlot = game.getBoard().getNearbySlot(direction, player.getWorkerPosition(player.getWorker(workerGender)));
            String textError = null;
            if (turn.getNumberOfMovements() == player.getGod().getMAX_MOVEMENTS())
                textError = "You've yet reached the max number of movements in this turn";
            else if (destinationSlot.getLevel() == (Level.DOME) || destinationSlot.getLevel() == (Level.ATLAS_DOME))
                textError = "This slot contains a dome, you cannot move here";
            else if (destinationSlot.isWorkerOnSlot()) {
                if (!(player.getGod().getName().equals("Apollo") || player.getGod().getName().equals("Minotaur"))) {
                    textError = "This slot contains another worker, you cannot move here";
                }
            }
            else if (destinationSlot.getLevel().ordinal() > actualSlot.getLevel().ordinal() && player.cannotMoveUp() ||
                destinationSlot.getLevel().ordinal() > (actualSlot.getLevel().ordinal() + 1))
                textError = "This slot is unreachable, its level is too high";
            if (textError != null) {
                views.get(indexOfCurrentPlayer).sendError(textError);
                views.get(indexOfCurrentPlayer).sendWhichAction();
                return;
            }
            turn.executeMove(direction);
            if (player.isWinning() && !(heraWinCondition(player.getWorker(workerGender)))) {
                controller.endGame(player.getUsername());
                return;
            }
            if (player.getGod().getName().equals("Athena")) {
                boolean moveUp = ((Athena)player.getGod()).isMoveUp();
                for (int i = 0; i<game.getNumberOfPlayers(); i++) {
                    if (game.getPlayer(i) != null && game.getPlayer(i) != player) {
                        game.getPlayer(i).setCannotMoveUp(moveUp);
                    }
                }
            }
            views.get(indexOfCurrentPlayer).sendWhichAction();
        } catch (InvalidDirectionException | InvalidMoveException | IndexOutOfBoundsException e) {
            String textError = e.getMessage();
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichAction();
        }
    }

    /**
     * This method executes a build. There are several check before the model's method
     * is called: if all of them are negative, the action is executed.
     * Otherwise, it sends an error and asks again.
     * @param direction the direction where the worker is going to build
     */
    private void build(Direction direction) {
        try {
            String textError = null;
            Slot destinationSlot = game.getBoard().getNearbySlot(direction, player.getWorkerPosition(player.getWorker(workerGender)));
            if (turn.getNumberOfBuildings() == player.getGod().getMAX_BUILDINGS())
                textError = "You've yet reached the max number of buildings in this turn";
            else if (destinationSlot.getLevel() == Level.DOME || destinationSlot.getLevel() == Level.ATLAS_DOME)
                textError = "This slot yet contains a dome, you cannot build ih this position";
            else if (destinationSlot.isWorkerOnSlot()) {
                if (!(player.getGod().getName().equals("Zeus") && direction == Direction.HERE))
                    textError = "This slot is occupied by a worker, you cannot build here";
            }
            if (textError != null) {
                views.get(indexOfCurrentPlayer).sendError(textError);
                views.get(indexOfCurrentPlayer).sendWhichAction();
                return;
            }
            turn.executeBuild(direction);
            if (game.getBoard().getCountDomes() == 5 && chronusPlayer() != null) {
                controller.endGame(chronusPlayer().getUsername());
                return;
            }
            views.get(indexOfCurrentPlayer).sendWhichAction();
        } catch (InvalidDirectionException | InvalidBuildException | IndexOutOfBoundsException e) {
            String textError = e.getMessage();
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichAction();
        }
    }

    /**
     * This method executes a dome building. There are several check before the model's method
     * is called: if all of them are negative, the action is executed.
     * Otherwise, it sends an error and asks again.
     * @param direction the direction where the worker is going to build a dome
     */
    private void buildDome(Direction direction) {
        try {
            String textError = null;
            Slot destinationSlot = game.getBoard().getNearbySlot(direction, player.getWorkerPosition(player.getWorker(workerGender)));
            turn.setWantsToBuildDome(true);
            if (turn.getNumberOfBuildings() == player.getGod().getMAX_BUILDINGS())
                textError = "You've yet reached the max number of buildings in this turn";
            else if (!player.getGod().canAlwaysBuildDome())
                textError = "You're not allowed to build a dome in this way";
            else if (destinationSlot.getLevel() == Level.DOME || destinationSlot.getLevel() == Level.ATLAS_DOME)
                textError = "This slot yet contains a dome, you cannot build ih this position";
            else if (destinationSlot.isWorkerOnSlot())
                textError = "This slot is occupied by a worker, you cannot build here";
            if (textError != null) {
                views.get(indexOfCurrentPlayer).sendError(textError);
                views.get(indexOfCurrentPlayer).sendWhichAction();
                return;
            }
            turn.executeBuild(direction);
            views.get(indexOfCurrentPlayer).sendWhichAction();
        } catch (InvalidDirectionException | InvalidBuildException | IndexOutOfBoundsException e) {
            String textError = e.getMessage();
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichAction();
        }
    }

    /**
     * This method controls if the worker that has been playing currently is in a perimeter slot and if
     * in this game there is a player who is using Hera's power.
     * That's because in this case even if a player reached the third level he cannot win.
     * @param currentWorker the worker who is actually moving.
     * @return if the worker is on a perimeter slot and if Hera is in the game.
     */
    private boolean heraWinCondition(Worker currentWorker){
        boolean thereIsHera = false;
        for(Player player : game.getPlayers()){
            if (player.getGod().getName().equals("Hera")) {
                thereIsHera = true;
                break;
            }
        }
        return currentWorker.getSlot().isPerimeterSlot() && !currentWorker.getPlayer().getGod().getName().equals("Hera") && thereIsHera ;
    }

    /**
     * This method controls if there is a player who is using Chronus' power.
     * @return the instance of the player.
     */
    private Player chronusPlayer() {
        for (int i = 0; i<game.getNumberOfPlayers(); i++) {
            if (game.getPlayer(i).getGod().getName().equals("Chronus")) {
                return player;
            }
        }
        return null;
    }
}
