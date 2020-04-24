package Model.Exceptions;

import java.io.Serializable;

public class GameAlreadyStartedException extends Exception {

    public GameAlreadyStartedException (String s) {
            super(s);
        }

}
