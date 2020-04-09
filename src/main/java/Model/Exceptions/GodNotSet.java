package Model.Exceptions;

public class GodNotSet extends Exception {
    
    public GodNotSet () {
        super("Set God in this player before constructing the turn.");
    }
}
