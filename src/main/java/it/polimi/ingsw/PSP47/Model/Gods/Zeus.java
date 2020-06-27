package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * {@link Player}'s {@link Worker} may also choose to build under himself and so add another level on his/her
 * current Slot. However,he/she cannot win if the level 3 is reached in this way.
 */
public class Zeus extends God  {

    public Zeus(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method calls the standard build of a worker, except if the player decides to build under himself:
     * in this case, through the catching of the SlotOccupiedException, it's build a slot under the worker, except if
     * there is already a LEVEL3, as the worker cannot stay over a dome.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidDirectionException if there are problems with I/O
     * @throws InvalidBuildException if building is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {
        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements incorrect");

            if (worker.getSlot() == player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot())){
                Level levelToUpdate;
                levelToUpdate = worker.getSlot().getLevel();
                switch (levelToUpdate) {
                    case LEVEL3:
                        throw new InvalidBuildException("You cannot build a dome under yourself!");
                    case LEVEL2: worker.getSlot().setLevel(Level.LEVEL3);
                        break;
                    case LEVEL1: worker.getSlot().setLevel(Level.LEVEL2);
                        break;
                    case GROUND: worker.getSlot().setLevel(Level.LEVEL1);
                }
            }
            else
                worker.build(direction);
        }

    /**
     * This method checks if the slot is occupied by a worker.
     * It always return false if the direction is HERE as Zeus is able to build over himself.
     * The other directions have to be checked.
     * @param slot the slot that has to be checked.
     * @param direction the direction to be checked.
     * @return true if the slot is occupied by a worker, false otherwise (or if the direction is HERE).
     */
    @Override
    public boolean checkIfAWorkerIsOnSlot(Slot slot, Direction direction) {
        if (direction == Direction.HERE)
            return false;
        return slot.isWorkerOnSlot();
    }
}
