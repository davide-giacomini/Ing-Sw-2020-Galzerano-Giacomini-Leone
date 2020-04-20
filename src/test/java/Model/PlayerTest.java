package Model;

import Enumerations.Direction;
import Enumerations.Gender;
import Model.Exceptions.*;
import Model.Gods.Apollo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player;

    @Before
    public void setUp()  {
        player = new Player ("Arianna", Color.BLACK);

    }

    @After
    public void tearDown()  {
        //nothing to do?
    }

    @Test
    public void playerSetUp() {
        assertFalse(player.isLoosing());
        assertFalse(player.isWinning());
        assertEquals(player.getWorkersNumber(), 2);
        assertEquals(player.getWorker(Gender.MALE).getColor(), Color.BLACK);
        assertEquals(player.getWorker(Gender.FEMALE).getColor(), Color.BLACK);
    }

    @Test
    public void setLoosing() {
        player.setLoosing(true);
        assertTrue(player.isLoosing());
        assertFalse(player.isWinning());
        assertEquals(player.getWorkersNumber(), 2);
        assertEquals(player.getWorker(Gender.MALE),null);
        assertEquals(player.getWorker(Gender.FEMALE), null);
    }

  @Test
    public void setLoosing_withOneWorkerDeleted() {
        player.deleteWorker(player.getWorker(Gender.MALE));
        player.setLoosing(true);
        assertTrue(player.isLoosing());
        assertFalse(player.isWinning());
        assertEquals(player.getWorkersNumber(), 2);
        assertEquals(player.getWorker(Gender.MALE),null);
        assertEquals(player.getWorker(Gender.FEMALE), null);
    }

    @Test
    public void setWinning() {
        player.setWinning(true);
        assertTrue(player.isWinning());
    }

    @Test
    public void setCannotMoveUp() {
        player.setCannotMoveUp(true);
        assertTrue(player.cannotMoveUp());
    }

    @Test
    public void setGod() {
        Apollo god = new Apollo(player,"Apollo");

        player.setGod(new Apollo(player, "Apollo"));
        assertEquals(player.getGod().getName(),"Apollo");
        assertFalse(player.CanBuildDome());

    }

    @Test
    public void setTurn() throws InvalidDirectionException, GodNotSetException {
        assertEquals(player.getTurn(), null);
        player.setGod(new Apollo(player, "Apollo"));
        Turn turn = new Turn(player);
        player.setTurn(turn);
        assertEquals(player.getTurn(), turn);
    }

    @Test
    public void getUsername() throws InvalidDirectionException, GodNotSetException {
        assertEquals(player.getUsername(), "Arianna");
        assertNotEquals(player.getUsername(), "ARIANNA");
    }

    @Test
    public void deleteWorker() {
        player.deleteWorker(player.getWorker(Gender.MALE));
        assertFalse(player.isLoosing());
        assertEquals(player.getWorker(Gender.MALE),null);

        player.deleteWorker(player.getWorker(Gender.FEMALE));
        assertTrue(player.isLoosing());
    }

    @Test
    public void deleteWorker_firstFemale() {
        player.deleteWorker(player.getWorker(Gender.FEMALE));
        assertFalse(player.isLoosing());
        assertEquals(player.getWorker(Gender.FEMALE),null);

        player.deleteWorker(player.getWorker(Gender.MALE));
        assertTrue(player.isLoosing());
    }

    @Test(expected = NullPointerException.class)
    public void deleteWorker_NullPointerException() {
        player.deleteWorker(player.getWorker(Gender.MALE));
        player.deleteWorker(player.getWorker(Gender.MALE));

    }


    @Test
    public void putWorkerOnSlot() {
      Board board =  Board.getBoard();
        player.putWorkerOnSlot(player.getWorker(Gender.MALE), board.getSlot(1,1));
        assertEquals(player.getWorker(Gender.MALE), board.getSlot(1,1).getWorker());
    }

    @Test
    public void getWorkerPosition(){
        Board board =  Board.getBoard();
        player.putWorkerOnSlot(player.getWorker(Gender.MALE), board.getSlot(1,1));
        assertEquals(player.getWorkerPosition(player.getWorker(Gender.MALE)), board.getSlot(1,1));
    }

    @Test
    public void move() throws IndexOutOfBoundsException, NotReachableLevelException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        Board board = Board.getBoard();
        player.setGod(new Apollo(player, "Apollo"));
        player.putWorkerOnSlot(player.getWorker(Gender.MALE), board.getSlot(1, 1));
        player.move(Direction.LEFTUP, player.getWorker(Gender.MALE));
        assertEquals(player.getWorker(Gender.MALE), board.getSlot(0,0).getWorker());
    }

    //problem with build test to solve
   /* @Test
    public void build() throws IndexOutOfBoundsException, GodNotSetException, NotReachableLevelException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException, NoAvailableMovementsException {
        Board board = Board.getBoard();

        player.setGod(new Apollo(player, "Apollo"));
        Turn turn = new Turn(player);
        player.setTurn(turn);

        player.putWorkerOnSlot(player.getWorker(Gender.MALE), board.getSlot(0, 1));
        player.getTurn().executeMove(Direction.RIGHT);

        player.build(Direction.LEFTUP, player.getWorker(Gender.MALE));
        assertEquals(board.getSlot(0,0).getLevel(), Level.LEVEL1);
    }*/

    }