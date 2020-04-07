package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.*;
import Model.Player;
import Model.Turn;
import Model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.*;

public class PanTest {
    private Worker worker;
    private Board board;
    private Player player;
    private Player otherPlayer;
    private Worker otherWorker;
    private Turn turn;

    @Before
    public void setUp () throws WrongBuildOrMoveException {
        board = board.getBoard();
        player = new Player("Monica", Color.YELLOW);
        player.setGod(new Pan(player, "Pan"));
        otherPlayer = new Player("Monica", Color.BLUE);
        otherWorker = player.getWorker(Gender.FEMALE);
        worker = player.getWorker(Gender.MALE);
        worker.setSlot(board.getSlot(3,3));
        turn = new Turn(player);
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_FalseCase() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        boolean winningCondition = turn.executeMove(Direction.LEFT);
        assertFalse(winningCondition);
        assertEquals(board.getSlot(3,2), worker.getSlot());
        assertNull(board.getSlot(3, 3).getWorker());
        turn.executeBuild(Direction.RIGHT);
        assertEquals(Level.LEVEL1, board.getSlot(3,3).getLevel());
    }

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        otherWorker.setSlot(board.getSlot(2,3));
        boolean winningCondition = turn.executeMove(Direction.UP);
    }

    @Test (expected = NotReachableLevelException.class)
    public void move_NotReachableLevelException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, WrongBuildOrMoveException, NoAvailableMovementsException {
        otherWorker.setSlot(board.getSlot(2,2));
        otherWorker.build(Direction.RIGHT);
        otherWorker.build(Direction.RIGHT);
        turn.executeMove(Direction.UP);
    }

    @Test (expected = NoAvailableMovementsException.class)
    public void move_NoAvailableMovementsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        assertFalse(turn.executeMove(Direction.UP));
        turn.executeMove(Direction.LEFT);
    }

    @Test (expected = SlotOccupiedException.class)
    public void build_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        otherWorker.setSlot(board.getSlot(2,2));
        boolean winningCondition = turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void build_WrongBuildOrMoveException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeBuild(Direction.LEFTDOWN);
    }

    @Test (expected = NoAvailableBuildingsException.class)
    public void build_NoAvailableBuildingsException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException, NoAvailableMovementsException, NotReachableLevelException {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.LEFTDOWN);
        turn.executeBuild(Direction.UP);
    }
}
