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

public class HephaestusTest {

    private Turn turn;
    private Player player;
    private Worker worker;
    private Board board;
    private Player secondPlayer;
    private Worker secondWorker;
    private Game game;
    
    @Before
    public void setUp() throws Exception{
        game = new Game(3);
        board = game.getBoard();

        player = new Player("Arianna", Color.BLUE);
        secondPlayer = new Player("David", Color.YELLOW);

        worker = player.getWorker(Gender.MALE);
        secondWorker = player.getWorker(Gender.FEMALE);
        worker.setSlot(board.getSlot(3,3));
        secondWorker.setSlot(board.getSlot(4,4));

        player.setGod(new Hephaestus(player, "Hephaestus"));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);

    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_oneBuild()  throws Exception{
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
    public void turn_CorrectInput_CorrectOutput_twoBuild()  throws Exception {
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

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException() throws Exception {
        turn.executeMove(Direction.RIGHTDOWN);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NotReachableLevelException() throws  Exception {
        secondWorker.build(Direction.LEFT);
        secondWorker.build(Direction.LEFT);
        turn.executeMove(Direction.DOWN);
    }

    //fatto dal controller
   /* @Test (expected = InvalidMoveException.class)
    public void move_NoAvailableMovementsException() throws  Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeMove(Direction.RIGHTUP);
    }*/

    @Test (expected = InvalidBuildException.class)
    public void build_WrongBuildOrMoveException() throws  Exception{
        turn.executeBuild(Direction.UP);
    }

    @Test (expected = InvalidBuildException.class)
    public void second_build_WrongBuildOrMoveException() throws  Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.RIGHT);
        turn.executeBuild(Direction.LEFTUP);
    }

    //fatto dal controller
    /*@Test (expected = InvalidBuildException.class)
    public void build_NoAvailableBuildingsException() throws  Exception{
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.DOWN);
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.getGod().checkIfCanBuild(worker));
        turn.executeBuild(Direction.DOWN);
    }*/

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException() throws Exception{
        turn.executeMove(Direction.DOWN);
        turn.executeBuild(Direction.RIGHT);
    }

    @Test
    public void checkIfCanBuild_firstBuild_OutputFalse()  throws Exception{
        turn.executeMove(Direction.RIGHT);
        board.getSlot(2,4).setLevel(Level.DOME);
        board.getSlot(2,3).setLevel(Level.DOME);
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);
        board.getSlot(4,4).setLevel(Level.DOME);
        assertFalse(player.getGod().checkIfCanBuild(worker));

    }

    @Test
    public void checkIfCanBuild_SecondBuild_OutputFalse() throws Exception {
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