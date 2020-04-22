package Model.Gods;

import Model.Board;
import Enumerations.Direction;
import Enumerations.Gender;
import Model.Exceptions.*;
import Model.Player;
import Model.Turn;
import Model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PanTest {
    private Worker worker;
    private Board board;
    private Player player;
    private Turn turn;
    private Player otherPlayer;
    private Worker otherWorker;
    private Worker femaleWorker;

    @Before
    public void setUp () throws Exception{
        board = Board.getBoard();
        player = new Player("Monica", Color.YELLOW);
        player.setGod(new Pan(player, "Pan"));
        worker = player.getWorker(Gender.MALE);
        worker.setSlot(board.getSlot(3,3));
        femaleWorker = player.getWorker(Gender.FEMALE);
        femaleWorker.setSlot(board.getSlot(4,4));
        otherPlayer = new Player("Arianna", Color.BLUE);
        otherWorker = player.getWorker(Gender.FEMALE);
        turn = new Turn(player);
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput()
            throws Exception{
        turn.executeMove(Direction.LEFT);
        assertTrue(player.getGod().checkIfCanGoOn(worker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(worker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException() throws Exception{
        otherWorker.setSlot(board.getSlot(2,3));
        turn.executeMove(Direction.UP);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NotReachableLevelException()
            throws Exception{
        otherWorker.setSlot(board.getSlot(2,2));
        otherWorker.build(Direction.RIGHT);
        otherWorker.build(Direction.RIGHT);
        turn.executeMove(Direction.UP);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NoAvailableMovementsException()
            throws Exception{
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException()
            throws Exception{
        otherWorker.setSlot(board.getSlot(2,2));
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_NoAvailableBuildingsException()
            throws Exception{
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_WrongBuildOrMoveException()
            throws Exception{
        turn.executeBuild(Direction.UP);
    }
}
