package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Game;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If {@link Player}'s {@link Worker} moves up, all the other players cannot move up in their turns.
 */
public class Athena extends God{
    private boolean moveUp;

    public Athena(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method control if the player has moved up in this turn. If he has, the boolean CannotMoveUp of the other players
     * is setted as true, so they won't be able to move up until this player do another turn.
     * If he hasn't, this boolean is setted as false, so they become able to move up.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the winning condition has been verified, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidMoveException if the move is invalid.
     */
    @Override
    public boolean move(Direction direction, Worker worker)
            throws InvalidMoveException, IndexOutOfBoundsException {
        int initialLevel = worker.getSlot().getLevel().ordinal();
        boolean winCondition;
        try {
            winCondition = worker.move(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }
        int actualLevel = worker.getSlot().getLevel().ordinal();
        if (actualLevel>initialLevel)
            setMoveUp(true);
        else
            setMoveUp(false);
        return winCondition;
    }


    /**
     * This method checks if the worker is paralyzed or not.
     * @param worker the worker chosen to be checked.
     * @return true if the worker can go on, false otherwise.
     */
    @Override
    public boolean checkIfCanGoOn(Worker worker) {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfMovements==0)
            return checkIfCanMove(worker);
        else if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanBuild(worker);
        return false;
    }

    /**
     * This method checks if the player has completed a turn or if he still have to do some actions.
     * @return true if he can end his turn, false otherwise.
     */
    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfBuildings >= MIN_BUILDINGS && numberOfMovements >= MIN_MOVEMENTS
                || numberOfMovements >= MIN_MOVEMENTS && player.isWinning();
    }


    public boolean isMoveUp() {
        return moveUp;
    }

    private void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }
}
