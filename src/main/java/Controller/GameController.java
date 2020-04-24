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
    
    // Start the game calling setStart of Game.
    // When the game is started, a random player is chosen as the beginner player.
    // La scelta del giocatore è inoltrata alla view, che mostrerà a tutti i giocatori quale
    // giocatore è stato scelto come Challenger.
    // Nel frattempo, GameController aggiornerà il proprio Challenger e chiamerà il metodo chooseGods(), metodo che servirà per
    // scegliere tre dei tra quelli disponibili.
    // chooseGods() chiamerà il metodo della view che chiede al challenger di scegliere gli dei. Una volta scelti gli dei,
    // viene notificato al GameController che gli dei sono stati scelti e quindi GameController provvederà a
    // chiamare il metodo che si fa il giro dei giocatori e gli chiede quale dio vogliono tra quei tre.
    // Il metodo in questione chiamerà una chooseGod(Player player) della view e
    // La VirtualView implementerà MessageListener e non appena gli dei saranno scelti, notificherà il GameController.
    // Il GameController viene notificato e inserisce gli dei corrispondenti nei giocatori.
    // A questo punto GameController chiede al Challenger, tramite VirtualView, di scegliere il giocatore iniziale.
}
