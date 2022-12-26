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
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {}
			Soldat s = (Soldat)e;
			s.seDeplace(s.aleaElem(s.getZoneDeplacement()).pos);
			carte.getPanPartie().repaint();
		}
		carte.finirTour(MECHANT);
	}
}
