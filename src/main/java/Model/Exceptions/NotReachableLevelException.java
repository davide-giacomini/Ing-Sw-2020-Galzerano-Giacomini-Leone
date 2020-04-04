package Model.Exceptions;

public class NotReachableLevelException extends Exception {
    
    public NotReachableLevelException (){
        super("The level is not reachable");
    }
}
