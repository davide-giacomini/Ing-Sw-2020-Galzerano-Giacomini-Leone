package Model.Exceptions;

public class NoAvailableBuildingsException extends Exception {
    
    public NoAvailableBuildingsException(){
        super("You can not build anymore. You constructed already enogh times.");
    }
    
}
