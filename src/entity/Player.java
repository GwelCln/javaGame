package entity;
import map.*;
import type.*;

/**
 * Player class
 */
public class Player extends Entity {
		/**
		* @author Collin Gweltaz
		* @version 1.0
		*/

		private int score; // Only the class player can acces this variable 
		private static int numberOfPlayer=0; //Shared Variable

		/**
		 * Constructor function, build the player with a score = 0. 
		 * @param name Name given to the player 
		 * @param x x coordonate of the player
		 * @param y y coordonate of the player
		 */
		public Player(String name, int x, int y) {
			super(name, x, y, 5);
			this.score = 0;
			numberOfPlayer++;
		}

		/**
		 * Default player constructor, argumentless method that give PlayerN as name with N the number total of player
		 */
		public Player(){
			this(null, 1, 1);
		}

		/**
		 * Display function of player object 
		 * The function display the name and the score of the player in the terminal. Allowing the player to know his score.
		 */
		public void display(){
			System.out.println(this.toString());
		}

		/**
		 * update the player score 
		 * @param value positive or negative value which is added to the score
		 */
		public void updateScore(int value){
			this.score = this.score+value<0 ? 0 : this.score+value;
		}

		//@override
		/**
		 * Redefinition of the player toString methode, now print name : score pt(s)
		 */
		@Override
		public String toString(){
			for(int i=0 ;i<5;i++){
					if(i < this.hp){
						System.out.print("❤️  ");
					}
					else{
						System.out.print("💔 ");
					}
				}
			System.out.println();
			if(this.score <= 1){
				return "\n" + this.name + " : " + this.score + " pt";
			}
			else{
				return  "\n" + this.name + " : " + this.score + " pts";
			}
		}

		/**
		 * Redefinition of the equals function for Player object. It return True if the parameter and the caller are both Player instance with same name.
		 * @param obj generical object
		 * @return Boolean value True if the parameter and the caller are both Player instance with same name else false.
		 */
		public boolean equals(Object obj){
			if(obj instanceof Player p){
				//System.out.println(p.name);
				if(this.name.equalsIgnoreCase(p.name)){
					return true;
				}
			}
			return false;
		}

		/**
		 * Static method that return the total number of player initialized
		 * @return return an integer that is the number of player initialized
		 */
		public static int getNumberOfPlayer(){
			return numberOfPlayer;
		}

		/**
		 * Function that reset the player Hp to 5 id the player restart a game
		 */
		public void resetHP(){
			this.hp=5;
		}


		/**
		 * Function that reset the player score to 0 if the player restart a game
		 */
		public void resetScore(){
        	this.score =0;
    	}


	/**
     * Function that change the coordonate of the player on the map if the case is available
     * @param m M on which you want to do action
     * @param mv Decide in which direction you want to go 
     */
	@Override
    public void move(Map m, Move mv){ //ajouter la verif en cas de mur
		Event e = new Event();
        switch(mv){
            case DROITE:
                if(e.collisionDetector(m, this.x+1, this.y) == false){
                    super.move(m, mv);
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case GAUCHE:
                if(e.collisionDetector(m, this.x-1, this.y) == false){
                    super.move(m, mv);
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case BAS:
                if(e.collisionDetector(m, this.x, this.y+1) == false){
                    super.move(m, mv);
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
            case HAUT:
                if(e.collisionDetector(m, this.x, this.y-1) == false){
                    super.move(m, mv);
                }
                else{
                    System.out.println("Impossible d'aller par ici !");
                }
                break;
        }
    }

}
