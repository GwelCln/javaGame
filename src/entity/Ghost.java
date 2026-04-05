package entity;
import map.*;
import type.Move;



/**
 * Ghost class 
 * 
 */
public class Ghost extends Enemy{

    private static int numberOfGhost=0;

    public Ghost(String name, int x, int y){

        super(name, x, y);
        this.originX = x;
        this.originY = y;
        numberOfGhost++;

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


    public int getGhostNumber(){
        return numberOfGhost;
    }

    public static void resetGhostNumber(){
        numberOfGhost=0;
    }

    /**
     * Function that return the direction that the ghost must take to get close of the player
     */
    public Move getDirection(Player p){
        
        if(this.x == p.getX() && this.y == p.getY()){
            return Move.FIXE;
        }
        else if((this.x == p.getX() || this.x < p.getX() || this.x > p.getX()) && this.y < p.getY()){
            return Move.BAS;
        }
        else if((this.x == p.getX() || this.x < p.getX() || this.x > p.getX()) && this.y > p.getY()){
            return Move.HAUT;
        }
        else if(this.x < p.getX() && this.y == p.getY()){
            return Move.DROITE;
        }
        else if(this.x > p.getX() && this.y == p.getY()){
            return Move.GAUCHE;
        }

        return Move.FIXE;
    }

    /**
     * Function that change the coordonate of the player on the map if the case is available
     * @param m M on which you want to do action
     * @param mv Decide in which direction you want to go 
     */
    public void move(Map m){ //ajouter la verif en cas de mur
        Move mv = getDirection(m.getPlayer());
        Event e = new Event();
        switch(mv){
            case DROITE:
                if(e.enemyCollisionDetector(m, this.x+1, this.y) == false){}
                this.x = (this.x+1)%m.getWidth();
                break;
            case GAUCHE:
                this.x = (this.x-1+m.getWidth())%(m.getWidth());
                break;
            case BAS:
                this.y = (this.y+1)%(m.getHeight());
                break;
            case HAUT:
                this.y = (this.y-1+m.getHeight())%(m.getHeight());
                break;
            case FIXE:
                break;
        }
    }



}