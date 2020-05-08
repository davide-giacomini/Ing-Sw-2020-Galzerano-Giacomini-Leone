package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If {@link Player}'s {@link Worker} does not move up, it may build both before and after moving.
 */
public class Prometheus extends God {
    // true if the player began with a move. In this case it's impossible to build two times
    private boolean moveThenBuild = false;
    
    public Prometheus(Player player, String name) {
        super(player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canAlwaysBuildDome = false;
        canUseBothWorkers = false;
    }
    
    /**
     * This method moves a {@link Worker} from a {@link Slot} to another, towards the destination specified.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level, false otherwise
     * @throws InvalidDirectionException if the switch-else of {@link Board#getNearbySlot(Direction, Slot)} enters
     * the default case. It shouldn't happen.
     * @throws IndexOutOfBoundsException if the destination {@link Slot} is outside the {@link Board}
     * @throws InvalidMoveException if the move is not permitted.
     */
    @Override
    public boolean move(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidDirectionException, InvalidMoveException {
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfBuildings==0) {
            moveThenBuild = true;
            try {
                return worker.move(direction);
            } catch (SlotOccupiedException e) {
                throw new InvalidMoveException("Slot occupied");
            }
        }
        else if (numberOfBuildings==1){
            moveThenBuild = false;
            // if the destination slot is higher than the current slot
            if (worker.getSlot().getLevel().ordinal() < player.getGame().getBoard().getNearbySlot(direction, worker.getSlot()).getLevel().ordinal())
                throw new InvalidMoveException("Since you built before moving, you cannot go up");
            else {
                try {
                    return worker.move(direction);
                } catch (SlotOccupiedException e) {
                    throw new InvalidMoveException("Slot occupied");
                }
            }
        }
        else throw new InvalidMoveException("Order of movements not correct");
    }
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker} in the direction chosen.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the {@link Slot} where to build is outside the {@link Board}
     * @throws InvalidMoveException if the move is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidBuildException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();
        
        if (numberOfMovements==0 && numberOfBuildings==1) throw new InvalidBuildException("Order of movements not correct");
        if (numberOfBuildings==1 && moveThenBuild)  throw new InvalidBuildException("Order of movements not correct");

        try {
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }
    }

    /**
     * It does nothing.
     */
    @Override
    public void resetParameters() {
        moveThenBuild = false;
    }

    /**
     * This method directly calls the God's method checkIfCanMoveInNormalConditions,
     * as in this case there is nothing else to control.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can move, false otherwise
     */
    @Override
    public boolean checkIfCanMove(Worker worker) {
        return checkIfCanMoveInNormalConditions(worker);
    }

    /**
     * This method directly calls the God's method checkIfCanBuildInNormalConditions or
     * does a special check for the second build
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can build, false otherwise.
     */
    @Override
    public boolean checkIfCanBuild(Worker worker) {
        return checkIfCanBuildInNormalConditions(worker);
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
        
        if (numberOfMovements==0 && numberOfBuildings==0)
            return checkIfCanMove(worker) || checkIfCanBuild(worker);
        if (numberOfMovements==1 && numberOfBuildings==0 && moveThenBuild || numberOfMovements==1 && numberOfBuildings==1 && !moveThenBuild)
            return checkIfCanBuild(worker);
        if (numberOfMovements==0 && numberOfBuildings==1 && !moveThenBuild)
            return checkIfCanMove(worker);
        
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
        
        return numberOfMovements==1 && numberOfBuildings==1 && moveThenBuild
                || numberOfMovements==1 && numberOfBuildings==2 && !moveThenBuild
                || player.isWinning();
    }
    
    /**
     * This method return the status of Prometheus.
     * It isn't callable by the interface {@link God}, because it's a Prometheus' personal field.
     * @return true if Prometheus is obligated to do a normal turn (because he moved before having built).
     */
    public boolean moveThenBuild() {
        return moveThenBuild;
    }
}
