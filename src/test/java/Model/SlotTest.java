package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.*;
import Model.Gods.*;

import java.awt.*;

import static org.junit.Assert.*;

public class SlotTest {
    private Slot slot;

    @Before
    public void setUp() {
        slot = new Slot(2,1);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void getColumn(){
        assertEquals(slot.getColumn(),1);
    }
    @Test
    public void getRow(){
        assertEquals(slot.getRow(),2);
    }
    @Test
    public void getWorker(){
        Worker w = new Worker(Color.BLUE, Gender.MALE);
        slot.setWorker(w);
        assertEquals(slot.getWorker(),w);
    }
    @Test
    public void getLevel(){
        slot.setLevel(Level.DOME);
        assertEquals(slot.getLevel(),Level.DOME);
    }


    @Test
    public void isOccupied(){
        assertFalse(slot.isOccupied());
        slot.setLevel(Level.DOME);
        assertTrue(slot.isOccupied());
        slot.setLevel(Level.GROUND);
        Worker w = new Worker(Color.BLUE, Gender.MALE);
        slot.setWorker(w);
        assertTrue(slot.isOccupied());
    }

    @Test
    public void toString_compareOfStrings(){
        assertEquals(slot.toString(),"Row: " + slot.getRow() + "\nColumn: " + slot.getColumn());
    }


}
