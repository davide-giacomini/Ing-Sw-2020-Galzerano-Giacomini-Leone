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
    private Worker maleWorker, femaleWorker;
    private Board board;
    private Player secondPlayer;
    private Worker otherWorker;
    private Game game;
    
    @Before
    public void setUp() throws Exception{
        game = new Game(3);
        board = game.getBoard();
        
        player = new Player("Arianna", Color.BLUE);
        player.setGod(new Minotaur(player, "Minotaur"));
        secondPlayer = new Player("David", Color.WHITE);

        maleWorker = player.getWorker(Gender.MALE);
        femaleWorker = player.getWorker(Gender.FEMALE);
        otherWorker = secondPlayer.getWorker(Gender.FEMALE);
        maleWorker.setSlot(board.getSlot(2,2));
        femaleWorker.setSlot(board.getSlot(0,0));
        otherWorker.setSlot(board.getSlot(3,3));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_withoutSpecialMove() throws Exception{
        assertTrue (player.getGod().checkIfCanMove(maleWorker));
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.LEFTUP);
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());


    }

    @Test
    public void turn_CorrectInput_CorrectOutput_withSpecialMove() throws Exception{
        assertTrue (player.getGod().checkIfCanMove(maleWorker));
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.LEFTUP);
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());


    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException_becauseOutOfBoard() throws Exception{
        otherWorker.setSlot(board.getSlot(2,4));
        maleWorker.setSlot(board.getSlot(2,3));
        turn.executeMove(Direction.RIGHT);
    }

    @Test
    public void build_IndexOutOfBoundsException_firstBuild() throws Exception{
        maleWorker.setSlot(board.getSlot(4,4));
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(3,4).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);

        assertFalse(player.getGod().checkIfCanMove(maleWorker));

    }


    @Test
    public void checkIfCanMove_withCannotMoveUp() throws Exception{
        player.setCannotMoveUp(true);
        assertTrue(player.getGod().checkIfCanMove(maleWorker));

    }

    @Test
    public void checkIfCanMove() {
        otherWorker.setSlot(board.getSlot(2,1));
        assertTrue(player.getGod().checkIfCanMove(maleWorker));

    }

    }