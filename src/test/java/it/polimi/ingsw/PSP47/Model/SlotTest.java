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
    private Game game;
    Player player;
    
    @Before
    public void setUp() {
        player = new Player("Monica", Color.BLUE);
        game = new Game(3);
        slot = game.getBoard().getSlot(2,1);
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
        Worker worker = new Worker(player, Color.BLUE, Gender.MALE);
        slot.setWorker(worker);
        assertEquals(slot.getWorker(),worker);
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
        Worker w = new Worker(player, Color.BLUE, Gender.MALE);
        slot.setWorker(w);
        assertTrue(slot.isOccupied());
    }

    @Test
    public void toString_compareOfStrings(){
        assertEquals(slot.toString(),"Row: " + slot.getRow() + "\nColumn: " + slot.getColumn());
    }

    @Test
    public void getWorkerColor() {
        Worker worker = new Worker(player, Color.BLUE, Gender.MALE);
        slot.setWorker(worker);
        assertEquals(slot.getWorkerColor(), worker.getColor());
    }

    @Test
    public void setWorkerColor() {
        Worker worker = new Worker(player, Color.BLUE, Gender.MALE);
        slot.setWorker(worker);
        slot.setWorkerColor(Color.BLUE);
        assertEquals(slot.getWorkerColor(), worker.getColor());
    }

    @Test
    public void setWorkerOn() {
        slot.setWorkerOn(true);
        assertTrue(slot.isWorkerOnSlot());
    }

    @Test
    public void isWorkerOnSlot() {
        assertFalse(slot.isWorkerOnSlot());
        Worker worker = new Worker(player, Color.BLUE, Gender.MALE);
        slot.setWorker(worker);
        assertTrue(slot.isWorkerOnSlot());
    }

    @Test
    public void isPerimeterSlot(){
        Slot perimeterSlot = game.getBoard().getSlot(0,0);
        assertTrue(perimeterSlot.isPerimeterSlot());
    }


}
