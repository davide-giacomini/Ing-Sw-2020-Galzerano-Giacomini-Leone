package Model;

import Model.Enumerations.Gender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;

    @Before
    public void setUp()  {
        player = new Player ("Arianna", Color.BLACK);

    }

    @After
    public void tearDown()  {
        //nothing to do?
    }

    @Test
    public void playerSetUp() {
        assertFalse(player.isLoosing());
        assertFalse(player.isWinning());
        assertEquals(player.getWorkersNumber(), 2);
        assertEquals(player.getWorker(Gender.MALE).getColor(), Color.BLACK);
        assertEquals(player.getWorker(Gender.FEMALE).getColor(), Color.BLACK);
    }
}