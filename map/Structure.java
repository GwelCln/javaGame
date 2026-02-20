package map;



public class Structure {
    
    /**
    * @author Collin Gweltaz
    * @version 1.0
    */
    
    private int shape;
    private int widht;
    private int lenght;
    private char[][] structure;

    /**
    * Constructor method of the Structure class. The structure can have different shape defined by type value.
    * @param shape decide the shape of the structure : 0 line, 1 square
    * @param widht widht of the structure
    * @param lenght lenght of the structure
    */
    public Structure Structure(int shape, int widht, int lenght){

        if(shape != 0 || shape != 1){ // More shape available soon !
            throw new ShapeNotFoundError("Error : the shape do not exist !");
        }
        this.shape = shape;
        this.widht = widht;
        this.lenght = lenght;

        this.structure = new char[this.widht][this.lenght];

        for(int i =0;i<widht;i++){
            for(int j=0;j<lenght;j++){
                this.structure[i][j] = '#';
            }
        }
    }

    /**
     * Method that return the widht of the struture
     * @return integer that is the widht of the structure
     */
    public int getWidht(){
        return this.widht;
    }

    /**
     * Method that return the lenght of the struture
     * @return integer that is the lenght of the structure
     */
    public int getLenght(){
        return this.lenght;
    }


}