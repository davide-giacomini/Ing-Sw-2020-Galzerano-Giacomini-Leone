package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

//TODO CHIEDI CHIARIMENTO POTERE
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
        canUseBothWorkers = false;

    }

    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidMoveException, InvalidDirectionException {
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

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {
        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements incorrect");

        try {
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }
    }

    @Override
    public void resetParameters() {
        MAX_MOVEMENTS = 1;
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
        else if (numberOfMovements>=1 && numberOfBuildings==0)
            return checkIfCanMove(worker) || checkIfCanBuild(worker);

        return false;
    }

    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfBuildings >= MIN_BUILDINGS && numberOfMovements >= MIN_MOVEMENTS
                || numberOfMovements >= MIN_MOVEMENTS && player.isWinning();
    }
}
