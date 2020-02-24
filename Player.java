/**
 * @Author Alb�ric Cusin | Tran L�o
 * 11/03/2019
 * @brief Classe, m�thodes, attributs de joueur
 */


public class Player {
	private String name;
	private int points;
	private boolean canPlay;

	/*
	 * @constructor
	 */
	public Player(String s) {
		this.name = s;
		points = 0;
		canPlay = false;
	}

	/*
	 * @brief getter de "name"
	 */
	public String getName() {
		return name;
	}

	/*
	 * @brief getter de "points"
	 */
	public int getPoints() {
		return points;
	}

	/*
	 * @brief setter de "points"
	 */
	public void setPoints() {
		points++;
	}

	/*
	 * @brief getter de "canPlay"
	 */
	public boolean getcanPlay() {
		return canPlay;
	}

	/*
	 * @brief setter de "canPlay"
	 */
	public void setcanPlay(boolean bool) {
		canPlay = bool;
	}

	/*
	 * @brief toString le nom, points du joueur
	 */
	public String toString() {
		return (getName() + " : " + getPoints() + " points");
	}

}
