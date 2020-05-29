package it.polimi.ingsw.PSP47.View.CLI;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.NetworkConnectionUtil;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.GameView;
import it.polimi.ingsw.PSP47.View.View;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CLI extends ViewObservable implements View  {
    private GameView gameView;

    private PrintSupport printSupport;
    private Scanner in;
    private PrintStream out;
    

    public CLI() {
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out);
        gameView = new GameView();
        printSupport = new PrintSupport();
        showTitle();
        setConnection();
    }


    /**
     * This method is used to print the initial Santorini Logo
     */
    private void showTitle() {

        String intro ="Welcome to the Digital Version of Santorini Board Game, \n" +
                      "programmers:AriannaGalzerano-DavideGiacomini-MonicaLeone\n" +
                      "Before starting please insert the required parameters...\n";

         printSupport.printTitle(out) ;

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printSupport.printDotSequence(out);

        out.println(AnsiCode.ANSI_CYAN + intro + AnsiCode.ANSI_RESET);

       // clearConsole();

        //othersTurn("anna");
        //gameView.setMyUsername("moni");
        //theWinnerIs("moni");
        //theLoserIs();
        //showEnd();

    }

    /**
     * This method is used to ask username and color.
     */
    public void askFirstConnection(){

        String username = askUsername();
        Color color= askColorWorkers();


        VisitableInformation visitableInformation = new VisitableInformation();
        visitableInformation.setUsername(username);
        visitableInformation.setColor(color);
        notifyViewListener(visitableInformation);
        clearConsole();

    }


    /**
     * This method asks for the username
     * @return string which becomes the username of the player
     */
    public String askUsername () {
        String username = null;

        do {

            out.println("Insert your Username and press " + AnsiCode.ANSI_ENTER_KEY + " : ");


            if (in.hasNextLine()){

                username = in.nextLine();
                if(username.equals("")) {
                 printSupport.printError(out);
                out.println(AnsiCode.ANSI_RED + "Username not inserted or wrong!\n"+ AnsiCode.ANSI_RESET);
                username = null;
            }}
        }while (username== null);

        clearConsole();
        return username;
    }

    /**
     * This method asks the user which color he/she wants for the workers
     * @return string which indicates the name of the color
     */
    public Color askColorWorkers() {
        String color = null;
        Color acceptableColor = null;

        out.println("Here are the possible workers' colors : ");
        out.println(AnsiCode.ANSI_BLUE + AnsiCode.ANSI_WORKER  + " This is blue");
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
                    printSupport.printError(out);
                    out.println(AnsiCode.ANSI_RED +"Color not available! \n"+ AnsiCode.ANSI_RESET);
                }
            }else {
                printSupport.printError(out);
                out.println(AnsiCode.ANSI_RED + "Color not inserted! \n" + AnsiCode.ANSI_RESET);
            }
        }while (color == null);

        clearConsole();

        return acceptableColor;
    }

    /**
     * This method starts the thread that will be the network handler
    */
    public void setConnection () {
        String ipAddress = askServerIpAddress(in);
        NetworkHandler networkHandler;

        try {
            networkHandler = NetworkConnectionUtil.setConnection(this, ipAddress);
        } catch (IOException e) {
            printSupport.printError(out);
            out.println("Server unreachable.");
            return;
        }
        out.println("Connected to the address " + ipAddress);

        addViewListener(networkHandler);
    }

    /**
     * This method asks the client the address to connect to.
     *
     * @param scanner the input scanner.
     * @return the ip address to connect to.
     */
    private String askServerIpAddress (Scanner scanner) {
        String address;

        do {

            System.out.println("Insert address : ");

            address = scanner.nextLine();
            if(address.equals("")) {
                System.out.println( "Address not inserted or wrong!\n");
                address = null;
            }

        }while (address== null);

        return address;
    }



    /**
     * This method asks the user which of the worker he/she wants to use
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
                printSupport.printError(out);
                out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                newRowAndColumn[0] = 9000;
            }

        }while (newRowAndColumn[0] == 9000);

        out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": ");
        do {
            try {
                newRowAndColumn[1] = Integer.parseInt(in.nextLine())-1;
            } catch (NumberFormatException e) {
                printSupport.printError(out);
                out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                newRowAndColumn[1] = 9000;
            }

        }while (newRowAndColumn[1] == 9000);

        VisitableRowsAndColumns visitableRowsAndColumns = new VisitableRowsAndColumns();
        visitableRowsAndColumns.setRowsAndColumns(newRowAndColumn);
        notifyViewListener(visitableRowsAndColumns);


        clearConsole();

    }


    /**
     * This method asks the user where he/she wants to put the worker
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
                        printSupport.printError(out);
                        out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                        newRowAndColumn[0] = 9000;
                    }

                }while (newRowAndColumn[0] == 9000);

                out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": ");
                do {
                    try {
                        newRowAndColumn[1] = Integer.parseInt(in.nextLine())-1;
                    } catch (NumberFormatException e) {
                        printSupport.printError(out);
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
                        printSupport.printError(out);
                        out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                        newRowAndColumn[2] = 9000;
                    }

                }while (newRowAndColumn[2] == 9000);

                out.println("Insert Column and press" + AnsiCode.ANSI_ENTER_KEY + ": ");
                do {
                    try {
                        newRowAndColumn[3] = Integer.parseInt(in.nextLine())-1;
                    } catch (NumberFormatException e) {
                        printSupport.printError(out);
                        out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                        newRowAndColumn[3] = 9000;
                    }

                }while (newRowAndColumn[3] == 9000);

        VisitableInitialPositions visitableRowsAndColumns = new VisitableInitialPositions();
        visitableRowsAndColumns.setRowsAndColumns(newRowAndColumn);
        notifyViewListener(visitableRowsAndColumns);

        clearConsole();


    }

    /**
     * This method asks only one of the users (the challenger) which gods will be used during the game
     * and which player will play first.
     * @param usernames the list of usernames of the players.
     */
    @Override
    public void challengerWillChooseThreeGods(ArrayList<String> usernames) {
        ArrayList<GodName> godsChosen = new ArrayList<>();
        String god ;
        GodName godName ;

        String firstPlayer = null;

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

        do {
            out.println("Choose which god you want to add in the list : you can choose " + gameView.getNumberOfPlayers() );

            if (in.hasNextLine()){
                god = in.nextLine();
                godName = GodName.getGodsNameByName(god);

                if(godName == GodName.WRONGGODNAME || godsChosen.contains(godName)) {
                    printSupport.printError(out);
                    out.println(AnsiCode.ANSI_RED +"God already chosen or wrong!\n"+ AnsiCode.ANSI_RESET);
                    i--;
                }else{
                    godsChosen.add(godName);
                }
            }else {
                printSupport.printError(out);
                out.println(AnsiCode.ANSI_RED + "God not inserted! \n" + AnsiCode.ANSI_RESET);
            }
            i++;

        }while (i < gameView.getNumberOfPlayers());

        out.println("Now you have to choose the first player of the game between ");

        for (int j = 0; j<gameView.getNumberOfPlayers(); j++) {
            out.println(AnsiCode.ANSI_RED + usernames.get(j) + " " + AnsiCode.ANSI_RESET);
        }

        do{
            out.println("Write the username of the one you choose: ");
            if (in.hasNextLine()) {
                firstPlayer = in.nextLine();

                if (!usernames.contains(firstPlayer)) {
                    firstPlayer = null;
                    printSupport.printError(out);
                    out.println(AnsiCode.ANSI_RED + "You must choose one of these names! \n" + AnsiCode.ANSI_RESET);
                }
            }
        }while (firstPlayer == null);

        VisitableListOfGods visitableGods = new VisitableListOfGods();
        visitableGods.setGodNames(godsChosen);
        visitableGods.setChosenPlayer(firstPlayer);
        notifyViewListener(visitableGods);

        clearConsole();


    }

    /**
     * This method makes the user choose his/her god between the ones already chosen by the challenger
     * @param godsChosen is the array of the available gods (chosen by the challenger)
     */
    @Override
    public void chooseYourGod(ArrayList<GodName> godsChosen){
        String god = null;
        GodName godName = null;

        out.println("These are the available gods :");
        for (GodName g: godsChosen){
            out.println( g) ;
        }


        do {
            out.println("Pick one: ");
            if (in.hasNextLine()){
                god = in.nextLine();
                godName = GodName.getGodsNameByName(god);

                if(godName == GodName.WRONGGODNAME || god.equals("")) {
                    printSupport.printError(out);
                    out.println(AnsiCode.ANSI_RED + "God not available or wrong!\n"+ AnsiCode.ANSI_RESET);
                    god = null;
                }else{
                    gameView.setMyGod(godName);
                }
            }else {
                printSupport.printError(out);
                out.println(AnsiCode.ANSI_RED + "God not inserted! \n" + AnsiCode.ANSI_RESET);
            }
        }while (god == null);

        VisitableGod visitableGodChosen = new VisitableGod();
        visitableGodChosen.setGodName(godName);
        notifyViewListener(visitableGodChosen);

        clearConsole();
    }


    /**
     * This method tells the username of the winner
     * @param usernameWinner is the username of the winner
     */
    @Override
    public void theWinnerIs(String usernameWinner ){
        if(gameView.getMyUsername().equals(usernameWinner))
            printSupport.printWin(out);
        else {
            theLoserIs();
            showImportantMessage(usernameWinner + " WON.\n\n");
        }
    }

    /**
     * This method tells the username of the winner
     */
    @Override
    public void theLoserIs( ){
        printSupport.printLost(out);
    }

    /**
     * This method prints an advice about who is currently playing.
     * @param usernameOnTurn username of the player who's doing its turn
     */
    @Override
    public void othersTurn(String usernameOnTurn) {
        if (usernameOnTurn != null) {
            printSupport.printDotSequence(out);
            out.println("It's " + usernameOnTurn + "'s Turn . . . Please Wait ");
            printSupport.printWait(out);
        }
    }

    @Override
    public void whileOthersTurn(String changes) {
        if (!gameView.isTurn()) {
            clearConsole();
            printSupport.printDotSequence(out);
            out.println(changes + "\n\n");
            printSupport.printCurrBoard(printSupport.getBOARD_PARTS(), out);
        }else{
            clearConsole();
            printSupport.printCurrBoard(printSupport.getBOARD_PARTS(), out);
        }

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
     */
    @Override
    public void askNumberOfPlayers() {
        int num ;

        out.println("Do you want to play in a game with two or three players?\n" +
                "Insert a number between 2 and 3: " + AnsiCode.ANSI_ENTER_KEY);
            do {
                try {
                    num = Integer.parseInt(in.nextLine());
                } catch (NumberFormatException e) {
                    printSupport.printError(out);
                    out.println(AnsiCode.ANSI_RED + "Insert a number !\n"+ AnsiCode.ANSI_RESET);
                    num = 0;
                }

            }while (num < 2 || num > 3);

        VisitableInt visitableNumber = new VisitableInt(num);
        notifyViewListener(visitableNumber);

        clearConsole();

    }

    /**
     * This is the basic method to ask what the user wants to do in its turn.
     */
    @Override
    public void askAction(){

        out.println("\n\nHere are the possible actions:  move direction  /  build direction  /  buildDome direction / end / quit ");
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
                else if (!(actionInserted.equals(Action.END) || actionInserted.equals(Action.QUIT)) && (directionInserted == Direction.WRONGDIRECTION || directionInserted == null))
                    line = null;
                else if (line.equals(""))
                    line = null;
                else if ((actionInserted.equals(Action.END) || actionInserted.equals(Action.QUIT)) && stringParts.length >1)
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

        clearConsole();
    }

    /**
     * This method prints an error message.
     * @param text the text about the kind of error.
     */
    @Override
    public void showErrorMessage(String text) {
        printSupport.printError(out);
        out.println(AnsiCode.ANSI_RED +text + AnsiCode.ANSI_RESET);
    }

    /**
     * This method prints an information about the game.
     * @param text the text about the information.
     */
    @Override
    public void showImportantMessage(String text) {

        out.println(AnsiCode.ANSI_GREEN +text + AnsiCode.ANSI_RESET);
    }

    /**
     * Clears the console
     */
    private void clearConsole() {
        out.print(AnsiCode.ANSI_CLEARCONSOLE);
        out.flush();
    }

    public void showEnd(){
        printSupport.printGoodBye(out);
    }

    @Override
    public void showNewBoard(Slot slot) {
        printSupport.buildSlot(slot);

    }

    //TODO FIX CLEARING OF CONSOLE
    private void checkClearning() {
        if (gameView.getCurrentScene()==CurrentScene.WAIT)
            clearConsole();
    }

    @Override
    public void showGame() {
        printSupport.createEmptyBoard();
        printSupport.printCurrBoard(printSupport.getBOARD_PARTS(), out);
    }


}
