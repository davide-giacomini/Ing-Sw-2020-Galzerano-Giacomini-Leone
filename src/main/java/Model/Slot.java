package Model;

import Model.Enumerations.Level;

/**
 * This class represent a slot of the board.
 * The slot can't be instanced by anyone. It is thought to be instanced only one time by the {@link Board}, therefore
 * it's got a protected constructor.
 */
public class Slot {
    private final int column;
    private final int row;
    private Worker worker;
    private Level level;
    
    /**
     * Solo constructor. It is thought to be called only by {@link Board}
     * @param i slot's row
     * @param j slot's column
     */
    protected Slot (int i, int j) {
        this.row = i;
        this.column = j;
        this.worker = null;
        level = Level.GROUND;
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

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    
    public boolean isOccupied() {
        return worker!=null || level==Level.DOME;
    }
    
    
    /**
     * @return a string which display the row and the column of the slot.
     */
    public String toString() {
        return "Row: " + row + "\nColumn: " + column;
    }

}
