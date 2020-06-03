package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * {@link Player} can win also if his {@link Worker} moves down two or more levels.
 */
public class Pan extends God{
    public Pan(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method calls the standard move of a worker:
     * Pan doesn't modify the moving rules.
     * The only difference is in the return value.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level or if moves down
     * two or more levels, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidMoveException if the move is not permitted.
     */
    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException,  InvalidMoveException {
        int previousLevel = worker.getSlot().getLevel().ordinal();
        boolean winCondition;
        try {
            winCondition = worker.move(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }
        int actualLevel = worker.getSlot().getLevel().ordinal();
        return winCondition || (actualLevel - previousLevel < -1);
    }

}
