package Model.Enumerations;

public enum Move {

    LEFT (0),
    LEFTUP (1),
    UP (2),
    RIGHTUP (3),
    RIGHT (4),
    RIGHTDOWN (5),
    DOWN (6),
    LEFTDOWN (7);

    private final int move;
    Move(int move) {
        this.move = move;
    }

}
