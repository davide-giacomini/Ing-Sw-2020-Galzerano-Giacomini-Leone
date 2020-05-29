package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Gods.Hera;
import it.polimi.ingsw.PSP47.Model.Gods.Triton;
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

public class HeraTest {
    private GameController controller;
    private TurnController turnController;
    private Player playerArianna;
    private Player playerMonica;
    private HashMap<String, Color> mapUserColor = new HashMap<>();
    private HashMap<String, VirtualView> mapUserVirtualView = new HashMap<>();


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

        playerArianna = controller.getGame().getPlayer(0);
        playerMonica = controller.getGame().getPlayer(1);

        playerArianna.setGod(new Hera(playerArianna, "Hera"));
        playerMonica.setGod(new Triton(playerMonica, "Triton"));

    }

    @After
    public void tearDown() {
        controller.getGame().getBoard().clearBoard();
    }

    @Test
    public void executeAction_caseMove_winningCondition_heraCondition() {
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(4,4));
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(3,3));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(2,2));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(1,1));

        int[] rowAndColumn;

        controller.getGame().getBoard().getSlot(2,1).setLevel(Level.LEVEL1);
        controller.getGame().getBoard().getSlot(3,1).setLevel(Level.LEVEL1);
        controller.getGame().getBoard().getSlot(4,1).setLevel(Level.LEVEL2);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{1, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{2, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{3, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);

        assertTrue(playerMonica.isWinning());
        assertFalse(turnController.getGame().checkWinningCondition(playerMonica.getWorker(turnController.getWorkerGender()), playerMonica));
        assertEquals(2, controller.getGame().getNumberOfPlayers());
        assertTrue(controller.getGame().isActive());
    }

    @Test
    public void executeAction_caseMove_winningCondition_not_heraCondition() {
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(4,4));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(3,3));
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(2,2));
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(1,1));

        int[] rowAndColumn;

        controller.getGame().getBoard().getSlot(2,1).setLevel(Level.LEVEL1);
        controller.getGame().getBoard().getSlot(3,1).setLevel(Level.LEVEL1);
        controller.getGame().getBoard().getSlot(4,1).setLevel(Level.LEVEL2);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{1, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{2, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        rowAndColumn = new int[]{3, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);

        assertTrue(playerArianna.isWinning());
        assertFalse(turnController.getGame().checkWinningCondition(playerArianna.getWorker(turnController.getWorkerGender()), playerMonica));
        assertFalse(controller.getGame().isActive());

    }

    @Test
    public void executeAction_caseMove_winningCondition_not_on_perimeter() {
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(4,4));
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(3,3));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(2,2));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(1,1));

        int[] rowAndColumn;

        controller.getGame().getBoard().getSlot(2,1).setLevel(Level.LEVEL1);
        controller.getGame().getBoard().getSlot(3,1).setLevel(Level.LEVEL1);
        controller.getGame().getBoard().getSlot(3,2).setLevel(Level.LEVEL2);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{1, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{2, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.RIGHT);
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);

        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
        rowAndColumn = new int[]{3, 1};
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.RIGHT);

        assertTrue(playerMonica.isWinning());
        assertTrue(turnController.getGame().checkWinningCondition(playerMonica.getWorker(turnController.getWorkerGender()), playerMonica));
        assertFalse(controller.getGame().isActive());

    }

}
