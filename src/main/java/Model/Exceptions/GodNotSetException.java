package Model.Exceptions;

public class GodNotSetException extends Exception {
    
    public GodNotSetException () {
        super("Set God in this player before constructing the turn.");
    }
}
