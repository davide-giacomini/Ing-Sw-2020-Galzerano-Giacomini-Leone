package it.polimi.ingsw.PSP47.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;

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
        assertFalse(slot.getIsOccupied());
        slot.setLevel(Level.DOME);
        assertTrue(slot.getIsOccupied());
        slot.setLevel(Level.GROUND);
        Worker w = new Worker(Color.BLUE, Gender.MALE);
        slot.setWorker(w);
        assertTrue(slot.getIsOccupied());
    }

    @Test
    public void toString_compareOfStrings(){
        assertEquals(slot.toString(),"Row: " + slot.getRow() + "\nColumn: " + slot.getColumn());
    }


}
