package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Board;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Game;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Turn;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

        /*
                              ------------
                                CASE ONE
                              ------------
                
                0           1         2          3          4
            ------------------------------------------------------
           | opponent |          |          |          |          |
        0  |  worker  |          |          |          |          |
           |   MALE   |          |          |          |          |
           |  GROUND  |          |          |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        1  |          |  worker  |          |          |          |
           |          |   MALE   |          |          |          |
           |          |  LEVEL2  |  LEVEL3  |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        2  |          |          |          |          |          |
           |          |          |          |          |          |
           |          |   DOME   |          |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        3  |          |          |          |          |          |
           |          |          |          |          |          |
           |          |          |          |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        4  |          |          |          |          |          |
           |          |          |          |          |          |
           |          |          |          |          |          |
            ------------------------------------------------------
            
                              ------------
                                CASE TWO
                              ------------
                              
               0           1         2          3          4
            ------------------------------------------------------
           | opponent |          |          |          |          |
        0  |  worker  |          |          |          |          |
           |   MALE   |          |          |          |          |
           |  GROUND  |          |          |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        1  |          |  worker  |          |          |          |
           |          |   MALE   |          |          |          |
           |          |  LEVEL2  |  LEVEL3  |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        2  |          |          |          |          |          |
           |          |          |          |          |          |
           |          |   DOME   |          |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        3  |          |          |          |          |          |
           |          |          |          |          |          |
           |          |          |          |          |          |
            ------------------------------------------------------
           |          |          |          |          |          |
        4  |          |          |          |          |          |
           |          |          |          |          |          |
           |          |          |          |          |          |
            ------------------------------------------------------
            
         */

public class PrometheusTest {
    private Player player;
    private Board board;
    private Player opponentPlayer;
    private Turn turn;
    private Game game;
    
    
    @Before
    public void setUp() throws Exception {
        game = new Game(3);
        board = game.getBoard();

        player = new Player("player", Color.WHITE);
        player.setGod(new Prometheus(player, "Prometheus"));
        opponentPlayer = new Player("opponent player", Color.BLUE);
        opponentPlayer.getWorker(Gender.MALE).setSlot(game.getBoard().getSlot(0, 0));

        player.getWorker(Gender.MALE).setSlot(game.getBoard().getSlot(1, 1));
        game.getBoard().getSlot(0, 0).setLevel(Level.GROUND);
        player.getWorker(Gender.MALE).getSlot().setLevel(Level.LEVEL2);
        game.getBoard().getSlot(1, 2).setLevel(Level.LEVEL3);
        game.getBoard().getSlot(2, 1).setLevel(Level.DOME);

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }
    
    @After
    public void tearDown() throws Exception {
        board.clearBoard();
    }
    
    @Test
    public void move_NumberOfBuildingsZero_OutputTrue() throws Exception {
        assertFalse(((Prometheus) player.getGod()).moveThenBuild());
        assertTrue(player.move(Direction.RIGHT, player.getWorker(Gender.MALE)));
        assertTrue(((Prometheus) player.getGod()).moveThenBuild());
        assertEquals(player.getWorker(Gender.MALE), game.getBoard().getSlot(1, 2).getWorker());
    }

    
    @Test
    public void move_NumberOfBuildingsOne_OutputFalse() throws Exception {
        assertEquals(turn.getNumberOfMovements(), 0);
        assertEquals(turn.getNumberOfBuildings(), 0);
        assertFalse(player.getGod().checkOrderOfActions(Action.MOVE));
        turn.executeBuild(Direction.LEFT);
        assertEquals(turn.getNumberOfBuildings(), 1);
        assertFalse(player.move(Direction.UP, player.getWorker(Gender.MALE)));
        assertFalse(((Prometheus) player.getGod()).moveThenBuild());
    }
    
    @Test
    public void move_NumberOfBuildingsTwo_WrongBuildOrMoveException() throws Exception {
        turn.executeBuild(Direction.LEFT);
        turn.executeMove(Direction.UP);
        turn.executeBuild(Direction.DOWN);
        assertEquals(turn.getNumberOfBuildings(), 2);
    }
    
    @Test
    public void build_CorrectOutput() throws Exception {
        turn.executeBuild(Direction.LEFT);
        assertEquals(game.getBoard().getSlot(1, 0).getLevel(), Level.LEVEL1);
    }
    
    @Test(expected = InvalidBuildException.class)
    public void build_NumberOfBuildingsOne_numberOfMovementsZero_WrongBuildOrMoveException() throws Exception {
        turn.executeBuild(Direction.LEFT);
        turn.executeBuild(Direction.UP);
    }
    
