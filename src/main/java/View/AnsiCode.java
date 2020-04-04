package View;

import Model.Exceptions.AnsiCodeException;

public class AnsiCode {
        /**
         * Variables to convert the ansi codes into names with meaning
         */
        static final  String ANSI_RED = "\u001B[31m" ;
        static final String ANSI_GREEN = "\u001B[32m" ;
        static final String ANSI_YELLOW = "\u001B[33m" ;
        static final String ANSI_BLUE = "\u001B[34m" ;
        static final String ANSI_PURPLE = "\u001B[35m";
        static final String ANSI_CYAN = "\u001B[36m";
        static final String ANSI_WHITE = "\u001B[37m";
        static final String ANSI_ENTER_KEY = "\u21A9";
        /**
         * Variable to Reset the game
         */
        static final String ANSI_CLEARLINE = "\33[1A\33[2K";
        static final String ANSI_CLEARCONSOLE = "\033[H\033[2J";
        static final String ANSI_RESET = "\u001B[0m";

        /**
         * Variable used to convert code of the worker symbol
         */
        static final String ANSI_WORKER = "\u265F";

        static final String ANSI_LEVEL1 = "\u2B1C"+"\uFE0F";
        static final String ANSI_LEVEL2 = "\u25FB" + "\uFE0F";
        static final String ANSI_LEVEL3 = "\u25FD" + "\uFE0F";
        static final String ANSI_DOME = "\uD83D\uDD35\n" ;



        public AnsiCode () {

        }

        /**
         * method used to convert names into code
         * @param name is the name as a string of the color/item
         * @return ansi code of the item
         */
        public String getAnsiByName (String name )  {


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
                        case "WORKER" :
                                return ANSI_WORKER ;
                        case "RESET" :
                                return ANSI_RESET ;
                        default :
                                //throw  new AnsiCodeException ();
                                return ANSI_WHITE;

                }
         }
}


