package monde2;


import player.Player;
import java.util.Scanner;  // Import the Scanner class, allow us to do input
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.*;
import map.*;
import java.io.FileNotFoundException;
import java.io.*;


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


			//System.out.println(Player.getNumberOfPlayer());
			System.out.println("Nombre de joueurs au début : " + Player.getNumberOfPlayer());
			Player j1 = new Player();
			Player j2 = new Player("Alice");
			

			j2.updateScore(2);

			try{

				Path chemin = Paths.get("save/test2.txt");
				OutputStream output;

				output = Files.newOutputStream(chemin);

				System.out.println(output.toString());

				if(! Files.exists(chemin)){
					throw new FileNotFoundException("Erreur : Fichier introuvable");
				}

				Structure test1 = new Structure(1, 3, 3, 5, 14);

				Structure[] structTab = new Structure[test1.getNumberStruct()];

				structTab[0] = test1;

				Map level1 = new Map(15,15, j2 ,5,5, structTab);

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
			catch(FileNotFoundException e){
				System.err.println(e.getMessage());
			}
			catch(IOException e){
				System.err.println(e.getMessage());
			}

			
			

	}
	
}
