package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TourOrdi extends Thread implements IConfig {
	// Infos
	Carte carte;
	List<Element> listeMonstres, listeHeros;
	
	// Constructeurs
	public TourOrdi(Carte carte) {
		super();
		this.carte = carte;
		this.listeMonstres = carte.getListeMonstres();
		this.listeHeros = carte.getListeHeros();
	}
	
	// Méthodes
	public void run() {
		faireJouerOrdi();
	}
	// Fait jouer un tour de jeu à l'ordinateur
	public void faireJouerOrdi() {
		for (Element e : listeMonstres) {
			Soldat s = (Soldat)e;
			Element cible = s.aleaElem(s.getZoneDeplacement());
			CheminDijkstra chemin = new CheminDijkstra(s, cible, s.getZoneDeplacement());
			DeplacementSoldat dp = new DeplacementSoldat(carte, s, chemin);
			dp.start();
		}
		carte.finirTour(MECHANT);
	}
}
