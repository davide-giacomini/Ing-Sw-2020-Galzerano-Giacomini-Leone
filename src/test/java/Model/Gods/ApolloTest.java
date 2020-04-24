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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ApolloTest {
    private Turn turn;
    private Player player;
    private Worker workerF,workerM;
    private Slot slotF, slotM, slot2;
    private Board board;
    private Player secondPlayer;
    private Worker secondWorker;

    @Before
    public void setUp()
            throws Exception {
        board = Board.getBoard();
        slotM = board.getSlot(2,2);
        slot2 = board.getSlot(3,3);
        slotF = board.getSlot(0,0);

        player = new Player("Arianna", Color.BLUE);
        secondPlayer = new Player("Monichella", Color.BLACK);

        workerM = player.getWorker(Gender.MALE);
        workerF = player.getWorker(Gender.FEMALE);

        secondWorker = secondPlayer.getWorker(Gender.FEMALE);
        workerM.setSlot(slotM);
        workerF.setSlot(slotF);
        secondWorker.setSlot(slot2);

        player.setGod(new Apollo(player, "Apollo"));

        turn = new Turn(player);
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown()  {
        board.clearBoard();
    }


    @Test
    public void turn_CorrectInput_CorrectOutput_withoutSpecialMove()  throws Exception{
        assertEquals(player.getGod().getName(), "Apollo");
        assertTrue (player.getGod().checkIfCanMove(workerM));
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(workerM));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(workerM));
        turn.executeBuild(Direction.LEFTUP);
        assertFalse(player.getGod().checkIfCanGoOn(workerM));
        assertTrue(player.getGod().validateEndTurn());


    }

    @Test
    public void turn_CorrectInput_CorrectOutput_withSpecialMove()  throws Exception{

        assertTrue (player.getGod().checkIfCanMove(workerM));
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(workerM));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(workerM));
        turn.executeBuild(Direction.LEFT);
        assertFalse(player.getGod().checkIfCanGoOn(workerM));
        assertTrue(player.getGod().validateEndTurn());


    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException()  throws Exception {
        board.getSlot(1,1).setLevel(Level.DOME);
        turn.executeMove(Direction.LEFTUP);

    }

        @Test
    public void checkIfCanMove_withCannotMoveUp() throws Exception{
        player.setCannotMoveUp(true);
        secondWorker.setSlot(board.getSlot(2,1));
        assertTrue(player.getGod().checkIfCanMove(workerM));
    }

    @Test
    public void checkIfCanMoveInNormalCondition_withCannotMoveUp() throws Exception {
        player.setCannotMoveUp(true);
        assertTrue(player.getGod().checkIfCanMove(workerM));
    }

    @Test
    public void checkIfCanMove() throws Exception {
        secondWorker.setSlot(board.getSlot(2,1));
        assertTrue(player.getGod().checkIfCanMove(workerM));

    }

    @Test
    public void move_IndexOutOfBoundsException() throws Exception {
        workerM.setSlot(board.getSlot(4,4));
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(3,4).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);

        assertFalse(player.getGod().checkIfCanMove(workerM));

    }

    @Test (expected = InvalidBuildException.class)
    public void build_InvalidBuildException()  throws Exception{

        assertTrue(player.getGod().checkIfCanBuild(workerM));
        turn.executeBuild(Direction.LEFT);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException()  throws Exception {
        board.getSlot(1,1).setLevel(Level.DOME);
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);

    }

}