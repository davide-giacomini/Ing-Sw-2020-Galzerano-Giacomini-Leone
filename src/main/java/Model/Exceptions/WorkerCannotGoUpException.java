package Model.Exceptions;

public class WorkerCannotGoUpException extends Exception{
    public WorkerCannotGoUpException() {
        super("It's not possible to go higher than Level 3");
    };

}
