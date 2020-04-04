package Model.Enumerations;

public enum Direction {

    LEFT (0),
    LEFTUP (1),
    UP (2),
    RIGHTUP (3),
    RIGHT (4),
    RIGHTDOWN (5),
    DOWN (6),
    LEFTDOWN (7);

    private final int move;
    Direction(int move) {
        this.move = move;
    }

}
