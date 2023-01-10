package wargame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * <b>Classe TourOrdi gère la stratégie de l'ordinateur extension de APatient et implémentation de IConfig</b>
 * Elle est caractérisée par:
 * <ul>
 * <li>Une carte</li>
 * <li>Une liste de monstres</li>
 * <li> une liste de héros</li>
 * <li> Une liste des ciblées attaquées </li>
 * <li> Une liste des processus </li>
 * </ul>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */
public class TourOrdi extends APatient implements IConfig {
	/**
	 * Carte
	 */
	Carte carte;
	/**
	 * Liste de Monstres
	 */
	List<Element> listeMonstres, 
	/**
	 * Liste de héros
	 */
	listeHeros, 
	/**
	 * Liste des cibles attaquées
	 */
	listeCiblesAttaquees;
	/**
	 * Liste des processus
	 */
	List<Thread> processus;

	
	/**
	 * Constructeur d'un tour
	 * @param carte
	 * @param lastProcessus
	 */
	public TourOrdi(Carte carte, List<Thread> lastProcessus) {
		super();
		this.processusAttendre = lastProcessus;
		this.carte = carte;
		this.listeMonstres = carte.getListeMonstres();
		this.listeHeros = carte.getListeHeros();
		this.listeCiblesAttaquees = new LinkedList<Element>();
		this.processus = new ArrayList<Thread>();
	}
	
	/**
	 * run
	 */
	public void run() {
		attendre();
		faireJouerOrdi();
	}
	/**
	 *  Fait jouer un tour de jeu à l'ordinateur
	 */
	public void faireJouerOrdi() {
		for (Element e : listeMonstres) {
			reflechirStrategie((Soldat)e);
		}
		while(MethodesAuxiliaires.threadVivant(processus));
		carte.finirTour(MECHANT);
	}
	
	
	/**
	 * Recherche les cibles possibles du soldat en fonction du type de l'attaque (corps-à-corps ou à distance)
	 * @param soldat
	 * @param typeAttaque
	 * @return List Element
	 */
	public List<Element> chercherCiblesPossibles(Soldat soldat, int typeAttaque) {
		List<Element> ciblesPossibles = new LinkedList<Element>();
		if (typeAttaque == CORPS_CORPS) {
			for (Element h : listeHeros)
				if (soldat.zoneDeplacementContient(h))
					ciblesPossibles.add(h);
		} else if (typeAttaque == DISTANCE) {
			for (Element h : listeHeros)
				if (soldat.getZoneVisuelle().contient(h))
					ciblesPossibles.add(h);
		}
		return ciblesPossibles;
	}
	/**
	 * Recherche la cible la plus faible en fonction du type de l'attaque
	 * @param cibles
	 * @param typeAttaque
	 * @return Soldat
	 */
	public Soldat chercherCibleFaible(List<Element> cibles, int typeAttaque) {
		Element faible = cibles.size() == 0 ? null : cibles.get(0);
		if (typeAttaque == CORPS_CORPS) {
			for (Element c : cibles)
				if (((Soldat)faible).getPuissance() > ((Soldat)c).getPuissance())
					faible = c;
		} else if (typeAttaque == DISTANCE) {
			for (Element c : cibles)
				if (((Soldat)faible).getTir() > ((Soldat)c).getTir())
					faible = c;
		}
		return (Soldat)faible;	
	}
	/**
	 *  Retourne la cible avec le moins de points de vie
	 * @param cibles
	 * @return Soldat
	 */
	public Soldat chercherCibleLessPTS(List<Element> cibles) {
		Element nearDeath = cibles.size() == 0 ? null : cibles.get(0);
		for (Element c : cibles)
			if (((Soldat)nearDeath).getPointsDeVie() > ((Soldat)c).getPointsDeVie())
				nearDeath = c;
		return (Soldat)nearDeath;
	}
	/**
	 *  Recherche des cibles qui pourrait être tuée par l'attaque
	 * @param soldat
	 * @param cibles
	 * @param typeAttaque
	 * @return List Element
	 */
	public List<Element> chercherVictoires(Soldat soldat, List<Element> cibles, int typeAttaque) {
		List<Element> victoires = new LinkedList<Element>();
		if (typeAttaque == CORPS_CORPS) {
			for (Element c : cibles)
				if (((Soldat)c).getPointsDeVie() <= soldat.getPuissance() / 2)
					victoires.add(c);
		} else if (typeAttaque == DISTANCE) {
			for (Element c : cibles)
				if (((Soldat)c).getPointsDeVie() <= soldat.getTir() / 2)
					victoires.add(c);
		}
		return victoires;
	}
	/**
	 * Fait déplacer le soldat à une position cible aléatoire
	 * @param soldat
	 */
	public void deplacerMonstre(Soldat soldat) {
		Element cible;
		do cible = soldat.aleaElem(soldat.getZoneDeplacement());
		while(cible instanceof Heros);
		CheminDijkstra chemin = new CheminDijkstra(soldat, cible, soldat.getZoneDeplacement());
		DeplacementSoldat dp = new DeplacementSoldat(carte, soldat, chemin.getChemin(), new ArrayList<Thread>(processus));
		processus.add(dp);
		dp.start();
	}
	
