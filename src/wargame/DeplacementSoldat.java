package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeplacementSoldat extends Thread implements IConfig {
	// Infos
	Carte carte;
	Soldat soldat;
	CheminDijkstra chemin;
	
	// Constructeurs
	public DeplacementSoldat(Carte carte, Soldat soldat, CheminDijkstra chemin) {
		super();
		this.carte = carte;
		this.soldat = soldat;
		this.chemin = chemin;
	}
	
	// Méthodes
	public void run() {
		if (soldat instanceof Heros) carte.getPanPartie().getTableauBord().getBoutonsTour().setVisible(false);
		deplacerSoldat();
		if (soldat instanceof Heros) carte.getPanPartie().getTableauBord().getBoutonsTour().setVisible(true);
	}
	// Déplace le soldat case par case
	public void deplacerSoldat() {
		List<Element> ch = chemin.getChemin();
		for (Element e : ch) {
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e1) {}
			soldat.seDeplace(e.pos);
			soldat.setPorteeDeplacement(soldat.getPorteeDeplacement() - 1);
			carte.recalculerZonesDep();
			carte.getPanPartie().repaint();
		}
	}
}
