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

public class TritonTest {
    private Board board;
    private Turn turn;
    private Player player;
    private Worker femaleWorker;
    private Worker maleWorker;
    private Player otherPlayer;
    private Worker otherWorker;
    private Game game;

    @Before
    public void setUp () {
        game =new Game(2);
        board = game.getBoard();

        player = new Player("Monica", Color.PURPLE);
        player.setGod(new Triton(player, "Triton"));

        otherPlayer = new Player("Marianna", Color.GREEN);
        otherWorker = otherPlayer.getWorker(Gender.FEMALE);

        maleWorker = player.getWorker(Gender.MALE);
        maleWorker.setSlot(board.getSlot(1,1));
        femaleWorker = player.getWorker(Gender.FEMALE);
        femaleWorker.setSlot(board.getSlot(4,4));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.FEMALE);
    }

    @After
    public void tearDown() {
        game.getBoard().clearBoard();
    }

    @Test ()
    public void move_not_in_perimeter_slot() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        turn.executeMove(Direction.LEFTUP);
        assertFalse(player.getGod().validateEndTurn());
        assertEquals(1, player.getGod().getMAX_MOVEMENTS());
        turn.executeBuild(Direction.RIGHT);
        assertTrue(player.getGod().validateEndTurn());
    }

    @Test
    public void move_in_a_perimeter_slot() throws InvalidMoveException, InvalidDirectionException, InvalidBuildException {
        turn.executeMove(Direction.UP);
        assertEquals(2, player.getGod().getMAX_MOVEMENTS());
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        turn.executeMove(Direction.UP);
        assertEquals(3, player.getGod().getMAX_MOVEMENTS());
        turn.executeMove(Direction.LEFT);
        assertEquals(3, player.getGod().getMAX_MOVEMENTS());
        assertFalse(player.getGod().validateEndTurn());
        assertTrue(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
        turn.executeBuild(Direction.LEFT);
        assertTrue(player.getGod().validateEndTurn());
        assertFalse(player.getGod().checkIfCanGoOn(player.getWorker(Gender.FEMALE)));
    }

    @Test (expected = InvalidMoveException.class)
    public void build_before_moving() throws InvalidBuildException, InvalidDirectionException, InvalidMoveException {
        turn.executeMove(Direction.LEFT);
        turn.executeBuild(Direction.LEFT);
        turn.executeMove(Direction.UP);
    }

}
