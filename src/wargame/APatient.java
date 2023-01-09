package wargame;

import java.util.List;

public abstract class APatient extends Thread {
	// Infos
	transient List<Thread> processusAttendre;
	
	// Méthodes
	// Attend la fin des threads à attendre
	public void attendre() {
		while(threadVivant(processusAttendre));
	}
	// Vérifie si parmi une liste de threads il y en au moins un d'actif
	public boolean threadVivant(List<Thread> threads) {
		boolean ret = false;
		for (Thread t : threads) ret = ret || t.isAlive();
		return ret;	
	}
}
