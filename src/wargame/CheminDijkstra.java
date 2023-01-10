package wargame;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wargameInterface.Fenetre;
import wargameInterface.PanneauPartie;

/**
 * <b>CheminDjikstra permet de trouver le chemin le plus court d'un soldat.</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une liste de sommets représentés par des éléments.</li>
 * <li>Une liste de sommets contenant le chemins le plus court.</li>
 * <li>Un tableau contenant le poids de chaque sommet.</li>
 * </ul>
 * </p>
 * @see AttaqueSoldatCorps
 * @see Carte
 * @see TourOrdi
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */



public class CheminDijkstra implements IConfig, Serializable {
	private static final long serialVersionUID = -1614658200180524351L;
	/**
	 * Liste des sommets. Cette liste peut changer.
	 * @see CheminDijkstra#CheminDijkstra(Element, Element, List)
	 */
	List<Element> sommets;
	
	/**
	 * Tableaux contenant la distance de chaque sommet (d) et le predecesseur (pred).
	 * @see CheminDijkstra#CheminDijkstra(Element, Element, List)
	 */
	int[] d, pred;
	
	/**
	 * Tableau contenant le poid de chaque sommet.
	 * @see CheminDijkstra#CheminDijkstra(Element, Element, List)
	 */
	int[][] poids;
	
	/**
	 * Liste de sommets contenant le chemin le plus court.
	 * @see CheminDijkstra#CheminDijkstra(Element, Element, List)
	 * @see CheminDijkstra#inverserChemin()
	 */
	List<Element> chemin;
	
	

	/**
	 * Constructeur CheminDijkstra
	 * <p>
	 * A la construction, initialisqtoin des tableaux puis construction du chemin le plus court.
	 * </p>
	 * 
	 * @param depart
	 * 				Element de départ.
	 * @param arrivee
	 *            	Element d'arrivée.
	 * @param sommets
	 * 				Liste des sommets.
	 * @see Carte#panPartie
	 * @see Carte#LARGC
	 * @see Carte#HAUTC
	 * @see Carte#nbHeros
	 * @see Carte#nbMonstres
	 */
	public CheminDijkstra(Element depart, Element arrivee, List<Element> sommets) {
		this.sommets = sommets;
		this.d = new int[sommets.size()];
		this.pred = new int[sommets.size()];
		this.poids = new int[sommets.size()][sommets.size()];
		initialise(depart, sommets);
		dijkstra(depart);
		plusCourtChemin(depart, arrivee);
		inverserChemin();
	}

	 /**
     * Retourne le chemin le plus court.
     * 
     * @return Le chemin le plus court. 
     * 
     * @see CheminDijkstra#chemin
     */
	public List<Element> getChemin() {
		return chemin;
	}
	
	
	/**
	 * Initialise les tableaux d, pred, poids et le chemin
	 * @param depart
	 * 			Elément de départ
	 * @param sommets
	 * 			Liste des sommets
	 * @return liste du chemin 
	 * 
	 * @see CheminDijkstra#d
	 * @see CheminDijkstra#poids
	 * @see CheminDijkstra#pred
	 * @see CheminDijkstra#sommets
	 */
	private void initialise(Element depart, List<Element> sommets) {
		// d
		for (int i = 0; i < d.length; i++) d[i] = 100000;
		d[sommets.indexOf(depart)] = 0;
		// pred
		for (int i = 0; i < d.length; i++) pred[i] = -1;
		pred[sommets.indexOf(depart)] = 0;
		// poids
		for (Element e : sommets)
			for (Element f : sommets)
				poids[sommets.indexOf(e)][sommets.indexOf(f)] = 
					(e.getPos().toPositionAxiale().estVoisin(f.getPos().toPositionAxiale())) ? 1 : 0;
		// chemin
		this.chemin = new ArrayList<Element>();
	}
	
	/**
	 * Trouve le sommet de distance minimum
	 * @param q
	 * 			Liste des éléments
	 * 
	 * @return sommet de distance minimum
	 */
	private int trouveMin(List<Element> q) {
		int min = 100000,
			sommet = -1;
		for (Element e : q) {
			if (d[sommets.indexOf(e)] < min) {
				min = d[sommets.indexOf(e)];
				sommet = sommets.indexOf(e);
			}
		}
		return sommet;
	}
	
	/**
	 * Mets à jour les distances dans le tableau d
	 * 
	 * @param s1
	 * 		Premier sommet
	 * 
	 *  @param s2
	 * 		deuxième sommet
	 
	 */
	public void maj_distances(int s1, int s2) {
		if (d[s2] > d[s1] + poids[s1][s2]) {
			d[s2] = d[s1] + poids[s1][s2];
			pred[s2] = s1;
		}
	}
	
	/**
	 * Trouve le chemint le plus court à partir de l'élément de départ
	 * 
	 * @param depart
	 * 		élément de départ
	 * 
	 * @see CheminDijkstra#CheminDijkstra(Element, Element, List)
	 */
	public void dijkstra(Element depart) {
		List<Element> q = new ArrayList<Element>();
		for (Element e : sommets) q.add(e);
		while (q.size() > 0) {
			int s1 = trouveMin(q);
			q.remove(sommets.get(s1));
			for (int j = 0; j < poids[s1].length; j++)
				if (poids[s1][j] != 0)
					maj_distances(s1, j);
		}
	}
	
	/**
	 * Trouve le chemin le plus court entre l'élément de départ et l'arrivée
	 * 
	 * @param depart
	 * 		Element de départ
	 * @param arrivee
	 * 		Element d'arrivée
	 * 
	 */
	public void plusCourtChemin(Element depart, Element arrivee) {
		int s = sommets.indexOf(arrivee),
			deb = sommets.indexOf(depart);
		while (s != deb) {
			chemin.add(sommets.get(s));
			s = pred[s];
		}
	}
	
	/**
	 * Inverse le chemin pour avoir le chemin le plus court dans chemin
	 *  
	 * @see CheminDijkstra#chemin
	 */
	public void inverserChemin() {
		List<Element> chemInv = new ArrayList<Element>();
		for (int i = chemin.size() - 1; i >= 0; i--)
			chemInv.add(chemin.get(i));
		chemin = chemInv;
	}
	
	/**
	 * Dessine le chemin le plus court 
	 * 
	 * @param g
	 * 		Graphics2D
	 */
	public void seDessiner(Graphics2D g) {
		for (Element e : chemin)
			if (e != null) e.seDessinerCadre(g, COULEUR_CHEMIN);
	}
}
