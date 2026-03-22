package player;


/**
 * Player class
 */
public class Player {
		/**
		* @author Collin Gweltaz
		* @version 1.0
		*/


		private final String name; // Not updatable
		private int score; // Only the class player can acces this variable 
		private static int numberOfPlayer=0; //Shared Variable
		private int hp=5;
	

		/**
		 * Constructor function, build the player with a score = 0. 
		 * @param name Name given to the player 
		 */
		public Player(String name) {
			this.numberOfPlayer++;
			if(name == null){
				this.name="Joueur"+Player.getNumberOfPlayer();
			}
			else{
				this.name = name;
			}		
			this.score = 0;
			this.display();
		}

		/**
		 * Default player constructor, argumentless method that give PlayerN as name with N the number total of player
		 */
		public Player(){
			this(null);
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
		public String toString(){
			if(this.score <= 1){
				return this.hp + " HP" + "\n" + this.name + " : " + this.score + " pt";
			}
			else{
				return  this.hp + " HP" + "\n" + this.name + " : " + this.score + " pts";
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
		 * Function that return the name of the player
		 * @return String : name given to the player
		 */
		public String getName(){
			return this.name;
		}

		/**
		 * Function that return the score of the player 
		 * @return Integer : score of the player
		 */
		public int getScore(){
			return this.score;
		}
		
		/**
		 * update the player health point 
		 * @param value positive or negative value which is added to the hp
		 */
		public void updateHP(int value){
			this.hp = this.hp+value<0 ? 0 : this.hp+value;
		}

		/**
		 * Method that return the current number of hp of the player
		 */
		public int getHP(){
			return this.hp;
		}

		/**
		 * Method that reset the number of HP of the player
		 * reset value = 5hp
		 */
		public void resetHP(){
			hp=5;
		}

		/**
		 * Method that reset the player score to 0
		 */
		public void resetScore(){
			this.score=0;
		}


}
