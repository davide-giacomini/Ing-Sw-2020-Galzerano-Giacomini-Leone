package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Gods.God;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Network.Server.ClientHandler;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GameControllerTest_twoPlayers {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private int numberOfPlayers = 2;

    @Mock
    ClientHandler ariannaClientMock;
    @Mock
    ClientHandler monicaClientMock ;

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

        controller = new GameController(numberOfPlayers, mapUserColor, mapUserVirtualView);
    }

    @After
    public void tearDown() {
        controller.clearController();
    }

    @Test
    public void startController(){
        assertEquals(controller.getGame().getRandomPlayer(), controller.getGame().getPlayer(numberOfPlayers - 1));
        assertEquals(controller.getNumberOfPlayers(),2);
        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getViews().get(i).getUsername(), controller.getGame().getPlayer(i).getUsername());
        }
    }

    @Test
    public void setGods_correct() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.ARTEMIS);
        gods.add(GodName.TRITON);
        controller.setGods(gods, "Monica");

        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getGame().getGods().get(i), gods.get(i));
        }

        controller.setGod(GodName.ARTEMIS);
        assertEquals(GodName.ARTEMIS, controller.getGame().getPlayer(0).getGodName());
        assertEquals(1, controller.getGame().getGods().size());
        assertEquals(1, controller.getIndexOfCurrentPlayer());

        controller.setGod(GodName.TRITON);

        assertEquals(GodName.TRITON, controller.getGame().getRandomPlayer().getGodName());
        assertEquals(0, controller.getGame().getGods().size());
        assertEquals(0, controller.getIndexOfCurrentPlayer());

        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getViews().get(i).getUsername(), controller.getGame().getPlayer(i).getUsername());
        }
    }

    @Test
    public void setGods_wrong_input() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.HESTIA);
        gods.add(GodName.HERA);
        controller.setGods(gods, "Monica");
        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getGame().getGods().get(i), gods.get(i));
        }

        controller.setGod(GodName.WRONGGODNAME);
        assertNull(controller.getGame().getPlayer(0).getGodName());
        assertEquals(2, controller.getGame().getGods().size());
        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void setGods_not_available_god() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.ZEUS);
        gods.add(GodName.DEMETER);
        controller.setGods(gods, "Monica");
        for (int i=0; i<numberOfPlayers; i++) {
            assertEquals(controller.getGame().getGods().get(i), gods.get(i));
        }

        controller.setGod(GodName.APOLLO);
        assertNull(controller.getGame().getPlayer(0).getGodName());
        assertEquals(2, controller.getGame().getGods().size());
        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void setWorkers_correct() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.APOLLO);
        gods.add(GodName.ZEUS);
        controller.setGods(gods, "Monica");
        controller.setGod(GodName.ZEUS);
        controller.setGod(GodName.APOLLO);

        int[] rowsAndColumns = {0, 0, 1, 1};
        controller.setWorkers(rowsAndColumns);
        assertEquals(controller.getGame().getPlayer(0).getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(0, 0).getWorker());
        assertEquals(controller.getGame().getPlayer(0).getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(1, 1).getWorker());

        assertEquals(1, controller.getIndexOfCurrentPlayer());

        rowsAndColumns = new int[]{2, 2, 3, 3};
        controller.setWorkers(rowsAndColumns);
        assertEquals(controller.getGame().getPlayer(1).getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(2, 2).getWorker());
        assertEquals(controller.getGame().getPlayer(1).getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(3, 3).getWorker());

        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }


        @Test
    public void setWorkers_out_of_bounds() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.MINOTAUR);
        gods.add(GodName.HEPHAESTUS);
        controller.setGods(gods, "Monica");
        controller.setGod(GodName.MINOTAUR);
        controller.setGod(GodName.HEPHAESTUS);

        int[] rowsAndColumns = {1,6,1,1};
        controller.setWorkers(rowsAndColumns);
        assertNull(controller.getGame().getBoard().getSlot(1, 1).getWorker());

        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void setWorkers_same_slot() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.PROMETHEUS);
        gods.add(GodName.HERA);
        controller.setGods(gods, "Monica");
        controller.setGod(GodName.HERA);
        controller.setGod(GodName.PROMETHEUS);

        int[] rowsAndColumns = {1,1,1,1};
        controller.setWorkers(rowsAndColumns);
        assertNull(controller.getGame().getBoard().getSlot(1, 1).getWorker());

        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void setWorkers_occupied_slot() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.CHRONUS);
        gods.add(GodName.PAN);
        controller.setGods(gods, "Monica");
        controller.setGod(GodName.PAN);
        controller.setGod(GodName.CHRONUS);

        int[] rowsAndColumns = {1,4,2,2};
        controller.setWorkers(rowsAndColumns);

        assertEquals(controller.getGame().getPlayer(0).getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(1,4).getWorker());
        assertEquals(controller.getGame().getPlayer(0).getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(2,2).getWorker());

        assertEquals(1, controller.getIndexOfCurrentPlayer());

        rowsAndColumns = new int[]{0, 0, 2, 2};
        controller.setWorkers(rowsAndColumns);
        assertEquals(controller.getGame().getPlayer(0).getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(2,2).getWorker());
        assertNull(controller.getGame().getBoard().getSlot(0,0).getWorker());
        assertEquals(1, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void removeLosingPlayer() {
        ArrayList<GodName> gods = new ArrayList<>();
        gods.add(GodName.HESTIA);
        gods.add(GodName.ATLAS);
        controller.setGods(gods, "Monica");
        controller.setGod(GodName.HESTIA);
        controller.setGod(GodName.ATLAS);

        int[] rowsAndColumns = {0, 0, 1, 1};
        controller.setWorkers(rowsAndColumns);

        rowsAndColumns = new int[]{2, 2, 3, 3};
        controller.setWorkers(rowsAndColumns);

        controller.removeLosingPlayer();

        assertFalse(controller.getGame().isActive());
    }
}
