package Model.Exceptions;

public class WrongBuildOrMoveException extends Exception{
    public WrongBuildOrMoveException() {super("You cannot build/move in your first slot, choose another one");}
}
