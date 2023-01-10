package wargame;

import java.util.List;

public class MethodesAuxiliaires {
	// Vérifie si parmi une liste de threads il y en au moins un d'actif
	public static boolean threadVivant(List<Thread> threads) {
		boolean ret = false;
		for (Thread t : threads) ret = ret || t.isAlive();
		return ret;	
	}
	// Renvoie un nombre aléatoire compris entre inf et sup
	public static int alea(int inf, int sup) {
		return inf + (int)(Math.random() * ((sup - inf) + 1));
	}
}
