package it.polimi.ingsw.PSP47.View.CLI;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.View.View;
import it.polimi.ingsw.PSP47.Visitor.*;

import java.io.PrintStream;
import java.util.*;

public class CLI extends View {


    private PrintSupport printSupport;
    private Scanner in;
    private PrintStream out;
    

    public CLI(Client client) {
        super(client);
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out);
        gameView = new GameView();
        printSupport = new PrintSupport();

    }


    /**
     * This method is used to print the initial Santorini Logo
     */
    public void showTitle() {
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

    public void askFirstConnection(){

        String username = askUsername();
        Color color= askColorWorkers();


        VisitableInformation visitableInformation = new VisitableInformation();
        visitableInformation.setUsername(username);
        visitableInformation.setColor(color);
        notifyViewListener(visitableInformation);

    }


    /**
     * This method asks for the username
     * @return string which becomes the username of the player
     */
    @Override
    public String askUsername () {
        String username = null;

        do {

            out.println("\nInsert your Username and press " + AnsiCode.ANSI_ENTER_KEY + " : ");


            if (in.hasNextLine()){

                username = in.nextLine();
                if(username.equals("")) {
                out.println(AnsiCode.ANSI_RED + "Username not inserted or wrong!\n"+ AnsiCode.ANSI_RESET);
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

        out.println("Here are the possible workers' colors : ");
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
                    out.println(AnsiCode.ANSI_RED +"Color not available! \n"+ AnsiCode.ANSI_RESET);
                }
            }else
                out.println(AnsiCode.ANSI_RED +"Color not inserted! \n"+ AnsiCode.ANSI_RESET);

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

            out.println("Insert address and press " + AnsiCode.ANSI_ENTER_KEY + " : ");


            if (in.hasNextLine()){

                address = in.nextLine();
                if(address.equals("")) {
                    out.println(AnsiCode.ANSI_RED+ "Address not inserted or wrong!\n"+ AnsiCode.ANSI_RESET);
                    address = null;
                }
            }
        }while (address== null);

        return address;

    }



    /**
     * This method asks the user which of the worker he/she wants to use
     * @return the gender of the worker the user wants to use
     */
    @Override
    public void askWhichWorkerToUse() {
        int[] newRowAndColumn = new int[2];

        out.println("Insert the slot of the worker you want to use :  ");
        out.println("Insert Row and press" + AnsiCode.ANSI_ENTER_KEY + ":  ");
        do {
            try {
                newRowAndColumn[0] = Integer.parseInt(in.nextLine())-1;
            } catch (NumberFormatException e) {
                out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                newRowAndColumn[0] = 9000;
            }

        }while (newRowAndColumn[0] == 9000);

        out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": ");
        do {
            try {
                newRowAndColumn[1] = Integer.parseInt(in.nextLine())-1;
            } catch (NumberFormatException e) {
                out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                newRowAndColumn[1] = 9000;
            }

        }while (newRowAndColumn[1] == 9000);

        VisitableRowsAndColumns visitableRowsAndColumns = new VisitableRowsAndColumns();
        visitableRowsAndColumns.setRowsAndColumns(newRowAndColumn);
        notifyViewListener(visitableRowsAndColumns);

        /*no more return*/

    }


    /**
     * This method asks the user where he/she wants to put the worker
     * @return an array of two int indicating one the row and one the column
     */
    public void askWhereToPositionWorkers() {
        int[] newRowAndColumn = new int[4];

                out.println("Choose where to  Initially position your workers :  ");
                out.println("Worker MALE");

                out.println("Insert Row and press" + AnsiCode.ANSI_ENTER_KEY + ":  ");
                do {
                    try {
                        newRowAndColumn[0] = Integer.parseInt(in.nextLine())-1;
                    } catch (NumberFormatException e) {
                        out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                        newRowAndColumn[0] = 9000;
                    }

                }while (newRowAndColumn[0] == 9000);

                out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": ");
                do {
                    try {
                        newRowAndColumn[1] = Integer.parseInt(in.nextLine())-1;
                    } catch (NumberFormatException e) {
                         out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                        newRowAndColumn[1] = 9000;
                    }

                }while (newRowAndColumn[1] == 9000);
                out.println("Worker FEMALE");
                out.println("Insert Row and press" + AnsiCode.ANSI_ENTER_KEY + ":  ");
                do {
                    try {
                        newRowAndColumn[2] = Integer.parseInt(in.nextLine())-1;
                     } catch (NumberFormatException e) {
                        out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                        newRowAndColumn[2] = 9000;
                    }

                }while (newRowAndColumn[2] == 9000);

                out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": ");
                do {
                    try {
                        newRowAndColumn[3] = Integer.parseInt(in.nextLine())-1;
                    } catch (NumberFormatException e) {
                        out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                        newRowAndColumn[3] = 9000;
                    }

                }while (newRowAndColumn[3] == 9000);

        VisitableInitialPositions visitableRowsAndColumns = new VisitableInitialPositions();
        visitableRowsAndColumns.setRowsAndColumns(newRowAndColumn);
        notifyViewListener(visitableRowsAndColumns);


    }

    /**
     * This method asks only one of the users ( the challenger) which gods will be used during the game
     * @return an Arraylist with the names of the 2 or 3 gods that have been chosen
     */
    @Override
    public void challengerWillChooseThreeGods() {
        ArrayList<GodName> godsChosen = new ArrayList<>();
        String god ;
        GodName godName ;
        int i = 0;

        out.println("Hey you! You have been picked as Challenger ");

        out.println("Here are the gods and their powers: ");

        out.println("#1 Apollo – You may move your worker onto a space occupied by an opponent’s builder. Their builder will be moved to the space your builder was just on.");

        out.println("#2 Artemis – You may move your builders two spaces but you may not move back to the space you started your turn on.");

        out.println("#3 Athena – If you moved one of your workers up one level on your previous turn, your opponent may not move up a level on their turn.");

        out.println("#4 Atlas – Your builders can build a dome on any level including the ground.");

        out.println("#5 Demeter – Your builders can build twice on your turn, but may not build twice on the same space.");

        out.println("#6 Hephaestus – Your builders can build twice on the same space. They may not use this ability to place a dome though.");

        out.println("#7 Minotaur – You may move your builder onto a space occupied by an opponent’s builder if the next space in the same direction is unoccupied. You will push the other player’s builder into the next space in the same direction.");

        out.println("#8 Pan – If one of your builders moves down two spaces in one movement you will automatically win the game.");

        out.println("#9 Prometheus – If you don’t move up a level during your turn you may build before and after you move.");

        out.println("#10 Hestia - Your worker may build one additional time. The additional build cannot be on a perimeter space.");

        out.println("#11 Hera - An opponent cannot win by moving on to a perimeter space.");

        out.println("#12 Chronus - You also win when there are at least five complete Towers on the board.");

        out.println("#13 Triton - Each time your Worker moves onto a perimeter space (ground or block), it may immediately move again.");

        out.println("#14 Zeus - your Worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up the 3rd level.");
        //TODO AGGIUNGERE GETPOWER DEGLI DEI

        do {
            out.println("Choose which god you want to add in the list : you can choose " + gameView.getNumberOfPlayers() );

            if (in.hasNextLine()){
                god = in.nextLine();
                godName = GodName.getGodsNameByName(god);

                if(godName == GodName.WRONGGODNAME || godsChosen.contains(godName)) {
                    out.println(AnsiCode.ANSI_RED +"God already chosen or wrong!\n"+ AnsiCode.ANSI_RESET);
                    i--;
                }else{
                    godsChosen.add(godName);
                }
            }else
                out.println(AnsiCode.ANSI_RED + "God not inserted! \n"+ AnsiCode.ANSI_RESET);

            i++;

        }while (i < gameView.getNumberOfPlayers());

        VisitableListOfGods visitableGods = new VisitableListOfGods();
        visitableGods.setGodNames(godsChosen);
        notifyViewListener(visitableGods);


    }

    /**
     * This method makes the user choose his/her god between the ones already chosen by the challenger
     * @param godsChosen is the array of the available gods (chosen by the challenger)
     * @return the God chosen by the client
     */
    @Override
    public void chooseYourGod(ArrayList<GodName> godsChosen){
        String god = null;
        GodName godName = null;

        out.println("These are the available gods :");
        for (GodName g: godsChosen){
            System.out.println( g) ;
        }


        do {
            out.println("Pick one: ");
            if (in.hasNextLine()){
                god = in.nextLine();
                godName = GodName.getGodsNameByName(god);

                if(godName == GodName.WRONGGODNAME || god.equals("")) {
                    out.println(AnsiCode.ANSI_RED + "God not available or wrong!\n"+ AnsiCode.ANSI_RESET);
                    god = null;
                }else{
                    gameView.setMyGod(godName);
                }
            }else
                out.println(AnsiCode.ANSI_RED + "God not inserted! \n"+ AnsiCode.ANSI_RESET);

        }while (god == null);

        VisitableGod visitableGodChosen = new VisitableGod();
        visitableGodChosen.setGodName(godName);
        notifyViewListener(visitableGodChosen);
    }


    public void tellYourTurnIndex(int Index){
        gameView.setMyIndex(Index);
        out.println("You're the "+ Index + " player in the game. \n");
    }


    /**
     * This method tells the username of the winner
     * @param usernameWinner is the username of the winner
     */
    @Override
    public void theWinnerIs(String usernameWinner ){
        out.println("\n\n THE WINNER IS : "+ usernameWinner);
    }

    /**
     * This method tells the username of the winner
     * @param usernameLoser is the username of the winner
     */
    @Override
    public void theLoserIs(String usernameLoser ){
        out.println("\n\n" + usernameLoser + " you lost. Your adventure ends here \n ");
    }
    
    @Override
    public GameView getGameView() {
        return gameView;
    }

    /**
     * This method resets the color to the default one when called
     * @param o is the out console where I apply the reset
     */
    public void reset( PrintStream o) {
        o.println(AnsiCode.ANSI_RESET);
        o.flush();
    }

    /**
     * This method is used to ask the numbers of players of the game
     * @return int to indicate the number chosen
     */
    @Override
    public void askNumberOfPlayers() {
        int num ;

        out.println("How many players do you want in the game? Insert a number between 2 and 3");
            do {
                try {
                    num = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException e) {
                    out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                    num = 0;
                }

            }while (num == 0 || num < 2 || num > 3);

        VisitableInt visitableNumber = new VisitableInt(num);
        notifyViewListener(visitableNumber);



    }

    /**
     * This is the basic method to ask what the user wants to do in its turn.
     * @return an arraylist which contains as first parameter the enum Action and as second the enum Direction
     */
    @Override
    public void askAction(){
        out.println("Here are the possible actions:  move direction  /  build direction  /  buildDome direction / end / ");
        out.println("The available directions are : "+ Arrays.toString(Direction.values())+ "\n");


        String line = null;
        String[] stringParts ;
        Action actionInserted =null ;
        Direction directionInserted= null;
        ArrayList<Enum> ActionAndDirection = new ArrayList<>();
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

        VisitableActionAndDirection visitableActionAndDirection = new VisitableActionAndDirection();
        visitableActionAndDirection.setAction(actionInserted);
        visitableActionAndDirection.setDirection(directionInserted);
        notifyViewListener(visitableActionAndDirection);
    }

    /**
     * This method is used to show the initial public information about all the clients in the game
     */
    @Override
    public void showPublicInformation(){
        ArrayList<String> usernames ;
         ArrayList<Color> colors ;
         ArrayList <GodName> gods ;

        usernames = gameView.getUsernames();
        colors = gameView.getColors();
        gods = gameView.getGods();
       printSupport.printUsersAndColorsAndGods(usernames, colors, gods, gameView.getNumberOfPlayers(), out);
    }



    @Override
    public void showErrorMessage(String text) {
        System.out.println(AnsiCode.ANSI_RED +text + AnsiCode.ANSI_RESET);
    }

    @Override
    public void showImportantMessage(String text) {
        System.out.println(AnsiCode.ANSI_GREEN +text + AnsiCode.ANSI_RESET);
    }


    /**
     * this method calls the print support that prints the updated board of the game
     */
    @Override
    public void showCurrentBoard(){
        printSupport.printCurrBoard(printSupport.buildCurrBoard(gameView.getBoardView()), out);
    }

}
