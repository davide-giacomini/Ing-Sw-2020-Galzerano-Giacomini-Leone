package Model;

import Model.Exceptions.GameAlreadyStartedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Player player;

    @Before
    public void setUp() {
        game = new Game();
        player = new Player("Ari", Color.RED);
    }

    @After
    public void tearDown() {
        //nothing to put?
    }

    @Test
    public void checkInitialParameters(){
        assertFalse(game.isGameEnded());
        assertFalse(game.isGameStarted());
        assertEquals(game.getNumberOfPlayers(), 0);
    }

    @Test
    public void addPlayer_correctAddition() throws GameAlreadyStartedException {
        game.addPlayer(player);
        assertEquals(game.getNumberOfPlayers(),1);

    }

    @Test (expected = GameAlreadyStartedException.class)
    public void addPlayer_gameAlreadyStarted() throws GameAlreadyStartedException {
        game.setStart();
        assertFalse(game.isGameEnded());
        assertTrue(game.isGameStarted());
        game.addPlayer(player);

    }

    @Test (expected = GameAlreadyStartedException.class)
    public void addPlayer_tooManyPlayers() throws GameAlreadyStartedException {
        Player p2 = new Player("A", Color.BLUE);
        Player p3 = new Player("B", Color.GREEN);
        Player p4 = new Player("C", Color.WHITE);
        game.addPlayer(player);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.addPlayer(p4);

    }

    @Test
    public void setStart(){
        game.setStart();
        assertFalse(game.isGameEnded());
        assertTrue(game.isGameStarted());
    }

    @Test
    public void setEnd(){
        game.setEnd();
        assertTrue(game.isGameEnded());
        assertFalse(game.isGameStarted());
    }

    @Test
    public void getPlayer() throws GameAlreadyStartedException {
        game.addPlayer(player);
        assertEquals(game.getNumberOfPlayers(),1);
        assertEquals(game.getPlayer(0), player);

    }

    @Test
    public void getPlayerNumber2() throws GameAlreadyStartedException {
        game.addPlayer(player);
        Player p2 = new Player("A", Color.BLUE);
        game.addPlayer(p2);
        assertEquals(game.getNumberOfPlayers(),2);
        assertEquals(game.getPlayer(1), p2);

    }

    @Test
    public void setNumberOfPlayers(){
        game.setNumberOfPlayers(1);
        assertEquals(game.getNumberOfPlayers(),1);
    }

    @Test
    public void getPlayers() throws GameAlreadyStartedException{
        Player p2 = new Player("A", Color.BLUE);
        game.addPlayer(player);
        game.addPlayer(p2);

        ArrayList<Player> listForCompare = new ArrayList<>();
        listForCompare.add(player);
        listForCompare.add(p2);

        assertEquals(game.getPlayers(), listForCompare);

    }


}