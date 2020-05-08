package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If a {@link Player} has Hestia, their {@link Worker} can build a second time, but this
 * second construction cannot be on a perimeter slot.
 */
public class Hestia extends God {

    public Hestia(Player player, String name) {
        super(player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canAlwaysBuildDome = false;
        canUseBothWorkers = false;;
    }

    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidMoveException, InvalidDirectionException {
        try {
            return worker.move(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidMoveException("Slot occupied");
        }
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {

        if (player.getTurn().getNumberOfMovements() == 0) throw new InvalidBuildException("Order of movements incorrect");

        if (player.getTurn().getNumberOfBuildings() == 1 && ((Board.getNearbySlot(direction, worker.getSlot()).getRow()==0) || (Board.getNearbySlot(direction, worker.getSlot()).getRow()==4)))
            throw new InvalidBuildException("You are not allowed to build a second time on a perimeter slot!");

        try {
            worker.build(direction);
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
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfBuildings==0)
            return checkIfCanBuildInNormalConditions(worker);
        if (numberOfBuildings==1) {
            for (Direction direction: Direction.values()){
                try {
                    // If the direction is out of the board, jump to the catch
                    worker.checkDirection(direction);
                    Slot destinationSlot = Board.getBoard().getNearbySlot(direction, worker.getSlot());
                    // else, check if the worker can build on the destinationSlot
                    if ( !((destinationSlot.getRow()==0) || (destinationSlot.getRow()==4)) && !destinationSlot.isOccupied())
                        return true;
                }
                catch (IndexOutOfBoundsException e) {

                } catch (InvalidDirectionException e) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkIfCanGoOn(Worker worker) {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfMovements==0 && numberOfBuildings==0)
            return checkIfCanMove(worker);
        if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanBuild(worker);
        if (numberOfMovements==1 && numberOfBuildings==1)
            return checkIfCanBuild(worker);

        return false;
    }

    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfBuildings==1 && numberOfMovements==1 || numberOfBuildings==2 && numberOfMovements==1;
    }
}
