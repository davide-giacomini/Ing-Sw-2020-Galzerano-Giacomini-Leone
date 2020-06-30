package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.util.ArrayList;

/**
 * This class represents the controller of a turn.
 * The GameController creates a new instance every time a new turn is going on and when it ends the instance is substituted.
 */
public class TurnController {

    private final GameController controller;
    private final ArrayList<VirtualView> views;
    private final Game game;
    private final int indexOfCurrentPlayer;
    private final Turn turn;
    private final Player player;
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
            controller.removeLosingPlayer(player.getUsername());
            return;
        }
        views.get(indexOfCurrentPlayer).sendWhichWorker();
        controller.sendWhoseIsTheTurn();
    }

    /**
     * This method sets the workerGender chosen by the player for this turn.
     * If he selected a slot where there isn't one of his workers, the controller asks again.
     * If he selected a paralyzed worker, the controller forced him to use the other one sending him an advice.
     *
     * @param position the coordinates of the slot where is located the chosen worker.
     */
    public void setWorkerGender(int[] position) {
        int row = position[0];
        int column = position[1];
        if (row > 4 || row < 0 || column > 4 || column < 0) {
            String errorText = "You have select a value that is out of range.";
            views.get(indexOfCurrentPlayer).sendError(errorText);
            views.get(indexOfCurrentPlayer).sendWhichWorker();
            return;
        }
        else if (game.getBoard().getSlot(row,column).getWorker() == null) {
            String textError = "In the selected slot there isn't any worker";
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichWorker();
            return;
        }
        else if (game.getBoard().getSlot(row,column).getWorker().getColor() != player.getColor()) {
            String textError = "In the selected slot there is the worker of another player";
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
            views.get(indexOfCurrentPlayer).sendNewPosition(player.getWorker(workerGender).getSlot().getRow(), player.getWorker(workerGender).getSlot().getColumn());
        }
        turn.setWorkerGender(workerGender);
        controller.sendAnAdviceDuringGame(player.getUsername() + " has just chosen which worker to use");
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
        if (direction == Direction.WRONGDIRECTION) {
            String textError = "You must choose a near slot";
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichAction();
            return;
        }
        if (!(player.getGod().checkIfCanGoOn(player.getWorker(workerGender))) && !(player.getGod().validateEndTurn()) ) {
            player.setLoosing(true);
        }
        if (player.isLoosing()) {
            controller.removeLosingPlayer(player.getUsername());
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
                if (!player.getGod().validateEndTurn()) {
                    String textError = "You're not allowed to end your turn. You have to choose another action";
                    views.get(indexOfCurrentPlayer).sendError(textError);
                    views.get(indexOfCurrentPlayer).sendWhichAction();
                    return;
                }
                controller.changeTurn();
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
                textError = "You've already reached the max number of movements in this turn";
            else if (player.getGod().checkOrderOfActions(Action.MOVE)) {
                textError = "You have already built in this turn, so you can't move anymore";
            }
            else if (destinationSlot.getLevel() == (Level.DOME) || destinationSlot.getLevel() == (Level.ATLAS_DOME))
                textError = "This slot contains a dome, you cannot move here";
            else if (player.getGod().checkIfAWorkerIsOnSlot(destinationSlot))
                textError = "This slot contains another worker, you cannot move here";
            else if (destinationSlot.getLevel().ordinal() > (actualSlot.getLevel().ordinal() + 1))
                textError = "This slot is unreachable, its level is too high";
            else if (destinationSlot.getLevel().ordinal() > actualSlot.getLevel().ordinal() && player.cannotMoveUp())
                textError = "During this turn your player is unable to move up. Try to move up on the next turn";
            if (textError != null) {
                views.get(indexOfCurrentPlayer).sendError(textError);
                views.get(indexOfCurrentPlayer).sendWhichAction();
                return;
            }
            turn.executeMove(direction);
            if (game.checkWinningCondition(player.getWorker(workerGender), player)) {
                controller.endGame(player.getUsername());
                return;
            }
            game.checkIfPlayersCanMoveUp(player);
            controller.sendAnAdviceDuringGame(player.getUsername() + " has just executed the action MOVE! ");
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
                textError = "You've already reached the max number of buildings in this turn";
            else if (destinationSlot.getLevel() == Level.DOME || destinationSlot.getLevel() == Level.ATLAS_DOME)
                textError = "This slot already contains a dome, you cannot build ih this position";
            else if (player.getGod().checkIfAWorkerIsOnSlot(destinationSlot, direction)) {
                textError = "This slot is occupied by a worker, you cannot build here";
            }
            else if (player.getGod().checkOrderOfActions(Action.BUILD))
                textError = "You are not allowed to build in this moment of your turn";
            if (textError != null) {
                views.get(indexOfCurrentPlayer).sendError(textError);
                views.get(indexOfCurrentPlayer).sendWhichAction();
                return;
            }
            turn.executeBuild(direction);
            if (game.checkWinningCondition() != null) {
                controller.endGame(game.checkWinningCondition().getUsername());
                return;
            }
            controller.sendAnAdviceDuringGame(player.getUsername() + " has just executed the action BUILD! ");
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
            if (!player.getGod().canAlwaysBuildDome())
                textError = "You're not allowed to build a dome in this way";
            else if (turn.getNumberOfBuildings() == player.getGod().getMAX_BUILDINGS())
                textError = "You've yet reached the max number of buildings in this turn";
            else if (destinationSlot.getLevel() == Level.DOME || destinationSlot.getLevel() == Level.ATLAS_DOME)
                textError = "This slot already contains a dome, you cannot build ih this position";
            else if (player.getGod().checkIfAWorkerIsOnSlot(destinationSlot))
                textError = "This slot is occupied by a worker, you cannot build here";
            else if (player.getGod().checkOrderOfActions(Action.BUILDDOME))
                textError = "You have to move your worker before build";
            if (textError != null) {
                views.get(indexOfCurrentPlayer).sendError(textError);
                views.get(indexOfCurrentPlayer).sendWhichAction();
                return;
            }
            turn.executeBuild(direction);
            controller.sendAnAdviceDuringGame(player.getUsername() + " has just executed the action BUILD DOME! ");
            views.get(indexOfCurrentPlayer).sendWhichAction();
        } catch (InvalidDirectionException | InvalidBuildException | IndexOutOfBoundsException e) {
            String textError = e.getMessage();
            views.get(indexOfCurrentPlayer).sendError(textError);
            views.get(indexOfCurrentPlayer).sendWhichAction();
        }
    }

    Gender getWorkerGender() {
        return workerGender;
    }

    Turn getTurn() {
        return turn;
    }

    Game getGame() {
        return game;
    }


}
