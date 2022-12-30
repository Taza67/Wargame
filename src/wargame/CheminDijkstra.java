package wargame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CheminDijkstra implements IConfig {
	// Infos
	List<Element> sommets;
	int[] d, pred;
	int[][] poids;
	List<Element> chemin;
	
	// Constructeurs
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
	
	// Accesseurs
	public List<Element> getChemin() {
		return chemin;
	}
	
	// Méthodes
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
					(e.pos.toPositionAxiale().estVoisin(f.pos.toPositionAxiale())) ? 1 : 0;
		// chemin
		this.chemin = new ArrayList<Element>();
	}
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
	public void maj_distances(int s1, int s2) {
		if (d[s2] > d[s1] + poids[s1][s2]) {
			d[s2] = d[s1] + poids[s1][s2];
			pred[s2] = s1;
		}
	}
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
	public void plusCourtChemin(Element depart, Element arrivee) {
		int s = sommets.indexOf(arrivee),
			deb = sommets.indexOf(depart);
		while (s != deb) {
			chemin.add(sommets.get(s));
			s = pred[s];
		}
	}
	public void inverserChemin() {
		List<Element> chemInv = new ArrayList<Element>();
		for (int i = chemin.size() - 1; i >= 0; i--)
			chemInv.add(chemin.get(i));
		chemin = chemInv;
	}
	
	// Méthodes grahiques
	public void seDessiner(Graphics g) {
		for (Element e : chemin)
			if (e != null) e.seDessinerCadre(g, COULEUR_CHEMIN);
	}
}
