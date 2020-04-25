package View;

import Controller.GameController;
import Enumerations.*;
import Network.Client.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class CLI extends View {

    private ViewDatabase viewDatabase;
    private PrintSupport printSupport;
    private Scanner in;
    private PrintStream out;

    //main per prova
    public static void main(String[] args) {
        CLI c = new CLI(null);
        ArrayList<GodName> gods = null;
        boolean t ;
        ArrayList<Enum> arr = new ArrayList<Enum>();
        arr = c.askAction();
       System.out.println(arr);

       /* c.viewDatabase.setNumberOfPlayers(3);
        gods = c.challengerWillChooseThreeGods();

        c.viewDatabase.setMyGod(GodName.ATLAS);*/

       // t = c.askIfAtlasWantsToBuildDome();


    }
/*
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

    //Constructor
    public CLI(Client client) {
        super(client);
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out);
        viewDatabase = new ViewDatabase();
        printSupport = new PrintSupport();
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
    @Override
    public String askUsername () {
        String username = null;

        do {

            out.println("\n\n Insert your Username and press " + AnsiCode.ANSI_ENTER_KEY + " : ");


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
    @Override
    public Color askColorWorkers() {
        String color = null;
        Color acceptableColor = null;

        out.println("Here are the possible workers' colors : \n ");
        out.println(AnsiCode.ANSI_BLUE + AnsiCode.ANSI_WORKER + " This is blue");
        out.println(AnsiCode.ANSI_YELLOW + AnsiCode.ANSI_WORKER + " This is yellow");
        out.println(AnsiCode.ANSI_GREEN + AnsiCode.ANSI_WORKER +" This is green");
        out.println(AnsiCode.ANSI_RED + AnsiCode.ANSI_WORKER +" This is red");
        out.println(AnsiCode.ANSI_WHITE + AnsiCode.ANSI_WORKER + " This is white");
        out.println(AnsiCode.ANSI_CYAN + AnsiCode.ANSI_WORKER + " This is cyan");
        out.println(AnsiCode.ANSI_PURPLE + AnsiCode.ANSI_WORKER + " This is purple" + AnsiCode.ANSI_RESET);

        do {
            out.println("Insert the color you prefer :  ");

            if (in.hasNextLine()) {
                color = in.nextLine();
                acceptableColor = Color.getColorByName(color);
                if (acceptableColor == Color.WRONGCOLOR) {
                    color = null;
                    out.println("Color not available! \n");
                }
            }else
                out.println("Color not inserted! \n");

        }while (color == null);

        return acceptableColor;
    }

    /**
     * This method asks the user to insert the address of the serve
     * @return address of the client/server
     */
    @Override
    public String askServerIpAddress () {
        String address = null;

        do {

            out.println("\n\n Insert address and press " + AnsiCode.ANSI_ENTER_KEY + " : ");


            if (in.hasNextLine()){

                address = in.nextLine();
                if(address.equals("")) {
                    out.println("Address not inserted or wrong!\n");
                    address = null;
                }
            }
        }while (address== null);

        return address;

    }


    /*

    public void RandomPrint1stPlayer (){

        out.println("Now a Randow Player will be picked to be the first : \n ... \n .. \n . \n");

        String firstPlayer = GameController.RandomPick1stPlayer();

        out.println( firstPlayer + " you start !");

    }*/

    /**
     * This method asks the user which of the worker he/she wants to use
     * @return the gender of the worker the user wants to use
     */
    @Override
    public Gender askWhichWorkerToUse() {
        String worker = null;
        Gender workerGender = null;

        do {
        out.println("Choose which worker you wanna use? Male or Female?  ");

        if (in.hasNextLine()){
            worker = in.nextLine();
            workerGender = Gender.getGenderByName(worker);
            if(workerGender == Gender.WRONGGENDER) {
                    out.println("Worker not inserted or wrong!\n");
                    worker = null;
                }
            }else
            out.println("Worker not inserted! \n");

        }while (worker== null);

        return workerGender;
    }


    /**
     * This method asks the user where he/she wants to put the worker
     * @return an array of two int indicating one the row and one the column
     */
    public int[] askWhereToPositionWorkers() {
        int[] newRowAndColumn = new int[4];

                out.println("Choose where to  Initially position your workers :  ");
                out.println("Worker MALE");
                out.println("Insert Row and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
                newRowAndColumn[0] = in.nextInt();
                out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
                newRowAndColumn[1] = in.nextInt();
                out.println("Worker FEMALE");
                out.println("Insert Row and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
                newRowAndColumn[2] = in.nextInt();
                out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
                newRowAndColumn[3] = in.nextInt();


        return newRowAndColumn;
    }

    /**
     * This method asks only one of the users ( the challenger) which gods will be used during the game
     * @return an Arraylist with the names of the 2 or 3 gods that have been chosen
     */
    @Override
    public ArrayList<GodName> challengerWillChooseThreeGods() {
        ArrayList<GodName> godsChosen = new ArrayList<>();
        String god = null;
        GodName godName ;
        int i = 0;

        out.println("Hey you! You have been picked as Challenger \n ");

        out.println("Here are the gods and their powers: \n");

        out.println("#1 Apollo – You may move your worker onto a space occupied by an opponent’s builder. Their builder will be moved to the space your builder was just on.\n");

        out.println("#2 Artemis – You may move your builders two spaces but you may not move back to the space you started your turn on.\n");

        out.println("#3 Athena – If you moved one of your workers up one level on your previous turn, your opponent may not move up a level on their turn.\n");

        out.println("#4 Atlas – Your builders can build a dome on any level including the ground.\n");

        out.println("#5 Demeter – Your builders can build twice on your turn, but may not build twice on the same space.\n");

        out.println("#6 Hephaestus – Your builders can build twice on the same space. They may not use this ability to place a dome though.\n");

        out.println("#7 Minotaur – You may move your builder onto a space occupied by an opponent’s builder if the next space in the same direction is unoccupied. You will push the other player’s builder into the next space in the same direction.\n");

        out.println("#8 Pan – If one of your builders moves down two spaces in one movement you will automatically win the game.\n");

        out.println("#9 Prometheus – If you don’t move up a level during your turn you may build before and after you move.\n");


        do {
            out.println("Choose which god you want to add in the list : you can choose " + viewDatabase.getNumberOfPlayers() );

            if (in.hasNextLine()){
                god = in.nextLine();
                godName = GodName.getGodsNameByName(god);

                if(godName == GodName.WRONGGODNAME || godsChosen.contains(godName)) {
                    out.println("God already chosen or wrong!\n");
                    i--;
                }else{
                    godsChosen.add(godName);
                }
            }else
                out.println("God not inserted! \n");

            i++;

        }while (i < viewDatabase.getNumberOfPlayers());

        return godsChosen;

    }

    /**
     * This method makes the user choose his/her god between the ones already chosen by the challenger
     * @param godsChosen is the array of the available gods (chosen by the challenger)
     * @return
     */
    @Override
    public GodName chooseYourGod(ArrayList<GodName> godsChosen){
        String god = null;
        GodName godName = null;

        out.println("These are the available gods : \n");
        for (GodName g: godsChosen){
            System.out.println( g) ;
        }


        do {
            out.println("Pick one: \n");
            if (in.hasNextLine()){
                god = in.nextLine();
                godName = GodName.getGodsNameByName(god);

                if(godName == GodName.WRONGGODNAME || !godsChosen.contains(godName) || god.equals("")) {
                    out.println("God not available or wrong!\n");
                    god = null;
                }else{
                    viewDatabase.setMyGod(godName);
                }
            }else
                out.println("God not inserted! \n");

        }while (god == null);

        return godName;

    }

    @Override
    public int[] askWhereToMoveWorkers() {
        return new int[0];
    }

    @Override
    public int[] askWhereToBuildWorkers() {
        return new int[0];
    }


    public void tellYourTurnIndex(int Index){
        viewDatabase.setMyIndex(Index);
        out.println("You're the "+ Index + " player in the game. \n");
    }


    /*public int[] askWhereToMoveWorkers() {
        int[] newRowAndColumn = new int[2];

        out.println("Choose where to  move your worker : \n ");
            out.println("Worker ");
            out.println("Insert Row and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
            newRowAndColumn[0] = in.nextInt();
            out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
            newRowAndColumn[1] = in.nextInt();

        return newRowAndColumn;
    }

    public int[] askWhereToBuildWorkers() {
        int[] newRowAndColumn = new int[2];

        out.println("Choose where to build : \n ");
        out.println("Insert Row and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
        newRowAndColumn[0] = in.nextInt();
        out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": \n ");
        newRowAndColumn[1] = in.nextInt();

        return newRowAndColumn;
    }*/

    /**
     * This method tells the username of the winner
     * @param usernameWinner is the username of the winner
     */
    public void theWinnerIs(String usernameWinner ){
        out.println("\n\n THE WINNER IS : "+ usernameWinner);
    }
    /**
     * This method tells the username of the winner
     * @param usernameLoser is the username of the winner
     */
    public void theLoserIs(String usernameLoser ){
        out.println("\n\n" + usernameLoser + " you lost. Your adventure ends here \n ");
    }
    
    @Override
    public ViewDatabase getViewDatabase() {
        return viewDatabase;
    }
    
    /*public boolean askIfAtlasWantsToBuildDome(){
        String Dome = null;
        if (viewDatabase.getMyGod()== GodName.ATLAS) {
            out.println("Do you want to build a Dome? Yes/No");

            do {
                if (in.hasNextLine()) {
                    Dome = in.nextLine();

                    if (Dome.toUpperCase().equals("YES")) {
                        return true;
                    } else if (Dome.toUpperCase().equals("NO")) {
                        return false;
                    }
                    else Dome = null;
                    out.println("Answer pls \n");
                } else
                    out.println("Answer pls \n");

            } while (Dome == null);
        }
        return false;
    }*/

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

    /**
     * This method is used to ask the numbers of players of the game
     * @return int to indicate the number chosen
     */
    @Override
    public int askNumberOfPlayers() {
        int num = 0;

        out.println("How many players do you want in the game? Insert a number between 2 and 3");
        //do {
            if (in.hasNextLine()) {
                //try {
                    num = in.nextInt();
                //}catch(InputMismatchException e ){
                   //out.println("Insert a number");
                  // num = 0;
                //}
            }


        //}while(num == 0);

        return num;
    }

    /**
     * This is the basic method to ask what the user wants to do in its turn.
     * @return an arraylist which contains as first parameter the enum Action and as second the enum Direction
     */
    public ArrayList<Enum> askAction(){
        out.println("Here are the possible actions:  move direction  /  build direction  /  buildDome direction / end / ");
        out.println("The available directions are : "+ Arrays.toString(Direction.values())+ "\n");


        String line = null;
        String[] stringParts = null;
        Action actionInserted = null;
        Direction directionInserted= null;
        ArrayList<Enum> ActionAndDirection = new ArrayList<Enum>();
        do {
            out.println("Insert action :");

            if (in.hasNextLine()) {
                line = in.nextLine();
                stringParts = line.split(" ");
                actionInserted = Action.getActionByName(stringParts[0]);
                if (stringParts.length>1)
                directionInserted = Direction.getDirectionByName(stringParts[1]);

                if(actionInserted == Action.WRONGACTION)
                    line = null;
                else if (!actionInserted.equals(Action.END) && (directionInserted == Direction.WRONGDIRECTION || directionInserted == null))
                    line = null;
                else if (line.equals(""))
                    line = null;
                else if (actionInserted.equals(Action.END) && stringParts.length >1)
                    line = null;
                else{
                    ActionAndDirection.add(actionInserted);
                    ActionAndDirection.add(directionInserted);
                }
            }

        } while (line == null);

        return ActionAndDirection;
    }

    public void showPublicInformation(){
        ArrayList<String> usernames = new ArrayList<String>(getViewDatabase().getNumberOfPlayers());
         ArrayList<Color> colors = new ArrayList<Color>(getViewDatabase().getNumberOfPlayers());
         ArrayList <GodName> gods = new ArrayList<GodName>(getViewDatabase().getNumberOfPlayers());

        usernames = viewDatabase.getUsernames();
        colors = viewDatabase.getColors();
        gods = viewDatabase.getGods();
       printSupport.printUsersAndColorsAndGods(usernames, colors, gods, viewDatabase.getNumberOfPlayers(), out);
    }

    public void showCurrentBoard(){
        printSupport.printCurrBoard(printSupport.buildCurrBoard(viewDatabase.getBoardView()), out);
    }

}
