package View;

import Enumerations.Color;
import Enumerations.Gender;
import Enumerations.GodName;
import Network.Client.Client;
import Network.Message.ErrorMessages.ConnectionFailed;

import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.security.PublicKey;
import java.util.Scanner;

public abstract class View {
    public Client currentClient;
    public final static int SOCKET_PORT = 7777;

    public View (Client client){
        currentClient = client;
    }

    public void startConnection(){}

    public abstract String askUsername();

    public abstract Color askColorWorkers();

    public abstract void print(String text);

    public abstract int askNumberOfPlayers();

    public abstract String askServerIpAddress();


    public abstract Gender askWhichWorkerToUse ();

    public abstract int[] askWhereToPositionWorkers();

    public abstract ArrayList<GodName> challengerWillChooseThreeGods();

    public abstract GodName chooseYourGod(ArrayList<GodName> godsChosen);

    public abstract int[] askWhereToMoveWorkers();

    public abstract int[] askWhereToBuildWorkers();

    public abstract void theWinnerIs(String usernameWinner );

    public abstract void theLoserIs(String usernameLoser );

    public abstract ArrayList<Enum> askAction();
    
    public abstract ViewDatabase getViewDatabase();

    public abstract void showPublicInformation();
}
