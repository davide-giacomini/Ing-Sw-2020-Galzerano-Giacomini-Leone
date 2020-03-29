package Model;

import Enumerations.Move;

import java.awt.*;

public class Player {
    private String username;
    private Worker workers[];
    private boolean isWinning;
    private PlayerBehaviour state;
    private boolean isGodActive;


    Player(String u, Color c) {
        this.username = u;

        workers = new Worker[2];
        workers[0] = new Worker(c);
        workers[1] = new Worker(c);
    }

    public void setState(PlayerBehaviour state) {
        this.state = state;
    }

    public Worker chooseWorker (int i) {
        return workers[i];
    }

    public void putWorker(Worker w, Slot s) {
        w.setWorkerSlot(s);
        w.getWorkerSlot().becomeOccupied(w);
    }

    public Slot getWorkerPosition(Worker w) {
        return w.getWorkerSlot();
    }

    //manca l'eccezione per cui la casella è già piena
    public Move checkMove (Slot wishedSlot, Worker choseWorker) {
        int i = choseWorker.getWorkerSlot().getRow() -  wishedSlot.getRow();
        int j = choseWorker.getWorkerSlot().getColumn() - wishedSlot.getColumn();
        if (i == 0 && j == -1) {
            return Move.LEFT;
        }
        return Move.DOWN; // andrebbero implementate tutte ed otto
    }

    public void move(Slot wishedSlot, Worker choseWorker) {
        switch (checkMove(wishedSlot, choseWorker)) {
            case LEFT : choseWorker.goLeft();
            case LEFTUP:
            case UP:
                break;
            case RIGHTUP:
                break;
            case RIGHT:
                break;
            case RIGHTDOWN:
                break;
            case DOWN:
                break;
            case LEFTDOWN:
                break;
        }
    }
}
