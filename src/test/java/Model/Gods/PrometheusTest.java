package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Enumerations.Level;
import Model.Exceptions.*;
import Model.Player;
import Model.Slot;
import Model.Turns.Turn;
import Model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PrometheusTest {
    Turn turn;
    Player player;
    Worker worker;
    Slot slot1, slot2;
    Board board;
    
    @Before
    public void setUp() throws Exception {
        board = Board.getBoard();
        slot1 = board.getSlot(1,1);
        slot2 = board.getSlot(2,2);
        player = new Player("testUsername", Color.BLUE);
        worker = player.getWorker(0);
        worker.setSlot(slot1);
        player.setGod(new Prometheus(player, "Prometeo"));
        turn = new Turn(player);
    }
    
    @After
    public void tearDown() throws Exception {
        turn = null;
        player = null;
        worker = null;
        slot1.setLevel(Level.GROUND);
        slot2.setLevel(Level.GROUND);
        slot1.setWorker(null);
        slot2.setWorker(null);
        slot2 = null;
        slot1 = null;
        board = null;
    }
    
    @Test
    public void build_ThenMove_ThenBuild_CorrectOutput() {
        try {
            turn.executeBuild(Direction.RIGHTDOWN);
        } catch (SlotOccupiedException e) {
            e.printStackTrace();
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        } catch (WrongBuildOrMoveException e) {
            e.printStackTrace();
        } catch (NoAvailableBuildingsException e) {
            e.printStackTrace();
        }
        assertEquals(slot2.getLevel(), Level.LEVEL1);
        try {
            turn.executeMove(Direction.RIGHT);
        } catch (NotReachableLevelException e) {
            e.printStackTrace();
        } catch (SlotOccupiedException e) {
            e.printStackTrace();
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        } catch (NoAvailableMovementsException e) {
            e.printStackTrace();
        } catch (WrongBuildOrMoveException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(Board.getNearbySlot(Direction.RIGHT, slot1).getWorker(), worker);
            assertNull(slot1.getWorker());
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        }
        try {
            turn.executeBuild(Direction.DOWN);
        } catch (SlotOccupiedException e) {
            e.printStackTrace();
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        } catch (NoAvailableBuildingsException e) {
            e.printStackTrace();
        } catch (WrongBuildOrMoveException e) {
            e.printStackTrace();
        }
        assertEquals(slot2.getLevel(), Level.LEVEL2);
    }
    
    @Test
    public void build_ThenMove_ThenBuild_ThrowsNotReachableLevelException() {
        try {
            turn.executeBuild(Direction.RIGHTDOWN);
        } catch (SlotOccupiedException e) {
            e.printStackTrace();
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        } catch (WrongBuildOrMoveException e) {
            e.printStackTrace();
        } catch (NoAvailableBuildingsException e) {
            e.printStackTrace();
        }
        assertEquals(slot2.getLevel(), Level.LEVEL1);
        try {
            turn.executeMove(Direction.RIGHTDOWN);
        } catch (NotReachableLevelException e) {
            assertEquals(worker.getSlot(), slot1);
            System.out.println("OK");
        } catch (SlotOccupiedException e) {
            e.printStackTrace();
        } catch (InvalidDirectionException e) {
            e.printStackTrace();
        } catch (NoAvailableMovementsException e) {
            e.printStackTrace();
        } catch (WrongBuildOrMoveException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void build_ThenBuild_ThenMove_Throws_WrongBuildOrMoveException() {
    }
    
    @Test
    public void move_ThenBuild_ThenBuild_Throws_WrongBuildOrMoveException() {
    }
    
    @Test
    public void move_ThenBuild_CorrectOutput() {
    
    }
}