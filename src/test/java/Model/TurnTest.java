package Model;

import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.GodNotSet;
import Model.Exceptions.InvalidDirectionException;
import Model.Gods.God;
import Model.Gods.Prometheus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class TurnTest {
    Turn turn;
    Worker maleWorker, femaleWorker;
    Player player;
    God god;
    Slot[][] slots = new Slot[Board.ROWSNUMBER][Board.COLUMNSNUMBER];
    
    @Before
    public void setUp() throws Exception {
        player = new Player("1", Color.BLACK);
        maleWorker = player.getWorker(Gender.MALE);
        femaleWorker = player.getWorker(Gender.FEMALE);
        for (int i=0; i<Board.ROWSNUMBER; i++) {
            for (int j=0; j<Board.COLUMNSNUMBER; j++) {
                slots[i][j] = Board.getBoard().getSlot(i,j);
            }
        }
    }
    
    @After
    public void tearDown() throws Exception {
        Board.getBoard().clearBoard();
        player = null;
        maleWorker = null;
        femaleWorker = null;
        turn = null;
        god = null;
        for (int i=0; i<Board.ROWSNUMBER; i++) {
            for (int j=0; j<Board.COLUMNSNUMBER; j++) {
                slots[i][j] = null;
            }
        }
    }
    
    @Test
    public void deleteWorkersIfParalyzed_BothWorkersParalyzed_WorkersDeleted () throws InvalidDirectionException, GodNotSet {
        setUp_DeleteWorkersIfParalyzed_WorkersParalyzed(player, maleWorker, femaleWorker);
        
        turn = new Turn(player);    // the constructor calls implicitly deleteWorkersIfParalyzed
        
        assertTrue(player.isLoosing());
        assertNull(player.getWorker(Gender.MALE));
        assertNull(player.getWorker(Gender.FEMALE));
    }
    @Test
    public void deleteWorkersIfParalyzed_MaleWorkerParalyzed_WorkersNotDeleted () throws InvalidDirectionException, GodNotSet {
        setUp_DeleteWorkersIfParalyzed_WorkersParalyzed(player, maleWorker, femaleWorker);
        // I am going to free the female worker (this is Prometheus, he can build)
        slots[3][3].setLevel(Level.LEVEL2);
        turn = new Turn(player);
        
        assertFalse(player.isLoosing());
        assertEquals(femaleWorker, player.getWorker(Gender.FEMALE));
        assertEquals(maleWorker, player.getWorker(Gender.MALE));
    }
    @Test
    public void deleteWorkersIfParalyzed_FemaleWorkerParalyzed_WorkersNotDeleted () throws InvalidDirectionException, GodNotSet {
        setUp_DeleteWorkersIfParalyzed_WorkersParalyzed(player, maleWorker, femaleWorker);
        // I am going to free the female worker (this is Prometheus, he can build)
        slots[0][0].setLevel(Level.GROUND);
        turn = new Turn(player);
        
        assertFalse(player.isLoosing());
        assertEquals(femaleWorker, player.getWorker(Gender.FEMALE));
        assertEquals(maleWorker, player.getWorker(Gender.MALE));
    }
    private void setUp_DeleteWorkersIfParalyzed_WorkersParalyzed(Player player, Worker maleWorker, Worker femaleWorker) throws InvalidDirectionException, GodNotSet {
        god = new Prometheus(player, "Prometeo");   // Prometheus has normal conditions checking

        // I am going to paralyze the workers.
        maleWorker.setSlot(slots[1][1]);
        femaleWorker.setSlot(slots[2][2]);
        slots[0][0].setLevel(Level.DOME);
        slots[1][0].setLevel(Level.DOME);
        slots[2][0].setLevel(Level.DOME);
        slots[2][1].setLevel(Level.DOME);
        slots[3][1].setLevel(Level.DOME);
        slots[3][2].setLevel(Level.DOME);
        slots[3][3].setLevel(Level.DOME);
        slots[2][3].setLevel(Level.DOME);
        slots[1][3].setLevel(Level.DOME);
        slots[1][2].setLevel(Level.DOME);
        slots[0][2].setLevel(Level.DOME);
        slots[0][1].setLevel(Level.DOME);
    }
}