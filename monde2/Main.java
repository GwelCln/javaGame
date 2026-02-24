package monde2;


import player.Player;
import java.util.Scanner;  // Import the Scanner class, allow us to do input
import java.nio.file.*;
import map.*;
import java.io.FileNotFoundException;
import java.io.*;
import java.lang.*;

/**
 * Main class
 */
public class Main {	
		/**
		* @author Collin Gweltaz
		* @version 1.0
		*/


		/**
		* Private constructor that aim to get rid of a warning that appear when javadoc is executed
		*/
		private Main() {
			// Empty constructor
		}

		
		/**
		* main fuction 
		* @param args Regular String argument in main
		*/
		public static void main(String[] args) {


			try{

				String chemin = "save/test2.txt";

				Map level1 = Map.loadMap(chemin);

				level1.display();

				level1.toString();

				Boolean game = true;

				Scanner input = new Scanner(System.in);
				Move move;


				System.out.println("z up | s down | q left | d right | e quit : ");
				while(game){

					String mv = input.nextLine();

					switch(mv){
						case "e":
							game = false;
						case "z":
							move = Move.HAUT;
							level1.movePlayer(move);
							level1.display();
							break;
						case "s":
							move = Move.BAS;
							level1.movePlayer(move);
							level1.display();
							break;
						case "d":
							move = Move.DROITE;
							level1.movePlayer(move);
							level1.display();
							break;
						case "q":
							move = Move.GAUCHE;
							level1.movePlayer(move);
							level1.display();
							break;
						default:
							System.out.println("z up | s down | q left | d right | e quit : ");
							break;
					}
					
				}
			}
			catch(ShapeNotFoundError e){
				System.err.println(e.getMessage());
			}
			catch(PlayerNullException e){
				System.err.println(e.getMessage());
			}
			catch(OutOfMapException e){
				System.err.println(e.getMessage());
			}
			catch(NotAllowedSizeException e){
				System.err.println(e.getMessage());
			}
			// catch(FileNotFoundException e){
			// 	System.err.println(e.getMessage());
			// }
			// catch(IOException e){
			// 	System.err.println(e.getMessage());
			// }		

	}
	
}
