package exception;


/**
 * Child class of RuntimeException. The exception is raised if a null player is passed as an arguments in a method.
 */
public class PlayerNullException extends RuntimeException{

    /**
     * Function that raise an exception when a null player is passed as an argument in a method.
     */
    public PlayerNullException(String msg){
        super(msg);
    }

}