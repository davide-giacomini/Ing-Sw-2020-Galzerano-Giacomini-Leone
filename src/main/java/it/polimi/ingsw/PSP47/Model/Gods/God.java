package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * This class is a skeleton to be implemented when gods are added.
 * When a god (as subclass) is created, every field MUST be initialized. Otherwise, the game may not work correctly.
 */
public abstract class God {
    protected int MIN_MOVEMENTS;
    protected int MIN_BUILDINGS;
    protected int MAX_MOVEMENTS;
    protected int MAX_BUILDINGS;
    protected boolean canAlwaysBuildDome;
    protected boolean threePlayersGame;
    protected Player player;
    protected String name;
    
    public God(Player player, String name) {
        this.player = player;
        this.name = name;
        player.setGod(this);
    }
    
    /**
     * @return the minimum amount of buildings to be constructed by a god
     */
    public int getMIN_BUILDINGS() {
        return MIN_BUILDINGS;
    }
    /**
     * @return the maximum amount of movements to be done by a god
     */
    public int getMAX_MOVEMENTS() {
        return MAX_MOVEMENTS;
    }
    /**
     * @return the maximum amount of buildings to be constructed by a god
     */
    public int getMAX_BUILDINGS() {
        return MAX_BUILDINGS;
    }

    /**
     * @return if the god is allowed to build dome at any level
     */
    public boolean canAlwaysBuildDome() {
        return canAlwaysBuildDome;
    }

    /**
     * @return if the card is available in a three players game
     */
    public boolean threePlayersGame() {
        return threePlayersGame;
    }

    /**
     * This method moves a {@link Worker} from a {@link Slot} to another, towards the destination specified.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the worker moved voluntarily up on the third level, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidDirectionException if there are some troubles of I/O.
     * @throws InvalidMoveException if the move is invalid.
     */
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidMoveException, InvalidDirectionException {
        return worker.move(direction);
    }
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker} in the direction chosen.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidDirectionException if there are problems with I/O
     * @throws InvalidBuildException if building is not permitted.
     */
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {

        worker.build(direction);

    }

    
    /**
     * Reset all the additional eventual parameters of the god.
     * It has to be called inside the god and it's not necessary for every god.
     */
    public void resetParameters() {}
    
    /**
     * @return true if it's possible to move,false otherwise.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     */
    public boolean checkIfCanMove(Worker worker) {
        return checkIfCanMoveInNormalConditions(worker);
    }
    
    /**
     * See {@link #checkIfCanGoOn(Worker)}
     *
     * @return true if it's possible to build, false otherwise.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     */
    public boolean checkIfCanBuild(Worker worker) {
        return checkIfCanBuildInNormalConditions(worker);
    }
    
    /**
     * See {@link #checkIfCanGoOn(Worker)}
     *
     * This method is a way not to repeat for each god the same check
     * in case they don't modify the normal conditions.
     *
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if it's possible to move in normal conditions, false otherwise.
     */
    protected final boolean checkIfCanMoveInNormalConditions(Worker worker) {
        for (Direction direction : Direction.values()) {
            try {
                // If the direction is out of the board, jump to the catch
                worker.checkDirection(direction);
                Slot destinationSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());
                // else, check if the worker can move to the destinationSlot
                if (!destinationSlot.isOccupied()){
                    // if the player can move up and the destinationSlot hasn't got too many levels, the player can move.
                    if (!player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal()+1)
                        return true;
                    // if the player cannot move up but the destinationSlot is equal or less high than the current slot, the player can move.
                    else if (player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal())
                        return true;
                }
            } catch (IndexOutOfBoundsException e){
                // just let the for continue
            } catch (InvalidDirectionException e){
                return false;
            }
        }
        return false;
    }

    /**
     * See {@link #checkIfCanGoOn(Worker)}
     *
     * This method is a way not to repeat for each god the same check,
     * in the case them don't modify the normal conditions.
     *
     * @return true if it's possible to build in normal conditions, false otherwise.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     */
    protected final boolean checkIfCanBuildInNormalConditions(Worker worker) {
        for (Direction direction: Direction.values()){
            try {
                // If the direction is out of the board, jump to the catch
                worker.checkDirection(direction);
                Slot destinationSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());
                // else, check if the worker can build on the destinationSlot
                if (!destinationSlot.isOccupied())  return true;
            } catch (IndexOutOfBoundsException e) {
                // just let the for continue
            } catch (InvalidDirectionException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * This method checks, using {@link #checkIfCanBuild(Worker)}, {@link #checkIfCanMove(Worker)},
     * {@link #checkIfCanBuildInNormalConditions(Worker)} and {@link #checkIfCanMoveInNormalConditions(Worker)}, can
     * understand if the worker chosen can go on doing something or they're blocked.
     *
     * @param worker the worker chosen to be checked.
     * @return true if the worker can go on, false otherwise.
     */
    public boolean checkIfCanGoOn (Worker worker) {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfMovements==0)
            return checkIfCanMove(worker);
        else if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanBuild(worker);
        return false;
    }

    /**
     * This method checks if the slot is occupied by a worker.
     * @param slot the slot that has to be checked
     * @return true if the slot is occupied by a worker, false otherwise.
     */
    public boolean checkIfAWorkerIsOnSlot(Slot slot) {
        return slot.isWorkerOnSlot();
    }

    /**
     * This method checks if the slot is occupied. It's useful for building.
     *
     * @param slot the slot to be checked.
     * @param direction the direction where to build with respect to the previous slot.
     * @return true if the slot is occupied, false otherwise.
     */
    public boolean checkIfAWorkerIsOnSlot(Slot slot, Direction direction) {
        return slot.isWorkerOnSlot();
    }

    /**
     * It returns true if the order of actions is uncorrected, false otherwise.
     *
     * @param action the action to compute.
     * @return true if the order of actions is uncorrected, false otherwise.
     */
    public boolean checkOrderOfActions(Action action) {
        if (action == Action.MOVE)
            return player.getTurn().getNumberOfBuildings() > 0;
        else if (action == Action.BUILD || action == Action.BUILDDOME)
            return player.getTurn().getNumberOfMovements() == 0;

        return false;
    }

    /**
     * This method controls if the player can end his turn. If the player is winning, it returns true.
     * @return true if the player can end his turn, false otherwise.
     */
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfBuildings >= MIN_BUILDINGS && numberOfMovements >= MIN_MOVEMENTS
                || numberOfMovements >= MIN_MOVEMENTS && player.isWinning();
    }

    /**
     * @return the name of the god.
     */
    public String getName() {
        return name;
    }
}
