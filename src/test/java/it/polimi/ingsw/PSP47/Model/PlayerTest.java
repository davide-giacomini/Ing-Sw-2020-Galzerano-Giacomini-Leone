package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Gods.Apollo;
import it.polimi.ingsw.PSP47.Model.Gods.God;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player;
    Board board;
    Game game;
    Turn turn;

    @Before
    public void setUp()  {
        game = new Game(3);
        board = game.getBoard();

        player = new Player ("Arianna", Color.BLUE);
        player.setGod(new Apollo(player, "Apollo"));
        player.getWorker(Gender.MALE).setSlot(game.getBoard().getSlot(1, 1));
        player.getWorker(Gender.FEMALE).setSlot(game.getBoard().getSlot(2, 1));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }


    @Test
    public void playerSetUp() {
        assertFalse(player.isLoosing());
        assertFalse(player.isWinning());
        assertEquals(player.getWorkersNumber(), 2);
        assertEquals(player.getWorker(Gender.MALE).getColor(), Color.BLUE);
        assertEquals(player.getWorker(Gender.FEMALE).getColor(), Color.BLUE);
    }

   /* @Test
    public void setLoosing() {
        player.setLoosing(true);
        assertTrue(player.isLoosing());
        assertFalse(player.isWinning());
        assertEquals(player.getWorkersNumber(), 2);
        assertEquals(player.getWorker(Gender.MALE),null);
        assertEquals(player.getWorker(Gender.FEMALE), null);
    }*/

  /*@Test
    public void setLoosing_withOneWorkerDeleted() {
        player.deleteWorker(player.getWorker(Gender.MALE));
        player.setLoosing(true);
        assertTrue(player.isLoosing());
        assertFalse(player.isWinning());
        assertEquals(player.getWorkersNumber(), 2);
        assertEquals(player.getWorker(Gender.MALE),null);
        assertEquals(player.getWorker(Gender.FEMALE), null);
    }*/

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
        assertEquals(player.getGodName(), GodName.APOLLO);
    }

    @Test
    public void getColor() {
        assertEquals(player.getColor(),player.getWorker(Gender.MALE).getColor());
        player.setColor(player.getWorker(Gender.MALE).getColor());
        assertEquals(player.getColor(),player.getWorker(Gender.MALE).getColor());
    }

    @Test
    public void setTurn() {
        player.setGod(new Apollo(player, "Apollo"));
        assertEquals(player.getTurn(), turn);
    }

    @Test
    public void getUsername() {
        assertEquals(player.getUsername(), "Arianna");
        assertNotEquals(player.getUsername(), "ARIANNA");
    }

    @Test
    public void getWorkerPosition(){
        Board board =  game.getBoard();
        player.putWorkerOnSlot(player.getWorker(Gender.MALE), board.getSlot(1,1));
        assertEquals(player.getWorkerPosition(player.getWorker(Gender.MALE)), board.getSlot(1,1));
    }

    @Test
    public void move()
            throws Exception {
        Board board = game.getBoard();
        player.setGod(new Apollo(player, "Apollo"));
        player.putWorkerOnSlot(player.getWorker(Gender.MALE), board.getSlot(1, 1));
        player.move(Direction.LEFTUP, player.getWorker(Gender.MALE));
        assertEquals(player.getWorker(Gender.MALE), board.getSlot(0,0).getWorker());
    }


    @Test
    public void deleteWorkers() {
        player.deleteWorkers();
        assertNull(player.getWorker(Gender.MALE).getSlot().getWorker());
        assertNull(player.getWorker(Gender.FEMALE).getSlot().getWorker());
    }



   /*@Test
    public void build() throws IndexOutOfBoundsException, InvalidDirectionException, InvalidMoveException, InvalidBuildException {
        Board board = Board.getBoard();

        player.setGod(new Apollo(player, "Apollo"));
        Turn turn = new Turn(player);
        player.setTurn(turn);

        player.putWorkerOnSlot(player.getWorker(Gender.MALE), board.getSlot(0, 1));
        player.getTurn().executeMove(Direction.RIGHT);

        player.build(Direction.LEFTUP, player.getWorker(Gender.MALE));
        assertEquals(board.getSlot(0,0).getLevel(), Level.LEVEL1);
    } */

    }