package Model;

import Model.Enumerations.Gender;
import Model.Exceptions.SlotOccupiedException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class WorkerTest {
    Worker worker = null;
    
    @Before
    public void setUp() throws Exception {
        worker = new Worker(Color.BLACK, Gender.MALE);
        worker.setSlot(Board.getBoard().getSlot(2,3));
    }
    
    @After
    public void tearDown() throws Exception {
        worker = null;
    }
}