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

			Scanner input = new Scanner(System.in);

			boolean replay = true;

			System.out.print("What's your name : ");
            String playerName = input.nextLine();

            Player j = new Player(playerName);

			int lvl=0;

			while(replay){

				Map level1 = Map.loadMap(args[lvl], j);
				
				if(level1 == null){
					System.exit(0); // Error exit due to unknow save file
				}


				level1.display();

				level1.toString();

				Boolean game = true;

				
				Move move;


				System.out.println("z up | s down | q left | d right | e quit : ");
				while(game){

					String mv = input.nextLine();

					switch(mv){
						case "e":
							game = false;
							replay = false;
							System.exit(0);
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
						game = false;
						level1.levelComplete();
						lvl++;

						if(lvl == args.length){
							System.out.println("Congratulation " + level1.getName() + ", you finished the game !! \nThank you very much...\nCollin Gweltaz");
							input.nextLine();
							System.exit(0);
						}

						System.out.print("Press enter to continue...");
						input.nextLine();

					}
					else if(level1.getHP() == 0){
						game = false;
						level1.gameOver();

						System.out.print("Press r to retry or q to quit : ");
						String cr = input.nextLine();
						switch(cr){

							case "r":
								System.out.println("Starting new game.");
								j.resetHP();
								j.resetScore();
								
								lvl=0;
								break;

							case "q":
								replay = false;
								break;

							default:		
								replay = false;
								break;
						}
					}
				}
				System.out.println();
				
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
