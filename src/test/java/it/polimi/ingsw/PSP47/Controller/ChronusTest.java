package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Gods.Chronus;
import it.polimi.ingsw.PSP47.Model.Gods.Demeter;
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

import java.util.HashMap;

import static junit.framework.TestCase.*;

public class ChronusTest {
    private GameController controller;
    private TurnController turnController;
    private HashMap<String, Color> mapUserColor = new HashMap<>();
    private HashMap<String, VirtualView> mapUserVirtualView = new HashMap<>();

    Player playerMonica;


    @Mock
    ClientHandler ariannaClientMock;

    @Mock
    ClientHandler monicaClientMock ;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        int numberOfPlayers = 2;

        mapUserColor.put("Arianna", Color.RED);
        VirtualView v1 = new VirtualView("Arianna", Color.RED, ariannaClientMock);
        mapUserVirtualView.put("Arianna",  v1);

        mapUserColor.put("Monica", Color.CYAN);
        VirtualView v2 = new VirtualView("Monica", Color.CYAN, monicaClientMock);
        mapUserVirtualView.put("Monica", v2);

        controller = new GameController(numberOfPlayers, mapUserColor, mapUserVirtualView);

        Player playerArianna = controller.getGame().getPlayer(0);
        playerMonica = controller.getGame().getPlayer(1);

        playerArianna.setGod(new Demeter(playerArianna, "Demeter"));
        playerMonica.setGod(new Chronus(playerMonica, "Chronus"));

        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(4,4));
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(3,3));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(2,2));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(1,1));

        controller.setIndexOfCurrentPlayer(1);
        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
    }

    @After
    public void tearDown() {
        controller.getGame().getBoard().clearBoard();
    }

    @Test
    public void five_complete_towers_build_by_another_god() {
        int[] rowAndColumn;

        controller.getGame().getBoard().getSlot(4,2).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(3,2).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(4,4).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(2,4).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(1,3).setLevel(Level.LEVEL3);

        assertEquals(0,controller.getGame().getBoard().getCountDomes());

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{4, 4};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.LEFT);
        turnController.executeAction(Action.BUILD, Direction.LEFT);
        assertEquals(1,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{3, 3};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.UP);
        turnController.executeAction(Action.BUILD, Direction.RIGHT);
        assertEquals(2,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{2, 3};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.LEFTUP);
        turnController.executeAction(Action.BUILD, Direction.RIGHT);
        assertEquals(3,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{4, 3};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.UP);
        turnController.executeAction(Action.BUILD, Direction.LEFT);
        assertEquals(4,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{3, 3};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.RIGHT);
        assertEquals(5,controller.getGame().getBoard().getCountDomes());

        assertFalse(controller.getGame().isActive());
    }

    @Test
    public void five_complete_towers_build_by_chronus() {
        int[] rowAndColumn;

        controller.getGame().getBoard().getSlot(2,1).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(2,3).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(3,2).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(0,0).setLevel(Level.LEVEL3);
        controller.getGame().getBoard().getSlot(4,0).setLevel(Level.LEVEL3);

        assertEquals(0,controller.getGame().getBoard().getCountDomes());

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{1, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.LEFT);
        turnController.executeAction(Action.BUILD, Direction.UP);
        assertEquals(1,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{1, 0};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.RIGHT);
        assertEquals(2,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{2, 0};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.RIGHTDOWN);
        turnController.executeAction(Action.BUILD, Direction.LEFTDOWN);
        assertEquals(3,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{3, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.RIGHTUP);
        assertEquals(4,controller.getGame().getBoard().getCountDomes());
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{2, 2};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.RIGHTUP);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        assertEquals(5,controller.getGame().getBoard().getCountDomes());

        assertFalse(controller.getGame().isActive());
    }

    @Test
    public void chronusPlayer_test() {
        Player chronusPlayer;
        chronusPlayer = turnController.chronusPlayer();
        assertEquals(playerMonica, chronusPlayer);
    }

}
