package View;

import Model.Board;
import Model.Enumerations.Level;
import Model.Slot;
import Model.Worker;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Objects;

public class PrintSupport {

    private static final String UPPER_PART_SLOT = " ________ ";
    private static final String LOWER_FREE_PART_SLOT = "|________|";
    private static final String MIDDLE_FREE_PART_SLOT ="|        |";


    private static final String WITH_LEV1_PART_SLOT = "|___" + AnsiCode.ANSI_LEVEL1+ "___|";
    private static final String WITH_LEV2_PART_SLOT = "|   " + AnsiCode.ANSI_LEVEL2+ "   |";
    private static final String WITH_LEV3_PART_SLOT = "|   " + AnsiCode.ANSI_LEVEL3 + "   |";
    private static final String WITH_DOME_PART_SLOT = "|   " + AnsiCode.ANSI_DOME + "   |";

    private String WITH_WORKER_PART_SLOT = "|___" + AnsiCode.ANSI_WORKER + "____|";

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

    public String[][][] buildCurrBoard (Board board){
        for (int j = 0 ; j<5; j++){
            for (int k = 0; k<5 ; k++){
                BOARD_PARTS[j][k] = buildOneByOneSlot(board.getSlot(j, k));
            }
        }
        return BOARD_PARTS;
    }


    public String[] buildOneByOneSlot(Slot slot){
        int level = slot.getLevel().ordinal();
        Worker worker = slot.getWorker();
         String[] SLOT_PARTS = new String[5];

        if ( worker != null) {
            String color = AnsiCode.getAnsiByName(worker.getColor().toString());
            WITH_WORKER_PART_SLOT = "|___" + color+ AnsiCode.ANSI_WORKER + AnsiCode.ANSI_RESET + "____|";

            switch (level){
                case 0 :
                    SLOT_PARTS[0]= UPPER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[3]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[4]= WITH_WORKER_PART_SLOT;

                    return SLOT_PARTS;

                case 1 :
                    SLOT_PARTS[0]=UPPER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[2]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[3]= WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT ;

                    return SLOT_PARTS;

                case 2 :
                    SLOT_PARTS[4]=  WITH_LEV1_PART_SLOT ;
                    SLOT_PARTS[3]=  WITH_LEV2_PART_SLOT ;
                    SLOT_PARTS[2]= WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[1]= MIDDLE_FREE_PART_SLOT;
                    SLOT_PARTS[0]= UPPER_PART_SLOT;

                    return SLOT_PARTS;

                case 3 :
                    SLOT_PARTS[4]= WITH_LEV1_PART_SLOT ;
                    SLOT_PARTS[3]= WITH_LEV2_PART_SLOT ;
                    SLOT_PARTS[2]= WITH_LEV3_PART_SLOT ;
                    SLOT_PARTS[1]= WITH_WORKER_PART_SLOT;
                    SLOT_PARTS[0]=UPPER_PART_SLOT;

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


    public void printCurrBoard (String[][][] finishedBoard, PrintStream out){
        int count = 0;
        out.println("    0         1         2         3         4");
        for (int i = 0 ; i<5; i++){
            for (int j = 0 ; j<5; j++){
                for (int k = 0; k<5 ; k++){

                    out.print(finishedBoard[i][k][j]);

                    if (j == 2 && k==4) {
                        out.print(" "+count);
                        count++;
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

}
