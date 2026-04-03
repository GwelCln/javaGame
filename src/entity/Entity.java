package entity;
import map.*;
import type.*;


public abstract class Entity{
    /**
    * @author Collin Gweltaz
    * @version 1.0
    */


    protected final String name; // Not updatable
    protected int hp;
    protected int x;
    protected int y;



    /**
     * Constructor of the entity class
     * @param name name of the entity
     * @param x x coordonate of the entity 
     * @param y y coordonate of the entity
     * @param hp number of health point of the entity
     */
    public Entity(String name, int x, int y, int hp){

        if(name == null){
            this.name="Entite";
        }
        else{
            this.name = name;
        }
        this.x = x;
        this.y = y;
        this.hp = hp;
    }


    /**
     * Default entity constructor, argumentless method that give EntityN as name with N the number total of entity
     */
    public Entity(){
        this(null, 1, 1, 1);
    }


    /**
     * update the entity health point 
     * @param value positive or negative value which is added to the hp
     */
    public void updateHP(int value){
        this.hp = this.hp+value<0 ? 0 : this.hp+value;
    }

    /**
     * Method that update the x entity in the map
     * 
     */
    public void updateX(int x, int width){
        if(x>width || x<-1){

        }
        else{
            this.x=x;
        }
    }

	/**
     * Method that update the y entity in the map
     * 
     */
    public void updateY(int y, int height){
        if(y>height || y<-1){

        }
        else{
            this.y=y;
        }
    }



    //@override
    /**
     * Redefinition of the entity toString methode, now print HP and name
     */
    public String toString(){
        return this.hp + " HP" + "\n" + this.name;
    }




    //getters

    // /**
    //  * Static method that return the total number of entity initialized
    //  * @return return an integer that is the number of entity initialized
    //  */
    // public static int getNumberOfEntity(){
    //     return numberOfEntity;
    // }


    /**
     * Function that return the name of the entity
     * @return String : name given to the entity
     */
    public String getName(){
        return this.name;
    }

    

    /**
     * Method that return the current number of hp of the entity
     */
    public int getHP(){
        return this.hp;
    }


	/**
     * Method that return the X position of the entity
     * @return int value that is the x (abscisse) coordonate of the entity
     */
    public int getX(){
        return this.x;
    }

    /**
     * Method that return the Y position of the entity
     * @return int value that is the y (ordonate) coordonate of the entity
     */
    public int getY(){
        return this.y;
    }	

    /**
     * Function that change the coordonate of the player on the map if the case is available
     * @param m M on which you want to do action
     * @param mv Decide in which direction you want to go 
     */
    public void move(Map m, Move mv){ //ajouter la verif en cas de mur
        switch(mv){
            case DROITE:
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
        }
    }


}