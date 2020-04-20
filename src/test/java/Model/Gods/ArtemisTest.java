package Model.Gods;

import Model.*;
import Enumerations.Direction;
import Enumerations.Gender;
import Enumerations.Level;
import Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

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
    public void setUp () throws WrongBuildOrMoveException, InvalidDirectionException, GodNotSetException {
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
    public void turn_CorrectInput_CorrectOutput_OneMove() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_TwoMoves() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
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

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
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

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        otherWorker.setSlot(board.getSlot(1,2));
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = NotReachableLevelException.class)
    public void move_NotReachableLevelException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, WrongBuildOrMoveException, NoAvailableMovementsException {
        otherWorker.setSlot(board.getSlot(2,2));
        otherWorker.build(Direction.UP);
        otherWorker.build(Direction.UP);
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = NoAvailableMovementsException.class)
    public void move_NoAvailableMovementsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = SlotOccupiedException.class)
    public void build_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        otherWorker.setSlot(board.getSlot(2,0));
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = NoAvailableBuildingsException.class)
    public void build_NoAvailableBuildingsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = NoAvailableBuildingsException.class)
    public void build_NoAvailableBuildingsException_MoveTwice() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.DOWN);
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void build_WrongBuildOrMoveException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void move_WrongBuildOrMoveException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException, NoAvailableMovementsException, NotReachableLevelException {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        turn.executeMove(Direction.DOWN);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst_1() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.RIGHTUP);
        turn.executeMove(Direction.LEFTDOWN);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst_3() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFTDOWN);
        turn.executeMove(Direction.RIGHTUP);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst_5() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.DOWN);
        turn.executeMove(Direction.UP);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst_4() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.UP);
        turn.executeMove(Direction.DOWN);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst_6() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFTUP);
        turn.executeMove(Direction.RIGHTDOWN);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst_7() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.RIGHTDOWN);
        turn.executeMove(Direction.LEFTUP);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void turn_tryToBackInTheFirst_8() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHT);
    }

    @Test
    public void checkIfCanMove_withCannotMoveUp() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException,NoAvailableBuildingsException {
        player.setCannotMoveUp(true);
        turn.executeMove(Direction.LEFT);
        assertTrue(player.getGod().checkIfCanMove(maleWorker));
    }

    @Test
    public void move_IndexOutOfBoundsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        board.getSlot(1,1).setLevel(Level.LEVEL2);
        maleWorker.setSlot(board.getSlot(1,1));
        turn.executeMove(Direction.LEFTUP);

        board.getSlot(0,1).setLevel(Level.DOME);
        board.getSlot(1,0).setLevel(Level.DOME);

        assertFalse(player.getGod().checkIfCanMove(maleWorker));

    }

}
