package Model;

public class Board {
    private Slot[][] board = new Slot[5][5];

    private static Board single_instance = null;

    private Board() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Slot(i , j );
            }
        }
    }

    public Slot getSlot(int row, int column) {
        return board[row][column];
    }

    public static Board getInstance(){
        if (single_instance == null)
            single_instance = new Board();

        return single_instance;

    }

    // va aggiunta l'eccezione "sono sulla riga 5"  added in worker

    public Slot getLeftSlot(Slot actualSlot) {
        int i = actualSlot.getRow();
        int j = actualSlot.getColumn() - 1;
        Slot slot = board[i][j];
        return slot;
    }

    public Slot getRightSlot(Slot actualSlot) {
        int i = actualSlot.getRow();
        int j = actualSlot.getColumn() + 1;
        Slot slot = board[i][j];
        return slot;
    }

    public Slot getUpSlot(Slot actualSlot) {
        int i = actualSlot.getRow() - 1;
        int j = actualSlot.getColumn() ;
        Slot slot = board[i][j];
        return slot;
    }

    public Slot getDownSlot(Slot actualSlot) {
        int i = actualSlot.getRow() + 1;
        int j = actualSlot.getColumn() ;
        Slot slot = board[i][j];
        return slot;
    }

    public Slot getUpLeftSlot(Slot actualSlot) {
        int i = actualSlot.getRow() - 1;
        int j = actualSlot.getColumn() - 1 ;
        Slot slot = board[i][j];
        return slot;
    }

    public Slot getDownLeftSlot(Slot actualSlot) {
        int i = actualSlot.getRow() + 1;
        int j = actualSlot.getColumn() - 1 ;
        Slot slot = board[i][j];
        return slot;
    }

    public Slot getUpRightSlot(Slot actualSlot) {
        int i = actualSlot.getRow() - 1;
        int j = actualSlot.getColumn() + 1;
        Slot slot = board[i][j];
        return slot;
    }

    public Slot getDownRightSlot(Slot actualSlot) {
        int i = actualSlot.getRow() + 1;
        int j = actualSlot.getColumn() + 1;
        Slot slot = board[i][j];
        return slot;
    }
}
