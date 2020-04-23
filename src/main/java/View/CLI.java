package View;

import Controller.GameController;
import Network.Client.Client;

import java.awt.*;
import java.io.PrintStream;
import java.util.Scanner;

public class CLI extends View {


    private Scanner in;
    private PrintStream out;

    //main per prova
    /*public static void main(String[] args)
    {
        CLI c = new CLI();

        System.out.println (AnsiCode.ANSI_DOME + AnsiCode.ANSI_LEVEL2 + AnsiCode.ANSI_LEVEL3);


        c.printSantorini() ;

        String un = c.askUsername() ;

        System.out.println( un + " is your username! \n");

        String color = c.askColorWorkers() ;

        AnsiCode a = new AnsiCode();
        String ansiColor = a.getAnsiByName(color) ;

        String worker = a.getAnsiByName("WORKER");

        System.out.println( ansiColor + worker) ;
        PrintSupport p = new PrintSupport();


        c.reset(c.out);

        System.out.println("\n Now the empty Board will be printed : \n"+AnsiCode.ANSI_RESET);

        p.PrintEmptyBoard(c.out);
        p.clearConsole(c.out);

        Board board = Board.getBoard();
        Player player = new Player("first", Color.LIGHT_GRAY);
        Worker workerMale = player.getWorker(Gender.MALE);

        workerMale.setSlot(board.getSlot(1,3));
        //board.getSlot(1,3).setWorker(workerMale);

        board.getSlot(2,2).setLevel(Level.DOME);
        p.printCurrBoard(p.buildCurrBoard(board),c.out);

        p.clearConsole(c.out);

    }*/

    //costruttore
    public CLI(Client client) {
        super(client);
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out);
    }

    /**
     * This method starts the connection with the server asking for the ip, the username and the color.
     */
    @Override
    public void startConnection(){
        String ip;
        printSantorini();

        do {
            System.out.println("IP address of server?");
            ip = in.nextLine();
        } while (!ip.equals("127.0.0.1"));

        String username = askUsername();

        Color color = askColorWorkers();

       // currentClient.setConnection(ip, SOCKET_PORT, username, color);

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
    public Color askColorWorkers() {
        String color = null;

        out.println("Here are the possible workers' colors : \n ");
        out.println(AnsiCode.ANSI_BLUE + AnsiCode.ANSI_WORKER + " This is blue");
        out.println(AnsiCode.ANSI_YELLOW + AnsiCode.ANSI_WORKER + " This is yellow");
        out.println(AnsiCode.ANSI_GREEN + AnsiCode.ANSI_WORKER +" This is green");
        out.println(AnsiCode.ANSI_RED + AnsiCode.ANSI_WORKER +" This is red");
        out.println(AnsiCode.ANSI_WHITE + AnsiCode.ANSI_WORKER + " This is white");
        out.println(AnsiCode.ANSI_CYAN + AnsiCode.ANSI_WORKER + " This is cyan");
        out.println(AnsiCode.ANSI_PURPLE + AnsiCode.ANSI_WORKER + " This is purple" + AnsiCode.ANSI_RESET);

        do {
            out.println("Insert the color you prefer : \n ");

            if (in.hasNextLine())
                color = in.nextLine();
            else
                out.println("Color not inserted or not available! \n");

        }while (color == null);
    
        return stringToColor(color);
    }
    /**
     * @deprecated
     * for debugging, it has to be deleted.
     */
    private Color stringToColor(String string){
        String str = string.toUpperCase();
        switch (str){
            case "BLUE":
                return Color.BLUE;
            case "YELLOW":
                return Color.YELLOW;
            case "GREEN":
                return Color.GREEN;
            case "RED":
                return Color.RED;
            case "WHITE":
                return Color.WHITE;
            case "CYAN":
                return Color.CYAN;
            case "PURPLE":
                return Color.MAGENTA;
            default:
                return Color.BLACK;
        }
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

    public void print(String text) {
        System.out.println(text);
    }
    
    @Override
    public int askNumberOfPlayers() {
        int numberOfPlayers = 0;
        
        out.println("How many players do you want in the game?");
        if (in.hasNextLine())
            numberOfPlayers = Integer.parseInt(in.nextLine());
        
        return numberOfPlayers;
    }
    
    @Override
    public String askServerIpAddress() {
        String ipAddress = null;
    
        out.println("Write server address?");
        if (in.hasNextLine())
            ipAddress = in.nextLine();
    
        return ipAddress;
    }
    
}
