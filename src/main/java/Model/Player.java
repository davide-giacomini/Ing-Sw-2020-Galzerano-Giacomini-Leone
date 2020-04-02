package Model;

import Model.Enumerations.Gender;

import java.awt.*;

/**
 * This class represents the user. A user can have two workers, male and female, and each player can move differently
 * depending on the god assigned to them. Hence, a state pattern has been used in order to devolve the moves to the
 * {@link PlayerBehaviour} interface.
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
    private PlayerBehaviour god;
    private boolean isGodActive;


    public Player(String username, Color workersColor) {
        this.username = username;

        workers = new Worker[WORKERSNUMBER];
        workers[Worker.MALE] = new Worker(workersColor, Gender.MALE);
        workers[Worker.FEMALE] = new Worker(workersColor, Gender.FEMALE);
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

    public void setGod(PlayerBehaviour god) {
        this.god = god;
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
    
}
