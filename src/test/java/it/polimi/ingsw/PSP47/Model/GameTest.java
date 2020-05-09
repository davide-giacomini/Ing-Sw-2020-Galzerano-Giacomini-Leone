package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    Board board;
    private Player player, secondPlayer;
    private int numberOfPlayers = 2;

    @Before
    public void setUp() {
        game = new Game(numberOfPlayers);
        player = new Player("Ari", Color.RED);
        secondPlayer = new Player("Anna", Color.GREEN);
        game.addPlayer(player);
        board = game.getBoard();
    }

    @After
    public void tearDown() {
        //nothing to put?
    }

    @Test
    public void checkNumberOfPlayers() {
        assertEquals(game.getNumberOfPlayers(), numberOfPlayers);
    }

    @Test
    public void getPlayer() {
        game.addPlayer(player);
        assertEquals(game.getPlayer(0), player);
        assertEquals(game.getPlayer("Ari"), player);

    }

    @Test
    public void getPlayers(){
        game.addPlayer(secondPlayer);

        ArrayList<Player> listForCompare = new ArrayList<>();
        listForCompare.add(player);
        listForCompare.add(secondPlayer);

        assertEquals(game.getPlayers(), listForCompare);
        game.removePlayer(player);
        game.removePlayer(secondPlayer);
    }

    @Test
    public void setNumberOfPlayers(){
        game.setNumberOfPlayers(1);
        assertEquals(game.getNumberOfPlayers(),1);
    }

    @Test
    public void putRandomInLastPosition_WhenHeWasFirst_twoPlayers() {
        Player p2 = new Player("Moni", Color.RED);
        game.addPlayer(p2);
        game.setRandomPlayer(player);
        game.putRandomAtLastPosition();
        assertEquals(player, game.getPlayer(1));
    }

    @Test
    public void putRandomInLastPosition_WhenHeWasAlreadyHere_twoPlayers() {
        Player p2 = new Player("Moni", Color.RED);
        game.addPlayer(p2);
        game.setRandomPlayer(p2);
        game.putRandomAtLastPosition();
        assertEquals(p2, game.getPlayer(1));
    }

    @Test
    public void putRandomInLastPosition_WhenHeWasFirst_threePlayers() {
        game.setNumberOfPlayers(3);
        Player p2 = new Player("Moni", Color.RED);
        game.addPlayer(p2);
        Player p3 = new Player("David", Color.CYAN);
        game.addPlayer(p3);
        game.setRandomPlayer(player);
        game.putRandomAtLastPosition();
        assertEquals(player, game.getPlayer(2));
    }

    @Test
    public void putRandomInLastPosition_WhenHeWasAlreadyHere_threePlayers() {
        game.setNumberOfPlayers(3);
        Player p2 = new Player("Moni", Color.RED);
        game.addPlayer(p2);
        Player p3 = new Player("David", Color.CYAN);
        game.addPlayer(p3);
        int size = game.getPlayers().size();
        game.setRandomPlayer(p3);
        game.putRandomAtLastPosition();
        assertEquals(p3, game.getPlayer(2));
    }

    @Test
    public void putRandomInLastPosition_WhenHeWasTheSecondOne_threePlayers() {
        game.setNumberOfPlayers(3);
        Player p2 = new Player("Moni", Color.RED);
        game.addPlayer(p2);
        Player p3 = new Player("David", Color.CYAN);
        game.addPlayer(p3);
        game.setRandomPlayer(p2);
        assertEquals(p2, game.getRandomPlayer());
        game.putRandomAtLastPosition();
        assertEquals(p2, game.getPlayer(2));
    }

    @Test
    public void randomOrder() {
        game.setNumberOfPlayers(3);
        Player p2 = new Player("Moni", Color.RED);
        game.addPlayer(p2);
        Player p3 = new Player("David", Color.CYAN);
        game.addPlayer(p3);
        game.createNewPlayersList();
        assertEquals(game.getPlayers().size(), 3);
        assertSame(player, game.getPlayer("Ari"));
        assertSame(p2, game.getPlayer("Moni"));
        assertSame(p3, game.getPlayer("David"));
    }

    @Test
    public void setGods() {
        ArrayList<GodName> gods = new ArrayList<>(2);
        gods.add(GodName.APOLLO);
        gods.add(GodName.PAN);
        game.setGods(gods);
        ArrayList<GodName> newGods = game.getGods();
        assertEquals(gods, newGods);
    }

}