package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Gods.Atlas;
import it.polimi.ingsw.PSP47.Model.Gods.Minotaur;
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

import static org.junit.Assert.assertEquals;

public class AtlasTest {

    private GameController controller;
    private TurnController turnController;
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

        Player playerArianna = controller.getGame().getPlayer(0);
        Player playerMonica = controller.getGame().getPlayer(1);

        playerArianna.setGod(new Minotaur(playerArianna, "Minotaur"));
        playerMonica.setGod(new Atlas(playerMonica, "Atlas"));

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
    public void executeAction_caseBuildDome_correct() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        rowAndColumn[1] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILDDOME, Direction.DOWN);
        assertEquals(Level.ATLAS_DOME, controller.getGame().getBoard().getSlot(3,1).getLevel());
    }

    @Test
    public void executeAction_caseBuildDome_max_buildings_yet_reached() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        rowAndColumn[1] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        assertEquals(Level.LEVEL1, controller.getGame().getBoard().getSlot(3,1).getLevel());
        assertEquals(1, turnController.getTurn().getNumberOfBuildings());
        turnController.executeAction(Action.BUILDDOME, Direction.DOWN);
        assertEquals(Level.LEVEL1, controller.getGame().getBoard().getSlot(3,1).getLevel());
        assertEquals(1, turnController.getTurn().getNumberOfBuildings());
    }

    @Test
    public void executeAction_caseBuildDome_slot_with_a_dome() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        rowAndColumn[1] = 1;
        controller.getGame().getBoard().getSlot(3,1).setLevel(Level.DOME);
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILDDOME, Direction.DOWN);
        assertEquals(Level.DOME, controller.getGame().getBoard().getSlot(3,1).getLevel());
        assertEquals(0, turnController.getTurn().getNumberOfBuildings());
    }

    @Test
    public void executeAction_caseBuildDome_slot_occupied() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        rowAndColumn[1] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILDDOME, Direction.RIGHT);
        assertEquals(Level.GROUND, controller.getGame().getBoard().getSlot(2,2).getLevel());
        assertEquals(0, turnController.getTurn().getNumberOfBuildings());
    }

    @Test
    public void executeAction_caseBuildDOme_out_of_bounds() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        rowAndColumn[1] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.LEFT);
        turnController.executeAction(Action.BUILDDOME, Direction.LEFT);
        assertEquals(0, turnController.getTurn().getNumberOfBuildings());
    }
}
