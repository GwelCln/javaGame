package exception;


/**
 * Child class of RuntimeException. Exception thrown when player intialized on or out of the map's wall
 */
public class OutOfMapException extends RuntimeException {

    public OutOfMapException(String msg){
        super(msg);
    }


}