package map;

import player.Player;

/**
 * Map class
 */
public class Map {
        /**
		* @author Collin Gweltaz
		* @version 1.0
		*/
    private int lenght;  // longueur
    private int widht; // Largeur
    private char[][] tab; // tableau contenant les caractères
    private final Player p;
    private int x; // Player x coordonate
    private int y; // Player y coordonate

    /**
     * Constructor of Map, it create a map with a widht and a lenght defined in the parameter. 
     * The wall are included in the widht and the lenght.
     * The function also take a player that is placed at a (x,y) position defined in the parameters
     * @param widht widht of the map, each unit equal one ascii character
     * @param lenght lenght of the map, each unit equal one ascii character
     * @param p Player that you want to put on the map
     * @throws NotAllowedSizeException If the widht or the lenght is less or equal to 2.
     * @throws OutOfMapException If the coordonate (x,y) are on or out of the map's walls
     * @throws PlayerNullException If a player passed in argument is null this exception is raised.
     */
    public Map(int widht, int lenght, Player p, int x, int y) throws NotAllowedSizeException, OutOfMapException, PlayerNullException{
        if(widht <3 || lenght<3){
            throw new NotAllowedSizeException("Erreur dans les arguments la map ne peut pas être initialiser.");
        }
        else if( x<=0 || x >= lenght-1 || y<=0 || y>= widht-1){
            throw new OutOfMapException("Erreur dans les arguments le joueur à des coordonnés impossibles.");
        }
        else if(p == null){
            throw new PlayerNullException("Erreur impossible de passé un joueur null en argument");
        }
        else{
            this.lenght = lenght;
            this.widht = widht;
            this.tab = new char[this.widht][this.lenght];
            for(int i=0;i<widht;i++){
                for(int j=0;j<lenght;j++){
                    if(i==0 || j==lenght-1 || j==0 || i==widht-1){
                        tab[i][j] = '#';
                    }
                    else{
                        tab[i][j] = ' ';
                    }
                }
            }
            this.p = p;
            this.x=x;
            this.y=y;
            this.tab[x][y] = '1';
        }
    }

    /**
     * Function that display the map and the entity in it. It also print at the end the player in it with his score and coordonate
     */
    public void display(){
        for(int i=0;i<this.widht;i++){
            System.out.println();
            for(int j=0;j<this.lenght;j++){
                System.out.print(tab[i][j]);
            }
        }
        System.out.println();
        this.toString();
    }   


    /**
     * Redefinition of the Map toString methode, now print name : score pt(s) | x y positions of the player in the map.
     */
    public String toString(){
		return this.p.toString()+" | x="+this.x+" y="+this.y;
	}

    /**
     * Function that return False if a coordonate is not appropriate 
     * /!\ important to enter first the x coodonate and then de y coordonate
     * @param x x coordonate of the player you want to test
     * @param y y coordonate of the player you want to test
     * @return return a boolean true if the case is available else false 
     */
    public boolean collisionDetector(int x, int y){
        if(x==0 || x==this.lenght-1 || this.tab[y][x] != ' '){
            return false;
        }
        else if(y==0 || y==this.widht-1 || this.tab[y][x] != ' '){
            System.out.println("y impossible");
            return false;
        }
        return true;
    }
    /**
     * Function that change the coordonate of the player on the map if the case is available
     * @param m Decide in which direction you want to go 
     */
    public void movePlayer(Move m){
        switch(m){
            case DROITE:
                if(collisionDetector(this.x+1, this.y)){
                    this.tab[this.y][this.x] = ' ';
                    this.x += 1;
                    this.tab[this.y][this.x] = '1';
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case GAUCHE:
                if(collisionDetector(this.x-1, this.y)){
                    this.tab[this.y][this.x] = ' ';
                    this.x -= 1;
                    this.tab[this.y][this.x] = '1';
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case BAS:
                if(collisionDetector(this.x, this.y+1)){
                    this.tab[this.y][this.x] = ' ';
                    this.y += 1;
                    this.tab[this.y][this.x] = '1';
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case HAUT:
                if(collisionDetector(this.x, this.y-1)){
                    this.tab[this.y][this.x] = ' ';
                    this.y -= 1;
                    this.tab[this.y][this.x] = '1';
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
        }
    }

}
