package Model;

public class Board {
    private Slot[][] board = new Slot[5][5];


    Board() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Slot(i + 1, j + 1);
            }
        }
    }

    public Slot getSlot(int row, int column) {
        return board[row][column];
    }

    // va aggiunta l'eccezione "sono sulla riga 5"
    public Slot getLeftSlot(Slot actualSlot) {
        int i = actualSlot.getRow();
        int j = actualSlot.getColumn() + 1;
        Slot slot = board[i][j];
        return slot;
    }
}
