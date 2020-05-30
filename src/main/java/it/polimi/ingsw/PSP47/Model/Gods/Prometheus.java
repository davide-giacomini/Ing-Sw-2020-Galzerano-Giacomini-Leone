package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Action;
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
        threePlayersGame = true;
    }
    
    /**
     * This method moves a {@link Worker} from a {@link Slot} to another, towards the destination specified.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level, false otherwise
     * @throws IndexOutOfBoundsException if the destination {@link Slot} is outside the {@link Board}
     * @throws InvalidMoveException if the move is not permitted.
     */
    @Override
    public boolean move(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidMoveException {
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfBuildings==0) {
            moveThenBuild = true;
            try {
                return worker.move(direction);
            } catch (SlotOccupiedException e) {
                throw new InvalidMoveException("Slot occupied");
            }
        }
        else{
            moveThenBuild = false;
                try {
                    return worker.move(direction);
                } catch (SlotOccupiedException e) {
                    throw new InvalidMoveException("Slot occupied");
                }
            }
    }
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker} in the direction chosen.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the {@link Slot} where to build is outside the {@link Board}
     * @throws InvalidBuildException if the build is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidBuildException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();
        
        if (numberOfMovements==0 && numberOfBuildings==1) throw new InvalidBuildException("Order of movements not correct");
        if (numberOfBuildings==1 && moveThenBuild)  throw new InvalidBuildException("Order of movements not correct");

        try {
            if (numberOfMovements == 0)
                player.setCannotMoveUp(true);
            worker.build(direction);
        } catch (SlotOccupiedException e) {
            throw new InvalidBuildException("Slot occupied");
        }
    }

    /**
     * It reset the parameter moveThenBuild as it changes in every turn.
     */
    @Override
    public void resetParameters() {
        moveThenBuild = false;
        player.setCannotMoveUp(false);
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
     * This method checks if the order of the actions is correct.
     * If the player wants to move there is anything to control, as he can move both before and after build.
     * If the player wants to build, must be checked if he has built before moving or not.
     * @param action the action to control.
     * @return false is the order is correct, true otherwise.
     */
    public boolean checkOrderOfActions(Action action) {
        if (action == Action.MOVE) {
            return false;
        }
        else if (action == Action.BUILD) {
            if (moveThenBuild && player.getTurn().getNumberOfBuildings() == 1)
                return true;
        }
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
    boolean moveThenBuild() {
        return moveThenBuild;
    }
}
