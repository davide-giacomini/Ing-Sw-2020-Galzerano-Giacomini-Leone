package Model;

import Model.Enumerations.Direction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


public class BoardTest {
    Board board;

    @Before
    public void setUp() {
        board = board.getBoard();
    }

    @After
    public void tearDown() {
        board = null;
    }

    @Test
    public void singletonBoard() {
        Board secondBoard = Board.getBoard();
        assertSame(board,secondBoard);
    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutput_Left() {
        try {
            Slot slot = board.getNearbySlot(Direction.LEFT,board.getSlot(3,3));
            assertSame(board.getSlot(3,2), slot);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutput_Right() {
        try {
            Slot slot = board.getNearbySlot(Direction.RIGHT,board.getSlot(3,3));
            assertSame(board.getSlot(3,4), slot);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputUp() {
        try {
            Slot slot = board.getNearbySlot(Direction.UP,board.getSlot(3,3));
            assertSame(board.getSlot(2,3), slot);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputDown() {
        try {
            Slot slot = board.getNearbySlot(Direction.DOWN,board.getSlot(3,3));
            assertSame(board.getSlot(4,3), slot);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
