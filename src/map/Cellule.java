package map;
import type.Type;
import exception.*;


/**
 * Cellule Class
 */
public class Cellule{

    private int x;
    private int y;
    private Type type;
    private boolean coin;
    private boolean collision;

    /**
     * Contructor method of the cellule class
     * @param x column of the cell
     * @param y ligne of the cell
     * @param type type of the cell can be a trap, empty, wall, or door
     * @param coin boolean value if the cell contain a coin
     * @param collision if true the player can pass throught else no
     */
    public Cellule(int x, int y, Type type, boolean coin, boolean collision){

        this.x = x;
        this.y = y;
        this.type = type;
        this.coin = coin;
        this.collision = collision;

    }

    /**
     * Method that return the ligne of the cell
     * @return integer value
     */
    public int celluleGetX(){
        return this.x;
    }

    /**
     * Method that return the column of the cell 
     * @return integer value
     */
    public int celluleGetY(){
        return this.y;
    }

    /**
     * Method that return the type of the cell 
     * @return Type value
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Method that return if the cell contain a coin or not
     * @return boolean value 
     */
    public boolean getCoin(){
        return this.coin;
    }

    /**
     * Method that change the type of the cell
     */
    public void updateType(Type type){
        this.type = type;
    }

    /**
     * Function that update the cell if a player pick up the coin
     * if so the coin value is now false
     * @return boolean value true if the operation has successfully happened
     */
    public boolean pickCoin(){
        if(this.coin == true){
            this.coin = false;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Function that activate the coin value
     */
    public void activateCoin(){
        if(this.coin == true){
            this.coin = false;
        }
        else{
            this.coin = true;
        }
    }

    /**
     * Method that return the collision value of the instance
     * @return boolean value that is true if the player can't pass it or false if he can
     */
    public boolean getCollision(){
        return this.collision;
    }

    /**
     * Function that turn to true or false the collison value
     */
    public void activateCollision(){
        if(this.collision == true){
            this.collision = false;
        }
        else{
            this.collision = true;
        }
    }

    /**
     * Two cells are equal if they share the same (x, y) position on the map.
     * Required by HashSet to detect duplicates.
     */
    @Override
    public boolean equals(Object obj){ // permet de dire a java ce que c'est 2 cellule égal (égal en x et en y)
        if(this == obj) return true;
        if(!(obj instanceof Cellule)) return false;
        Cellule other = (Cellule) obj;
        return this.x == other.x && this.y == other.y;
    }

    /**
     * HashCode coherent with equals() : based on x and y only.
     * Two cells at the same position will always produce the same hashCode.
     */
    @Override
    public int hashCode(){
        return 31 * this.x + this.y;
    }

}