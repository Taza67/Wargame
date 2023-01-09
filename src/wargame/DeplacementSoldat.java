package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeplacementSoldat extends APatient implements IConfig {
	// Infos
	Carte carte;
	Soldat soldat;
	private List<Element> chemin;
	
	// Constructeurs
	public DeplacementSoldat(Carte carte, Soldat soldat, List<Element> chemin, List<Thread> processusAttendre) {
		super();
		this.processusAttendre = processusAttendre;
		this.carte = carte;
		this.soldat = soldat;
		this.chemin = chemin;
	}
	
	// Méthodes
	public void run() {
		attendre();
		deplacerSoldat();
	}
	// Déplace le soldat case par case
	public void deplacerSoldat() {
		soldat.getSol().enleverEffets(soldat);
		for (Element e : chemin) {
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {}
			soldat.seDeplace(e.getPos());
			soldat.setPorteeDeplacement(soldat.getPorteeDeplacement() - 1);
			carte.recalculerZonesDep();
			carte.recalculerZonesVisuelles();
			carte.getPanPartie().repaint();
		}
		soldat.getSol().enleverEffetPorteeVisuelle(soldat);
		soldat.getSol().appliquerEffets(soldat);
		soldat.majZoneDeplacement();
		soldat.majZoneVisuelle();
		if (soldat.getPorteeDeplacement() <= 0)
			soldat.setAJoue(true);
		carte.getPanPartie().repaint();
	}
}
