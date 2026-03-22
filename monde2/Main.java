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

			if (args.length == 0) {
				throw new FileNotFoundException("Erreur : absence de fichier de sauvegarde introuvable.\nVous devez donner un nom de sauvegarde par exemple map1.");
			}

			Map level1 = Map.loadMap(args[0]);
			
			if(level1 == null){
				System.exit(0); // Error exit due to unknow save file
			}

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
						break;
					case "z":
						move = Move.HAUT;
						level1.movePlayer(move);
						break;
					case "s":
						move = Move.BAS;
						level1.movePlayer(move);
						break;
					case "d":
						move = Move.DROITE;
						level1.movePlayer(move);
						break;
					case "q":
						move = Move.GAUCHE;
						level1.movePlayer(move);
						
						break;
					default:
						System.out.println("z up | s down | q left | d right | e quit : ");
						break;
				}
				level1.display();
				System.out.println(level1.toString());
				if(level1.getCoinNumber() == 0){
					level1.levelComplete();
					break;
				}
				
			}
		}
		catch(ShapeNotFoundError e){
			System.err.println(e.getMessage());
		}
		catch(FileNotFoundException e){
			System.err.println(e.getMessage());
		}
		// catch(IOException e){
		// 	System.err.println(e.getMessage());
		// }		
		catch (NotAllowedSizeException | OutOfMapException | PlayerNullException | NotAllowedCoordonate e) {
			System.err.println(e.getMessage());
		}
	}
	
}
