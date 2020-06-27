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

public class HestiaTest {
    private Turn turn;
    private Player player;
    private Worker maleWorker;
    private Worker femaleWorker;
    private Board board;
    private Player secondPlayer;

    private Game game;

    @Before
    public void setUp() {
        game = new Game(2);
        board = game.getBoard();

        player = new Player("Arianna", Color.WHITE);
        player.setGod(new Hestia(player, "Hestia"));
        secondPlayer = new Player("Monica", Color.CYAN);

        maleWorker = player.getWorker(Gender.MALE);
        femaleWorker = player.getWorker(Gender.FEMALE);
        maleWorker.setSlot(board.getSlot(2,2));
        femaleWorker.setSlot(board.getSlot(4,4));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.FEMALE);
    }

    @After
    public void tearDown() {
        game.getBoard().clearBoard();
    }

    @Test (expected = InvalidBuildException.class)
    public void second_build_in_perimeter_slot() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        turn.executeMove(Direction.UP);
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().validateEndTurn());
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        turn.executeBuild(Direction.DOWN);
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        assertTrue(player.getGod().validateEndTurn());
        assertEquals(Level.GROUND, board.getSlot(4,4).getLevel());
        assertEquals(1, turn.getNumberOfBuildings());
    }

    @Test
    public void second_build_not_in_perimeter_slot() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        turn.executeMove(Direction.LEFTUP);
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        turn.executeBuild(Direction.UP);
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        assertTrue(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.LEFT);
        assertFalse(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        assertTrue(player.getGod().validateEndTurn());
        assertEquals(Level.LEVEL1, board.getSlot(3,2).getLevel());
        assertEquals(Level.LEVEL1, board.getSlot(2,3).getLevel());
        assertEquals(2, turn.getNumberOfBuildings());
    }

    @Test (expected = InvalidBuildException.class)
    public void build_in_an_occupied_slot() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        turn.executeMove(Direction.LEFTUP);
        turn.executeBuild(Direction.LEFTUP);
        assertTrue(board.getSlot(2,2).isWorkerOnSlot());
        assertEquals(Level.GROUND, board.getSlot(2,2).getLevel());
    }
}
