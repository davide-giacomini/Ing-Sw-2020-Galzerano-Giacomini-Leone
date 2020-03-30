package Model;

import Model.Enumerations.Level;

public class Slot {
    private final int column;
    private final int row;
    private boolean isThereAWorker;
    private Worker slotWorker;
    private Level slotLevel;
    private static Board gameBoard;


    public Slot (int i, int j) {
        this.row = i;
        this.column = j;
        this.isThereAWorker = false;
        slotLevel = Level.GROUND;
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

    public boolean getIfBusyOrNot (){ return isThereAWorker; }

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
        return Game.gameBoard.getLeftSlot(actualSlot);
    }

    public Slot getRightSlot (Slot actualSlot) {
        return Game.gameBoard.getRightSlot(actualSlot);
    }

    public Slot getUpSlot (Slot actualSlot) {
        return Game.gameBoard.getUpSlot(actualSlot);
    }

    public Slot getDownSlot (Slot actualSlot) {
        return Game.gameBoard.getDownSlot(actualSlot);
    }

    public Slot getUpLeftSlot (Slot actualSlot) { return Game.gameBoard.getUpLeftSlot(actualSlot); }

    public Slot getDownLeftSlot (Slot actualSlot) {
        return Game.gameBoard.getDownLeftSlot(actualSlot);
    }

    public Slot getUpRightSlot (Slot actualSlot) {
        return Game.gameBoard.getUpRightSlot(actualSlot);
    }

    public Slot getDownRightSlot (Slot actualSlot) { return Game.gameBoard.getDownRightSlot(actualSlot); }



}
