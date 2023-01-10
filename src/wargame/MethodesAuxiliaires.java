package wargame;

import java.util.List;


/**
 * classe de methodes auxiliaire
 *
 */
public class MethodesAuxiliaires {
	/**
	 * Methode booleenne qui renvoi vrai si le perso est vivant
	 * @param threads
	 * @return bool
	 */
	public static boolean threadVivant(List<Thread> threads) {
		boolean ret = false;
		for (Thread t : threads) ret = ret || t.isAlive();
		return ret;	
	}
	
	/**
	 * Renvoie un nombre aléatoire compris entre inf et sup
	 * @param inf
	 * @param sup
	 * @return
	 */
	public static int alea(int inf, int sup) {
		return inf + (int)(Math.random() * ((sup - inf) + 1));
	}
}
