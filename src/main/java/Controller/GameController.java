package Controller;

import Model.Game;
import Model.Player;
import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private static Game game;

    //costruttore
    public GameController(){
        game = new Game();
    }

    /**
     * This method is used at the beginning of the game to randomly pick from the list of players
     * the first one that will play
     * @return username of the first player
     */
    public static String RandomPick1stPlayer(){
        ArrayList<Player> list4RandomPick = game.getPlayers();
        Player FirstPlayer ;
        String username1stPlayer = null;
        int numberPlayers = list4RandomPick.size();
        Random rand = new Random();

        FirstPlayer = list4RandomPick.get(rand.nextInt(numberPlayers));
        //at this point the player picked has to be moved at the beginning of the arraylist

        username1stPlayer = FirstPlayer.getUsername();

        return username1stPlayer;
    }
}
