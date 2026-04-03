package exception;


/**
 * Child class of RuntimeException. Raise an exception if a shape that do not exist is passed has an argument ine the Structure() method.
 */
public class ShapeNotFoundError extends RuntimeException{
    

    /**
     * Function that raise an exception if a shape that do not exist is passed has an argument ine the Structure() method.
     */
    public ShapeNotFoundError(String s){
        super(s);
    }

}