package Model.Exceptions;

public class InvalidActionException extends Exception{


    public InvalidActionException (){ super("The worker cannot do this move"); };
}
