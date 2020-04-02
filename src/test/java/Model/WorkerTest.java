package Model;

import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.InvalidActionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

import static org.junit.Assert.*;

public class WorkerTest {
    private Worker worker;
    private Worker otherWorker;
    private Board board;

    @Before
    public void setUp() {
        board = Board.getBoard();
        otherWorker = new Worker(Color.LIGHT_GRAY, Gender.MALE);
        worker = new Worker(Color.BLUE, Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void setSlot_ifNotOccupied() {
        boolean result = worker.setSlot(board.getSlot(1,3));
        assertTrue(result);
        assertEquals(board.getSlot(1,3), worker.getSlot());
    }

    @Test
    public void setSlot_ifOccupied() {
        otherWorker.setSlot(board.getSlot(4,4));
        boolean result = worker.setSlot(board.getSlot(4,4));
        assertFalse(result);
        assertNull(worker.getSlot());
    }

    @Test
    public void move_CorrectInput_CorrectOutput_SameLevel() {
        worker.setSlot(board.getSlot(3,3));
        boolean result;
        try {
            result = worker.move(Direction.LEFTDOWN);
            assertFalse(result);
            assertNull(board.getSlot(3,3).getWorker());
            assertEquals(worker, board.getSlot(4,2).getWorker());
            assertFalse(board.getSlot(3,3).isOccupied());
            assertTrue(board.getSlot(4,2).isOccupied());
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
        } catch (NotReachableLevelException e) {
            System.out.println("Too high level");
        } catch (InvalidActionException e) {
            System.out.println("Invalid Action");
        }
    }

    @Test
    public void move_SlotOccupiedException() {
        worker.setSlot(board.getSlot(3,3));
        otherWorker.setSlot(board.getSlot(4,2));
        try {
            worker.move(Direction.LEFTDOWN);
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
            assertEquals(worker, board.getSlot(3,3).getWorker());
            assertEquals(otherWorker, board.getSlot(4,2).getWorker());
            assertTrue(board.getSlot(3,3).isOccupied());
            assertTrue(board.getSlot(4,2).isOccupied());
        } catch (NotReachableLevelException e) {
            System.out.println("Too high level");
        } catch (InvalidActionException e) {
            System.out.println("Invalid action");
        }
    }

    @Test
    public void build_CorrectInput_CorrectOutput() {
        worker.setSlot(board.getSlot(3,3));
        try {
            worker.build(Direction.RIGHTDOWN);
            assertEquals(Level.LEVEL1, board.getSlot(4,4).getLevel());
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
        } catch (InvalidActionException e) {
            System.out.println("Invalid Action");
        }
    }

    @Test
    public void build_SlotOccupiedException_otherWorker() {
        worker.setSlot(board.getSlot(3,3));
        otherWorker.setSlot(board.getSlot(4,4));
        try {
            worker.build(Direction.RIGHTDOWN);
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
        } catch (InvalidActionException e) {
            System.out.println("Invalid Action");
        }
    }

    @Test
    public void build_SlotOccupiedException_dome() {
        worker.setSlot(board.getSlot(3,3));
        try {
            worker.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.LEVEL1);
            worker.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.LEVEL2);
            worker.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.LEVEL3);
            worker.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4,4).getLevel(), Level.DOME);
            worker.build(Direction.RIGHTDOWN);
        } catch (SlotOccupiedException e) {
            System.out.println("Yet occupied slot");
            assertEquals(board.getSlot(4,4).getLevel(), Level.DOME);
        } catch (InvalidActionException e) {
            System.out.println("Invalid Action");
        }
    }
}