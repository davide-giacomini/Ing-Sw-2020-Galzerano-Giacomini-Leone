package it.polimi.ingsw.PSP47.View.CLI;

public class AnsiCode {
        /**
         * Variables to convert the ansi codes into names with a meaning
         */
        static final String ANSI_RED = "\u001B[31m" ;
        static final String ANSI_GREEN = "\u001B[32m" ;
        static final String ANSI_YELLOW = "\u001B[33m" ;
        static final String ANSI_BLUE = "\u001B[34m" ;
        static final String ANSI_PURPLE = "\u001B[35m";
        static final String ANSI_CYAN = "\u001B[36m";
        static final String ANSI_WHITE = "\u001B[37m";
        static final String ANSI_ENTER_KEY = "\u21A9";
        static final String ANSI_WRONG = "";
        //backgrounds
        static final String ANSI_BLACK = "\u001b[40m";
        static final String ANSI_BG_CYAN = "\u001b[46m";
        static final String ANSI_BG_GREEN = "\u001b[42m";
        /**
         * Variable to Reset the game
         */
        static final String ANSI_CLEARCONSOLE = "\033[H\033[2J";
        static final String ANSI_RESET = "\u001B[0m";

        /**
         * Variable used to convert code of the worker symbol
         */
        static final String ANSI_WORKER = "\u265F" + "\uFE0E";

        static final String ANSI_LEVEL1 = "\u2B1C"+"\uFE0F";
        static final String ANSI_LEVEL2 = "\u25FB" + "\uFE0F";
        static final String ANSI_LEVEL3 = "\u25FD" + "\uFE0F";
        static final String ANSI_DOME = "🔹";



        public AnsiCode () {

        }

        /**
         * method used to convert names into code
         * @param name is the name as a string of the color/item
         * @return ansi code of the item
         */
        public static String getAnsiByName(String name)  {


                switch (name.toUpperCase()) {
                        case "RED" :
                                return ANSI_RED;
                        case "GREEN" :
                                return ANSI_GREEN;
                        case "YELLOW" :
                                return ANSI_YELLOW ;
                        case  "BLUE" :
                                return ANSI_BLUE ;
                        case "PURPLE" :
                                return ANSI_PURPLE;
                        case "CYAN" :
                                return ANSI_CYAN ;
                        case "WHITE" :
                                return ANSI_WHITE ;
                        default :
                                return ANSI_WRONG;

                }
         }
}


