/**
 * @Author Albéric Cusin | Tran Léo
 * 11/03/2019
 * @brief Classe, méthodes, attributs de carte
 */


import java.util.Arrays;

public class Card {
	private Animal.animal[] red;
	private Animal.animal[] blue;
	private int redLength;// connaitre le nombre d'animaux sur le podium rouge
	private int blueLength;// connaitre le nombre d'animaux sur le podium bleu
	/*
	 * @constructor
	 */

	public Card() {
		this.red = new Animal.animal[3];
		this.blue = new Animal.animal[3];
		Arrays.fill(red, Animal.animal.VIDE);
		Arrays.fill(blue, Animal.animal.VIDE);
		this.redLength = 0;
		this.blueLength = 0;
	}

	/*
	 * @brief informe si l'ordre donné est conforme (mode régulier)
	 * 
	 * @param[in] s : l'ordre (doit être un nombre de lettre pair)
	 * 
	 * @return true si l'ordre est conforme, false sinon
	 */
	public boolean movesRegular(String s) {
		if (s.length() % 2 != 0) {
			System.out.println("Mouvement incorrect ! Vous n'avez plus le droit de jouer ce tour.");
			return false;
		}
		for (int k = 0; k < s.length() / 2; k++) {
			String order = new String();
			order = "" + s.charAt(k * 2) + s.charAt(k * 2 + 1);
			if (!order.equals("KI") && !order.equals("LO") && !order.equals("SO") && !order.equals("MA")
					&& !order.equals("NI")) {
				System.out.println("Instruction inconnue ! Vous ne pouvez plus jouer ce tour.");
				return false;
			}
			Animal.animal swap;
			switch (order) {
			case "KI":
				if (blueLength != 0) {
					red[redLength] = blue[blueLength - 1];
					blue[blueLength - 1] = Animal.animal.VIDE;
					blueLength--;
					redLength++;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			case "LO":
				if (redLength != 0) {
					blue[blueLength] = red[redLength - 1];
					red[redLength - 1] = Animal.animal.VIDE;
					blueLength++;
					redLength--;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			case "SO":
				if (redLength != 0 && blueLength != 0) {
					swap = red[redLength - 1];
					red[redLength - 1] = blue[blueLength - 1];
					blue[blueLength - 1] = swap;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			case "MA":
				if (redLength == 2) {
					swap = red[0];
					red[0] = red[1];
					red[1] = swap;
					break;
				} else if (redLength == 3) {
					swap = red[0];
					red[0] = red[1];
					red[1] = red[2];
					red[2] = swap;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			case "NI":
				if (blueLength == 2) {
					swap = blue[0];
					blue[0] = blue[1];
					blue[1] = swap;
					break;
				} else if (blueLength == 3) {
					swap = blue[0];
					blue[0] = blue[1];
					blue[1] = blue[2];
					blue[2] = swap;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * @brief affichage des mouvements autorisés (mode régulier)
	 * 
	 * @return retourne les ordres et mouvement possibles
	 */
	public String displayMovesReg() {
		return String.format(" %5s %5s  %15s\n %5s %5s %15s\n %s", "KI : Bleu-->Rouge", "|", "MA : Rouge ^",
				"LO : Rouge-->Bleu", "|", "NI : Bleu ^", "SO : Bleu<-->Rouge");
	}

	/*
	 * @brief affichage des mouvements autorisés (mode difficile)
	 * 
	 * @return retourne les ordres et mouvement possibles
	 */
	public boolean movesDifficult(String s) {
		assert (s.length() % 2 == 0);
		for (int k = 0; k < s.length() / 2; k++) {
			String order = new String();
			order = "" + s.charAt(k * 2) + s.charAt(k * 2 + 1);
			if (!order.equals("KI") && !order.equals("LO") && !order.equals("MA") && !order.equals("NI")) {
				System.out.println("Instruction inconnue ! Vous ne pouvez plus jouer ce tour.");
				return false;
			}
			Animal.animal swap;
			switch (order) {
			case "KI":
				if (blueLength != 0) {
					red[redLength] = blue[blueLength - 1];
					blue[blueLength - 1] = Animal.animal.VIDE;
					blueLength--;
					redLength++;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			case "LO":
				if (redLength != 0) {
					blue[blueLength] = red[redLength - 1];
					red[redLength - 1] = Animal.animal.VIDE;
					blueLength++;
					redLength--;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			case "MA":
				if (redLength == 2) {
					swap = red[0];
					red[0] = red[1];
					red[1] = swap;
					break;
				} else if (redLength == 3) {
					swap = red[0];
					red[0] = red[1];
					red[1] = red[2];
					red[2] = swap;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			case "NI":
				if (blueLength == 2) {
					swap = blue[0];
					blue[0] = blue[1];
					blue[1] = swap;
					break;
				} else if (blueLength == 3) {
					swap = blue[0];
					blue[0] = blue[1];
					blue[1] = blue[2];
					blue[2] = swap;
					break;
				} else {
					System.out.println("Ordre incorrect ! Vous n'avez plus le droit de jouer ce tour.");
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * @brief informe si l'ordre donné est conforme (mode difficile)
	 * 
	 * @param[in] s : l'ordre (doit être un nombre de lettre pair)
	 * 
	 * @return true si l'ordre est conforme, false sinon
	 */
	public String displayMovesDif() {
		return String.format(" %5s %5s  %15s\n %5s %5s %15s\n", "KI : Bleu-->Rouge", "|", "MA : Rouge ^",
				"LO : Rouge-->Bleu", "|", "NI : Bleu ^");
	}

	/*
	 * @brief setter du podium rouge
	 */
	public void setRed(Animal.animal a1, Animal.animal a2, Animal.animal a3, int length) {
		red[0] = a1;
		red[1] = a2;
		red[2] = a3;
		redLength = length;
	}

	/*
	 * @brief setter du podium rouge
	 */
	public void setBlue(Animal.animal a1, Animal.animal a2, Animal.animal a3, int length) {
		blue[0] = a1;
		blue[1] = a2;
		blue[2] = a3;
		blueLength = length;
	}

	/*
	 * @brief informe si les cartes sont les mêmes
	 * 
	 * @param[in] a : carte comparé à celle de la méthode
	 * 
	 * @return true si même carte, false sinon
	 */
	public boolean sameCard(Card a) {
		for (int i = 0; i < 3; i++) {
			if (red[i] != a.red[i]) {
				return false;
			}
			if (blue[i] != a.blue[i]) {
				return false;
			}
		}
		return true;
	}

	/*
	 * @brief copie la carte param[in] a : la carte à copié
	 */
	public void copyCard(Card a) {
		red[0] = a.red[0];
		red[1] = a.red[1];
		red[2] = a.red[2];
		blue[0] = a.blue[0];
		blue[1] = a.blue[1];
		blue[2] = a.blue[2];
		redLength = a.redLength;
		blueLength = a.blueLength;
	}

	/*
	 * @brief affiche les podiums
	 * 
	 * @return retourne la dispositions des animaux sur les podiums
	 */
	public String toString() {
		if (redLength == 3) {
			return String.format("%16s\n %15s\n %15s\n %15s %15s\n %15s %15s\n", red[2], red[1], red[0], "- - - -",
					"- - - -", "ROUGE", "BLEU");
		}
		if (redLength == 2) {
			return String.format("%16s\n %15s %15s\n %15s %15s\n %15s %15s\n", red[1], red[0], blue[0], "- - - -",
					"- - - -", "ROUGE", "BLEU");
		}
		if (redLength == 1) {
			return String.format("%16s %15s\n %15s %15s\n %15s %15s\n %15s %15s\n", "", blue[1], red[0], blue[0],
					"- - - -", "- - - -", "ROUGE", "BLEU");
		}
		return String.format("%16s %15s\n %15s %15s\n %15s %15s\n %15s %15s\n %15s %15s\n", "", blue[2], "", blue[1],
				"", blue[0], "- - - -", "- - - -", "ROUGE", "BLEU");
	}

}
