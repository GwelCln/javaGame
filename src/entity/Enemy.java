package entity;
import map.*;
import type.*;
import exception.EnemyIndexOutOfBound;

/**
 * Enemy class
 */
public class Enemy extends Entity {
    /**
    * @author Collin Gweltaz
    * @version 1.0
    */


    private static int numberOfEnemy=0;
    protected int originX;
    protected int originY;
    protected int damage=-1;
    /**
     * Constructor function, build the Enemy. 
     * @param name Name given to the Enemy
     * @param x x coordonate of the Enemy
     * @param y y coordonate of the Enemy
     */
    public Enemy(String name, int x, int y) {
        super(name, x, y, 1);  // même appel au parent
        this.originX = x;
        this.originY = y;
        numberOfEnemy++;
    }
    
    /**
     * Redefinition of the enemy toString methode, now print name 
     */
    @Override
    public String toString(){
        return this.name + " (enemy) | " + "x=" + this.x + " y=" + this.y;
    }

    // public String getName(){
    //     return this.name;
    // }

    /**
     * Function that return the total number of enemy
     * @return int value that is the number of enemy
     */
    public int getNumberOfEnemy(){
        return numberOfEnemy;
    }

    /**
     * Function that return the enemy first x position
     * @return int value that is the first x position of the enemy
     */
    public int getOriginX(){ return this.originX; }


    /**
     * Function that return the enemy first y position
     * @return int value that is the first y position of the enemy
     */
    public int getOriginY(){ return this.originY; }

    public int getDamage(){ return this.damage; }

    public void resetPosition(){
        this.x = originX;
        this.y = originY;
    }

    public static void resetEnemyNumber(){
        numberOfEnemy =0;
    }



    /**
     * Redefinition of the enemy move method
     */
    public void move(Map m){
        
        try{
        Event e = new Event();
        int move = (int) ( Math.random() * 4);

        switch(move){
            case 0: // droite
                if(e.enemyCollisionDetector(m, this.x+1, this.y) == false){
                    //m.updateEnemyPosition(index ,(m.getEnemyX(index)+1)%(m.getWidth()), y); 
                    super.move(m, Move.DROITE);
                }
                else{
                }
                break;
            case 1: // Gauche
                if(e.enemyCollisionDetector(m, this.x-1, this.y) == false){
                    //m.updateEnemyPosition(index ,((m.getEnemyX(index)-1+m.getWidth())%(m.getWidth())), y);
                    super.move(m, Move.GAUCHE);
                }
                else{
                }
                break;
            case 2: // bas
                if(e.enemyCollisionDetector(m, this.x, this.y+1) == false){
                    //m.updateEnemyPosition(index ,x , (m.getEnemyY(index)+1)%(m.getHeight())); 
                    super.move(m, Move.BAS);
                }
                else{
                }
                break;
            case 3: //haut
                if(e.enemyCollisionDetector(m, this.x, this.y-1) == false){
                    //m.updateEnemyPosition(index ,x, ((m.getEnemyY(index)-1+m.getHeight())%(m.getHeight())));
                    super.move(m, Move.HAUT);
                }
                else{   
                }
                break;
        }
        }
        catch(EnemyIndexOutOfBound er){
            System.err.println(er.getMessage());
        }
    }
}