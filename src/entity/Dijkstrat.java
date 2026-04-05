package entity;

import type.*;
import map.*;
import java.util.PriorityQueue;

public class Dijkstrat{

    public static Move nextMove(Map m, Hunter e, Player p){

        int[][] dist = new int[m.getHeight()][m.getWidth()];
        Cellule[][] parent = new Cellule[m.getHeight()][m.getWidth()];
        for(int i=0;i<m.getHeight();i++){
            for(int j=0;j<m.getWidth();j++){
                dist[i][j] = Integer.MAX_VALUE;
                parent[i][j] = null;
            }
        }

        PriorityQueue<Node> file = new PriorityQueue<>();

        file.add(new Node(m.getCell(e.getX(), e.getY()), 0));

        while(! file.isEmpty()){

            Node courant = file.poll(); // on récup le sommet avec la plus "grande" priorité


            if(courant.getCell().celluleGetX() == p.getX() && courant.getCell().celluleGetY() == p.getY()){

                break;

            }


            int nouvDist = courant.getDistance()+1;


            if( (courant.getCell().celluleGetX() < m.getWidth()-1) && (m.getCell(courant.getCell().celluleGetX()+1,courant.getCell().celluleGetY()).getType() != Type.MUR && m.getCell(courant.getCell().celluleGetX()+1,courant.getCell().celluleGetY()).getType() != Type.PIEGE) ){
                if(nouvDist < dist[courant.getCell().celluleGetY()][courant.getCell().celluleGetX()+1]){
                    dist[courant.getCell().celluleGetY()][courant.getCell().celluleGetX()+1] = nouvDist;
                    parent[courant.getCell().celluleGetY()][courant.getCell().celluleGetX()+1] = courant.getCell();
                    file.add(new Node(m.getCell(courant.getCell().celluleGetX()+1,courant.getCell().celluleGetY()), nouvDist));
                }             
            }
            if( (courant.getCell().celluleGetX() > 0) && (m.getCell(courant.getCell().celluleGetX()-1,courant.getCell().celluleGetY()).getType() != Type.MUR && m.getCell(courant.getCell().celluleGetX()-1,courant.getCell().celluleGetY()).getType() != Type.PIEGE )){
                if(nouvDist < dist[courant.getCell().celluleGetY()][courant.getCell().celluleGetX()-1]){
                    dist[courant.getCell().celluleGetY()][courant.getCell().celluleGetX()-1] = nouvDist;
                    parent[courant.getCell().celluleGetY()][courant.getCell().celluleGetX()-1] = courant.getCell();
                    file.add(new Node(m.getCell(courant.getCell().celluleGetX()-1,courant.getCell().celluleGetY()), nouvDist));
                }  
            }
            if( (courant.getCell().celluleGetY() < m.getHeight()-1) && (m.getCell(courant.getCell().celluleGetX(),courant.getCell().celluleGetY()+1).getType() != Type.MUR && m.getCell(courant.getCell().celluleGetX(),courant.getCell().celluleGetY()+1).getType() != Type.PIEGE )){
                if(nouvDist < dist[courant.getCell().celluleGetY()+1][courant.getCell().celluleGetX()]){
                    dist[courant.getCell().celluleGetY()+1][courant.getCell().celluleGetX()] = nouvDist;
                    parent[courant.getCell().celluleGetY()+1][courant.getCell().celluleGetX()] = courant.getCell();
                    file.add(new Node(m.getCell(courant.getCell().celluleGetX(),courant.getCell().celluleGetY()+1), nouvDist));
                }  
            }
            if( (courant.getCell().celluleGetY() > 0) && (m.getCell(courant.getCell().celluleGetX(),courant.getCell().celluleGetY()-1).getType() != Type.MUR && m.getCell(courant.getCell().celluleGetX(),courant.getCell().celluleGetY()-1).getType() != Type.PIEGE )){
                if(nouvDist < dist[courant.getCell().celluleGetY()-1][courant.getCell().celluleGetX()]){
                    dist[courant.getCell().celluleGetY()-1][courant.getCell().celluleGetX()] = nouvDist;
                    parent[courant.getCell().celluleGetY()-1][courant.getCell().celluleGetX()] = courant.getCell();
                    file.add(new Node(m.getCell(courant.getCell().celluleGetX(),courant.getCell().celluleGetY()-1), nouvDist));
                }  
            }


        }

        if (dist[p.getY()][p.getX()] == Integer.MAX_VALUE) {
            System.out.println("No path to the player finded...");
            return Move.FIXE;
        }

        Cellule etape = m.getCell(p.getX(), p.getY());

        while(parent[etape.celluleGetY()][etape.celluleGetX()] != null && (parent[etape.celluleGetY()][etape.celluleGetX()].celluleGetX() != e.getX() || parent[etape.celluleGetY()][etape.celluleGetX()].celluleGetY() != e.getY())){
            etape = parent[etape.celluleGetY()][etape.celluleGetX()];
        }

        if(etape.celluleGetX()> e.getX()){
            return Move.DROITE;
        }
        else if(etape.celluleGetX() < e.getX()){
            return Move.GAUCHE;
        }
        else if(etape.celluleGetY() < e.getY()){
            return Move.HAUT;
        }
        else{
            return Move.BAS;
        }

        

    }

    //return Move.FIXE;

}