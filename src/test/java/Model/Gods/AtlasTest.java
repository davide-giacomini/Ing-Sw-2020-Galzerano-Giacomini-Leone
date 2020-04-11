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

public class AtlasTest {
    private Board board;
    private Turn turn;
    private Player player;
    private Worker femaleWorker;
    private Worker maleWorker;
    private Player otherPlayer;
    private Worker otherWorker;

    @Before
    public void setUp () throws WrongBuildOrMoveException, InvalidDirectionException, GodNotSet {
        board = Board.getBoard();
        player = new Player("Monica", Color.YELLOW);
        player.setGod(new Atlas(player, "Atlas"));
        maleWorker = player.getWorker(Gender.MALE);
        maleWorker.setSlot(board.getSlot(0,0));
        femaleWorker = player.getWorker(Gender.FEMALE);
        femaleWorker.setSlot(board.getSlot(2,2));
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
    public void turn_CorrectInput_CorrectOutput_NotWantsToBuildDome() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.setWantsToBuildDome(false);
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertEquals(Level.LEVEL1, board.getSlot(2,1).getLevel());
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_WantsToBuildDome() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.setWantsToBuildDome(true);
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertEquals(Level.DOME, board.getSlot(2,1).getLevel());
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        otherWorker.setSlot(board.getSlot(0,1));
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = NotReachableLevelException.class)
    public void move_NotReachableLevelException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, WrongBuildOrMoveException, NoAvailableMovementsException {
        otherWorker.setSlot(board.getSlot(1,1));
        otherWorker.build(Direction.UP);
        otherWorker.build(Direction.UP);
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = NoAvailableMovementsException.class)
    public void move_NoAvailableMovementsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        turn.executeMove(Direction.RIGHT);
        turn.executeMove(Direction.DOWN);
    }

    @Test (expected = SlotOccupiedException.class)
    public void build_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        otherWorker.setSlot(board.getSlot(1,1));
        turn.executeMove(Direction.DOWN);
        turn.executeBuild(Direction.RIGHT);
    }

    @Test (expected = NoAvailableBuildingsException.class)
    public void build_NoAvailableBuildingsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.RIGHTDOWN);
        turn.executeBuild(Direction.UP);
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void build_WrongBuildOrMoveException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeBuild(Direction.RIGHTDOWN);
    }
}
