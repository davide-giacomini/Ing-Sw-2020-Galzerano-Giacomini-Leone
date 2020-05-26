package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Network.Server.ClientHandler;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameControllerTest_threePlayers {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private int numberOfPlayers = 3;

    @Mock
    ClientHandler ariannaClientMock;
    @Mock
    ClientHandler monicaClientMock;
    @Mock
    ClientHandler davideClientMock;

    private GameController controller;
    private HashMap<String, Color> mapUserColor = new HashMap<>();
    private HashMap<String, VirtualView> mapUserVirtualView = new HashMap<>();

    @Before
    public void setUp() {

        mapUserColor.put("Arianna", Color.BLUE);
        VirtualView v1 = new VirtualView("Arianna", Color.BLUE, ariannaClientMock);
        mapUserVirtualView.put("Arianna",  v1);

        mapUserColor.put("Monica", Color.YELLOW);
        VirtualView v2 = new VirtualView("Monica", Color.YELLOW, monicaClientMock);
        mapUserVirtualView.put("Monica", v2);

        mapUserColor.put("Davide", Color.WHITE);
        VirtualView v3 = new VirtualView("Davide", Color.WHITE, davideClientMock);
        mapUserVirtualView.put("Davide", v3);

        controller = new GameController(numberOfPlayers, mapUserColor, mapUserVirtualView);
    }

    @After
    public void tearDown() {
        controller.clearController();
    }

    @Test
    public void startController(){
        assertEquals(controller.getGame().getRandomPlayer(), controller.getGame().getPlayer(numberOfPlayers - 1));
        assertEquals(controller.getNumberOfPlayers(),3);
        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getViews().get(i).getUsername(), controller.getGame().getPlayer(i).getUsername());
        }
    }

    @Test
    public void setUp_correct() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.PAN);
        gods.add(GodName.HERA);
        gods.add(GodName.APOLLO);
        controller.setGods(gods, "Arianna");

        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getGame().getGods().get(i), gods.get(i));
        }

        controller.setGod(GodName.PAN);
        assertEquals(GodName.PAN, controller.getGame().getPlayer(0).getGodName());
        assertEquals(2, controller.getGame().getGods().size());
        assertEquals(1, controller.getIndexOfCurrentPlayer());

        controller.setGod(GodName.HERA);
        assertEquals(GodName.HERA, controller.getGame().getPlayer(1).getGodName());
        assertEquals(1, controller.getGame().getGods().size());
        assertEquals(2, controller.getIndexOfCurrentPlayer());

        controller.setGod(GodName.APOLLO);
        assertEquals(GodName.APOLLO, controller.getGame().getRandomPlayer().getGodName());
        assertEquals(0, controller.getGame().getGods().size());
        assertEquals(0, controller.getIndexOfCurrentPlayer());

        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getViews().get(i).getUsername(), controller.getGame().getPlayer(i).getUsername());
        }

        int[] rowsAndColumns = {0, 0, 1, 1};
        controller.setWorkers(rowsAndColumns);
        assertEquals(controller.getGame().getPlayer(0).getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(0, 0).getWorker());
        assertEquals(controller.getGame().getPlayer(0).getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(1, 1).getWorker());

        assertEquals(1, controller.getIndexOfCurrentPlayer());

        rowsAndColumns = new int[]{2, 2, 3, 3};
        controller.setWorkers(rowsAndColumns);
        assertEquals(controller.getGame().getPlayer(1).getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(2, 2).getWorker());
        assertEquals(controller.getGame().getPlayer(1).getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(3, 3).getWorker());

        assertEquals(2, controller.getIndexOfCurrentPlayer());

        rowsAndColumns = new int[]{1, 4, 4, 1};
        controller.setWorkers(rowsAndColumns);
        assertEquals(controller.getGame().getPlayer(2).getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(1, 4).getWorker());
        assertEquals(controller.getGame().getPlayer(2).getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(4, 1).getWorker());

        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void removeLosingPlayer_when_is_the_first() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.ATHENA);
        gods.add(GodName.APOLLO);
        gods.add(GodName.DEMETER);
        controller.setGods(gods, "Arianna");
        controller.setGod(GodName.ATHENA);
        controller.setGod(GodName.DEMETER);
        controller.setGod(GodName.APOLLO);

        int[] rowsAndColumns = {0, 0, 1, 1};
        controller.setWorkers(rowsAndColumns);

        rowsAndColumns = new int[]{2, 2, 3, 3};
        controller.setWorkers(rowsAndColumns);

        rowsAndColumns = new int[]{0, 1, 2, 3};
        controller.setWorkers(rowsAndColumns);

        controller.removeLosingPlayer("Arianna");

        assertTrue(controller.getGame().isActive());
        for (int i=0; i<controller.getNumberOfPlayers(); i++) {
            assertFalse(controller.getGame().getPlayer(i).cannotMoveUp());
        }
        assertEquals(2, controller.getNumberOfPlayers());
        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void removeLosingPlayer_when_is_the_last() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.ZEUS);
        gods.add(GodName.APOLLO);
        gods.add(GodName.DEMETER);
        controller.setGods(gods, "Arianna");
        controller.setGod(GodName.ZEUS);
        controller.setGod(GodName.DEMETER);
        controller.setGod(GodName.APOLLO);

        int[] rowsAndColumns = {0, 0, 1, 1};
        controller.setWorkers(rowsAndColumns);

        rowsAndColumns = new int[]{2, 2, 3, 3};
        controller.setWorkers(rowsAndColumns);

        rowsAndColumns = new int[]{0, 1, 2, 3};
        controller.setWorkers(rowsAndColumns);

        controller.setIndexOfCurrentPlayer(2);
        controller.removeLosingPlayer("Arianna");

        assertTrue(controller.getGame().isActive());
        assertEquals(2, controller.getNumberOfPlayers());
        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }
}
