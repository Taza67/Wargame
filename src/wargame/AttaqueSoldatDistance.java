package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AttaqueSoldatDistance extends APatient implements IConfig {
	// Infos
	Carte carte;
	Soldat soldat;
	Soldat cible;
	
	// Constructeurs
	public AttaqueSoldatDistance(Carte carte, Soldat soldat, Soldat cible, List<Thread> processusAttendre) {
		super();
		this.processusAttendre = processusAttendre;
		this.carte = carte;
		this.soldat = soldat;
		this.cible = cible;
	}
	
	// Méthodes
	public void run() {
		attendre();
		faireAttaquerSoldat();
	}
	// Déplace le soldat case par case
	public void faireAttaquerSoldat() {
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e1) {}
		soldat.attaqueSoldatDistance(cible);
		soldat.setAJoue(true);
		carte.getPanPartie().repaint();
	}
}
