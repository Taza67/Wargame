package wargame;

import java.util.List;

public abstract class APatient extends Thread {
	// Infos
	protected List<Thread> processusAttendre;
	
	// Méthodes
	// Attend la fin des threads à attendre
	public void attendre() {
		while(MethodesAuxiliaires.threadVivant(processusAttendre));
	}
}
