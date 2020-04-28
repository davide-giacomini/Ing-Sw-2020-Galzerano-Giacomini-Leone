package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;

import java.io.PrintStream;
import java.util.ArrayList;

public class PrintSupport {

    private static final String UPPER_PART_SLOT = " ________ ";
    private static final String LOWER_FREE_PART_SLOT = "|________|";
    private static final String MIDDLE_FREE_PART_SLOT ="|        |";


    private static final String WITH_LEV1_PART_SLOT = "|___" + AnsiCode.ANSI_LEVEL1+ "___|";
    private static final String WITH_LEV2_PART_SLOT = "|   " + AnsiCode.ANSI_LEVEL2+ "   |";
    private static final String WITH_LEV3_PART_SLOT = "|   " + AnsiCode.ANSI_LEVEL3 + "   |";
    private static final String WITH_DOME_PART_SLOT = "|   " + AnsiCode.ANSI_DOME + "   |";

    private static final String[] EMPTY_PARTS = new String[5];


    private String[][][] BOARD_PARTS = new String[5][5][5];



    public PrintSupport() {
        EMPTY_PARTS[0]= UPPER_PART_SLOT;
        EMPTY_PARTS[1]= MIDDLE_FREE_PART_SLOT;
        EMPTY_PARTS[2]= MIDDLE_FREE_PART_SLOT;
        EMPTY_PARTS[3]= MIDDLE_FREE_PART_SLOT;
        EMPTY_PARTS[4]= LOWER_FREE_PART_SLOT;
    }

    /**This method prints an empty board */
    public void PrintEmptyBoard(PrintStream out){
        for (int i = 0 ; i<5; i++){
            for (int j = 0 ; j<5; j++){
                for (int k = 0; k<5 ; k++){
                    out.print(EMPTY_PARTS[j]);
                }
                out.print("\n");
            }
        }
        out.print("\n");

    }

    /**
     * This method is used to build the entire Board graphically
     * @param board is the current board received from the ViewDatabase
     * @return the graphic representation of the entire board
     */
    public String[][][] buildCurrBoard (BoardView board){
        for (int j = 0 ; j<5; j++){
            for (int k = 0; k<5 ; k++){
                BOARD_PARTS[j][k] = buildOneByOneSlot(board.getSlot(j, k));
            }
        }
        return BOARD_PARTS;
    }

    /**
     * This method is used to create graphically the slot
     * @param slot is the slot received by the BoardView
     * @return String[] that graphically represents the slot
     */
    public String[] buildOneByOneSlot(Slot slot){
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

                    return SLOT_PARTS;

                case 1 :
                    SLOT_PARTS[0]=UPPER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[3]= WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT ;

                    return SLOT_PARTS;

                case 2 :
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[3]=  WITH_LEV2_PART_SLOT ;
                    SLOT_PARTS[4]=  WITH_LEV1_PART_SLOT ;


                    return SLOT_PARTS;

                case 3 :
                    SLOT_PARTS[0]=  UPPER_PART_SLOT;
                    SLOT_PARTS[1]=  WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[2]=  WITH_LEV3_PART_SLOT;
                    SLOT_PARTS[3]=  WITH_LEV2_PART_SLOT ;
                    SLOT_PARTS[4]=  WITH_LEV1_PART_SLOT ;

                    return SLOT_PARTS;

                default :
                    ///throw an exception , it should never end in here
                    return EMPTY_PARTS;

            }
        }else{
            switch (level) {
                case 0:
                    SLOT_PARTS[4]= LOWER_FREE_PART_SLOT;
                    SLOT_PARTS[3]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;

                    return SLOT_PARTS;
                case 1:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;

                    return SLOT_PARTS;
                case 2:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= WITH_LEV2_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;

                    return SLOT_PARTS;
                case 3:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= WITH_LEV2_PART_SLOT;
                    SLOT_PARTS[2]=WITH_LEV3_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;

                    return SLOT_PARTS;
                case 4:
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT;
                    SLOT_PARTS[3]= WITH_LEV2_PART_SLOT;
                    SLOT_PARTS[2]=WITH_LEV3_PART_SLOT;
                    SLOT_PARTS[1]= WITH_DOME_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;

                    return SLOT_PARTS;
                default:
                    //throw an exception
                    return EMPTY_PARTS;
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
     * Clears the console
     */
    static void clearConsole(PrintStream out) {
        out.print(AnsiCode.ANSI_CLEARCONSOLE);
        out.flush();
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

}
