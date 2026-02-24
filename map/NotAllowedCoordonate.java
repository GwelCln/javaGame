package map;


/**
 * Child class of RuntimeException. The exception is thrown when the player spawn on a wall or structure 
 */
public class NotAllowedCoordonate extends RuntimeException {

    public NotAllowedCoordonate(String msg){
        super(msg);
    }

}