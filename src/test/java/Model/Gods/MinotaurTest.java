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

public class MinotaurTest {
    private Turn turn;
    private Player player;
    private Worker workerM, workerF;
    private Slot slot1, slot2,slotF;
    private Board board;
    private Player secondPlayer;
    private Worker secondWorker;

    @Before
    public void setUp() throws Exception {
        board = Board.getBoard();
        slot1 = board.getSlot(2,2);
        slot2 = board.getSlot(3,3);
        slotF = board.getSlot(0,0);

        player = new Player("Arianna", Color.BLUE);
        secondPlayer = new Player("David", Color.BLACK);

        workerM = player.getWorker(Gender.MALE);
        workerF = player.getWorker(Gender.FEMALE);

        secondWorker = secondPlayer.getWorker(Gender.FEMALE);
        workerM.setSlot(slot1);
        workerF.setSlot(slotF);
        secondWorker.setSlot(slot2);

        player.setGod(new Minotaur(player, "Minotaur"));

        turn = new Turn(player);
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_withoutSpecialMove()  throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException{

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
    public void turn_CorrectInput_CorrectOutput_withSpecialMove()  throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException{

        assertTrue (player.getGod().checkIfCanMove(workerM));
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(workerM));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(workerM));
        turn.executeBuild(Direction.LEFTUP);
        assertFalse(player.getGod().checkIfCanGoOn(workerM));
        assertTrue(player.getGod().validateEndTurn());


    }

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException_becauseOutOfBoard() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        secondWorker.setSlot(board.getSlot(2,4));
        workerM.setSlot(board.getSlot(2,3));
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = SlotOccupiedException.class)
    public void move_SlotOccupiedException_becauseOccupied() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException {
        secondWorker.setSlot(board.getSlot(2,3));
        secondWorker.build(Direction.RIGHT);
        secondWorker.build(Direction.RIGHT);
        secondWorker.build(Direction.RIGHT);
        secondWorker.build(Direction.RIGHT);
        turn.executeMove(Direction.RIGHT);
    }

    @Test
    public void checkIfCanMove_withCannotMoveUp() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException,NoAvailableBuildingsException {
        player.setCannotMoveUp(true);
        assertTrue(player.getGod().checkIfCanMove(workerM));

    }

    @Test
    public void checkIfCanMove() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException,NoAvailableBuildingsException {
        secondWorker.setSlot(board.getSlot(2,1));
        assertTrue(player.getGod().checkIfCanMove(workerM));

    }

    @Test
    public void build_IndexOutOfBoundsException_firstBuild() throws SlotOccupiedException, InvalidDirectionException, NotReachableLevelException, NoAvailableMovementsException, WrongBuildOrMoveException, NoAvailableBuildingsException {
        workerM.setSlot(board.getSlot(4,4));
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(3,4).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);
        /*
        board.getSlot(2,4).setLevel(Level.DOME);
        board.getSlot(2,3).setLevel(Level.DOME);


        board.getSlot(4,2).setLevel(Level.DOME);
        board.getSlot(2,2).setLevel(Level.DOME);
        board.getSlot(3,2).setLevel(Level.DOME);*/

        assertFalse(player.getGod().checkIfCanMove(workerM));

    }




    }