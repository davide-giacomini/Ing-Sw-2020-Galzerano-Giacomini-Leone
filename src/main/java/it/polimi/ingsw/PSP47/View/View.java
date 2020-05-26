package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;

import java.util.ArrayList;

public interface View  {


    /**
     * This method is used to ask username and color
     */
    void askFirstConnection();

    /**
     * This method is used to ask the numbers of players of the game
     */
    void askNumberOfPlayers();

    /**
     * This method asks the user which of the worker he/she wants to use
     */
    void askWhichWorkerToUse ();

    /**
     * This method asks the user where he/she wants to put the worker
     */
    void askWhereToPositionWorkers();

    /**
     * This method asks only one of the users ( the challenger) which gods will be used during the game
     * @param usernames the list of players in the game.
     */
    void challengerWillChooseThreeGods(ArrayList<String> usernames);

    /**
     * This method makes the user choose his/her god between the ones already chosen by the challenger
     * @param godsChosen is the array of the available gods (chosen by the challenger)
     */
    void chooseYourGod(ArrayList<GodName> godsChosen);

    /**
     * This is the basic method to ask what the user wants to do in its turn.
     */
    void askAction();

    /**
     * @return GameView
     */
    GameView getGameView();

    /**
     * This method is used to show the initial public information about all the clients in the game
     */
    void showPublicInformation();

    /**
     * Shows a message of Error or of Warning
     * @param text tho display
     */
    void showErrorMessage(String text);

     /**
     * Shows a message of Update of the game
     * @param text tho display
     */
     void showImportantMessage(String text);

    /**
     * This method tells the username of the winner
     * @param usernameWinner is the username of the winner
     */
     void theWinnerIs(String usernameWinner );

    /**
     * This method tells the user that he/she lost
     */
     void theLoserIs();

    /**
     * This method shows the user that it's someone else turn
     * @param usernameOnTurn username of the player who's doing its turn
     */
    void othersTurn(String usernameOnTurn);

    /**
     * This method show the final byebye of the game
     */
    void showEnd();

   void showNewBoard(Slot slot);

   void showGame();


}
