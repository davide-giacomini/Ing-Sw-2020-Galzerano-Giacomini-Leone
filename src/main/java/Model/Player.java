package Model;

import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Exceptions.InvalidActionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;

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
    public final static int WORKERSNUMBER = 2;
    private String username;
    private Worker[] workers;
    private boolean isWinning;
    private boolean cantMoveUp;
    private God god;
    private boolean isGodActive;


    public Player(String username, Color workersColor) {
        this.username = username;

        workers = new Worker[WORKERSNUMBER];
        workers[Worker.MALE] = new Worker(workersColor, Gender.MALE, this);
        workers[Worker.FEMALE] = new Worker(workersColor, Gender.FEMALE, this);
    }

    public void setWinning(boolean winning) {
        isWinning = winning;
    }

    public boolean isWinning() {
        return isWinning;
    }

    public boolean cantMoveUp() {
        return cantMoveUp;
    }

    public void setCantMoveUp(boolean cantMoveUp) {
        this.cantMoveUp = cantMoveUp;
    }

    public void setGod(God god) {
        this.god = god;
    }
    
    public God getGod() {
        return god;
    }

    public Worker getWorker(int i) {
        return workers[i];
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
     * @return true if the worker moved up on the third level, false otherwise
     * @throws IndexOutOfBoundsException if the destination {@link Slot} doesn't exist in the {@link Board}.
     * @throws NotReachableLevelException if the difference between the original level and the destination one is more
     * than 1.
     * @throws SlotOccupiedException if the destination {@link Slot} is occupied
     * @throws InvalidActionException if the switch-case of getNearbySlot of {@link Board} entered the default case. It
     * shouldn't happen.
     */
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, NotReachableLevelException, SlotOccupiedException, InvalidActionException {
        god.move(direction, worker);
        return false;
    }
    
    /**
     * It builds a construction in the direction specified, with the worker chosen
     * @param direction
     * @param worker
     * @return
     */
    public boolean build(Direction direction, Worker worker){
        return false;
    }
    
}
