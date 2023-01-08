package wargame;

import java.util.concurrent.TimeUnit;

public class AttaqueSoldatDistance extends Thread implements IConfig {
	// Infos
	Carte carte;
	Soldat soldat;
	Soldat cible;
	
	// Constructeurs
	public AttaqueSoldatDistance(Carte carte, Soldat soldat, Soldat cible) {
		super();
		this.carte = carte;
		this.soldat = soldat;
		this.cible = cible;
	}
	
	// Méthodes
	public void run() {
		if (soldat instanceof Heros) carte.getPanPartie().getTableauBord().getBoutonsTour().setVisible(false);
		faireAttaquerSoldat();
		if (soldat instanceof Heros) carte.getPanPartie().getTableauBord().getBoutonsTour().setVisible(true);
	}
	// Déplace le soldat case par case
	public void faireAttaquerSoldat() {
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e1) {}
		soldat.attaqueSoldatDistance(cible);
		soldat.setAJoue(true);
	}
}
