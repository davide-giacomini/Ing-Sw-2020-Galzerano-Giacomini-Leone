package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class MinotaurTest {
    private Turn turn;
    private Player player;
    private Worker workerM, workerF;
    private Slot slot2, slotM,slotF;
    private Board board;
    private Player secondPlayer;
    private Worker secondWorker;
    private Game game;
    
    @Before
    public void setUp() throws Exception{
        game = new Game(3);
        board = game.getBoard();
        slotM = board.getSlot(2,2);
        slotF = board.getSlot(0,0);
        slot2 = board.getSlot(3,3);
        player = new Player("Arianna", Color.BLUE);
        secondPlayer = new Player("David", Color.WHITE);

        workerM = player.getWorker(Gender.MALE);
        workerF = player.getWorker(Gender.FEMALE);

        secondWorker = secondPlayer.getWorker(Gender.FEMALE);
        workerM.setSlot(slotM);
        workerF.setSlot(slotF);
        secondWorker.setSlot(slot2);

        player.setGod(new Minotaur(player, "Minotaur"));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_withoutSpecialMove() throws Exception{
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
    public void turn_CorrectInput_CorrectOutput_withSpecialMove() throws Exception{
        assertTrue (player.getGod().checkIfCanMove(workerM));
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(workerM));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(workerM));
        turn.executeBuild(Direction.LEFTUP);
        assertFalse(player.getGod().checkIfCanGoOn(workerM));
        assertTrue(player.getGod().validateEndTurn());


    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException_becauseOutOfBoard() throws Exception{
        secondWorker.setSlot(board.getSlot(2,4));
        workerM.setSlot(board.getSlot(2,3));
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException_becauseOccupied()
            throws Exception{
        secondWorker.setSlot(board.getSlot(2,3));
        secondWorker.build(Direction.RIGHT);
        secondWorker.build(Direction.RIGHT);
        secondWorker.build(Direction.RIGHT);
        secondWorker.build(Direction.RIGHT);
        turn.executeMove(Direction.RIGHT);
    }

    @Test
    public void checkIfCanMove_withCannotMoveUp() throws Exception{
        player.setCannotMoveUp(true);
        assertTrue(player.getGod().checkIfCanMove(workerM));

    }

    @Test
    public void checkIfCanMove() throws Exception{
        secondWorker.setSlot(board.getSlot(2,1));
        assertTrue(player.getGod().checkIfCanMove(workerM));

    }

    @Test
    public void build_IndexOutOfBoundsException_firstBuild() throws Exception{
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

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException()  throws Exception {
        board.getSlot(1,1).setLevel(Level.DOME);
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.UP);

    }




    }