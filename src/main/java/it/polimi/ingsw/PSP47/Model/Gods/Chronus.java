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
        canUseBothWorkers = false;
    }

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

    @Override
    public void resetParameters() {

    }

    @Override
    public boolean checkIfCanMove(Worker worker) {
        return checkIfCanMoveInNormalConditions(worker);
    }

    @Override
    public boolean checkIfCanBuild(Worker worker) {
        return checkIfCanBuildInNormalConditions(worker);
    }

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

    @Override
    public boolean validateEndTurn() {
        return false;
    }
}
