package map;

import map.Cellule;
import map.Type;
import player.Player;
import map.Structure;
import java.nio.file.*;
import java.io.*;
import java.lang.*;
import java.io.FileNotFoundException;



/**
 * Map class
 */
public class Map {
        /**
		* @author Collin Gweltaz
		* @version 1.0
		*/
    private int height;  // hauteur height
    private int width; // Largeur
    private Cellule[][] tab; // tableau contenant les caractères
    private final Player p;
    private int x; // Player x coordonate
    private int y; // Player y coordonate
    private static int coinNumber=0; // Total number of coin in the map
    private int originX; // value that keep in memory the origin x coordonate of the player
    private int originY; // value that keep in memory the origin y coordonate of the player
    private static int trapNumber=0; // total number of trap on the map

    /**
     * Constructor of Map, it create a map with a width and a height defined in the parameter. 
     * The wall are included in the width and the height.
     * The function also take a player that is placed at a (x,y) position defined in the parameters
     * @param width width of the map, each unit equal one ascii character
     * @param height height of the map, each unit equal one ascii character
     * @param p Player that you want to put on the map
     * @param x x position that the player will have at the beginning of the game
     * @param y y position that the player will have at the beginning of the game
     * @param structCoord array of the different coordonate of the structure loaded in the map
     * @param struct array of the structure loaded in the map
     * @throws NotAllowedSizeException If the width or the height is less or equal to 2.
     * @throws OutOfMapException If the coordonate (x,y) are on or out of the map's walls
     * @throws PlayerNullException If a player passed in argument is null this exception is raised.
     */
    public Map(int height, int width, Player p, int x, int y ,Structure[] struct) throws NotAllowedSizeException, OutOfMapException, PlayerNullException{
        if(width <3 || height<3){
            throw new NotAllowedSizeException("Erreur dans les arguments la map ne peut pas être initialiser.");
        }
        else if( x<=0 || x >= height-1 || y<=0 || y>= width-1){
            throw new OutOfMapException("Erreur dans les arguments le joueur à des coordonnés impossibles.");
        }
        else if(p == null){
            throw new PlayerNullException("Erreur impossible de passé un joueur null en argument");
        }
        else{
            this.height = height;
            this.width = width;
            this.tab = new Cellule[this.height][this.width];
            for(int i=0;i<height;i++){
                for(int j=0;j<width;j++){
                    if(i==0 || i==height-1 || j==0 || j==width-1){
                        if((i == 0 && j == width/2) || (i == height-1 && j == width/2) || (i == height/2 && j == 0) || (i == height/2 && j == width-1) ){
                            this.tab[i][j] = new Cellule(j, i, Type.PORTE_V, false, true);
                        }
                        else{
                            this.tab[i][j] = new Cellule(j, i, Type.MUR, false, true);
                        }
                    }
                    else{
                        this.tab[i][j] = new Cellule(j, i, Type.VIDE, false, false);
                    }
                }
            }
        }
        
        this.p = p; //Player initialization
        this.x=x;
        this.y=y;
        this.originX = x;
        this.originY = y;


        int total = Structure.getNumberStruct();

        int freeSpace = (this.getHeight()-2)*(this.getWidth()-2);
        
        int numberOfCoin = 1+ (int) ( Math.random() * 10);
        System.out.println("Number of coin : " + numberOfCoin);


        int numberOfTrap = 2+ (int) ( Math.random() * 5);
        System.out.println("Number of trap : " + numberOfTrap);

        for(int i=0;i<total;i++){
            if(! isInLevel(struct[i])){

            }
            else{
                freeSpace -= struct[i].getHeight()+struct[i].getWidth();
                for(int j = 0;j<struct[i].getHeight();j++){
                    for(int k = 0;k<struct[i].getWidth();k++){
                        this.tab[struct[i].getY() + j][struct[i].getX() + k].updateType(Type.MUR);
                        this.tab[struct[i].getY() + j][struct[i].getX() + k].activateCollision();
                    }
                }
            }
        }

        if(freeSpace < 1+numberOfCoin+numberOfTrap){
            throw new NotAllowedSizeException("Error : not enough space for the coin.");
        }
        if(this.tab[y][x].getType() == Type.MUR){
            throw new NotAllowedCoordonate("Erreur : le joueur ne peux pas être initialisé sur un mur.");
        }

        while(this.getTrapNumber() != numberOfTrap){
            int xTrap = 1 + (int) ( Math.random() * this.getWidth()-1);
            int yTrap = 1 + (int) ( Math.random() * this.getHeight()-1);


            if(this.tab[yTrap][xTrap].getType() == Type.VIDE){

                this.tab[yTrap][xTrap].updateType(Type.PIEGE);
                this.trapNumber++;

            }
        }


        while(this.getCoinNumber() != numberOfCoin){
            int xCoin = 1 + (int) ( Math.random() * this.getWidth()-1);
            int yCoin = 1 + (int) ( Math.random() * this.getHeight()-1);


            if(this.tab[yCoin][xCoin].getType() == Type.VIDE && this.tab[yCoin][xCoin].getCoin() == false){

                this.tab[yCoin][xCoin].activateCoin();
                this.coinNumber++;

            }
        }

    }
    

