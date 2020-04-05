package Model;

import Model.Enumerations.Gender;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Gods.Atlas;
import Model.Gods.God;
import Model.Stubs.TestingGod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInfo;

import java.awt.*;

import static org.junit.Assert.*;

public class TurnTest {
    Turn canMoveBothWorkerCannotBuildDome_1;
    Turn cannotMoveBothWorkerCannotBuildDome_2;
    Turn cannotMoveBothWorkerCanBuildDome_3;
    TestingGod god1, god2;
    God god3;
    
    @Before
    public void setUp() throws Exception {
        Player player1 = new Player("player2", Color.CYAN);
        Player player2 = new Player("2", Color.LIGHT_GRAY);
        Player player3 = new Player("3", Color.BLACK);
        god1 = new TestingGod(player1, "1");
        god1.setCanUseBothWorkers(true);
        god2 = new TestingGod(player2, "2");
        god3 = new Atlas(player3, "3");
        canMoveBothWorkerCannotBuildDome_1 = new Turn(player1);
        cannotMoveBothWorkerCannotBuildDome_2 = new Turn(player2);
        cannotMoveBothWorkerCanBuildDome_3 = new Turn(player3);
    }
    
    @After
    public void tearDown() throws Exception {
        canMoveBothWorkerCannotBuildDome_1 = null;
        cannotMoveBothWorkerCannotBuildDome_2 = null;
        cannotMoveBothWorkerCanBuildDome_3 = null;
    }
    
    @Test
    public void setIndexOfWorker_CanUseBothWorkers_CannotBuildDome_CorrectOutput() {
        try {
            assertTrue(canMoveBothWorkerCannotBuildDome_1.canUseBothWorkers());
            assertFalse(canMoveBothWorkerCannotBuildDome_1.isAlreadySetWorker());
            assertFalse(god1.canBuildDome());
            canMoveBothWorkerCannotBuildDome_1.setWorkerGender(Gender.MALE);
        } catch (WrongBuildOrMoveException e) {
            e.printStackTrace();
        }
        assertEquals(canMoveBothWorkerCannotBuildDome_1.getWorkergender(), Gender.MALE);
        assertTrue(canMoveBothWorkerCannotBuildDome_1.isAlreadySetWorker());
        
        try {
            canMoveBothWorkerCannotBuildDome_1.setWorkerGender(Gender.FEMALE);
        } catch (WrongBuildOrMoveException e) {
            e.printStackTrace();
        }
        assertEquals(canMoveBothWorkerCannotBuildDome_1.getWorkergender(), Gender.FEMALE);
        assertTrue(canMoveBothWorkerCannotBuildDome_1.canUseBothWorkers());
    }
    
    @Test
    public void wantsToBuildDome() {
    }
    
    @Test
    public void setWantsToBuildDome() {
    }
    
    @Test
    public void executeMove() {
    }
    
    @Test
    public void executeBuild() {
    }
    
    @Test
    public void validateEndTurn() {
    }
}