    @Test(expected = InvalidBuildException.class)
    public void build_MoveThenBuildTrue_NumberOfBuildingsOne_WrongBuildOrMoveException() throws Exception {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.DOWN);
        turn.executeBuild(Direction.DOWN);
    }

    
    @Test
    public void resetParameters_correctOutput() throws Exception {
        turn.executeMove(Direction.LEFT);
        player.getGod().resetParameters();
        assertFalse(((Prometheus) player.getGod()).moveThenBuild());
    }
    
    @Test
    public void checkIfCanGoOn_NotBlocked_OutputTrue() throws Exception {
        assertTrue(((Prometheus) player.getGod()).checkIfCanGoOn(player.getWorker(Gender.MALE)));
    }
    
    @Test
    public void checkIfCanGoON_BlockedFirstMove_OutputFalse() throws Exception {
        game.getBoard().getSlot(0, 1).setLevel(Level.DOME);
        game.getBoard().getSlot(0, 2).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 0).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 2).setLevel(Level.DOME);
        game.getBoard().getSlot(2, 0).setLevel(Level.DOME);
        game.getBoard().getSlot(2, 2).setLevel(Level.DOME);
        assertFalse(((Prometheus) player.getGod()).checkIfCanGoOn(player.getWorker(Gender.MALE)));
    }
    
    @Test
    public void checkIfCanGoON_MoveThenCannotBuild_OutputFalse() throws Exception {
        turn.executeMove(Direction.UP);
        game.getBoard().getSlot(0, 2).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 0).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 1).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 2).setLevel(Level.DOME);
        assertFalse(((Prometheus) player.getGod()).checkIfCanGoOn(player.getWorker(Gender.MALE)));
    }
    
    @Test
    public void checkIfCanGoON_BuildThenCannotMove_OutputFalse() throws Exception {
        turn.executeBuild(Direction.RIGHT);
        game.getBoard().getSlot(0, 1).setLevel(Level.DOME);
        game.getBoard().getSlot(0, 2).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 0).setLevel(Level.DOME);
        game.getBoard().getSlot(2, 0).setLevel(Level.DOME);
        game.getBoard().getSlot(2, 2).setLevel(Level.DOME);
        assertFalse(((Prometheus) player.getGod()).checkIfCanGoOn(player.getWorker(Gender.MALE)));
    }
    
    @Test
    public void checkIfCanGoON_BuildThenMoveThenCannotBuild_OutputFalse() throws Exception {
        turn.executeBuild(Direction.RIGHT);
        turn.executeMove(Direction.UP);
        game.getBoard().getSlot(0, 2).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 0).setLevel(Level.DOME);
        game.getBoard().getSlot(1, 1).setLevel(Level.DOME);
        assertFalse(((Prometheus) player.getGod()).checkIfCanGoOn(player.getWorker(Gender.MALE)));
    }
    
    @Test
    public void checkIfCanGoON_BuildThenMoveThenBuild_OutputFalse() throws Exception {
        turn.executeBuild(Direction.RIGHT);
        turn.executeMove(Direction.UP);
        turn.executeBuild(Direction.DOWN);
        assertFalse(((Prometheus) player.getGod()).checkIfCanGoOn(player.getWorker(Gender.MALE)));
    }
    
    @Test
    public void validateEndTurn_NormalConditions_OutputTrue() throws Exception {
        turn.executeMove(Direction.UP);
        turn.executeBuild(Direction.DOWN);
        assertTrue(((Prometheus) player.getGod()).validateEndTurn());
    }
    
    @Test
    public void validateEndTurn_PrometheusConditions_OutputTrue() throws Exception {
        turn.executeBuild(Direction.LEFT);
        turn.executeMove(Direction.UP);
        turn.executeBuild(Direction.DOWN);
        assertTrue(((Prometheus) player.getGod()).validateEndTurn());
    }
    
    @Test
    public void validateEndTurn_OneMove_OutputFalse() throws Exception {
        turn.executeMove(Direction.UP);
        assertFalse(((Prometheus) player.getGod()).validateEndTurn());
    }
    
    @Test
    public void validateEndTurn_BuildThenMove_OutputFalse() throws Exception {
        turn.executeBuild(Direction.LEFT);
        turn.executeMove(Direction.UP);
        assertFalse(((Prometheus) player.getGod()).validateEndTurn());
    }
    
    @Test
    public void validateEndTurn_OneBuild_OutputFalse() throws Exception {
        turn.executeBuild(Direction.LEFT);
        assertFalse(((Prometheus) player.getGod()).validateEndTurn());
    }
    
    @Test
    public void validateEndTurn_DoNothing_OutputFalse() throws Exception {
        assertFalse(((Prometheus) player.getGod()).validateEndTurn());
    }
}