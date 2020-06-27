package it.polimi.ingsw.PSP47.View.CLI;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PrintSupport {

    private static final String UPPER_PART_SLOT = " ________ ";
    private static final String LOWER_FREE_PART_SLOT = "|________|";
    private static final String MIDDLE_FREE_PART_SLOT ="|        |";

    private static final String WITH_LEV1_PART_SLOT = "|___"+AnsiCode.ANSI_LEVEL1+ " ___|";
    private static final String WITH_LEV2_PART_SLOT = "|   "+AnsiCode.ANSI_LEVEL2+ "    |";
    private static final String WITH_LEV3_PART_SLOT = "|   " + AnsiCode.ANSI_LEVEL3+ "   |";
    private static final String WITH_DOME_PART_SLOT = "|   " + AnsiCode.ANSI_DOME +  "   |";
    private static final String WITH_DOME_ATLAS_PART_SLOT = "|___" + AnsiCode.ANSI_DOME + "___|";

    private String[][][] BOARD_PARTS = new String[5][5][5];
    private static final String[] EMPTY_PARTS = new String[5];

    public PrintSupport() {
        EMPTY_PARTS[0]= UPPER_PART_SLOT;
        EMPTY_PARTS[1]= MIDDLE_FREE_PART_SLOT;
        EMPTY_PARTS[2]= MIDDLE_FREE_PART_SLOT;
        EMPTY_PARTS[3]= MIDDLE_FREE_PART_SLOT;
        EMPTY_PARTS[4]= LOWER_FREE_PART_SLOT;
    }

    /**This method prints an empty board */
    public void createEmptyBoard(){
            for (int j = 0 ; j<5; j++){
                for (int k = 0; k<5 ; k++){
                    BOARD_PARTS[j][k] = EMPTY_PARTS ;
                }
            }
    }

    /**
     * This method prints the board created graphically by buildCurrBoard method
     * @param finishedBoard is the graphical representation of the board
     * @param out is the Stream where to print it
     */
    public void printCurrBoard (String[][][] finishedBoard, PrintStream out){
        int count = 1;
        out.println("    1         2         3         4         5");
        for (int i = 0 ; i<5; i++){
            for (int j = 0 ; j<5; j++){
                for (int k = 0; k<5 ; k++){

                    out.print(finishedBoard[i][k][j]);

                    if (j == 2 && k==4) {
                        out.print(" "+count);
                    count ++;
                    }
                }
                out.print("\n");
            }
        }
        out.print("\n");
    }

    /**
     * This method prints information
     * @param usernames list of usernames in the game
     * @param colors list of colors in the game
     * @param gods list of gods in the game
     * @param numOfPlayers in the game
     * @param out Stream where to print
     */
    public void printUsersAndColorsAndGods(ArrayList<String> usernames, ArrayList<Color> colors, ArrayList<GodName> gods, int numOfPlayers, PrintStream out){
        for (int i = 0 ; i < numOfPlayers; i++ ){
            out.println( "Name : " + usernames.get(i)+ "; Color : "+ colors.get(i) + "; God : "+ gods.get(i));
        }
    }

    public void printTitle(PrintStream out){
        String Title =
        " ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄\n"+
        "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░░▌\n"+
        "▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌     ▐░▌ ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░▌░▌     ▐░▌ ▀▀▀▀█░█▀▀▀▀ \n"+
        "▐░▌          ▐░▌       ▐░▌▐░▌▐░▌    ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌▐░▌    ▐░▌     ▐░▌     \n"+
        "▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌▐░▌ ▐░▌   ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░▌ ▐░▌   ▐░▌     ▐░▌     \n"+
        "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░░░░░░░░░░░▌     ▐░▌     ▐░▌  ▐░▌  ▐░▌     ▐░▌     \n"+
        " ▀▀▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌   ▐░▌ ▐░▌     ▐░▌     ▐░▌       ▐░▌▐░█▀▀▀▀█░█▀▀      ▐░▌     ▐░▌   ▐░▌ ▐░▌     ▐░▌    \n"+
        "          ▐░▌▐░▌       ▐░▌▐░▌    ▐░▌▐░▌     ▐░▌     ▐░▌       ▐░▌▐░▌     ▐░▌       ▐░▌     ▐░▌    ▐░▌▐░▌     ▐░▌    \n"+
        " ▄▄▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░▌     ▐░▐░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░▌      ▐░▌  ▄▄▄▄█░█▄▄▄▄ ▐░▌     ▐░▐░▌ ▄▄▄▄█░█▄▄▄▄\n"+
        "▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌      ▐░░▌     ▐░▌     ▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌      ▐░░▌▐░░░░░░░░░░░▌\n"+
        " ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀        ▀▀       ▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀        ▀▀  ▀▀▀▀▀▀▀▀▀▀▀ \n";

        out.println( AnsiCode.ANSI_BLUE + Title + AnsiCode.ANSI_RESET);
    }

    public void printDotSequence(PrintStream out){
        String dot = " ▐░▌  ";
        for (int i = 0; i< 3 ; i++){
            out.print(AnsiCode.ANSI_WHITE + dot);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        out.print("\n"+ AnsiCode.ANSI_RESET);
    }

    public void printWait(PrintStream out){
        String wait =
                "\n\n+====+\n" +
                "|(::)|\n" +
                 "| )( |\n" +
                 "|(..)|\n" +
                  "+====+\n\n";

        out.print(AnsiCode.ANSI_YELLOW+ wait + AnsiCode.ANSI_RESET);
    }

    public void printError(PrintStream out){
        String error =
                "███████╗██████╗ ██████╗  ██████╗ ██████╗     ██╗\n"+
                "██╔════╝██╔══██╗██╔══██╗██╔═══██╗██╔══██╗    ██║\n"+
                "█████╗  ██████╔╝██████╔╝██║   ██║██████╔╝    ██║\n"+
                "██╔══╝  ██╔══██╗██╔══██╗██║   ██║██╔══██╗    ╚═╝\n"+
                "███████╗██║  ██║██║  ██║╚██████╔╝██║  ██║    ██╗\n"+
                "╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝    ╚═╝\n";
        out.print(AnsiCode.ANSI_RED+ error + AnsiCode.ANSI_RESET);
    }

    public void printWin(PrintStream out){
        String win =
                    " ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄       ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄\n"+
                    "▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌     ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░░▌      ▐░▌\n"+
                    "▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌     ▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌     ▐░▌\n"+
                    "▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌▐░▌    ▐░▌\n"+
                    "▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌   ▄   ▐░▌▐░▌       ▐░▌▐░▌ ▐░▌   ▐░▌\n"+
                    "▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌  ▐░▌  ▐░▌▐░▌       ▐░▌▐░▌  ▐░▌  ▐░▌\n"+
                    " ▀▀▀▀█░█▀▀▀▀ ▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌ ▐░▌░▌ ▐░▌▐░▌       ▐░▌▐░▌   ▐░▌ ▐░▌\n"+
                    "     ▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌▐░▌ ▐░▌▐░▌▐░▌       ▐░▌▐░▌    ▐░▌▐░▌\n"+
                    "     ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌░▌   ▐░▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌     ▐░▐░▌\n"+
                    "     ▐░▌     ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░░▌     ▐░░▌▐░░░░░░░░░░░▌▐░▌      ▐░░▌\n"+
                    "      ▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀       ▀▀       ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀        ▀▀\n";
        String exclamationMark =" ▄\n"+
                                "▐░▌\n"+
                                "▐░▌\n"+
                                "▐░▌\n"+
                                "▐░▌\n"+
                                "▐░▌\n"+
                                "▐░▌\n"+
                                " ▀ \n"+
                                " ▄ \n"+
                                "▐░▌\n"+
                                " ▀ \n";

        String exclamationMarkSpace =
                "            ▄\n"+
                "           ▐░▌\n"+
                "           ▐░▌\n"+
                "           ▐░▌\n"+
                "           ▐░▌\n"+
                "           ▐░▌\n"+
                "           ▐░▌\n"+
                "            ▀ \n"+
                "            ▄ \n"+
                "           ▐░▌\n"+
                "            ▀ \n";
        String exclamationMarkSpace2 =
                "                            ▄\n"+
                "                           ▐░▌\n"+
                "                           ▐░▌\n"+
                "                           ▐░▌\n"+
                "                           ▐░▌\n"+
                "                           ▐░▌\n"+
                "                           ▐░▌\n"+
                "                            ▀ \n"+
                "                            ▄ \n"+
                "                           ▐░▌\n"+
                "                            ▀ \n";


        out.print(AnsiCode.ANSI_GREEN+ win);

        out.print( exclamationMark);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }

        out.print( exclamationMarkSpace);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }

        out.print( exclamationMarkSpace2);
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }

        out.print("\n"+ AnsiCode.ANSI_RESET);
    }


    public void printLost(PrintStream out){
        String lost =
                       " ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄       ▄            ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄\n"+
                       "▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌     ▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌\n"+
                       "▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌     ▐░▌          ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀  ▀▀▀▀█░█▀▀▀▀ \n"+
                       "▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌          ▐░▌       ▐░▌▐░▌               ▐░▌  \n"+
                       "▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌          ▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄      ▐░▌     \n"+
                       "▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌          ▐░▌       ▐░▌▐░░░░░░░░░░░▌     ▐░▌ \n"+
                       " ▀▀▀▀█░█▀▀▀▀ ▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌          ▐░▌       ▐░▌ ▀▀▀▀▀▀▀▀▀█░▌     ▐░▌\n"+
                       "     ▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌          ▐░▌       ▐░▌          ▐░▌     ▐░▌\n"+
                       "     ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌ ▄▄▄▄▄▄▄▄▄█░▌     ▐░▌ \n"+
                       "     ▐░▌     ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌ \n"+
                       "      ▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀       ▀\n";

        out.print(AnsiCode.ANSI_WHITE+ lost + AnsiCode.ANSI_RESET);
    }

    public void printGoodBye(PrintStream out){
        String bye =
                 "▄▄▄▄▄▄▄▄▄▄   ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄       ▄▄▄▄▄▄▄▄▄▄   ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄\n"+
                "▐░░░░░░░░░░▌ ▐░▌       ▐░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░▌ ▐░▌       ▐░▌▐░░░░░░░░░░░▌\n"+
                "▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀      ▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ \n"+
                "▐░▌       ▐░▌▐░▌       ▐░▌▐░▌               ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌          \n"+
                "▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄      ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄ \n"+
                "▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌\n"+
                "▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀      ▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀ \n"+
                "▐░▌       ▐░▌     ▐░▌     ▐░▌               ▐░▌       ▐░▌     ▐░▌     ▐░▌          \n"+
                "▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄▄▄      ▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░█▄▄▄▄▄▄▄▄▄ \n"+
                "▐░░░░░░░░░░▌      ▐░▌     ▐░░░░░░░░░░░▌     ▐░░░░░░░░░░▌      ▐░▌     ▐░░░░░░░░░░░▌\n"+
                " ▀▀▀▀▀▀▀▀▀▀        ▀       ▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀        ▀       ▀▀▀▀▀▀▀▀▀▀▀\n";
        out.println();
        out.println();
        printDotSequence(out);
        out.println();
        out.println();
        out.print(AnsiCode.ANSI_BLUE+ bye + AnsiCode.ANSI_RESET);

    }


    public String[][][] getBOARD_PARTS() {
        return BOARD_PARTS;
    }

    public void buildSlot(Slot slot){
        int level = slot.getLevel().ordinal();
        String[] SLOT_PARTS = new String[5];

        if ( slot.isWorkerOnSlot() ) {
            String color = AnsiCode.getAnsiByName(slot.getWorkerColor().toString());
            String WITH_WORKER_LOWER_SLOT = "|___" + color + AnsiCode.ANSI_WORKER + AnsiCode.ANSI_RESET + "____|";
            String WITH_WORKER_PART_SLOT =  "|   " + color + AnsiCode.ANSI_WORKER + AnsiCode.ANSI_RESET + "    |";

            switch (level){
                case 0 :
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[3]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[4]= WITH_WORKER_LOWER_SLOT;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;

                case 1 :
                    SLOT_PARTS[0]=UPPER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[3]= WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT ;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;

                case 2 :
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[3]=  WITH_LEV2_PART_SLOT ;
                    SLOT_PARTS[4]=  WITH_LEV1_PART_SLOT ;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;

                case 3 :
                    SLOT_PARTS[0]=  UPPER_PART_SLOT;
                    SLOT_PARTS[1]=  WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[2]=  WITH_LEV3_PART_SLOT;
                    SLOT_PARTS[3]=  WITH_LEV2_PART_SLOT ;
                    SLOT_PARTS[4]=  WITH_LEV1_PART_SLOT ;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;



            }
        }else{
            switch (level) {
                case 0:
                    SLOT_PARTS[4]= LOWER_FREE_PART_SLOT;
                    SLOT_PARTS[3]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;
                case 1:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;

                case 2:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= WITH_LEV2_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;

                case 3:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= WITH_LEV2_PART_SLOT;
                    SLOT_PARTS[2]=WITH_LEV3_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;

                case 4:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= WITH_LEV2_PART_SLOT;
                    SLOT_PARTS[2]=WITH_LEV3_PART_SLOT;
                    SLOT_PARTS[1]= WITH_DOME_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;


                case 5:
                    SLOT_PARTS[4]= WITH_DOME_ATLAS_PART_SLOT;
                    SLOT_PARTS[3]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    BOARD_PARTS[slot.getRow()][slot.getColumn()]= SLOT_PARTS;
                    return;

            }
        }
    }

}
