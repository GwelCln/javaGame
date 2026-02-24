package map;


/**
 * Structure class. Refer to wall in the game
 */
public class Structure {
    
    /**
    * @author Collin Gweltaz
    * @version 1.0
    */
    
    private int shape;
    private int widht;
    private int height;
    private int x;
    private int y;
    private char[][] structure;
    private static int numberStruct=0;

    /**
    * Constructor method of the Structure class. The structure can have different shape defined by type value.
    * @param shape decide the shape of the structure : 0 line, 1 square
    * @param widht widht of the structure
    * @param height height of the structure
    * @param x x coordonate of the structure
    * @param y y coordonate of the structure
    */
    public Structure(int shape, int height, int widht, int x, int y){

        if(! (shape == 0 || shape == 1)){ // More shape available soon !
            throw new ShapeNotFoundError("Error : the shape do not exist !");
        }
        this.numberStruct +=1;
        this.shape = shape;
        this.widht = widht;
        this.height = height;
        this.x = x;
        this.y = y;

        // this.structure = new char[this.widht][this.height];

        // for(int i =0;i<height;i++){
        //     for(int j=0;j<widht;j++){
        //         this.structure[i][j] = '#';
        //     }
        // }
    }

    /**
     * Method that return the widht of the struture
     * @return integer that is the widht of the structure
     */
    public int getWidht(){
        return this.widht;
    }

    /**
     * Method that return the height of the struture
     * @return integer that is the height of the structure
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Method that return the total number of structure defined
     * @return int value that is the number of struct
     */
    public static int getNumberStruct(){
        return numberStruct;
    }

    /**
     * Method that return the x coordonate of the structure
     * @return integer that is the x coordonate of the structure
     */
    public int getX(){
        return this.x;
    }

    /**
     * Method that return the y coordonate of the structure
     * @return integer that is the y coordonate of the structure
     */
    public int getY(){
        return this.y;
    }

}