package Model.Exceptions;

public class NoAvailableMovementsException extends Exception{
    
    public NoAvailableMovementsException() {
        super("You can not move anymore. You moved already enough times.");
    }
    
}
