package Model.Gods;

import Model.*;
import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
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
    public void setUp () throws WrongBuildOrMoveException, InvalidDirectionException, GodNotSet {
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
    public void turn_CorrectInput_CorrectOutput_NotMoveUp() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
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
    public void turn_CorrectInput_CorrectOutput_MoveUp() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, WrongBuildOrMoveException, NoAvailableMovementsException, NoAvailableBuildingsException {
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

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        Worker otherWorker = secondPlayer.getWorker(Gender.FEMALE);
        otherWorker.setSlot(board.getSlot(2,3));
        turn.executeMove(Direction.UP);
    }

    @Test (expected = NotReachableLevelException.class)
    public void move_NotReachableLevelException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, WrongBuildOrMoveException, NoAvailableMovementsException {
        Worker otherWorker = secondPlayer.getWorker(Gender.FEMALE);
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
        Worker otherWorker = secondPlayer.getWorker(Gender.FEMALE);
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
