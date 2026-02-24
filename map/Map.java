package map;

import player.Player;
import map.Structure;
import java.nio.file.*;
import java.io.FileNotFoundException;
import java.io.*;
import java.lang.*;



/**
 * Map class
 */
public class Map {
        /**
		* @author Collin Gweltaz
		* @version 1.0
		*/
    private int height;  // hauteur height
    private int widht; // Largeur
    private char[][] tab; // tableau contenant les caractères
    private final Player p;
    private int x; // Player x coordonate
    private int y; // Player y coordonate

    /**
     * Constructor of Map, it create a map with a widht and a height defined in the parameter. 
     * The wall are included in the widht and the height.
     * The function also take a player that is placed at a (x,y) position defined in the parameters
     * @param widht widht of the map, each unit equal one ascii character
     * @param height height of the map, each unit equal one ascii character
     * @param p Player that you want to put on the map
     * @param x x position that the player will have at the beginning of the game
     * @param y y position that the player will have at the beginning of the game
     * @param structCoord array of the different coordonate of the structure loaded in the map
     * @param struct array of the structure loaded in the map
     * @throws NotAllowedSizeException If the widht or the height is less or equal to 2.
     * @throws OutOfMapException If the coordonate (x,y) are on or out of the map's walls
     * @throws PlayerNullException If a player passed in argument is null this exception is raised.
     */
    public Map(int height, int widht, Player p, int x, int y ,Structure[] struct) throws NotAllowedSizeException, OutOfMapException, PlayerNullException{
        if(widht <3 || height<3){
            throw new NotAllowedSizeException("Erreur dans les arguments la map ne peut pas être initialiser.");
        }
        else if( x<=0 || x >= height-1 || y<=0 || y>= widht-1){
            throw new OutOfMapException("Erreur dans les arguments le joueur à des coordonnés impossibles.");
        }
        else if(p == null){
            throw new PlayerNullException("Erreur impossible de passé un joueur null en argument");
        }
        else{
            this.height = height;
            this.widht = widht;
            this.tab = new char[this.height][this.widht];
            for(int i=0;i<height;i++){
                for(int j=0;j<widht;j++){
                    if(i==0 || i==height-1 || j==0 || j==widht-1){
                        this.tab[i][j] = '#';
                    }
                    else{
                        this.tab[i][j] = ' ';
                    }
                }
            }

            
            int total = Structure.getNumberStruct();

            
            
            for(int i=0;i<total;i++){
                if(! isInLevel(struct[i])){

                 }
                else{
                    for(int j = 0;j<struct[i].getHeight();j++){
                        for(int k = 0;k<struct[i].getWidht();k++){
                            this.tab[struct[i].getY() + j][struct[i].getX() + k] = '#';
                        }
                    }
                }
            }

            
            if(this.tab[y][x] == '#'){
                throw new NotAllowedCoordonate("Erreur : le joueur ne peux pas être initialisé sur un mur.");
            }
            else{
                this.p = p; //Player initialization
                this.x=x;
                this.y=y;
                this.tab[y][x] = '1';
            }

        }
    }

    /**
     * Method that load a map with structure and player position from a file passed in argument
     * @param path string value of the relative path of the save file. The path start a the project's root directory
     */
    public static Map loadMap(String path){
        try{

            InputStream ips=new FileInputStream(path); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);

            String ligne;
            Player j = null;
            ligne=br.readLine();
            String[] st = ligne.split(":");
            Structure[] structTab = new Structure[Integer.parseInt(st[1])];
            int structIndex=0;
            int[] mapValue = new int[4];
            while ((ligne=br.readLine())!=null){

                    if (ligne.contains("player:")){
                        st = ligne.split(":");
                        j = new Player(st[1]);
                    }
                    else if(ligne.contains("struct")){

                        st = ligne.split(":");
                        structTab[structIndex] = new Structure( Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]), Integer.parseInt(st[4]), Integer.parseInt(st[5]));
                        structIndex++;
                    }
                    else if(ligne.contains("map")){
                        st = ligne.split(":"); 
                        for(int i=0;i<4;i++){
                            mapValue[i] = Integer.parseInt(st[i+1]);
                        }
                    }
				}
				br.close(); 

                return new Map(mapValue[0], mapValue[1], j, mapValue[2], mapValue[3], structTab);

        }
        catch(FileNotFoundException e){
            System.err.println("Erreur : fichier introuvable " + path + "\n" + e.getMessage());
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        
        return null;

    }
    


    /**
     * Function that display the map and the entity in it. It also print at the end the player in it with his score and coordonate
     */
    public void display(){ //FAIRE DANS TOSTRING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(int i=0;i<this.height;i++){
            System.out.println();
            for(int j=0;j<this.widht;j++){
                System.out.print(tab[i][j]);
            }
        }
        System.out.println();
        this.toString();
    }   

    /**
     * Method that return true if a structure can be placed on the coordonate indicated 
     * @return boolean that return true if the aera is available
     */
    public boolean isInLevel(Structure struct){

        if(struct.getX()<=0 || struct.getY()<=0){
            return false;
        }
        else if(((struct.getY()+struct.getHeight() - 1)>this.widht) || ((struct.getX()+struct.getWidht()-1)>this.height)){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Redefinition of the Map toString methode, now print name : score pt(s) | x y positions of the player in the map.
     */
    @Override
    public String toString(){
		return this.p.toString()+" | x="+this.x+" y="+this.y;
	}


      /**
     * Method that return the height of the map
     * @return integer that is the height of the map
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Method that return the widht of the map
     * @return integer that is the widht of the map
     */
    public int getWidht(){
        return this.widht;
    }

    /**
     * Function that return False if a coordonate is not appropriate 
     * /!\ important to enter first the x coordonate and then the y coordonate
     * @param x x coordonate of the player you want to test
     * @param y y coordonate of the player you want to test
     * @return return a boolean true if the case is available else false 
     */
    public boolean collisionDetector(int x, int y){
        if(this.tab[y][x] != ' ' || x==0 || x==this.widht-1 ){
            return false;
        }
        else if(this.tab[y][x] != ' ' || y==0 || y==this.height-1 ){ 
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
