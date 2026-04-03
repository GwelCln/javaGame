package exception;


/**
 * Child class of RuntimeException. The exception is thrown when the map is initialized with unvalid value the widht and the lenght 
 * must be greater or equal to 3 because the wall are counted.
 */
public class NotAllowedSizeException extends RuntimeException {

    public NotAllowedSizeException(String msg){
        super(msg);
    }

}