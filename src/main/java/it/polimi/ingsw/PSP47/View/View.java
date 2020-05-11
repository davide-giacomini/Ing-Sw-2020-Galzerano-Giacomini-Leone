package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.View.CLI.GameView;
import it.polimi.ingsw.PSP47.View.CLI.ViewObservable;

import java.util.ArrayList;

public abstract class View extends ViewObservable {
    public Client currentClient;
    public GameView gameView;

    public View (Client client){
        currentClient = client;
    }

    /**
     * This method is used to print the initial Santorini Logo
     */
    public abstract void showTitle();

    public abstract void askFirstConnection();
    /**
     * This method asks for the username
     * @return string which becomes the username of the player
     */
    public abstract String askUsername();

    /**
     * This method asks the user which color he/she wants for the workers
     * @return string which indicates the name of the color
     */
    public abstract Color askColorWorkers();

    /**
     * This method is used to ask the numbers of players of the game
     * @return int to indicate the number chosen
     */
    public abstract void askNumberOfPlayers();

    /**
     * This method asks the user to insert the address of the server
     * @return address of the client/server
     */
    public abstract String askServerIpAddress();

    /**
     * This method asks the user which of the worker he/she wants to use
     * @return the gender of the worker the user wants to use
     */
    public abstract void askWhichWorkerToUse ();

    /**
     * This method asks the user where he/she wants to put the worker
     * @return an array of two int indicating one the row and one the column
     */
    public abstract void askWhereToPositionWorkers();

    /**
     * This method asks only one of the users ( the challenger) which gods will be used during the game
     * @return an Arraylist with the names of the 2 or 3 gods that have been chosen
     */
    public abstract void challengerWillChooseThreeGods();

    /**
     * This method makes the user choose his/her god between the ones already chosen by the challenger
     * @param godsChosen is the array of the available gods (chosen by the challenger)
     * @return the God chosen by the client
     */
    public abstract void chooseYourGod(ArrayList<GodName> godsChosen);

    /**
     * This is the basic method to ask what the user wants to do in its turn.
     * @return an arraylist which contains as first parameter the enum Action and as second the enum Direction
     */
    public abstract void askAction();

    /**
     * @return GameView
     */
    public abstract GameView getGameView();

    /**
     * This method is used to show the initial public information about all the clients in the game
     */
    public abstract void showPublicInformation();

    /**
     * this method calls the print support that prints the updated board of the game
     */
    public abstract void showCurrentBoard();

    /**
     * Shows a message of Error or of Warning
     * @param text tho display
     */
    public abstract void showErrorMessage(String text);

     /**
     * Shows a message of Update of the game
     * @param text tho display
     */
    public abstract void showImportantMessage(String text);

    /**
     * This method tells the username of the winner
     * @param usernameWinner is the username of the winner
     */
    public abstract void theWinnerIs(String usernameWinner );

    /**
     * This method tells the username of the winner
     * @param usernameLoser is the username of the winner
     */
    public abstract void theLoserIs(String usernameLoser );



}