    /**
     * Method that load a map with structure and player position from a file passed in argument
     * @param path string value of the relative path of the save file. The path start a the project's root directory
     */
    public static Map loadMap(String path, Player p) throws FileNotFoundException{
        Path chemin = Paths.get(path);

        if (! Files.exists(chemin)){               
            throw new FileNotFoundException("Erreur : fichier " + path + " introuvable...");
        }
   
        
        try{
            Structure.resetCount();
            coinNumber =0;
            trapNumber =0;
            InputStream ips=new FileInputStream(path); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);

            String ligne;
            ligne=br.readLine();
            String[] st = ligne.split(":");
            Structure[] structTab = new Structure[Integer.parseInt(st[1])];
            int structIndex=0;
            int[] mapValue = new int[4];
            
            while ((ligne=br.readLine())!=null){

                if(ligne.contains("struct")){
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

            return new Map(mapValue[0], mapValue[1], p, mapValue[2], mapValue[3], structTab);
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
            for(int j=0;j<this.width;j++){
                if(this.x == j && this.y == i){
                    System.out.print('1');
                }
                else{
                    if(tab[i][j].getType() == Type.MUR){
                        System.out.print('#');
                    }
                    else if(tab[i][j].getType() == Type.PIEGE){
                        System.out.print('*');
                    }   
                    else if(tab[i][j].getType() == Type.PORTE_V){
                        System.out.print('D');
                    }         
                    else if(tab[i][j].getCoin() == true){
                        System.out.print('.');
                    }
                    else{
                        System.out.print(' ');
                    }
                }
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
        else if(((struct.getY()+struct.getHeight() - 1)>this.height) || ((struct.getX()+struct.getWidth()-1)>this.width)){
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
     * Method that return the width of the map
     * @return integer that is the width of the map
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Method that return the number of coin in the map
     * @return integer that is the total number of coin in tha map
     */
    public int getCoinNumber(){
        return this.coinNumber;
    }


    /**
     * Method that return the health point of the player
     * @return integer that is the number of Hp of the player
     */
    public  int getHP(){
        return this.p.getHP();
    }



    /**
     * Function that return False if a coordonate is not appropriate 
     * /!\ important to enter first the x coordonate and then the y coordonate
     * @param x x coordonate of the player you want to test
     * @param y y coordonate of the player you want to test
     * @return return a boolean true if the case is available else false 
     */
    public boolean collisionDetector(int x, int y){
        if(this.tab[y][x].getCollision() == true){

            if(this.tab[y][x].getType() == Type.PORTE_V){
                return false;
            }

            if(x==0){ //trying to pass left wall case
                return collisionDetector( this.width-2, y);
            }
            else if(x==this.width-1){ //trying to pass right wall case
                return collisionDetector(1, y);
            }
            else if(y==0){ //trying to pass top wall case
                return collisionDetector(x, this.height-2);
            }
            else if(y==this.height-1){ //trying to pass bottom wall case
                return collisionDetector(x, 1);
            }
            else{
                return false;
            }
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
                    this.x = (this.x+1)%(this.width-1);
                    if(this.x ==0){
                        this.x++;
                    }
                    this.eventManager();
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case GAUCHE:
                if(collisionDetector(this.x-1, this.y)){
                    this.x -= 1;
                    if(this.x==0){
                        this.x = this.width-2;
                    }
                    
                    this.eventManager();
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case BAS:
                if(collisionDetector(this.x, this.y+1)){
                    this.y = (this.y+1)%(this.height-1);
                    if(this.y == 0){
                        this.y++;
                    }
                    this.eventManager();
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case HAUT:
                if(collisionDetector(this.x, this.y-1)){
                    this.y -= 1;
                    if(this.y==0){
                        this.y = this.height-2;
                    }
                    this.eventManager();
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
        }
    }

    /**
     * Method that display the level Complete screen.
     */
    public void levelComplete(){
        // int i =0;
        // for(;i<this.height/2;i++){
        //     System.out.println();
        //     for(int j =0;j<this.width;j++){  
        //         if(i==0 || j==0 || j==width-1){
        //             System.out.print('#');
        //         }
        //         else{
        //             System.out.print(' ');
        //         }
        //     }
        // }
        // System.out.println(' ');
        // System.out.print('#');
        // int k=1;
        // for(;k<this.width/2-8;k++){
        //     System.out.print(' ');
        // }
        // k=this.width/2+8;
        // System.out.print("Level complete !");
        // for(;k<this.width-1;k++){
        //     System.out.print(' ');
        // }
        // System.out.print('#');
        // i++;
        // for(;i<this.height;i++){
        //     System.out.println();
        //     for(int j=0;j<this.width;j++){
        //         if( i==height-1 || j==0 || j==width-1){
        //             System.out.print('#');
        //         }
        //         else{
        //             System.out.print(' ');
        //         }
        //     }
        // }
        // System.out.println();

        System.out.println();
        System.out.println("  ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗");
        System.out.println("  ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║");
        System.out.println("   ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║");
        System.out.println("    ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║");
        System.out.println("     ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║");
        System.out.println("     ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝");
        System.out.println();
        System.out.println();

    }

    /**
     * Method that display the Game Over screen.
     */
    public void gameOver(){
        // int i =0;
        // for(;i<this.height/2;i++){
        //     System.out.println();
        //     for(int j =0;j<this.width;j++){  
        //         if(i==0 || j==0 || j==width-1){
        //             System.out.print('#');
        //         }
        //         else{
        //             System.out.print(' ');
        //         }
        //     }
        // }
        // System.out.println(' ');
        // System.out.print('#');
        // int k=1;
        // for(;k<this.width/2-5;k++){
        //     System.out.print(' ');
        // }
        // k=this.width/2+5;
        // System.out.print("Game Over.");
        // for(;k<this.width-1;k++){
        //     System.out.print(' ');
        // }
        // System.out.print('#');
        // i++;
        // for(;i<this.height;i++){
        //     System.out.println();
        //     for(int j=0;j<this.width;j++){
        //         if( i==height-1 || j==0 || j==width-1){
        //             System.out.print('#');
        //         }
        //         else{
        //             System.out.print(' ');
        //         }
        //     }
        // }
        // System.out.println();

        System.out.println();
        System.out.println("  ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ ");
        System.out.println("  ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗");
        System.out.println("  ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝");
        System.out.println("  ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗");
        System.out.println("  ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║");
        System.out.println("   ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝");
        System.out.println();
        System.out.println();


    }


    /**
     * Function that manage event when a player enter a new place of the map
     */
    public void eventManager(){

        if(this.tab[this.y][this.x].getCoin() == true){
            this.tab[this.y][this.x].activateCoin();
            this.p.updateScore(10);
            this.coinNumber --;
        }
        else if(this.tab[this.y][this.x].getType() == Type.PIEGE){
            this.tab[this.y][this.x].updateType(Type.VIDE);
            this.p.updateHP(-2);
            this.x = this.originX;
            this.y = this.originY;
        }

    }

     /**
     * Method that return the number of trap in the map
     * @return integer that is the total number of trap in the map
     */
    public int getTrapNumber(){
        return this.trapNumber;
    }

    /**
     * Method that reset the number of trap
     */
    public void resetTrap(){
        this.trapNumber =0;
    }

    /**
     * Method that reset the number of trap
     */
    public void resetCoin(){
        this.coinNumber =0;
    }

    public String getName(){
        return this.p.getName();
    }

}
