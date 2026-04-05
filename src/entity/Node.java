package entity;

import map.*;


public class Node implements Comparable<Node>{
    
    public Cellule cellule;
    public int distance;

    public Node(Cellule cellule, int distance) {
        this.cellule = cellule;
        this.distance = distance;
    }
 
    /**
     * Redefinition of the method compareTo in order to define the right Queue exit for the priority 
     * Queue of the Dijkstrat algorithm
     * @param autre other node you want to compare with this
     * @return int value that is negative if this instance has a smaller distance than the other positive if not equal if the distance is the same
     */
    @Override
    public int compareTo(Node autre) {
        return this.distance - autre.distance;
    }


    public int getDistance(){ return this.distance; }

    public Cellule getCell(){ return this.cellule; }

    public int getX(){ return this.cellule.celluleGetX(); }

    public int getY(){ return this.cellule.celluleGetY(); }


}