package Model;

import Model.Enumerations.Direction;
import Model.Enumerations.Level;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class WorkerTest {
    private Worker workerMale;
    private Worker otherWorkerMale;
    private Board board;
    private Player player;
    private Player otherPlayer;

    @Before
    public void setUp() {
        board = Board.getBoard();
        player = new Player("first", Color.LIGHT_GRAY);
        otherPlayer = new Player("second", Color.BLUE);
        otherWorkerMale = otherPlayer.getWorker(0);
        workerMale = player.getWorker(0);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void setSlot_ifNotOccupied() {
        boolean result = workerMale.setSlot(board.getSlot(1,3));
        assertTrue(result);
        assertEquals(board.getSlot(1,3), workerMale.getSlot());
    }

    @Test
    public void setSlot_ifOccupied() {
        otherWorkerMale.setSlot(board.getSlot(4,4));
        boolean result = workerMale.setSlot(board.getSlot(4,4));
        assertFalse(result);
        assertNull(workerMale.getSlot());
    }

    @Test
    public void move_CorrectInput_CorrectOutput_SameLevel() {
        workerMale.setSlot(board.getSlot(3,3));
        boolean result;
        try {
            result = workerMale.move(Direction.LEFTDOWN);
            assertFalse(result);
            assertNull(board.getSlot(3,3).getWorker());
            assertEquals(workerMale, board.getSlot(4,2).getWorker());
            assertFalse(board.getSlot(3,3).isOccupied());
            assertTrue(board.getSlot(4,2).isOccupied());
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
        } catch (NotReachableLevelException e) {
            System.out.println("Too high level");
        } catch (InvalidDirectionException e) {
            System.out.println("Invalid Action");
        }
    }

    @Test
    public void move_SlotOccupiedException() {
        workerMale.setSlot(board.getSlot(3,3));
        otherWorkerMale.setSlot(board.getSlot(4,2));
        try {
            workerMale.move(Direction.LEFTDOWN);
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
            assertEquals(workerMale, board.getSlot(3,3).getWorker());
            assertEquals(otherWorkerMale, board.getSlot(4,2).getWorker());
            assertTrue(board.getSlot(3,3).isOccupied());
            assertTrue(board.getSlot(4,2).isOccupied());
        } catch (NotReachableLevelException e) {
            System.out.println("Too high level");
        } catch (InvalidDirectionException e) {
            System.out.println("Invalid action");
        }
    }

    @Test
    public void build_CorrectInput_CorrectOutput() {
        workerMale.setSlot(board.getSlot(3,3));
        try {
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(Level.LEVEL1, board.getSlot(4,4).getLevel());
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
        } catch (InvalidDirectionException e) {
            System.out.println("Invalid Action");
        }
    }

    @Test
    public void build_SlotOccupiedException_otherWorker() {
        workerMale.setSlot(board.getSlot(3,3));
        otherWorkerMale.setSlot(board.getSlot(4,4));
        try {
            workerMale.build(Direction.RIGHTDOWN);
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
        } catch (InvalidDirectionException e) {
            System.out.println("Invalid Action");
        }
    }

    @Test
    public void build_SlotOccupiedException_dome() {
        workerMale.setSlot(board.getSlot(3,3));
        try {
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.LEVEL1);
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.LEVEL2);
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.LEVEL3);
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.DOME);
            workerMale.build(Direction.RIGHTDOWN);
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
            assertEquals(board.getSlot(4,4).getLevel(), Level.DOME);
        } catch (InvalidDirectionException e) {
            System.out.println("Invalid Action");
        }
    }
}