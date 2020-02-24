/**
 * @Author Albéric Cusin | Tran Léo
 */


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameTest {
	public static boolean testName(String s, int k, Player[] players) {
		for (int i = 0; i < k; i++) {
			if (s.equalsIgnoreCase(players[i].getName())) {
				return true;
			}
		}
		return false;
	}

	public int indexPlayer(String name, Player[] players) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public void playersCanPlay(Player[] players) {
		for (int i = 0; i < players.length; i++) {
			players[i].setcanPlay(true);
		}
	}

	public int countCanPlay(Player[] players) {
		int count = players.length;
		for (int i = 0; i < players.length; i++) {
			if (!players[i].getcanPlay()) {
				count--;
			}
		}
		return count;
	}

	@Test

	void test() {
		Player[] players = new Player[1];
		Player p1 = new Player("Léo");
		players[0] = p1;
		assertTrue(testName("léo", 1, players));
		assertFalse(testName("Albé", 1, players));
		assertTrue(indexPlayer("Léo", players) == 0);
		playersCanPlay(players);
		assertTrue(p1.getcanPlay());
		assertTrue(countCanPlay(players) == 1);
	}

}
