package map;

import map.Cellule;
import entity.*;
import map.Structure;
import java.nio.file.*;
import java.io.*;
import java.lang.*;
import java.io.FileNotFoundException;
import type.*;
import exception.*;
import java.util.HashSet;
import java.util.Set;

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
    private static int coinNumber=0; // Total number of coin in the map
    private int originX; // value that keep in memory the origin x coordonate of the player
    private int originY; // value that keep in memory the origin y coordonate of the player
    private static int trapNumber=0; // total number of trap on the map
    private Enemy[] e; // enemy on the map
    private Set<Cellule> occupiedCells; // cells occupied by enemies (no duplication)
    private int numberOfEnemy;


    /**
     * Constructor of Map, it create a map with a width and a height defined in the parameter. 
     * The wall are included in the width and the height.
     * The function also take a player that is placed at a (x,y) position defined in the parameters
     * @param width width of the map, each unit equal one ascii character
     * @param height height of the map, each unit equal one ascii character
     * @param p Player that you want to put on the map
     * @param structCoord array of the different coordonate of the structure loaded in the map
     * @param struct array of the structure loaded in the map
     * @throws NotAllowedSizeException If the width or the height is less or equal to 2.
     * @throws OutOfMapException If the coordonate (x,y) are on or out of the map's walls
     * @throws PlayerNullException If a player passed in argument is null this exception is raised.
     */
    public Map(int height, int width, Player p, int totalEnemy, int[] enemyTab, Structure[] struct) throws NotAllowedSizeException, PlayerNullException{ // OutOfMapException,
        if(width <3 || height<3){
            throw new NotAllowedSizeException("Erreur dans les arguments la map ne peut pas être initialiser.");
        }
        // else if( x<=0 || x >= height-1 || y<=0 || y>= width-1){
        //     throw new OutOfMapException("Erreur dans les arguments le joueur à des coordonnés impossibles.");
        // }
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
                            this.tab[i][j] = new Cellule(j, i, Type.VIDE, false, false);
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
        this.originX = this.p.getX();
        this.originY = this.p.getY();
        int x = this.p.getX();
        int y = this.p.getY();

        // this.e = e; // enemy initialization
        // this.updateEnemyX(this.width-2);
        // this.updateEnemyY(this.height-2);
        // this.enemyOriginX = this.e.getX();
        // this.enemyOriginY = this.e.getY();
        this.numberOfEnemy = totalEnemy;

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


        this.e = new Enemy[totalEnemy];
        this.occupiedCells = new HashSet<>(); // initialisation du Set

        int enemyNumber =0;

        int jobApplicationNumber = enemyTab[0];
        int ghostNumber = enemyTab[1];
        int hunterNumber = enemyTab[2];

        while(enemyNumber!=totalEnemy){
            int xEnemy = 1 + (int) ( Math.random() * this.getWidth()-2);
            int yEnemy = 1 + (int) ( Math.random() * this.getHeight()-2);
            
            

            if((this.tab[yEnemy][xEnemy].getType() == Type.VIDE) && ((yEnemy != this.p.getY())&& (xEnemy != this.p.getX())) ){
                if(enemyNumber < jobApplicationNumber){
                    this.e[enemyNumber] = new Enemy("Job application n°" + (enemyNumber+1), xEnemy, yEnemy); // enemy initialization
                    this.occupiedCells.add(this.tab[yEnemy][xEnemy]); // on mémorise la cellule occupée
                    enemyNumber++;    
                }
                else if(enemyNumber >= jobApplicationNumber && enemyNumber < jobApplicationNumber+ghostNumber){
                    this.e[enemyNumber] = new Ghost("Puppet master n°" + (enemyNumber+1), xEnemy, yEnemy); // enemy initialization
                    this.occupiedCells.add(this.tab[yEnemy][xEnemy]); // on mémorise la cellule occupée
                    enemyNumber++;
                    
                }
                else{
                    this.e[enemyNumber] = new Hunter("LE HUNTEEEEER n°" + (enemyNumber+1), xEnemy, yEnemy); // enemy initialization
                    this.occupiedCells.add(this.tab[yEnemy][xEnemy]); // on mémorise la cellule occupée
                    enemyNumber++;
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

            boolean valid = true;

            if(this.tab[yTrap][xTrap].getType() == Type.VIDE && ((yTrap != this.p.getY())&& xTrap != this.p.getX())){
                

                for(int i=0 ; i<totalEnemy;i++){
                    if((yTrap == this.e[i].getY())&& (xTrap == this.e[i].getX())){
                        valid = false;
                    }
                }
                
                

                if(valid == true){
                    this.tab[yTrap][xTrap].updateType(Type.PIEGE);
                    this.trapNumber++;
                }

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
            int[] enemyTab = new int[3];
            int nbEnemy = Integer.parseInt(st[2]);
            enemyTab[0] = nbEnemy;
            int nbGhost = Integer.parseInt(st[3]);
            enemyTab[1] = nbGhost;
            int nbHunter = Integer.parseInt(st[4]);
            enemyTab[2] = nbHunter;
            int totalEnemy = nbEnemy + nbGhost + nbHunter;
            
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

            return new Map(mapValue[0], mapValue[1], p, totalEnemy, enemyTab, structTab);
        }     
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        
        return null;

    }
    


    /**
     * Function that display the map and the entity in it. It also print at the end the player in it with his score and coordonate
     */
    public void display(){ 
        int total = this.e[0].getNumberOfEnemy();



        for(int i=0;i<this.height;i++){
            System.out.println();
            boolean printed = false;
            for(int j=0;j<this.width;j++){
                printed = false;
                
                if(this.p.getX() == j && this.p.getY() == i){
                    System.out.print('1');
                    printed = true;
                }
                else{
                    for(int k=0;k<total;k++){
                        if(this.e[k].getX() == j && this.e[k].getY() == i && printed == false){
                            if(this.e[k] instanceof Ghost g){
                                System.out.print('G');
                            }
                            else if(this.e[k] instanceof Hunter h){
                                System.out.print('C');
                            }
                            else{
                                System.out.print('R');
                            }
                            printed = true; 
                        }
                    }
                }
                if(printed == false){
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
     * Redefinition of the Map toString methode, now print name : score pt(s) | x y positions of the player in the map.
     */
    @Override
    public String toString(){
		return this.p.toString()+" | x="+this.p.getX()+" y="+this.p.getY() ;
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
     * Method that update the value of coin in the map 
     * The value passed in parameter is added to the total
     */
    public void updateCoinNumber(int v){
        this.coinNumber = this.coinNumber+v<0 ? 0 : this.coinNumber+v;
    }

    /**
     * Method that return the health point of the player
     * @return integer that is the number of Hp of the player
     */
    public  int getHP(){
        return this.p.getHP();
    }

    public int getEnemyNumber(){
        return this.numberOfEnemy;
    }

    /**
     * Method that return the player
     * @return Player value 
     */
    public Player getPlayer(){
        return this.p;
    }

    /**
     * Method that return the X position of the player
     * @return int value that is the x (abscisse) coordonate of the player
     */
    public int getPlayerX(){
        return this.p.getX();
    }

    /**
     * Method that return the Y position of the player
     * @return int value that is the y (ordonate) coordonate of the player
     */
    public int getPlayerY(){
        return this.p.getY();
    }

    /**
     * Method that return the X position of the enemy
     * @param n index of the enemy in the array
     * @return int value that is the x (abscisse) coordonate of the enemy
     */
    public int getEnemyX(int n) throws EnemyIndexOutOfBound{
        if(n>this.e[0].getNumberOfEnemy()){
            throw new EnemyIndexOutOfBound("Erreur : index donner trop élever.");
        }
        else{
            return this.e[n].getX();
        }
    }

    /**
     * Method that return the Y position of the enemy
     * @param n index of the enemy in the array
     * @return int value that is the y (ordonate) coordonate of the enemy
     */
    public int getEnemyY(int n) throws EnemyIndexOutOfBound{
        if(n>this.e[0].getNumberOfEnemy()){
            throw new EnemyIndexOutOfBound("Erreur : index donner trop élever.");
        }
        else{
            return this.e[n].getY();
        }
    }

    public int getEnemyDamage(int n) throws EnemyIndexOutOfBound{ 
        
        if(n>this.e[0].getNumberOfEnemy()){
            throw new EnemyIndexOutOfBound("Erreur : index donner trop élever.");
        }
        else{
            return this.e[n].getDamage();
        }

    }

   /**
     * Method that returns true if a cell at (x,y) is occupied by an enemy.
     * Uses the HashSet for instant lookup instead of looping through all enemies.
     * @param x column of the cell to check
     * @param y line of the cell to check
     * @return boolean true if an enemy is on that cell
     */
    public boolean enemyOnCell(int x, int y){
        return this.occupiedCells.contains(this.tab[y][x]);
    }

    /**
     * Method that return the spawn y position of the player
     * @return int value that is the spawn y (abscisse) coordonate of the player
     */
    public int getPlayerOrigninY(){
        return this.originY;
    }

    /**
     * Method that return the spawn x position of the player
     * @return int value that is the spawn x (ordonate) coordonate of the player
     */
    public int getPlayerOrigninX(){
        return this.originX;
    }

    /**
     * Method that update the x player in the map
     * 
     */
    public void updatePlayerX(int x){
        this.p.updateX(x, this.width);
    }

    /**
     * Method that update the y player in the map
     * 
     */
    public void updatePlayerY(int y){
        this.p.updateY(y, this.height);
    }


    /**
     * Function that add v value to the hp 
     * v can be positive or negative 
     * however the hp cannot be < 0
     */
    public void updatePlayerHP(int v){
        this.p.updateHP(v);
    }

    // /**
    //  * Method that update the x enemy in the map
    //  * @param x new x position given to the enemy
    //  * @param n index of the enemy
    //  */
    // public void updateEnemyX(int x, int n) throws EnemyIndexOutOfBound{
    //     if(n>this.e[0].getNumberOfEnemy()){
    //         throw new EnemyIndexOutOfBound("Erreur : index donner trop élever.");
    //     }
    //     else{
    //         this.e[n].updateX(x, this.width);
    //     }
    // }

    // /**
    //  * Method that update the y enemy in the map
    //  * @param x new x position given to the enemy
    //  * @param n index of the enemy
    //  */
    // public void updateEnemyY(int y, int n) throws EnemyIndexOutOfBound{
    //     if(n>this.e[0].getNumberOfEnemy()){
    //         throw new EnemyIndexOutOfBound("Erreur : index donner trop élever.");
    //     }
    //     else{
    //         this.e[n].updateY(y, this.height);
    //     }
    // }



    /**
     * Method that manage the changement of cordonate of the enemies
     */
    public void updateEnemyPosition(int n) throws EnemyIndexOutOfBound {
        if(n > this.e[0].getNumberOfEnemy() || n < 0){
            throw new EnemyIndexOutOfBound("Erreur : index invalide");
        }

        // mise a jour du set on retire l'ancienne valeur
        this.occupiedCells.remove(this.tab[this.e[n].getY()][this.e[n].getX()]);

        this.e[n].move(this);

        //ajout du nouveau sac
        this.occupiedCells.add(this.tab[this.e[n].getY()][this.e[n].getX()]);
    }

     /**
     * Method that return the number of trap in the map
     * @return integer that is the total number of trap in the map
     */
    public int getTrapNumber(){
        return this.trapNumber;
    }

    /**
     * Function, that return the total number of enemy initialiazed
     */
    public int getNumberOfEnemy(){
        return this.e[0].getNumberOfEnemy();
    }

    /**
     * Method that reset the number of trap
     */
    public void resetTrap(){
        this.trapNumber =0;
    }

    /**
     * Function that reset the coordonate of the enemy
     * @param index int value that is the index of the enemy in the array
     */
    public void resetEnemyPosition(int index) throws EnemyIndexOutOfBound{
        try{
            // mise a jour du set on retire l'ancienne valeur
            this.occupiedCells.remove(this.tab[this.e[index].getY()][this.e[index].getX()]);

            this.e[index].resetPosition();

            //ajout du nouveau sac
            this.occupiedCells.add(this.tab[this.e[index].getY()][this.e[index].getX()]);
        }
        catch(EnemyIndexOutOfBound e){
            System.err.println(e.getMessage());
        }
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

    /**
     * Method that return the (x,y) cell of the map
     */
    public Cellule getCell(int x, int y){
        return this.tab[y][x];
    }

    public void resetPlayerPosition(){

        this.p.updateX(this.originX, this.width);
        this.p.updateY(this.originY, this.height);
    }

    public String getEnemyName(int i) throws EnemyIndexOutOfBound{
        if(i> this.e[0].getNumberOfEnemy() || i<0){
            throw new EnemyIndexOutOfBound("Erreur : Erreur dans l'indexage du tableau d'ennemis");
        }
        return this.e[i].getName();
    }

    public void movePlayer(Move mv){
        this.p.move(this, mv);
    }

}