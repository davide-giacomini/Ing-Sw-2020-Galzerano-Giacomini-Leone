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
        if (player.isLoosing())
            controller.removeLosingPlayer();
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
        try {
            workerGender = game.getBoard().getSlot(row,column).getWorker().getGender();
            if (!player.getGod().checkIfCanGoOn(player.getWorker(workerGender))) {
                if (workerGender == Gender.MALE)
                    workerGender = Gender.FEMALE;
                if (workerGender == Gender.FEMALE)
                    workerGender = Gender.MALE;
                String textError = "Your worker is blocked. You are forced to use the other one";
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

    /**
     * This method is the main actor in a turn.
     * It sets which action has been selected and if is the player is allowed to, it executes it;
     * otherwise, it sends an error and ask again.
     * @param action the action chosen by the player
     * @param direction the direction chosen by the player
     */
    public void executeAction(Action action, Direction direction) {
        if (!(player.getGod().checkIfCanGoOn(player.getWorker(workerGender)))) { //TODO validate end turn
            player.setLoosing(true);
        }
        switch (action) {
            case MOVE:
                if (player.isLoosing()) {
                    controller.removeLosingPlayer();
                    break;
                }
                try {
                    if (turn.getNumberOfMovements() == turn.getMAX_MOVEMENTS()) {
                        String textError = "You've yet reached the max number of movements in this turn";
                        views.get(indexOfCurrentPlayer).sendError(textError);
                        views.get(indexOfCurrentPlayer).sendWhichAction();
                        return;
                    }
                    else if (game.getBoard().getNearbySlot(direction, player.getWorkerPosition(player.getWorker(workerGender))).getLevel() == (Level.DOME) ||
                            game.getBoard().getNearbySlot(direction, player.getWorkerPosition(player.getWorker(workerGender))).getLevel() == (Level.ATLAS_DOME)) {
                        String textError = "This slot contains a dome, you cannot move here";
                        views.get(indexOfCurrentPlayer).sendError(textError);
                        views.get(indexOfCurrentPlayer).sendWhichAction();
                        return;
                    }
                    else if (game.getBoard().getNearbySlot(direction, player.getWorkerPosition(player.getWorker(workerGender))).isWorkerOnSlot()) {
                        if (!(player.getGod().getName().equals("Apollo") || player.getGod().getName().equals("Minotaur"))) {
                            String textError = "This slot contains another worker, you cannot move here";
                            views.get(indexOfCurrentPlayer).sendError(textError);
                            views.get(indexOfCurrentPlayer).sendWhichAction();
                            return;
                        }
                    }
                    turn.executeMove(direction);
                    if (player.isWinning()) {
                        controller.endGame();
                        return;
                    }
                    if (player.getGod().getName().equals("Athena")) {
                        Boolean moveUp = ((Athena)player.getGod()).isMoveUp();
                        for (int i = 0; i<game.getNumberOfPlayers(); i++) {
                            if (game.getPlayer(i) != null && game.getPlayer(i) != player) {
                                game.getPlayer(i).setCannotMoveUp(moveUp);
                            }
                        }
                    }
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    break;
                } catch (InvalidDirectionException | InvalidMoveException | IndexOutOfBoundsException e) {
                    String textError = e.getMessage();
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                }
            case BUILD:
                if (player.isLoosing()) {
                    controller.removeLosingPlayer();
                    break;
                }
                try {
                    if (turn.getNumberOfBuildings() == turn.getMAX_BUILDINGS())
                        throw new InvalidBuildException("Max number of buildings reached");
                    turn.executeBuild(direction);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    break;
                } catch (InvalidDirectionException | InvalidBuildException | IndexOutOfBoundsException e) {
                    String textError = e.getMessage();
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                }
            case BUILDDOME:
                if (player.isLoosing()) {
                    controller.removeLosingPlayer();
                    break;
                }
                try {
                    if (turn.getNumberOfBuildings() == turn.getMAX_BUILDINGS())
                        throw new InvalidBuildException("Max number of buildings reached");
                    turn.setWantsToBuildDome(true);
                    turn.executeBuild(direction);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    break;
                } catch (InvalidDirectionException | InvalidBuildException | IndexOutOfBoundsException e) {
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
                break;
        }
    }
}
