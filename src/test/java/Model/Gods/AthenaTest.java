package Model.Gods;

import Model.*;
import Enumerations.Direction;
import Enumerations.Gender;
import Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class AthenaTest {
    private Game game;
    private Board board;
    private Turn turn;
    private Player athenaPlayer;
    private Player secondPlayer;
    private Player thirdPlayer;
    private Worker athenaWorker;
    private Worker otherAthenaWorker;

    @Before
    public void setUp ()
            throws Exception {
        board = Board.getBoard();
        athenaPlayer = new Player("Monica", Color.YELLOW);
        athenaPlayer.setGod(new Athena(athenaPlayer, "Athena"));
        athenaWorker = athenaPlayer.getWorker(Gender.MALE);
        athenaWorker.setSlot(board.getSlot(3,3));
        otherAthenaWorker = athenaPlayer.getWorker(Gender.FEMALE);
        otherAthenaWorker.setSlot(board.getSlot(0,0));
        secondPlayer = new Player("Arianna", Color.BLUE);
        thirdPlayer = new Player("Davide", Color.GREEN);
        game = new Game();
        game.addPlayer(athenaPlayer);
        game.addPlayer(secondPlayer);
        game.addPlayer(thirdPlayer);
        turn = new Turn(athenaPlayer);
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_NotMoveUp()
            throws Exception {
        turn.executeMove(Direction.RIGHT);
        assertTrue(athenaPlayer.getGod().checkIfCanGoOn(athenaWorker));
        assertFalse(athenaPlayer.getGod().validateEndTurn());
        assertFalse(secondPlayer.cannotMoveUp());
        assertFalse(thirdPlayer.cannotMoveUp());
        turn.executeBuild(Direction.DOWN);
        assertFalse(athenaPlayer.isWinning());
        assertFalse(athenaPlayer.getGod().checkIfCanGoOn(athenaWorker));
        assertTrue(athenaPlayer.getGod().validateEndTurn());
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_MoveUp()
            throws Exception {
        Worker otherWorker = secondPlayer.getWorker(Gender.FEMALE);
        otherWorker.setSlot(board.getSlot(2,2));
        otherWorker.build(Direction.RIGHT);
        turn.executeMove(Direction.UP);
        assertTrue(athenaPlayer.getGod().checkIfCanGoOn(athenaWorker));
        assertFalse(athenaPlayer.getGod().validateEndTurn());
        assertTrue(secondPlayer.cannotMoveUp());
        assertTrue(thirdPlayer.cannotMoveUp());
        turn.executeBuild(Direction.DOWN);
        assertFalse(athenaPlayer.isWinning());
        assertFalse(athenaPlayer.getGod().checkIfCanGoOn(athenaWorker));
        assertTrue(athenaPlayer.getGod().validateEndTurn());
    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException()
            throws Exception {
        Worker otherWorker = secondPlayer.getWorker(Gender.FEMALE);
        otherWorker.setSlot(board.getSlot(2,3));
        turn.executeMove(Direction.UP);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NotReachableLevelException()
            throws Exception {
        Worker otherWorker = secondPlayer.getWorker(Gender.FEMALE);
        otherWorker.setSlot(board.getSlot(2,2));
        otherWorker.build(Direction.RIGHT);
        otherWorker.build(Direction.RIGHT);
        turn.executeMove(Direction.UP);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NoAvailableMovementsException()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException()
            throws Exception {
        Worker otherWorker = secondPlayer.getWorker(Gender.FEMALE);
        otherWorker.setSlot(board.getSlot(2,2));
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_NoAvailableBuildingsException()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_WrongBuildOrMoveException()
            throws Exception {
        turn.executeBuild(Direction.UP);
    }

}