	/**
	 * Réfléchit à la stratégie à employer
	 * @param soldat
	 */
	public void reflechirStrategie(Soldat soldat) {
		List<Element> ciblesFrontale = chercherCiblesPossibles(soldat, CORPS_CORPS),
				 	  ciblesDistance = chercherCiblesPossibles(soldat, DISTANCE),
				 	  victoiresFrontale = chercherVictoires(soldat, ciblesFrontale, CORPS_CORPS),
				 	  victoiresDistance = chercherVictoires(soldat, ciblesDistance, DISTANCE);
		Soldat  faibleFrontale = chercherCibleFaible(ciblesFrontale, CORPS_CORPS),
				faibleDistance = chercherCibleFaible(ciblesDistance, DISTANCE),
				lessPTSFrontale = chercherCibleLessPTS(ciblesFrontale),
				lessPTSDistance = chercherCibleLessPTS(ciblesDistance),
				lessPTSVictoiresFrontale = chercherCibleLessPTS(victoiresFrontale),
				lessPTSVictoiresDistance = chercherCibleLessPTS(victoiresDistance),
				cible = null;
		int typeAttaque;
		
		// Si le soldat est mourrant ou s'il n'y a pas de cibles possibles , il se déplace
		if (soldat.estMourrant() || (ciblesFrontale.size() <= 0 && ciblesDistance.size() <= 0)) {
			deplacerMonstre(soldat);
			return;
		}
		// Sinon si une victoire par une attaque à distance est possible
		else if (lessPTSVictoiresDistance != null) {
			cible = lessPTSVictoiresDistance;
			typeAttaque = DISTANCE;
		} 
		// sinon si une victoire par une attaque frontale est possible
		else if (lessPTSVictoiresFrontale != null) {
			cible = lessPTSVictoiresFrontale;
			typeAttaque = CORPS_CORPS;
		}
		// Sinon si la cible avec le moins de pts parmi les cibles à distance a puissance de tir inférieure
		else if (lessPTSDistance != null && soldat.getTir() > lessPTSDistance.getTir()) {
				cible = lessPTSDistance;
				typeAttaque = DISTANCE;
		}
		// Sinon si la cible avec le moins de pts parmi les cibles frontales a une puissance inférieure
		else if (lessPTSFrontale != null && soldat.getPuissance() > lessPTSFrontale.getPuissance()) {
				cible = lessPTSFrontale;
				typeAttaque = CORPS_CORPS;
		} 
		// Sinon si la puissance du soldat est inférieure à sa puissance de tir
		else if (faibleDistance != null && soldat.getPuissance() < soldat.getTir()) {
			cible = faibleDistance;
			typeAttaque = DISTANCE;
		} 
		// Sinon on attaque le soldat avec la puissance la plus faible
		else {
			cible = faibleFrontale;
			typeAttaque = CORPS_CORPS;
		}
		// S'il n'y a pas de cible frontale
		if (cible == null) {
			cible = faibleDistance;
			typeAttaque = DISTANCE;
		}
		
		if (typeAttaque == DISTANCE) {
			AttaqueSoldatDistance as = new AttaqueSoldatDistance(carte, soldat, cible, new ArrayList<Thread>(processus));
			processus.add(as);
			as.start();
		} else {
			AttaqueSoldatCorps as = new AttaqueSoldatCorps(carte, soldat, cible, new ArrayList<Thread>(processus));
			processus.add(as);
			as.start();
		}
	}
	
}
