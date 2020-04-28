package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;

public class ArtemisTest {
    private Board board;
    private Turn turn;
    private Player player;
    private Worker femaleWorker;
    private Worker maleWorker;
    private Player otherPlayer;
    private Worker otherWorker;

    @Before
    public void setUp ()
            throws Exception {
        board = Board.getBoard();
        player = new Player("Monica", Color.YELLOW);
        player.setGod(new Artemis(player, "Artemis"));
        maleWorker = player.getWorker(Gender.MALE);
        maleWorker.setSlot(board.getSlot(1,1));
        femaleWorker = player.getWorker(Gender.FEMALE);
        femaleWorker.setSlot(board.getSlot(4,4));
        turn = new Turn(player);
        turn.setWorkerGender(Gender.MALE);
        otherPlayer = new Player("Arianna", Color.BLUE);
        otherWorker = otherPlayer.getWorker(Gender.FEMALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_OneMove()
            throws Exception {
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_TwoMoves()
            throws Exception {
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeMove(Direction.DOWN);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst()
            throws Exception {
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeMove(Direction.LEFT);
        assertSame(board.getSlot(1,2), maleWorker.getSlot());
        turn.executeMove(Direction.UP);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException()
            throws Exception {
        otherWorker.setSlot(board.getSlot(1,2));
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException_atSecondMove()
            throws Exception {
        otherWorker.setSlot(board.getSlot(1,2));
        turn.executeMove(Direction.DOWN);
        turn.executeMove(Direction.RIGHTUP);

    }

    @Test (expected = InvalidMoveException.class)
    public void move_NotReachableLevelException()
            throws Exception {
        otherWorker.setSlot(board.getSlot(2,2));
        otherWorker.build(Direction.UP);
        otherWorker.build(Direction.UP);
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NoAvailableMovementsException()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException()
            throws Exception {
        otherWorker.setSlot(board.getSlot(2,0));
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_NoAvailableBuildingsException()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_NoAvailableBuildingsException_MoveTwice()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.DOWN);
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_WrongBuildOrMoveException()
            throws Exception {
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_WrongBuildOrMoveException() throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        turn.executeMove(Direction.DOWN);
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst_1()
            throws Exception {
        turn.executeMove(Direction.RIGHTUP);
        turn.executeMove(Direction.LEFTDOWN);
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst_3()
            throws Exception {
        turn.executeMove(Direction.LEFTDOWN);
        turn.executeMove(Direction.RIGHTUP);
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst_5()
            throws Exception {
        turn.executeMove(Direction.DOWN);
        turn.executeMove(Direction.UP);
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst_4()
            throws Exception {
        turn.executeMove(Direction.UP);
        turn.executeMove(Direction.DOWN);
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst_6()
            throws Exception {
        turn.executeMove(Direction.LEFTUP);
        turn.executeMove(Direction.RIGHTDOWN);
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst_7()
            throws Exception {
        turn.executeMove(Direction.RIGHTDOWN);
        turn.executeMove(Direction.LEFTUP);
    }

    @Test (expected = InvalidMoveException.class)
    public void turn_tryToBackInTheFirst_8()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHT);
    }

    @Test
    public void checkIfCanMove_withCannotMoveUp()
            throws Exception {
        player.setCannotMoveUp(true);
        turn.executeMove(Direction.LEFT);
        assertTrue(player.getGod().checkIfCanMove(maleWorker));
    }

    @Test
    public void move_IndexOutOfBoundsException()
            throws Exception {
        board.getSlot(1,1).setLevel(Level.LEVEL2);
        maleWorker.setSlot(board.getSlot(1,1));
        turn.executeMove(Direction.LEFTUP);

        board.getSlot(0,1).setLevel(Level.DOME);
        board.getSlot(1,0).setLevel(Level.DOME);

        assertFalse(player.getGod().checkIfCanMove(maleWorker));

    }

}
