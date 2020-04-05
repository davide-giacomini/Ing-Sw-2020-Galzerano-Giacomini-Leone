package View;

import java.io.PrintStream;

public class PrintSupport {

    private static final String UPPER_PART_SLOT = " ________ ";
    private static final String LOWER_FREE_PART_SLOT = "|________|";
    private static final String MIDDLE_FREE_PART_SLOT ="|        |";
    private static final String WITH_LEV1_PART_SLOT = " ___" + AnsiCode.ANSI_LEVEL1+ "___";
    private static final String WITH_LEV2_PART_SLOT = "|   " + AnsiCode.ANSI_LEVEL2+ "   |";
    private static final String WITH_LEV3_PART_SLOT = "|   " + AnsiCode.ANSI_LEVEL3 + "   |";
    private static final String WITH_DOME_PART_SLOT = "|   " + AnsiCode.ANSI_DOME + "   |";

    private static final String[] EMPTY_PARTS = new String[5];


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


}
