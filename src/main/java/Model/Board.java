package Model;

/**
 * This class represents the model of the board of the game, with inside it 25 slots, each one represented by the class
 * {@link Slot}.
 * The board can be instanced only one time, hence it's a thread-safe singleton.
 */
public class Board {
    public final static int ROWSNUMBER = 5;
    public final static int COLUMNSNUMBER = 5;
    private Slot[][] slots = new Slot[5][5];
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
    private synchronized static Board createBoard(){
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
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} at the left of the origin slot.
     */
    public Slot getLeftSlot(Slot actualSlot) {
        int i = actualSlot.getRow();
        int j = actualSlot.getColumn() - 1;
        return slots[i][j];
    }
    /**
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} at the right of the origin slot.
     */
    public Slot getRightSlot(Slot actualSlot) {
        int i = actualSlot.getRow();
        int j = actualSlot.getColumn() + 1;
        return slots[i][j];
    }
    /**
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} up respect to the origin slot.
     */
    public Slot getUpSlot(Slot actualSlot) {
        int i = actualSlot.getRow() - 1;
        int j = actualSlot.getColumn() ;
        return slots[i][j];
    }
    /**
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} down respect to the origin slot.
     */
    public Slot getDownSlot(Slot actualSlot) {
        int i = actualSlot.getRow() + 1;
        int j = actualSlot.getColumn() ;
        return slots[i][j];
    }
    /**
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} at the up-left position respect to the origin slot.
     */
    public Slot getUpLeftSlot(Slot actualSlot) {
        int i = actualSlot.getRow() - 1;
        int j = actualSlot.getColumn() - 1 ;
        return slots[i][j];
    }
    /**
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} at the down-left position respect to the origin slot.
     */
    public Slot getDownLeftSlot(Slot actualSlot) {
        int i = actualSlot.getRow() + 1;
        int j = actualSlot.getColumn() - 1 ;
        return slots[i][j];
    }
    /**
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} at the up-right position respect to the origin slot.
     */
    public Slot getUpRightSlot(Slot actualSlot) {
        int i = actualSlot.getRow() - 1;
        int j = actualSlot.getColumn() + 1;
        return slots[i][j];
    }
    /**
     * @param actualSlot {@link Slot} of origin
     * @return  {@link Slot} at the down-right position respect to the origin slot.
     */
    public Slot getDownRightSlot(Slot actualSlot) {
        int i = actualSlot.getRow() + 1;
        int j = actualSlot.getColumn() + 1;
        return slots[i][j];
    }
}
