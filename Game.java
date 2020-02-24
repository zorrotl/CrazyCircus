/**
 * @Author Albéric Cusin | Tran Léo
 * 11/03/2019
 * @brief Classe, méthodes, attributs du jeu
 */


import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Game {
	private static Card[] deckGoal;
	private static Player[] players;
	private static final int NB_GOAL_CARD = 24;
	private boolean regular;

	/*
	 * @constructor
	 */
	public Game(int nbPlayers) {
		Game.deckGoal = new Card[NB_GOAL_CARD];
		Game.players = new Player[nbPlayers];
		this.regular = true;
		createGoalCard();

	}

	/*
	 * @brief initialisation de la partie
	 * 
	 * @return true si tous les paramètres sont respectés, false si exception levé
	 */
	public void start() {
		Scanner sc = new Scanner(System.in);
		int level;
		boolean inputCorrect = false;
		String inputInt = new String();
		String s = new String();
		for (int i = 0; i < players.length; i++) {
			System.out.println("Quel est votre nom ?");
			s = sc.next();
			while (testName(s, i)) {
				System.out.println("Nom déjà pris : Veuillez en saisir un autre.");
				s = sc.next();
			}
			Player p = new Player(s);
			players[i] = p;
		}
		do {
			try {
				do {
					System.out.println("Niveau de difficulté : Regulier : 1 ? Difficile : 2 ?");
					inputInt = sc.next();
					level = Integer.parseInt(inputInt);
					inputCorrect = true;
				} while (level != 1 && level != 2);
				if (level == 2) {
					System.out.println("Vous avez choisi le niveau difficile, vous pensez être expert ?");
					regular = false;
				}
			} catch (NumberFormatException e) {
				System.out.println(e);
				System.out.println("On avait besoin d'un nombre entier...");
			}
		} while (!inputCorrect);

	}

	/*
	 * @brief gestion de la partie
	 */
	public void play() {
		shuffleDeck();
		String stillPlay = new String();// savoir si le jeu continue
		int lap = 1;// nombre de tours
		String name = new String();// nom du joueur
		String order = new String();// ordre du joueur
		int indexPlayer;// index du joueur dans le tableau en fonction de son nom
		Card playerCard = new Card();// carte du joueur pour ne pas modifier celle du deck
		boolean solutionFound = false;// savoir si la solution est trouvée
		Scanner scan = new Scanner(System.in);
		while (lap < 24) {
			playersCanPlay();
			solutionFound = false;
			if (lap > 1) {// demande pour continuer le jeu
				do {
					System.out.println("Voulez-vous continuer ? Oui? Non?");
					stillPlay = scan.next();
				} while (!stillPlay.equalsIgnoreCase("oui") && !stillPlay.equalsIgnoreCase("non"));
			}
			if (stillPlay.equalsIgnoreCase("non")) {
				break;
			}
			System.out.println("Position à atteindre :");
			System.out.println(deckGoal[lap].toString());
			System.out.println("Position de départ :");
			System.out.println(deckGoal[lap - 1].toString());
			if (regular) {
				System.out.println(playerCard.displayMovesReg());
			} else {
				System.out.println(playerCard.displayMovesDif());
			}
			while (countCanPlay() > 0 && !solutionFound) {
				// tant qu'il y au moins 1 joueurs qui peut jouer
				// tant que solution pas trouvée
				System.out.println("A vous de jouer ! (nomJoueur ordre)");
				name = scan.next();
				order = scan.next();
				indexPlayer = indexPlayer(name);
				playerCard.copyCard(deckGoal[lap - 1]);
				if (indexPlayer < 0) {
					System.out.println("Tu n'existes pas ! Ne joue pas !");
				} else if (!players[indexPlayer].getcanPlay() && indexPlayer >= 0) {
					System.out.println("Vous ne pouvez plus jouer ce tour. Arrêtez d'essayer !");
				} else if (regular) {
					// si les mouvements sont incorrects
					// si le joueur existe effectivement
					if (!playerCard.movesRegular(order) && indexPlayer >= 0) {
						players[indexPlayer].setcanPlay(false);
					}

				} else {
					if (!playerCard.movesDifficult(order) && indexPlayer >= 0) {
						players[indexPlayer].setcanPlay(false);
					}
				}
				if (deckGoal[lap].sameCard(playerCard) && indexPlayer >= 0 && players[indexPlayer].getcanPlay()) {
					// si carte du joueur est la carte objectif
					// si le joueur existe effectivement
					// si le joueur peut jouer
					System.out.println("Tu as trouvé une solution. Bien vu !");
					players[indexPlayer].setPoints();
					solutionFound = true;
				} else if (!deckGoal[lap].sameCard(playerCard) && indexPlayer >= 0
						&& players[indexPlayer].getcanPlay()) {
					System.out.println("Raté ! Vous n'avez plus le droit de jouer !");
					players[indexPlayer].setcanPlay(false);
				}
			}
			lap++;
		}
	}

	/*
	 * @brief teste si un pseudo est déjà pris
	 * 
	 * @return true si il est pris, false sinon
	 */
	public static boolean testName(String s, int k) {
		for (int i = 0; i < k; i++) {
			if (s.equalsIgnoreCase(players[i].getName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * @brief renvoie la position d'un joueur à partir de son pseudo
	 * 
	 * @return sa position
	 */
	public int indexPlayer(String name) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * @brief réinitialise le droit de jouer pour tous les joueurs
	 */
	public void playersCanPlay() {
		for (int i = 0; i < players.length; i++) {
			players[i].setcanPlay(true);
		}
	}

	/*
	 * @brief permet de savoir combien de joueur peuvent jouer
	 * 
	 * @return le nombre de joueur encore en jeu
	 */
	public int countCanPlay() {
		int count = players.length;
		for (int i = 0; i < players.length; i++) {
			if (!players[i].getcanPlay()) {
				count--;
			}
		}
		return count;
	}

	/*
	 * @brief affiche les classement des joueurs
	 */
	public void viewScore() {
		System.out.println("classement :");
		for (int i = players.length - 1; i >= 0; i--) {
			System.out.println(
					players.length - i + ". " + players[i].getName() + " : " + players[i].getPoints() + " points");
		}
	}

	/*
	 * @brief tri et classe les joueurs
	 */
	public void rank() {
		int max;
		int index;
		Player swap;
		for (int i = 0; i < players.length; i++) {
			max = 0;
			index = 0;
			swap = players[0];
			for (int j = 0; j < players.length - i; j++) {
				if (players[j].getPoints() > max) {
					max = players[j].getPoints();
					swap = players[j];
					index = j;
				}
			}
			players[index] = players[players.length - i - 1];
			players[players.length - i - 1] = swap;
		}
	}

	/*
	 * @brief mélange le paquet de cartes
	 */
	public void shuffleDeck() {
		Random ran = new Random();
		Card swap;
		int j;
		for (int i = 0; i < deckGoal.length; i++) {
			j = ran.nextInt(i + 1);
			swap = deckGoal[i];
			deckGoal[i] = deckGoal[j];
			deckGoal[j] = swap;
		}
	}

	/*
	 * @brief crée les cartes
	 */
	public void createGoalCard() {
		Card c = new Card();
		c.setRed(Animal.animal.OURS, Animal.animal.LION, Animal.animal.ELEPHANT, 3);
		c.setBlue(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		Game.deckGoal[0] = c;

		Card c1 = new Card();
		c1.setRed(Animal.animal.OURS, Animal.animal.ELEPHANT, Animal.animal.LION, 3);
		c1.setBlue(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		Game.deckGoal[1] = c1;

		Card c2 = new Card();
		c2.setRed(Animal.animal.LION, Animal.animal.OURS, Animal.animal.ELEPHANT, 3);
		c2.setBlue(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		Game.deckGoal[2] = c2;

		Card c3 = new Card();
		c3.setRed(Animal.animal.LION, Animal.animal.ELEPHANT, Animal.animal.OURS, 3);
		c3.setBlue(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		Game.deckGoal[3] = c3;

		Card c4 = new Card();
		c4.setRed(Animal.animal.ELEPHANT, Animal.animal.LION, Animal.animal.OURS, 3);
		c4.setBlue(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		Game.deckGoal[4] = c4;

		Card c5 = new Card();
		c5.setRed(Animal.animal.ELEPHANT, Animal.animal.OURS, Animal.animal.LION, 3);
		c5.setBlue(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		Game.deckGoal[5] = c5;

		Card c6 = new Card();
		c6.setRed(Animal.animal.OURS, Animal.animal.LION, Animal.animal.VIDE, 2);
		c6.setBlue(Animal.animal.ELEPHANT, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		Game.deckGoal[6] = c6;

		Card c7 = new Card();
		c7.setRed(Animal.animal.OURS, Animal.animal.ELEPHANT, Animal.animal.VIDE, 2);
		c7.setBlue(Animal.animal.LION, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		Game.deckGoal[7] = c7;

		Card c8 = new Card();
		c8.setRed(Animal.animal.LION, Animal.animal.OURS, Animal.animal.VIDE, 2);
		c8.setBlue(Animal.animal.ELEPHANT, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		Game.deckGoal[8] = c8;

		Card c9 = new Card();
		c9.setRed(Animal.animal.LION, Animal.animal.ELEPHANT, Animal.animal.VIDE, 2);
		c9.setBlue(Animal.animal.OURS, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		Game.deckGoal[9] = c9;

		Card c10 = new Card();
		c10.setRed(Animal.animal.ELEPHANT, Animal.animal.OURS, Animal.animal.VIDE, 2);
		c10.setBlue(Animal.animal.LION, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		Game.deckGoal[10] = c10;

		Card c11 = new Card();
		c11.setRed(Animal.animal.ELEPHANT, Animal.animal.LION, Animal.animal.VIDE, 2);
		c11.setBlue(Animal.animal.OURS, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		Game.deckGoal[11] = c11;

		Card c12 = new Card();
		c12.setRed(Animal.animal.ELEPHANT, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		c12.setBlue(Animal.animal.LION, Animal.animal.OURS, Animal.animal.VIDE, 2);
		Game.deckGoal[12] = c12;

		Card c13 = new Card();
		c13.setRed(Animal.animal.ELEPHANT, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		c13.setBlue(Animal.animal.OURS, Animal.animal.LION, Animal.animal.VIDE, 2);
		Game.deckGoal[13] = c13;

		Card c14 = new Card();
		c14.setRed(Animal.animal.OURS, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		c14.setBlue(Animal.animal.LION, Animal.animal.ELEPHANT, Animal.animal.VIDE, 2);
		Game.deckGoal[14] = c14;

		Card c15 = new Card();
		c15.setRed(Animal.animal.OURS, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		c15.setBlue(Animal.animal.ELEPHANT, Animal.animal.LION, Animal.animal.VIDE, 2);
		Game.deckGoal[15] = c15;

		Card c16 = new Card();
		c16.setRed(Animal.animal.LION, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		c16.setBlue(Animal.animal.OURS, Animal.animal.ELEPHANT, Animal.animal.VIDE, 2);
		Game.deckGoal[16] = c16;

		Card c17 = new Card();
		c17.setRed(Animal.animal.LION, Animal.animal.VIDE, Animal.animal.VIDE, 1);
		c17.setBlue(Animal.animal.ELEPHANT, Animal.animal.OURS, Animal.animal.VIDE, 2);
		Game.deckGoal[17] = c17;

		Card c18 = new Card();
		c18.setRed(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		c18.setBlue(Animal.animal.OURS, Animal.animal.LION, Animal.animal.ELEPHANT, 2);
		Game.deckGoal[18] = c18;

		Card c19 = new Card();
		c19.setRed(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		c19.setBlue(Animal.animal.OURS, Animal.animal.ELEPHANT, Animal.animal.LION, 3);
		Game.deckGoal[19] = c19;

		Card c20 = new Card();
		c20.setRed(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		c20.setBlue(Animal.animal.LION, Animal.animal.OURS, Animal.animal.ELEPHANT, 3);
		Game.deckGoal[20] = c20;

		Card c21 = new Card();
		c21.setRed(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		c21.setBlue(Animal.animal.LION, Animal.animal.ELEPHANT, Animal.animal.OURS, 3);
		Game.deckGoal[21] = c21;

		Card c22 = new Card();
		c22.setRed(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		c22.setBlue(Animal.animal.ELEPHANT, Animal.animal.OURS, Animal.animal.LION, 3);
		Game.deckGoal[22] = c22;

		Card c23 = new Card();
		c23.setRed(Animal.animal.VIDE, Animal.animal.VIDE, Animal.animal.VIDE, 0);
		c23.setBlue(Animal.animal.ELEPHANT, Animal.animal.LION, Animal.animal.OURS, 3);
		Game.deckGoal[23] = c23;
	}

}
