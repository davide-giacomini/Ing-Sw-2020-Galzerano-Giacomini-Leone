package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BoardTest {
    private Board board;
    private Player player;
    Game game;

    @Before
    public void setUp() {
        game = new Game(3);
        board = game.getBoard();
        player = new Player("Arianna", Color.BLUE);
    }

    @After
    public void tearDown() {
        board = null;
    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutput_Left() throws InvalidDirectionException {

        Slot slot = board.getNearbySlot(Direction.LEFT,board.getSlot(3,3));
        assertSame(board.getSlot(3,2), slot);
    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutput_Right() throws InvalidDirectionException{
        Slot slot = board.getNearbySlot(Direction.RIGHT,board.getSlot(3,3));
        assertSame(board.getSlot(3,4), slot);

    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputUp() throws InvalidDirectionException{

        Slot slot = board.getNearbySlot(Direction.UP,board.getSlot(3,3));
        assertSame(board.getSlot(2,3), slot);

    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputDown() throws InvalidDirectionException{
        Slot slot = board.getNearbySlot(Direction.DOWN,board.getSlot(3,3));
        assertSame(board.getSlot(4,3), slot);

    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputLeftUp() throws InvalidDirectionException{
            Slot slot = board.getNearbySlot(Direction.LEFTUP,board.getSlot(3,3));
            assertSame(board.getSlot(2,2), slot);

    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputRightUp() throws InvalidDirectionException{
            Slot slot = board.getNearbySlot(Direction.RIGHTUP,board.getSlot(3,3));
            assertSame(board.getSlot(2,4), slot);

    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputLeftDown() throws InvalidDirectionException{
            Slot slot = board.getNearbySlot(Direction.LEFTDOWN,board.getSlot(3,3));
            assertSame(board.getSlot(4,2), slot);

    }

    @Test
    public void getNearbySlot_CorrectInput_CorrectOutputRightDown() throws InvalidDirectionException{
            Slot slot = board.getNearbySlot(Direction.RIGHTDOWN,board.getSlot(3,3));
            assertSame(board.getSlot(4,4), slot);

    }

    @Test
    public void clearBoard() {
        Worker w = player.getWorker(Gender.MALE);

        board.getSlot(1,1).setLevel(Level.DOME);
        board.getSlot(3,3).setWorker(w);

        board.clearBoard();

        assertNull(board.getSlot(3,3).getWorker());
        assertEquals(board.getSlot(1,1).getLevel(),Level.GROUND);
    }
}
