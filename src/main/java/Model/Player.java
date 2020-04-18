package Model;

import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Gods.God;

import java.awt.*;

/**
 * This class represents the user. A user can have two workers, male and female, and each player can move differently
 * depending on the god assigned to them. Hence, a state pattern has been used in order to devolve the moves to the
 * {@link God} interface.
 */
public class Player {
    /**
     * Number of workers a player has got.
     */
    public final int WORKERS_NUMBER = 2;
    private String username;
    private Worker[] workers;
    private boolean isWinning;
    private boolean isLoosing;
    private boolean cannotMoveUp;
    private boolean canBuildDome;
    private God god;
    private Turn turn;


    public Player(String username, Color workersColor) {
        this.username = username;

        workers = new Worker[WORKERS_NUMBER];
        workers[Worker.MALE] = new Worker(workersColor, Gender.MALE);
        workers[Worker.FEMALE] = new Worker(workersColor, Gender.FEMALE);
    }
    
    public boolean isLoosing() {
        return isLoosing;
    }
    
    public int getWorkersNumber() {
        return WORKERS_NUMBER;
    }
    
    /**
     * If it sets the player in loosing conditions, it also deletes their workers.
     * @param loosing the condition of loosing. If true, the player loses the game.
     */
    public void setLoosing(boolean loosing) {
        isLoosing = loosing;
        if (loosing) {
            for (Worker worker : workers) {
                if (worker==null)
                    continue;
                worker.setSlot(null);
                workers[worker.getGender().ordinal()] = null;
            }
        }
    }
    
    public void setWinning(boolean winning) {
        isWinning = winning;
    }

    public boolean isWinning() {
        return isWinning;
    }

    public void setCannotMoveUp(boolean cannotMoveUp) {
        this.cannotMoveUp = cannotMoveUp;
    }

    public boolean cannotMoveUp() {
        return cannotMoveUp;
    }

    public void setGod(God god) {
        this.god = god;
        this.canBuildDome = god.canAlwaysBuildDome();
    }

    public boolean CanBuildDome() {
        return canBuildDome;
    }

    public God getGod() {
        return god;
    }

    public Worker getWorker(Gender i) {
        return workers[i.ordinal()];
    }
    
    public void setTurn (Turn turn) {
        this.turn = turn;
    }
    
    public Turn getTurn() {
        return turn;
    }
    
    public String getUsername(){
        return username;
    }
    
    /**
     * This method delete a worker of the player.
     * If player's workers become zero, the player loose.
     *
     * @param worker the worker to be deleted
     */
    public void deleteWorker (Worker worker) {
        if (worker == null)
            throw new NullPointerException("The worker chosen to be deleted doesn't exist.");
        workers[worker.getGender().ordinal()] = null;
        
        boolean isLoosing = true;
        for (Worker w : workers) {
            if (w != null) {
                isLoosing = false;
            }
        }
        
        this.isLoosing = isLoosing;
    }
    
    /**
     * Put the worker on a slot of the board.
     * @param worker player's worker
     * @param slot slot chosen to put on the worker
     */
    public void putWorkerOnSlot(Worker worker, Slot slot) {
        worker.setSlot(slot);
    }
    
    /**
     * @param worker worker whose the caller wants to know the position
     * @return the slot where the worker is on
     */
    public Slot getWorkerPosition(Worker worker) {
        return worker.getSlot();
    }
    
    /**
     * It moves the specified worker in the specified direction.
     * @param direction direction where the worker is going to move to
     * @param worker one of the two player's workers
     * @return true if the worker moved voluntarily up on the third level, false otherwise
     * @throws IndexOutOfBoundsException if the destination {@link Slot} doesn't exist in the {@link Board}.
     * @throws NotReachableLevelException if the level of the destination has at least 2 blocks more than the current.
     * @throws SlotOccupiedException if the destination {@link Slot} is occupied
     * @throws InvalidDirectionException if the switch-case of getNearbySlot of {@link Board} entered the default case. It
     * shouldn't happen.
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    public boolean move(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, NotReachableLevelException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        int previousLevel = worker.getSlot().getLevel().ordinal();
        int wishedLevel = Board.getNearbySlot(direction, worker.getSlot()).getLevel().ordinal();
        if (cannotMoveUp && wishedLevel > previousLevel) {
            throw new NotReachableLevelException();
        }
        return god.move(direction, worker);
    }
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker} in the direction chosen.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the {@link Slot} where to build is outside the {@link Board}
     * @throws SlotOccupiedException if the slot where to build is occupied by a dome or another worker
     * @throws InvalidDirectionException if the switch-else of getNearbySlot enters the default case. It shouldn't happen.
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        god.build(direction, worker);
    }

}
