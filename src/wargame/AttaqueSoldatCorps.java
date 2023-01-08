package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AttaqueSoldatCorps extends Thread implements IConfig {
	// Infos
	Carte carte;
	Soldat soldat;
	Soldat cible;
	List<Element> chemin;
	
	// Constructeurs
	public AttaqueSoldatCorps(Carte carte, Soldat soldat, Soldat cible) {
		super();
		this.carte = carte;
		this.soldat = soldat;
		this.cible = cible;
		this.chemin = new CheminDijkstra(soldat, cible, soldat.getZoneDeplacement()).getChemin();
		this.chemin.remove(chemin.size() - 1);
	}
	
	// Méthodes
	public void run() {
		faireAttaquerSoldat();
	}
	// Déplace le soldat case par case
	public void faireAttaquerSoldat() {
		DeplacementSoldat ds = new DeplacementSoldat(carte, soldat, chemin);
		ds.run();
		while (ds.isAlive());
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e1) {}
		soldat.attaqueSoldatCorps(cible);
		soldat.setAJoue(true);
		carte.getPanPartie().repaint();
	}
}