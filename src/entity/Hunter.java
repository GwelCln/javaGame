package entity;


import type.*;
import map.*;

public class Hunter extends Enemy{

    public static int numberOfHunter=0;

    public Hunter(String name, int x, int y){
        super(name, x, y);
        this.hp =3;
        this.damage=-2;
        numberOfHunter++;
    }


    /**
     * Function that change the coordonate of the hunet on the map if the case is available
     * @param m M on which you want to do action
     */
    public void move(Map m){ //ajouter la verif en cas de mur

        Move mv = Dijkstrat.nextMove(m, this, m.getPlayer());
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