/**
 * @Author Albéric Cusin | Tran Léo
 */
package java_BPO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {
	public boolean movesRegular(String s) {
		if(s.length()%2 != 0) {
			System.out.println("Mouvement incorrect ! Vous n'avez plus le droit de jouer ce tour.");
			return false;
		}
		for(int k = 0; k < s.length()/2; k++) {
			String order = new String();
			order = "" + s.charAt(k*2) + s.charAt(k*2 + 1);
			if(!order.equals("KI") && !order.equals("LO") && !order.equals("SO") && !order.equals("MA") && !order.equals("NI")) {
				System.out.println("Instruction inconnue ! Vous ne pouvez plus jouer ce tour.");
				return false;
			}
		}
		return true;
	}
	public boolean movesDifficult(String s) {
		if(s.length()%2 != 0) {
			System.out.println("Mouvement incorrect ! Vous n'avez plus le droit de jouer ce tour.");
			return false;
		}
		for(int k = 0; k < s.length()/2; k++) {
			String order = new String();
			order = "" + s.charAt(k*2) + s.charAt(k*2 + 1);
			if(!order.equals("KI") && !order.equals("LO") && !order.equals("MA") && !order.equals("NI")) {
				System.out.println("Instruction inconnue ! Vous ne pouvez plus jouer ce tour.");
				return false;
			}
		}
		return true;
	}

	@Test
	void test() {
		String order = "SA";
		String order2 = "SO";
		String order3 = "KI";
		assertFalse(movesRegular(order));
		assertTrue(movesRegular(order2));
		assertFalse(movesDifficult(order2));
		assertTrue(movesDifficult(order3));
		
	}

}
