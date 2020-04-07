package Model.Gods;

import Model.Board;
import Model.Enumerations.Gender;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Turn;
import Model.Worker;
import org.junit.After;
import org.junit.Before;

import java.awt.*;

public class PanTest {
    private Worker worker;
    private Board board;
    private Player player;
    private Turn turn;

    @Before
    public void setUp () throws WrongBuildOrMoveException {
        board = board.getBoard();
        player = new Player("Monica", Color.YELLOW);
        worker = player.getWorker(Gender.MALE);
        worker.setSlot(board.getSlot(3,3));
        turn = new Turn(player);
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }
}
