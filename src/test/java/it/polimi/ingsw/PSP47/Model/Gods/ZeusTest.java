package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZeusTest {
    private Turn turn;
    private Player player;
    private Worker maleWorker;
    private Worker femaleWorker;
    private Board board;
    private Player secondPlayer;
    private Worker secondWorker;

    private Game game;


    @Before
    public void setUp() {
        game = new Game(2);
        board = game.getBoard();

        player = new Player("Moni", Color.RED);
        player.setGod(new Zeus(player, "Zeus"));
        secondPlayer = new Player("Ari", Color.YELLOW);

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
    public void build_not_here() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        assertFalse(player.getGod().validateEndTurn());
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.MALE)));
        turn.executeMove(Direction.RIGHT);
        assertFalse(player.getGod().validateEndTurn());
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.MALE)));
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        assertFalse(player.getGod().checkIfCanGoOn(player.getWorker(Gender.MALE)));
        assertEquals(Level.LEVEL1, board.getSlot(2,4).getLevel());
        assertTrue(board.getSlot(3,4).isWorkerOnSlot());
    }

    @Test
    public void build_here_level1() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.HERE);
        assertTrue(board.getSlot(3,2).isWorkerOnSlot());
        assertEquals(Level.LEVEL1, board.getSlot(3,2).getLevel());
    }

    @Test
    public void build_here_level2() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        board.getSlot(3,2).setLevel(Level.LEVEL1);
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.HERE);
        assertTrue(board.getSlot(3,2).isWorkerOnSlot());
        assertEquals(Level.LEVEL2, board.getSlot(3,2).getLevel());
    }

}
