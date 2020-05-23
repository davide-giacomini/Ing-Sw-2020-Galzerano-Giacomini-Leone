package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Slot;

public class BoardView {
    /**
     * Number of rows of the board.
     */
    public final static int ROWSNUMBER = 5;
    /**
     * Number of columns of the board
     */
    public final static int COLUMNSNUMBER = 5;



    private Slot[][] slots = new Slot[5][5];
    private Board board = null;

    /**
     * Create the slots inside the board.
     */
    BoardView() {
        for (int i = 0; i < ROWSNUMBER; i++) {
            for (int j = 0; j < COLUMNSNUMBER; j++) {
                slots[i][j] = new Slot(i,j);
            }
        }
    }

    public Slot getSlot(int row, int column) {
        return slots[row][column];
    }

    /**
     * This method return the slot nearby the slot you pass, in the direction which you specify.
     *
     * @param direction specifies which next slot you want to get
     * @param currentSlot you want to get the slot nearby this parameter
     * @return the slot nearby the current slot, in the direction specified
     * @throws InvalidDirectionException if none of the cases are verified.
     */
    public Slot getNearbySlot(Direction direction, Slot currentSlot) throws InvalidDirectionException {
        switch (direction){
            case LEFT:
                return slots[currentSlot.getRow()][currentSlot.getColumn()-1];
            case UP:
                return slots[currentSlot.getRow()-1][currentSlot.getColumn()];
            case DOWN:
                return slots[currentSlot.getRow()+1][currentSlot.getColumn()];
            case RIGHT:
                return slots[currentSlot.getRow()][currentSlot.getColumn()+1];
            case LEFTUP:
                return slots[currentSlot.getRow()-1][currentSlot.getColumn()-1];
            case RIGHTUP:
                return slots[currentSlot.getRow()-1][currentSlot.getColumn()+1];
            case LEFTDOWN:
                return slots[currentSlot.getRow()+1][currentSlot.getColumn()-1];
            case RIGHTDOWN:
                return slots[currentSlot.getRow()+1][currentSlot.getColumn()+1];
            default:
                throw new InvalidDirectionException();
        }
    }

    /**
     * This method sets everything in the board null.
     * It's useful for testing.
     */
    public void clearBoard() {
        for (int i = 0; i < ROWSNUMBER; i++) {
            for (int j = 0; j < COLUMNSNUMBER; j++) {
                slots[i][j].setLevel(Level.GROUND);
            }
        }
    }

    public void setSlot(Slot slot) {
        slots[slot.getRow()][slot.getColumn()] = slot;
    }

}
