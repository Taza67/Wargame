package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeplacementSoldat extends Thread implements IConfig {
	// Infos
	Carte carte;
	Soldat soldat;
	List<Element> chemin;
	
	// Constructeurs
	public DeplacementSoldat(Carte carte, Soldat soldat, List<Element> chemin) {
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
		soldat.getSol().enleverEffets(soldat);
		for (Element e : chemin) {
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e1) {}
			soldat.seDeplace(e.pos);
			soldat.setPorteeDeplacement(soldat.getPorteeDeplacement() - 1);
			carte.recalculerZonesDep();
			carte.recalculerZonesVisuelles();
			carte.getPanPartie().repaint();
		}
		soldat.getSol().appliquerEffets(soldat);
	}
}
