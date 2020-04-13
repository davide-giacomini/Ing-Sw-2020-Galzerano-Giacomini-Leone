package Model.Gods;

import Model.*;
import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class HephaestusTest {

    private Turn turn;
    private Player player;
    private Worker worker;
    private Slot slot1, slot2;
    private Board board;
    private Player secondPlayer;
    private Worker secondWorker;

    @Before
    public void setUp() throws WrongBuildOrMoveException, InvalidDirectionException, GodNotSet {

        board = Board.getBoard();

        slot1 = board.getSlot(3,3);
        slot2 = board.getSlot(4,4);

        player = new Player("Arianna", Color.BLUE);
        secondPlayer = new Player("David", Color.YELLOW);

        worker = player.getWorker(Gender.MALE);
        secondWorker = player.getWorker(Gender.FEMALE);
        worker.setSlot(slot1);
        secondWorker.setSlot(slot2);

        player.setGod(new Hephaestus(player, "Hephaestus"));

        turn = new Turn(player);
        turn.setWorkerGender(Gender.MALE);

    }

    @After
    public void tearDown() {
        Board.getBoard().clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_oneBuild()  throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException{

        assertTrue (player.getGod().checkIfCanMove(worker));
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(worker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(worker));
        turn.executeBuild(Direction.LEFTUP);
        assertTrue(player.getGod().checkIfCanGoOn(worker));
        assertTrue(player.getGod().validateEndTurn());

    }

    @Test
    public void turn_CorrectInput_CorrectOutput_twoBuild()  throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException{

        assertTrue (player.getGod().checkIfCanMove(worker));
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(worker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(worker));
        turn.executeBuild(Direction.LEFTUP);
        assertTrue(player.getGod().checkIfCanGoOn(worker));

        assertTrue(player.getGod().checkIfCanBuild(worker));
        turn.executeBuild(Direction.LEFTUP);
        assertFalse(player.getGod().checkIfCanGoOn(worker));
        assertTrue(player.getGod().validateEndTurn());

    }

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        turn.executeMove(Direction.RIGHTDOWN);
    }

    @Test (expected = NotReachableLevelException.class)
    public void move_NotReachableLevelException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, WrongBuildOrMoveException, NoAvailableMovementsException {

        secondWorker.build(Direction.LEFT);
        secondWorker.build(Direction.LEFT);
        turn.executeMove(Direction.DOWN);
    }

    @Test (expected = NoAvailableMovementsException.class)
    public void move_NoAvailableMovementsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void build_WrongBuildOrMoveException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = WrongBuildOrMoveException.class)
    public void second_build_WrongBuildOrMoveException() throws InvalidDirectionException, SlotOccupiedException, WrongBuildOrMoveException, NoAvailableBuildingsException, NotReachableLevelException, NoAvailableMovementsException{

        turn.executeMove(Direction.LEFT);

        turn.executeBuild(Direction.RIGHT);

        turn.executeBuild(Direction.LEFTUP);
    }

    @Test (expected = NoAvailableBuildingsException.class)
    public void build_NoAvailableBuildingsException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.DOWN);
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.getGod().checkIfCanBuild(worker));
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = SlotOccupiedException.class)
    public void build_SlotOccupiedException() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.DOWN);
        turn.executeBuild(Direction.RIGHT);
    }

    @Test
    public void checkIfCanBuild_firstBuild_OutputFalse() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.RIGHT);
        board.getSlot(2,4).setLevel(Level.DOME);
        board.getSlot(2,3).setLevel(Level.DOME);
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);
        board.getSlot(4,4).setLevel(Level.DOME);
        assertFalse(player.getGod().checkIfCanBuild(worker));

    }

    @Test
    public void checkIfCanBuild_SecondBuild_OutputFalse() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        turn.executeMove(Direction.RIGHT);
        turn.executeBuild(Direction.LEFT);
        board.getSlot(2,4).setLevel(Level.DOME);
        board.getSlot(2,3).setLevel(Level.DOME);
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);
        board.getSlot(4,4).setLevel(Level.DOME);
        assertFalse(player.getGod().checkIfCanBuild(worker));

    }

}