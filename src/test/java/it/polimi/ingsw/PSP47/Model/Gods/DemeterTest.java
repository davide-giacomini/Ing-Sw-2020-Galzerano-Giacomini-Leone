package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class DemeterTest {
    private Turn turn;
    private Player player;
    private Worker maleWorker;
    private Worker femaleWorker;
    private Board board;
    private Player secondPlayer;

    private Game game;
    
    
    @Before
    public void setUp()
            throws Exception {
        game = new Game(3);
        board = game.getBoard();
        
        player = new Player("Arianna", Color.BLUE);
        player.setGod(new Demeter(player, "Demeter"));
        secondPlayer = new Player("David", Color.YELLOW);
        
        maleWorker = player.getWorker(Gender.MALE);
        femaleWorker = player.getWorker(Gender.FEMALE);
        maleWorker.setSlot(board.getSlot(3,3));
        femaleWorker.setSlot(board.getSlot(4,4));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        game.getBoard().clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_OneBuild()
            throws Exception {

        assertTrue (player.getGod().checkIfCanMove(maleWorker));
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.LEFTUP);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());

    }

    @Test
    public void turn_CorrectInput_CorrectOutput_TwoBuild()
            throws Exception {

        assertTrue (player.getGod().checkIfCanMove(maleWorker));
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.LEFTUP);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));

        assertTrue(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.UP);
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());

    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException()
            throws Exception {
        turn.executeMove(Direction.RIGHTDOWN);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NotReachableLevelException()
            throws Exception {

        board.getSlot(4,3).setLevel(Level.LEVEL2);
        turn.executeMove(Direction.DOWN);
    }

    //fatto dal controller
   /* @Test (expected = InvalidMoveException.class)
    public void move_NoAvailableMovementsException()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
    }*/

    @Test (expected = InvalidBuildException.class)
    public void build_WrongBuildOrMoveException()
            throws Exception {
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = InvalidBuildException.class)
    public void second_build_WrongBuildOrMoveException()
            throws Exception {

        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.LEFTUP);
        turn.executeBuild(Direction.LEFTUP);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_NoAvailableBuildingsException()
            throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.DOWN);
        turn.executeBuild(Direction.UP);
        assertFalse(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.DOWN);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException()
            throws Exception {
        turn.executeMove(Direction.DOWN);
        turn.executeBuild(Direction.RIGHT);
    }

    @Test
    public void checkIfCanBuild_firstBuild_OutputFalse()
            throws Exception {
        turn.executeMove(Direction.RIGHT);
        board.getSlot(2,4).setLevel(Level.DOME);
        board.getSlot(2,3).setLevel(Level.DOME);
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);
        board.getSlot(4,4).setLevel(Level.DOME);
        assertFalse(player.getGod().checkIfCanBuild(maleWorker));

    }

    @Test
    public void checkIfCanBuild_SecondBuild_OutputFalse()
            throws Exception {
        turn.executeMove(Direction.RIGHT);
        turn.executeBuild(Direction.LEFT);
        board.getSlot(2,4).setLevel(Level.DOME);
        board.getSlot(2,3).setLevel(Level.DOME);
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);
        board.getSlot(4,4).setLevel(Level.DOME);
        assertFalse(player.getGod().checkIfCanBuild(maleWorker));

    }

}