package exception;


/**
 * Child class of RuntimeException. The exception is thrown when someone give a wrong index to a enemy array
 */
public class EnemyIndexOutOfBound extends RuntimeException {

    public EnemyIndexOutOfBound(String msg){
        super(msg);
    }

}