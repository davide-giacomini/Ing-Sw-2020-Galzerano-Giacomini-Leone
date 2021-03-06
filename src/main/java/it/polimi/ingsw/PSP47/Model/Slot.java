package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Level;

import java.io.Serializable;

/**
 * This class represent a slot of the board.
 * The slot can't be instanced by anyone. It is thought to be instanced only one time by the {@link Board}, therefore
 * it's got a protected constructor.
 */
public class Slot extends SlotObservable implements Serializable {
    private static final long serialVersionUID = -7636829625663848955L;
    private final int column;
    private final int row;
    private transient Worker worker;
    private Color workerColor;
    private Level level;
    private boolean isWorkerOnSlot;
    
    /**
     * Solo constructor. It is thought to be called only by {@link Board}
     * @param i slot's row
     * @param j slot's column
     */
    public Slot(int i, int j) {
        this.row = i;
        this.column = j;
        this.worker = null;
        this.workerColor = null;
        level = Level.GROUND;
        isWorkerOnSlot = false;

    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
    
    public Worker getWorker() {
        return worker;
    }
    
    /**
     * This method sets the worker, but it has to be called only if it's not necessary
     * to update the slot under the worker. Indeed, it doesn't update it.
     * See {@link Worker#setSlot(Slot)}
     *
     * @param worker the worker to be put on this slot.
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
        if (worker != null){
            this.workerColor = worker.getColor();
            this.isWorkerOnSlot = true;
        }else{
            this.workerColor = null;
            this.isWorkerOnSlot = false;
        }
        notifySlotListeners(this);
    }

    public Color getWorkerColor() {
        return workerColor;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
        notifySlotListeners(this);
    }
    
    public boolean isOccupied() {
        return isWorkerOnSlot || level==Level.DOME || level==Level.ATLAS_DOME;
    }
    
    
    /**
     * @return a string which display the row and the column of the slot.
     */
    public String toString() {
        return "Row: " + row + "\nColumn: " + column;
    }

    public void setWorkerColor(Color workerColor) {
        this.workerColor = workerColor;
    }

    public void setWorkerOn(boolean occupied) {
        isWorkerOnSlot = occupied;
    }
    
    public boolean isWorkerOnSlot() {
        return isWorkerOnSlot;
    }

    public boolean isPerimeterSlot(){
        return (getRow()==0 || getRow()==4 || getColumn()==0 || getColumn()==4);
    }
}
