package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.SlotOccupiedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        player = new Player("first", Color.RED);
        otherPlayer = new Player("second", Color.BLUE);
        otherWorkerMale = otherPlayer.getWorker(Gender.MALE);
        workerMale = player.getWorker(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void setSlot_ifSlotNotNull() {
        workerMale.setSlot(board.getSlot(1, 3));
        assertEquals(board.getSlot(1, 3), workerMale.getSlot());
    }

    @Test
    public void setSlot_IfSlotNull_SlotInWorkerIsNull_But_WorkerInSlotIsNotNull() {
        workerMale.setSlot(board.getSlot(1, 3));
        workerMale.setSlot(null);
        assertNull(workerMale.getSlot());
        assertEquals(workerMale, board.getSlot(1, 3).getWorker());
    }

    @Test
    public void move_CorrectInput_CorrectOutput_SameLevel()
            throws Exception {
        workerMale.setSlot(board.getSlot(3, 3));
        boolean result;
            result = workerMale.move(Direction.LEFTDOWN);
            assertFalse(result);
            assertNull(board.getSlot(3, 3).getWorker());
            assertEquals(workerMale, board.getSlot(4, 2).getWorker());
            assertFalse(board.getSlot(3, 3).getIsOccupied());
            assertTrue(board.getSlot(4, 2).getIsOccupied());

    }

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException()
            throws Exception {
        workerMale.setSlot(board.getSlot(3, 3));
        otherWorkerMale.setSlot(board.getSlot(4, 2));
        workerMale.move(Direction.LEFTDOWN);
    }

    @Test
    public void build_CorrectInput_CorrectOutput()
            throws Exception {
        workerMale.setSlot(board.getSlot(3, 3));
        workerMale.build(Direction.RIGHTDOWN);
        assertEquals(Level.LEVEL1, board.getSlot(4, 4).getLevel());
    }

    @Test (expected = SlotOccupiedException.class)
    public void build_SlotOccupiedException_otherWorker()
            throws Exception {
        workerMale.setSlot(board.getSlot(3, 3));
        otherWorkerMale.setSlot(board.getSlot(4, 4));
        workerMale.build(Direction.RIGHTDOWN);
    }

    @Test (expected = SlotOccupiedException.class)
    public void build_SlotOccupiedException_dome()
            throws Exception {
        workerMale.setSlot(board.getSlot(3, 3));
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4, 4).getLevel(), Level.LEVEL1);
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4, 4).getLevel(), Level.LEVEL2);
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4, 4).getLevel(), Level.LEVEL3);
            workerMale.build(Direction.RIGHTDOWN);
            assertEquals(board.getSlot(4, 4).getLevel(), Level.DOME);
            workerMale.build(Direction.RIGHTDOWN);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void move_IndexOutOfBound_1()
            throws Exception {
        workerMale.setSlot(board.getSlot(0, 3));
        workerMale.move(Direction.UP);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void move_IndexOutOfBound_2()
            throws Exception {
        workerMale.setSlot(board.getSlot(2, 4));
        workerMale.move(Direction.RIGHT);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void move_IndexOutOfBound_3()
            throws Exception {
        workerMale.setSlot(board.getSlot(4, 2));
        workerMale.move(Direction.DOWN);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void move_IndexOutOfBound_4()
            throws Exception {
        workerMale.setSlot(board.getSlot(0, 0));
        workerMale.move(Direction.LEFT);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void move_IndexOutOfBound_5()
            throws Exception {
        workerMale.setSlot(board.getSlot(0, 0));
        workerMale.move(Direction.LEFTUP);
    }

    @Test
    public void move_Correct()
            throws Exception {
        workerMale.setSlot(board.getSlot(3, 3));
        workerMale.move(Direction.RIGHTUP);
    }

    @Test
    public void getColor()
            throws Exception {
        assertEquals(workerMale.getColor(), Color.RED);
    }

    @Test
    public void getGender()
            throws Exception {
        assertEquals(workerMale.getGender(), Gender.MALE);
    }

    @Test
    public void buildDome()
            throws Exception {
        workerMale.setSlot(board.getSlot(0, 0));
        workerMale.buildDome(Direction.RIGHT);
        Slot slot = board.getSlot(0,1);
        assertEquals(slot.getLevel(), Level.DOME);
    }

    @Test (expected = SlotOccupiedException.class)
    public void buildDome_SlotOccupiedException()
            throws Exception {
        workerMale.setSlot(board.getSlot(0, 0));
        otherWorkerMale.setSlot(board.getSlot(0, 1));
        workerMale.buildDome(Direction.RIGHT);
    }


}