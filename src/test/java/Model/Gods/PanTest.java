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
    public void setUp () throws WrongBuildOrMoveException, InvalidDirectionException, GodNotSetException {
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
    public void turn_CorrectInput_CorrectOutput() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFT);
        assertTrue(player.getGod().checkIfCanGoOn(worker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(worker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        otherWorker.setSlot(board.getSlot(2,3));
        turn.executeMove(Direction.UP);
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
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
    }

    @Test (expected = SlotOccupiedException.class)
    public void build_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        otherWorker.setSlot(board.getSlot(2,2));
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = NoAvailableBuildingsException.class)
    public void build_NoAvailableBuildingsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void build_WrongBuildOrMoveException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeBuild(Direction.UP);
    }
}
