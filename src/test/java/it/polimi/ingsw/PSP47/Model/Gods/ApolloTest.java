package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.*;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ApolloTest {
    private Turn turn;
    private Player player;
    private Worker femaleWorker,maleWorker;
    private Slot slotF, slotM, slot2;
    private Board board;
    private Player secondPlayer;
    private Worker otherWorker;
    private Game game;
    
    @Before
    public void setUp()
            throws Exception {

        game = new Game(3);
        board = game.getBoard();

        player = new Player("Arianna", Color.BLUE);
        secondPlayer = new Player("Monichella", Color.WHITE);

        maleWorker = player.getWorker(Gender.MALE);
        femaleWorker = player.getWorker(Gender.FEMALE);

        otherWorker = secondPlayer.getWorker(Gender.FEMALE);
        maleWorker.setSlot(board.getSlot(2,2));
        femaleWorker.setSlot(board.getSlot(0,0));
        otherWorker.setSlot(board.getSlot(3,3));

        player.setGod(new Apollo(player, "Apollo"));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown()  {
        board.clearBoard();
    }

    @Test
    public void get_Numbers(){
        assertEquals(player.getGod().getMAX_BUILDINGS(),1);
        assertEquals(player.getGod().getMIN_BUILDINGS(),1);
        assertEquals(player.getGod().getMAX_MOVEMENTS(),1);
        assertEquals(player.getGod().getMIN_BUILDINGS(),1);
    }


    @Test
    public void turn_CorrectInput_CorrectOutput_withoutSpecialMove()  throws Exception{
        assertEquals(player.getGod().getName(), "Apollo");
        assertTrue (player.getGod().checkIfCanMove(maleWorker));
        assertFalse(player.getGod().checkIfAWorkerIsOnSlot(board.getNearbySlot(Direction.RIGHT,maleWorker.getSlot())));
        turn.executeMove(Direction.RIGHT);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.LEFTUP);
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());


    }

    @Test
    public void turn_CorrectInput_CorrectOutput_withSpecialMove()  throws Exception{

        assertTrue (player.getGod().checkIfCanMove(maleWorker));
        turn.executeMove(Direction.RIGHTDOWN);
        assertTrue(player.getGod().checkIfCanGoOn(maleWorker));
        assertFalse(player.getGod().validateEndTurn());

        assertTrue(player.getGod().checkIfCanBuild(maleWorker));
        turn.executeBuild(Direction.LEFT);
        assertFalse(player.getGod().checkIfCanGoOn(maleWorker));
        assertTrue(player.getGod().validateEndTurn());


    }

        @Test
    public void checkIfCanMove_withCannotMoveUp() throws Exception{
        player.setCannotMoveUp(true);
        otherWorker.setSlot(board.getSlot(2,1));
        assertTrue(player.getGod().checkIfCanMove(maleWorker));
    }

    @Test
    public void checkIfCanMoveInNormalCondition_withCannotMoveUp() throws Exception {
        player.setCannotMoveUp(true);
        assertTrue(player.getGod().checkIfCanMove(maleWorker));
    }

    @Test
    public void checkIfCanMove() throws Exception {
        otherWorker.setSlot(board.getSlot(2,1));

        assertTrue(player.getGod().checkIfCanMove(maleWorker));

    }

    @Test
    public void move_IndexOutOfBoundsException() throws Exception {
        maleWorker.setSlot(board.getSlot(4,4));
        board.getSlot(3,3).setLevel(Level.DOME);
        board.getSlot(3,4).setLevel(Level.DOME);
        board.getSlot(4,3).setLevel(Level.DOME);

        assertFalse(player.getGod().checkIfCanMove(maleWorker));

    }

}