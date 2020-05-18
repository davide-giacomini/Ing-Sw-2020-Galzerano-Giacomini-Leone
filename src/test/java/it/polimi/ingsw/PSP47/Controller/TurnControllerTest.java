package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Gods.Artemis;
import it.polimi.ingsw.PSP47.Model.Gods.Athena;
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

public class TurnControllerTest {

    private GameController controller;
    private TurnController turnController;
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

        mapUserColor.put("Arianna", Color.BLUE);
        VirtualView v1 = new VirtualView("Arianna", Color.BLUE, ariannaClientMock);
        mapUserVirtualView.put("Arianna",  v1);

        mapUserColor.put("Monica", Color.YELLOW);
        VirtualView v2 = new VirtualView("Monica", Color.YELLOW, monicaClientMock);
        mapUserVirtualView.put("Monica", v2);

        controller = new GameController(numberOfPlayers, mapUserColor, mapUserVirtualView);

        Player playerArianna = controller.getGame().getPlayer(0);
        playerMonica = controller.getGame().getPlayer(1);

        playerArianna.setGod(new Artemis(playerArianna, "Artemis"));
        playerMonica.setGod(new Athena(playerMonica, "Athena"));

        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(0,1));
        playerArianna.putWorkerOnSlot(playerArianna.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(1,1));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.MALE), controller.getGame().getBoard().getSlot(1,0));
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(0,0));

        controller.setIndexOfCurrentPlayer(1);
        turnController = new TurnController(controller.getViews(), controller.getGame(), 1, controller);
    }

    @After
    public void tearDown() {
        controller.getGame().getBoard().clearBoard();
    }

    @Test
    public void startTurn_when_player_is_not_losing() {
        turnController.startTurn();
    }

    @Test
    public void startTurn_when_player_is_losing() {
        controller.getGame().getPlayer(1).setLoosing(true);
        turnController.startTurn();
        assertFalse(controller.getGame().isActive());
    }

    @Test
    public void setWorker_with_position_out_of_bounds() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 5;
        rowAndColumn[1] = 2;
        turnController.setWorkerGender(rowAndColumn);
        assertNull(turnController.getWorkerGender());
    }

    @Test
    public void setWorker_with_his_worker_not_here() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 4;
        rowAndColumn[1] = 2;
        turnController.setWorkerGender(rowAndColumn);
        assertNull(turnController.getWorkerGender());
    }

    @Test
    public void setWorker_correct() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        assertEquals(Gender.MALE, turnController.getWorkerGender());
    }

    @Test
    public void setWorker_with_chosen_worker_paralyzed_femaleCase() {
        int[] rowAndColumn = new int[2];
        turnController.setWorkerGender(rowAndColumn);
        assertEquals(Gender.MALE, turnController.getWorkerGender());
    }

    @Test
    public void setWorker_with_chosen_worker_paralyzed_maleCase() {
        playerMonica.putWorkerOnSlot(playerMonica.getWorker(Gender.FEMALE), controller.getGame().getBoard().getSlot(3,4));
        controller.getGame().getBoard().getSlot(0,0).setWorker(null);

        int[] rowAndColumn = {1,0};

        controller.getGame().getBoard().getSlot(2,0).setLevel(Level.DOME);
        controller.getGame().getBoard().getSlot(2,1).setLevel(Level.DOME);
        controller.getGame().getBoard().getSlot(0,0).setLevel(Level.DOME);
        turnController.setWorkerGender(rowAndColumn);
        assertEquals(Gender.FEMALE, turnController.getWorkerGender());
    }

    @Test
    public void executeAction_player_is_loosing() {
        int[] rowAndColumn = new int[2];
        controller.getGame().getBoard().getSlot(2,0).setLevel(Level.DOME);
        controller.getGame().getBoard().getSlot(2,1).setLevel(Level.DOME);
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        assertFalse(controller.getGame().isActive());
    }

    @Test
    public void executeAction_caseMove_correct() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
    }

    @Test
    public void executeAction_caseMove_max_movements_yet_reached() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        assertTrue(controller.getGame().getBoard().getSlot(2,0).isWorkerOnSlot());
        assertFalse(controller.getGame().getBoard().getSlot(3,0).isWorkerOnSlot());
    }

    @Test
    public void executeAction_caseMove_occupied_slot() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.RIGHT);
        assertTrue(controller.getGame().getBoard().getSlot(1,0).isWorkerOnSlot());
        assertEquals(0, turnController.getTurn().getNumberOfMovements());
    }

    @Test
    public void executeAction_caseMove_have_already_build() {
        TurnController ariannaTurnController = new TurnController(controller.getViews(), controller.getGame(), 0, controller);
        int[] rowAndColumn = new int[]{0, 1};
        ariannaTurnController.setWorkerGender(rowAndColumn);
        ariannaTurnController.executeAction(Action.MOVE, Direction.RIGHT);
        assertEquals(1, ariannaTurnController.getTurn().getNumberOfMovements());
        ariannaTurnController.executeAction(Action.BUILD, Direction.RIGHT);
        assertEquals(1, ariannaTurnController.getTurn().getNumberOfBuildings());
        ariannaTurnController.executeAction(Action.MOVE,Direction.DOWN);
        assertEquals(1, ariannaTurnController.getTurn().getNumberOfMovements());
    }

    @Test
    public void executeAction_caseMove_unreachable_slot() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        controller.getGame().getBoard().getSlot(2,0).setLevel(Level.LEVEL2);
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        assertTrue(controller.getGame().getBoard().getSlot(1,0).isWorkerOnSlot());
        assertEquals(0, turnController.getTurn().getNumberOfMovements());
    }

    @Test
    public void executeAction_caseMove_slot_with_a_dome() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        controller.getGame().getBoard().getSlot(2,0).setLevel(Level.DOME);
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        assertTrue(controller.getGame().getBoard().getSlot(1,0).isWorkerOnSlot());
        assertEquals(0, turnController.getTurn().getNumberOfMovements());
    }

    @Test
    public void executeAction_caseMove_index_out_of_bound() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.LEFT);
        assertTrue(controller.getGame().getBoard().getSlot(1,0).isWorkerOnSlot());
        assertEquals(0, turnController.getTurn().getNumberOfMovements());
    }

    @Test
    public void executeAction_caseBuild_correct() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD,Direction.LEFT);
    }

    @Test
    public void executeAction_caseBuild_max_buildings_yet_reached() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        turnController.executeAction(Action.BUILD,Direction.DOWN);
        assertEquals(Level.LEVEL1, controller.getGame().getBoard().getSlot(3,0).getLevel());
        assertEquals(1, turnController.getTurn().getNumberOfBuildings());
    }

    @Test
    public void executeAction_caseBuild_occupied_slot() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.RIGHTUP);
        assertEquals(Level.GROUND, controller.getGame().getBoard().getSlot(1,1).getLevel());
        assertEquals(0, turnController.getTurn().getNumberOfBuildings());
    }

    @Test
    public void executeAction_caseBuild_slot_with_a_dome() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        controller.getGame().getBoard().getSlot(3,0).setLevel(Level.DOME);
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        assertEquals(0, turnController.getTurn().getNumberOfBuildings());
    }

    @Test
    public void executeAction_caseBuild_without_moving() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        assertEquals(Level.GROUND, controller.getGame().getBoard().getSlot(2,0).getLevel());
        assertEquals(0, turnController.getTurn().getNumberOfBuildings());
        assertEquals(0, turnController.getTurn().getNumberOfMovements());
    }

    @Test
    public void executeAction_caseBuildDome_correct() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILDDOME, Direction.DOWN);
        assertEquals(Level.GROUND, controller.getGame().getBoard().getSlot(3,0).getLevel());
    }

    @Test
    public void executeAction_caseQuit() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.QUIT, Direction.DOWN);
    }

    @Test
    public void executeAction_caseEnd_correct() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.MOVE, Direction.DOWN);
        turnController.executeAction(Action.BUILD, Direction.DOWN);
        turnController.executeAction(Action.END, Direction.WRONGDIRECTION);
        assertEquals(0, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void executeAction_caseEnd_not_correct() {
        int[] rowAndColumn = new int[2];
        rowAndColumn[0] = 1;
        turnController.setWorkerGender(rowAndColumn);
        turnController.executeAction(Action.END, Direction.DOWN);
        assertEquals(1, controller.getIndexOfCurrentPlayer());
    }

    @Test
    public void chronusPlayer_test() {
        Player chronusPlayer;
        chronusPlayer = turnController.chronusPlayer();
        assertNull(chronusPlayer);
    }
}
