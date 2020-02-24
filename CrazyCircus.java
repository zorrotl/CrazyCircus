/**
 * @Author Albéric Cusin | Tran Léo
 * 11/03/2019
 * @brief Main de l'application
 */


import java.util.*;

public class CrazyCircus {

	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		int nbPlayers = 0;
		String inputInt = new String();
		boolean inputCorrect = false;
		System.out.println("Combien de joueurs vont jouer?");
		do {
			try {
				do {

					System.out.println("Vous devez être au moins 2 joueurs !");
					inputInt = sca.next();
					nbPlayers = Integer.parseInt(inputInt);
					inputCorrect = true;
				} while (nbPlayers < 2);
				Game game = new Game(nbPlayers);
				game.start();
				game.play();
				game.rank();
				game.viewScore();
				

			} catch (NumberFormatException e) {
				System.out.println(e);
				System.out.println("On avait besoin d'un nombre entier...");
			}
		} while (!inputCorrect);

	}

}
