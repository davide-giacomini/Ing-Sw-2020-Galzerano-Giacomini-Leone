package Model.TurnsTest;

import Model.Enumerations.Direction;
import Model.Player;
import Model.Turns.Turn;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class TurnTest {

    Player player;
    Turn turn;

    @Before
    public void setUp() {
        player = new Player("Monica", Color.BLUE);
        turn = new Turn(player);
        }

    @After
    public void tearDown() {
        turn = null;
    }


}
