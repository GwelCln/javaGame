package map;

import type.*;
import map.*;
import exception.EnemyIndexOutOfBound;



/**
 * Event class that will manage event happening during the game such has move the player pick test a collision etc...
 */
public class Event{




    public Event(){

    }


    /**
     * Function that return False if a coordonate is not appropriate 
     * /!\ important to enter first the x coordonate and then the y coordonate
     * @param m current map on which the player is
     * @param x x coordonate of the player you want to test
     * @param y y coordonate of the player you want to test
     * @return return a boolean true if the case is available else false 
     */
    public boolean collisionDetector(Map m, int x, int y){
        //this.tab[y][x].getCollision();
        // if(m.getCell(x, y).getCollision() == true){
        //     System.out.println(m.getCell(x, y).getCollision());
        //     if(m.getCell(x, y).getType() == Type.PORTE_V){
        //         return false;
        //     }

        //     if(x==0){ //trying to pass left wall case
        //         return collisionDetector(m, m.getWidth()-2, y);
        //     }
        //     else if(x==m.getWidth()-1){ //trying to pass right wall case
        //         return collisionDetector(m, 1, y);
        //     }
        //     else if(y==0){ //trying to pass top wall case
        //         return collisionDetector(m, x, m.getHeight()-2);
        //     }
        //     else if(y==m.getHeight()-1){ //trying to pass bottom wall case
        //         return collisionDetector(m, x, 1);
        //     }
        //     else{
        //         return false;
        //     }
        // }
        
        // System.out.println(m.getCell(x, y).getCollision());
        // return false;
        if(x == -1){
            return m.getCell(m.getWidth()-1, y).getCollision();
        }
        else if(y == -1 ){
            return m.getCell(x, m.getHeight()-1).getCollision();
        }
        else if(x == m.getWidth()){
            return m.getCell(0, y).getCollision();
        }
        else if(y == m.getHeight()){
            return m.getCell(x, 0).getCollision();
        }
        return m.getCell(x, y).getCollision();
        
    }


    public boolean enemyCollisionDetector(Map m, int x, int y){

        if(x == -1){
            return enemyCollisionDetector(m, m.getWidth()-1, y);
        }
        else if(y == -1 ){
            return enemyCollisionDetector(m, x, m.getHeight()-1);
        }
        else if(x == m.getWidth()){
            return enemyCollisionDetector(m, 0, y);
        }
        else if(y == m.getHeight()){
            return enemyCollisionDetector(m, x, 0);
        }

        if(m.getCell(x,y).getType() == Type.PIEGE){
            return true;
        }
        else{
            return collisionDetector(m, x, y);
        }


    }

    /**
     * Function that manage event when a player enter a new place of the map
     */
    public void eventManager(Map m){

        if(m.getCell(m.getPlayerX(), m.getPlayerY()).getCoin() == true){
            m.getCell(m.getPlayerX(), m.getPlayerY()).activateCoin();
            m.getPlayer().updateScore(10);
            m.updateCoinNumber(-1);
        }
        else if(m.getCell(m.getPlayerX(), m.getPlayerY()).getType() == Type.PIEGE){
            m.getCell(m.getPlayerX(), m.getPlayerY()).updateType(Type.VIDE);
            m.getPlayer().updateHP(-2);
            m.resetPlayerPosition();
        }

        
        
        if(m.enemyOnCell(m.getPlayerX(), m.getPlayerY())){
            int total = m.getNumberOfEnemy();
            for(int i=0;i<total;i++){
                if(m.getPlayerX() == m.getEnemyX(i) && m.getPlayerY() == m.getEnemyY(i)){
                    m.updatePlayerHP(m.getEnemyDamage(i));
                    System.out.println("Oh no ! " + m.getEnemyName(i) + " hit you...");
                    m.resetEnemyPosition(i);
                    break; // note si plusieur enemies sur la même case seulement un fais des dégâts
                }
            }
      
        }

    }


    public boolean ghostCollisionDetector(Map m, int x, int y){
        if(x == 0){
            return true;
        }
        else if(y == 0 ){
            return true;
        }
        else if(x == m.getWidth()-1){
            return true;
        }
        else if(y == m.getHeight()-1){
            return true;
        }
        return false;
        
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




     // /**
    //  * Function that change the coordonate of the player on the map if the case is available
    //  * @param m M on which you want to do action
    //  * @param mv Decide in which direction you want to go 
    //  */
    // public void movePlayer(Map m, Move mv){ //ajouter la verif en cas de mur
    //     switch(mv){
    //         case DROITE:
    //             if(collisionDetector(m, m.getPlayerX()+1, m.getPlayerY()) == false){
    //                 m.movePlayer(mv);
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //         case GAUCHE:
    //             if(collisionDetector(m, m.getPlayerX()-1, m.getPlayerY()) == false){
    //                 m.movePlayer(mv);
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //         case BAS:
    //             if(collisionDetector(m, m.getPlayerX(), m.getPlayerY()+1) == false){
    //                 m.movePlayer(mv);
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //         case HAUT:
    //             if(collisionDetector(m, m.getPlayerX(), m.getPlayerY()-1) == false){
    //                 m.movePlayer(mv);
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //     }
    // }


    // public void moveEnemy(Map m, int index){

    //     int x = m.getEnemyX(index);
    //     int y = m.getEnemyY(index);


    //     try{
    //     int move = (int) ( Math.random() * 4);

    //     switch(move){
    //         case 0: // droite
    //             if(enemyCollisionDetector(m, m.getEnemyX(index)+1, m.getEnemyY(index)) == false){
    //                 m.updateEnemyPosition(index ,(m.getEnemyX(index)+1)%(m.getWidth()), y); 
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //         case 1: // Gauche
    //             if(enemyCollisionDetector(m, m.getEnemyX(index)-1, m.getEnemyY(index)) == false){
    //                 m.updateEnemyPosition(index ,((m.getEnemyX(index)-1+m.getWidth())%(m.getWidth())), y);
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //         case 2: // bas
    //             if(enemyCollisionDetector(m, m.getEnemyX(index), m.getEnemyY(index)+1) == false){
    //                 m.updateEnemyPosition(index ,x , (m.getEnemyY(index)+1)%(m.getHeight())); 
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //         case 3: //haut
    //             if(enemyCollisionDetector(m, m.getEnemyX(index), m.getEnemyY(index)-1) == false){
    //                 m.updateEnemyPosition(index ,x, ((m.getEnemyY(index)-1+m.getHeight())%(m.getHeight())));
    //             }
    //             else{
    //                 System.out.println("Impossible d'aller par ici !");
    //             }
    //             break;
    //     }
    //     }
    //     catch(EnemyIndexOutOfBound e){
    //         System.err.println(e.getMessage());
    //     }
    // }



}