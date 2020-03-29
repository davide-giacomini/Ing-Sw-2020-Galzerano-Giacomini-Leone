package Model;

import Enumerations.Level;

public class Slot {
    private final int column;
    private final int row;
    private boolean isThereAWorker;
    private Worker slotWorker;
    private Level slotLevel;
    private static Board gameBoard;

    Slot (int i, int j) {
        this.row = i;
        this.column = j;
        this.isThereAWorker = false;
        this.slotLevel = Level.GROUND;
        gameBoard = Game.gameBoard;
    }

    public boolean isThereAWorker() {
        return isThereAWorker;
    }

    public void becomeOccupied(Worker w) {
        this.isThereAWorker = true;
        this.slotWorker = w;
    }

    public void becomeUnoccupied() {
        this.isThereAWorker = false;
        this.slotWorker = null;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Worker getSlotWorker() {
        return slotWorker;
    }

    public void setSlotWorker(Worker slotWorker) {
        this.slotWorker = slotWorker;
    }

    public Level getSlotLevel() {
        return slotLevel;
    }

    public void setSlotLevel(Level slotLevel) {
        this.slotLevel = slotLevel;
    }

    public Slot getLeftSlot (Slot actualSlot) {
        return gameBoard.getLeftSlot(actualSlot);
    }

}
