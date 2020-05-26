package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * With Chronus, {@link Player} can win also if there are 5 COMPLETE towers on the Board.
 */
public class Chronus extends God  {


    public Chronus(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = false;
    }

    /**
     * This method calls the standard move of a worker after controlling
     * if there are five complete towards on the board, as in this case the
     * player instantly win.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the winning condition has been verified, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidMoveException if the move is invalid.
     */
    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidMoveException{

        boolean winCondition = false;

        if (player.getTurn().getBoard().getCountDomes() == 5)
            return true;

        try {
            return worker.move(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }

    }

    /**
     * This method calls the standard move of a worker after controlling
     * if there are five complete towards on the board, as in this case the
     * player instantly win.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidBuildException if building is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException{
        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements not correct");

        try {
            worker.build(direction);
            if (player.getTurn().getBoard().getCountDomes() == 5) player.setWinning(true);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }
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
}
