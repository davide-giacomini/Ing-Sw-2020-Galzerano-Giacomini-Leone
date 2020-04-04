package View;

import Controller.GameController;
import Model.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CLI {

    private Scanner in;
    private PrintStream out;

    //main per prova
    public static void main(String[] args)
    {
        CLI c = new CLI();

        System.out.println (AnsiCode.ANSI_DOME + AnsiCode.ANSI_LEVEL2 + AnsiCode.ANSI_LEVEL3);


        c.printSantorini() ;

        String un = c.askUsername() ;

        System.out.println( un + " is your username ! \n");

        String color = c.askColorWorkers() ;

        AnsiCode a = new AnsiCode();
        String ansiColor = a.getAnsiByName(color) ;

        String worker = a.getAnsiByName("WORKER");

        System.out.println( ansiColor + worker) ;

        c.reset(c.out);

        System.out.println("\n Now the empty Board will be printed : \n");
        PrintSupport p = new PrintSupport();
        p.PrintEmptyBoard(c.out);

    }

    //costruttore
    public CLI() {
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out);
    }

    /**
     * This method is used to print the initial Santorini Logo
     */
    public void printSantorini() {
        String santoriniName = "     ____       ____   ____    __      ____    ____    ____        __         \n" +
                               "    |____|     |      |    |  |  | |    ||    |    |  |    |  ||  |  | |  ||  \n" +
                               "    |_°°_|     |____  |____|  |  | |    ||    |    |  |____|  ||  |  | |  ||  \n" +
                               "    |____|          | |    |  |  | |    ||    |    |  |\\      ||  |  | |  ||  \n" +
                               "    |    |      ____| |    |  |  |_|    ||    |____|  | \\     ||  |  |_|  ||  \n";

        String intro ="Welcome to the Digital Version of Santorini Board Game, \n" +
                      "programmers:AriannaGalzerano-DavideGiacomini-MonicaLeone\n" +
                      "Before starting please insert the required parameters...\n";

        out.println( AnsiCode.ANSI_BLUE + santoriniName + AnsiCode.ANSI_RESET);
        out.println(AnsiCode.ANSI_CYAN + intro + AnsiCode.ANSI_RESET);
    }

    /**
     * This method asks for the username
     * @return string which becomes the username of the player
     */
    public String askUsername () {
        String username = null;

        do {

            out.println("\n\n Insert your Username and press " + AnsiCode.ANSI_ENTER_KEY + " : \n");


            if (in.hasNextLine()){

                username = in.nextLine();
                if(username.equals("")) {
                 //da inserire qui un metodo del controller che verifica che l'username sia diverso da quello degli
                 //altri giocatori prima di prenderlo
                out.println("Username not inserted or wrong!\n");
                username = null;
            }}
        }while (username== null);

        return username;

    }

    /**
     * This method asks the user which color he/she wants for the workers
     * @return string which indicates the name of the color
     */
    public String askColorWorkers() {
        String color = null;

        out.println("Here are the possible workers' colors : \n ");
        out.println(AnsiCode.ANSI_WORKER + AnsiCode.ANSI_BLUE);
        out.println(AnsiCode.ANSI_WORKER + AnsiCode.ANSI_YELLOW);
        out.println(AnsiCode.ANSI_WORKER + AnsiCode.ANSI_GREEN);
        out.println(AnsiCode.ANSI_WORKER + AnsiCode.ANSI_RED);
        out.println(AnsiCode.ANSI_WORKER + AnsiCode.ANSI_WHITE);
        out.println(AnsiCode.ANSI_WORKER + AnsiCode.ANSI_CYAN);
        out.println(AnsiCode.ANSI_WORKER + AnsiCode.ANSI_PURPLE);

        do {
            out.println("Insert the color you prefer : \n ");

            if (in.hasNextLine())

                color = in.nextLine();
            else
                out.println("Color not inserted or not available! \n");

        }while (color == null);


        return color;
    }

    /**
     * This method asks the user to insert the address of the client/serve
     * @return address of the client/server
     */
    public String askUserAddress () {
        String address = null;

        do {

            out.println("\n\n Insert address and press " + AnsiCode.ANSI_ENTER_KEY + " : \n");


            if (in.hasNextLine()){

                address = in.nextLine();
                if(address.equals("")) {
                    //da inserire qui un metodo del controller che verifica che l'address sia tra quelli accettabili
                    //nella forma 127.0.0....
                    out.println("Address not inserted or wrong!\n");
                    address = null;
                }}
        }while (address== null);

        return address;

    }



    /**
     * This method prints the username of the player that will be the first
     */
    public void RandomPrint1stPlayer (){

        out.println("Now a Randow Player will be picked to be the first : \n ... \n .. \n . \n");

        String firstPlayer = GameController.RandomPick1stPlayer();

        out.println( firstPlayer + " you start !");

    }


    //pick three random cards

    //questo metodo sarà lo stesso per ogni volta che chiedo spostamento worker?
    //o questo è solo per inserimento iniziale?
    /**
     * This method asks the user where he/she wants to put the worker
     * @return an array of two int indicating one the row and one the column
     */
    public int[] askWhereToPositionWorker() {
        int[] newRowAndColumn = new int[2];

            out.println("Choose where to position your worker : \n ");
            out.println("Insert Row and press"+ AnsiCode.ANSI_ENTER_KEY +  ": \n ");
            newRowAndColumn[0] = in.nextInt();
            out.println("Insert Column and press"+ AnsiCode.ANSI_ENTER_KEY + ": \n ");
            newRowAndColumn[1] = in.nextInt();

        return newRowAndColumn;
    }


    /**
     * This method resets the color to the default one when called
     * @param o is the out console where I apply the reset
     */
    public void reset( PrintStream o) {
        o.println(AnsiCode.ANSI_RESET);
        o.flush();
    }

}
