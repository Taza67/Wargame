package wargame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class LigneH implements IConfig {
	// Infos
	private List<Element> ligne;
	private Carte carte;
	
	// Constructeur
	public LigneH(Element dep, Element arr, Carte carte) {
		this.carte = carte;
		// Calcul de la ligne
		ligne = new ArrayList<Element>();
		this.calculerLigne(dep.pos.toPositionAxiale(), arr.pos.toPositionAxiale());
	}
	
	// Accesseurs
	public List<Element> getLigne() { return ligne; }
	
	// Méthodes
	// Interpolation d'une coordonnée
	double lerp(double a, double b, double t) {
	    return a * (1-t) + b * t;
	}
	// Interpolation d'une position
	PositionAxiale posAxialeLerp(PositionAxiale a, PositionAxiale b, double t) {
		return new PositionAxiale(lerp(a.getQ(), b.getQ(), t),
								  lerp(a.getR(), b.getR(), t));
	}
	// Calcul de la ligne
	void calculerLigne(PositionAxiale a, PositionAxiale b) {
	    int n = (int)a.distance(b);
	    double pas = 1.0 / ((n > 1) ? n : 1);
	    for (int i = 0; i <= n; i++) {
	    	Position p = posAxialeLerp(a, b, pas * i).round().toPosition();
	        ligne.add(carte.getElement(p));
	    }
	}
	
	// Méthodes graphiques
	void seDessiner(Graphics g) {
		for (Element e : ligne)
			e.seDessinerCadre(g, COULEUR_LIGNE);
	}
}
