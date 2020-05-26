package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If {@link Player}'s {@link Worker} moves onto a perimeter space (ground or block) , it may immediately move again.
 */
public class Triton extends God  {

    public Triton(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method controls if the worker is moving into a perimeter slot. In this case,
     * the player is allowed to move an additional time during this turn, so MAX_MOVEMENTS is incremented.
     * Then it's called the worker move.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level or if moves down
     * two or more levels, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidMoveException if the move is not permitted.
     */
    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidMoveException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        boolean result ;
        if (numberOfMovements >= 1 && player.getTurn().getNumberOfBuildings() == 1) {
            throw new InvalidMoveException("Order of movements incorrect");
        }

        try {
            result = worker.move(direction);
            if (worker.getSlot().isPerimeterSlot())
                MAX_MOVEMENTS++;
            return result;
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }

    }

    /**
     * At the end of the turn the MAX_MOVEMENTS, which could have been increased, must back to its original value.
     */
    @Override
    public void resetParameters() {
        MAX_MOVEMENTS = 1;
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
        else if (numberOfMovements>=1 && numberOfBuildings==0)
            return checkIfCanMove(worker) || checkIfCanBuild(worker);

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
}
