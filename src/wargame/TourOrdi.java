package wargame;

import java.util.ArrayList;
import java.util.List;

public class TourOrdi extends Thread implements IConfig {
	// Infos
	Carte carte;
	List<Element> listeMonstres, listeHeros;
	List<Thread> processus;
	List<Thread> lastProcessus;
	
	// Constructeurs
	public TourOrdi(Carte carte, List<Thread> lastProcessus) {
		super();
		this.carte = carte;
		this.listeMonstres = carte.getListeMonstres();
		this.listeHeros = carte.getListeHeros();
		this.processus = new ArrayList<Thread>();
		this.lastProcessus = lastProcessus;
	}
	// Accesseurs
	public List<Thread> getProcessus() { return processus; }
	
	// Méthodes
	public void run() {
		faireJouerOrdi();
	}
	// Fait jouer un tour de jeu à l'ordinateur
	public void faireJouerOrdi() {
		while(threadVivant(lastProcessus));
		for (Element e : listeMonstres) {
			Soldat s = (Soldat)e;
			Element cible = s.aleaElem(s.getZoneDeplacement());
			CheminDijkstra chemin = new CheminDijkstra(s, cible, s.getZoneDeplacement());
			DeplacementSoldat dp = new DeplacementSoldat(carte, s, chemin.getChemin());
			dp.start();
			processus.add(dp);
		}
		while(threadVivant(processus));
		carte.finirTour(MECHANT);
	}
	// Vérifie si parmi une liste de threads il y en au moins un d'actif
	public boolean threadVivant(List<Thread> threads) {
		boolean ret = false;
		for (Thread t : threads) ret = ret || t.isAlive();
		return ret;	
	}
}
