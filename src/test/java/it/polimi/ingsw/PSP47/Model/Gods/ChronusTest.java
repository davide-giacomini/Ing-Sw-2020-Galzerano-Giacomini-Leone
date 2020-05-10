package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Model.*;
import org.junit.After;
import org.junit.Before;

public class ChronusTest {
    private Board board;
    private Turn turn;
    private Player player;
    private Worker femaleWorker;
    private Worker maleWorker;
    private Player otherPlayer;
    private Worker otherWorker;
    private Game game;

    @Before
    public void setUp () {
        game =new Game(3);
        board = game.getBoard();

        player = new Player("Elisa", Color.WHITE);
        player.setGod(new Artemis(player, "Chronus"));

        otherPlayer = new Player("Alessia", Color.CYAN);
        otherWorker = otherPlayer.getWorker(Gender.FEMALE);

        maleWorker = player.getWorker(Gender.MALE);
        maleWorker.setSlot(board.getSlot(1,1));
        femaleWorker = player.getWorker(Gender.FEMALE);
        femaleWorker.setSlot(board.getSlot(4,4));

        turn = new Turn(player, game.getBoard());
        turn.setWorkerGender(Gender.MALE);
    }

    @After
    public void tearDown() {
        board.clearBoard();
    }
}
