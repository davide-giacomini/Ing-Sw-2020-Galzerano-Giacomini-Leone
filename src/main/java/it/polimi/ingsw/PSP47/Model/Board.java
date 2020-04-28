package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;

/**
 * This class represents the model of the board of the game, with inside it 25 slots, each one represented by the class
 * {@link Slot}.
 * The board can be instanced only one time, hence it's a thread-safe singleton.
 */
public class Board {
    /**
     * Number of rows of the board.
     */
    public final static int ROWSNUMBER = 5;
    /**
     * Number of columns of the board
     */
    public final static int COLUMNSNUMBER = 5;
    private static Slot[][] slots = new Slot[5][5];
    private static Board board = null;
    
    /**
     * Create the slots inside the board.
     */
    private Board() {
        for (int i = 0; i < ROWSNUMBER; i++) {
            for (int j = 0; j < COLUMNSNUMBER; j++) {
                slots[i][j] = new Slot(i,j);
            }
        }
    }
    private static Board createBoard(){
        if (board==null) board = new Board();
        return board;
    }
    
    public static Board getBoard() {
        if (board==null) createBoard();
        return board;
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
    public static Slot getNearbySlot(Direction direction, Slot currentSlot) throws InvalidDirectionException {
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
                slots[i][j].removeAll();
                slots[i][j].setWorker(null);
                slots[i][j].setLevel(Level.GROUND);
            }
        }
    }
    
}
