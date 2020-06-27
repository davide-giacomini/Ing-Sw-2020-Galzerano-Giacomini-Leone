package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PanTest {
    private Board board;
    private Player player;
    private Turn turn;
    private Player otherPlayer;
    private Worker otherWorker;
    private Worker maleWorker,femaleWorker;
    private Game game;
    
    @Before
    public void setUp () throws Exception{
        game = new Game(3);
        board = game.getBoard();

        player = new Player("Monica", it.polimi.ingsw.PSP47.Enumerations.Color.YELLOW);
        player.setGod(new Pan(player, "Pan"));
        otherPlayer = new Player("Arianna", Color.BLUE);


        maleWorker = player.getWorker(Gender.MALE);
        maleWorker.setSlot(board.getSlot(3,3));
        femaleWorker = player.getWorker(Gender.FEMALE);
        femaleWorker.setSlot(board.getSlot(4,4));
        otherWorker = player.getWorker(Gender.FEMALE);

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }

    @Test
    public void turn_CorrectInput_CorrectOutput()
            throws Exception{
        turn.executeMove(Direction.LEFT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());
        turn.executeBuild(Direction.DOWN);
        assertFalse(player.isWinning());
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());
    }


}
