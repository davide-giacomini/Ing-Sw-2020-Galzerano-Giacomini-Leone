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

import static junit.framework.TestCase.*;

public class AtlasTest {
    private Board board;
    private Turn turn;
    private Player player;
    private Worker femaleWorker;
    private Worker maleWorker;
    private Player otherPlayer;
    private Worker otherWorker;
    private Game game;
    
    @Before
    public void setUp ()
            throws Exception {
        game = new Game(3);
        board = game.getBoard();

        player = new Player("Monica", Color.YELLOW);
        player.setGod(new Atlas(player, "Atlas"));

        otherPlayer = new Player("Arianna", Color.BLUE);
        otherWorker = otherPlayer.getWorker(Gender.FEMALE);

        maleWorker = player.getWorker(Gender.MALE);
        maleWorker.setSlot(board.getSlot(0,0));
        femaleWorker = player.getWorker(Gender.FEMALE);
        femaleWorker.setSlot(board.getSlot(2,2));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);

    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput_NotWantsToBuildDome()
            throws Exception {
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
    public void turn_CorrectInput_CorrectOutput_WantsToBuildDome()
            throws Exception {
        turn.setWantsToBuildDome(true);
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertEquals(Level.ATLAS_DOME, board.getSlot(2,1).getLevel());
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test (expected = InvalidMoveException.class)
    public void move_SlotOccupiedException()
            throws Exception {
        otherWorker.setSlot(board.getSlot(0,1));
        turn.executeMove(Direction.RIGHT);
    }

    @Test (expected = InvalidMoveException.class)
    public void move_NotReachableLevelException()
            throws Exception {
        board.getSlot(0,1).setLevel(Level.LEVEL2);
        turn.executeMove(Direction.RIGHT);
    }

    //fatto dal controller
    /*@Test (expected = InvalidMoveException.class)
    public void move_NoAvailableMovementsException()
            throws Exception {
        turn.executeMove(Direction.RIGHT);
        turn.executeMove(Direction.DOWN);
    }*/

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException()
            throws Exception {
        otherWorker.setSlot(board.getSlot(1,1));
        turn.executeMove(Direction.DOWN);
        turn.executeBuild(Direction.RIGHT);
    }

    //fatto dal controller
    /*@Test (expected = InvalidBuildException.class)
    public void build_NoAvailableBuildingsException()
            throws Exception {
        turn.executeMove(Direction.RIGHTDOWN);
        turn.executeBuild(Direction.UP);
        turn.executeBuild(Direction.DOWN);
    }*/

    @Test (expected = InvalidBuildException.class)
    public void build_WrongBuildOrMoveException()
            throws Exception {
        turn.executeBuild(Direction.RIGHTDOWN);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_SlotOccupiedException_secondCheck()
            throws Exception {
        otherWorker.setSlot(board.getSlot(1,1));
        turn.executeMove(Direction.DOWN);
        turn.wantsToBuildDome();
        player.canBuildDome();
        turn.executeBuild(Direction.RIGHT);
    }

    @Test (expected = InvalidBuildException.class)
    public void build_WantsToBuildDome_slotOccupied()
            throws Exception {
        turn.setWantsToBuildDome(true);
        board.getSlot(2,1).setLevel(Level.DOME);
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }
}
