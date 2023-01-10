package wargame;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




/**<b>LigneH est la classe qui gère la ligne entre les éléments</b>
* @author AKIL M., BAYAZID H., AMIROUCHE A.
*/

public class LigneH implements IConfig, Serializable {
	private static final long serialVersionUID = -389247811436631214L;
	/**
	 * Liste d'éléments matérialisant une ligne sur la carte
	 */
	private List<Element> ligne;
	
	/**
	 *  Carte
	 */
	private Carte carte;
	
	/**
	 * Constructeur de ligne
	 * @param carte
	 */
	public LigneH(Carte carte) {
		this.carte = carte;
		ligne = new ArrayList<Element>();
	}
	
	/**
	 * Constructeur de ligne un peu plus poussé
	 * @param dep
	 * @param arr
	 * @param carte
	 */
	public LigneH(Element dep, Element arr, Carte carte) {
		this(carte);
		this.calculerLigne(dep.getPos().toPositionAxiale(), arr.getPos().toPositionAxiale());
	}
	
	/**
	 * Permet d'acceder à la liste materialisant une ligne
	 * @return ligne (List<Element>)
	 */
	public List<Element> getLigne() { return ligne; }
	
	
	/**
	 * Pseudo-mutateur
	 * @param elem
	 */
	public void addElement(Element elem) {
		ligne.add(elem);
	}
	
	// Méthodes
	// Interpolation d'une coordonnée
	double lerp(double a, double b, double t) {
	    return a * (1-t) + b * t;
	}
	/**
	 * Interpolation d'une position
	 * @param a
	 * @param b
	 * @param t
	 * @return
	 */
	PositionAxiale posAxialeLerp(PositionAxiale a, PositionAxiale b, double t) {
		return new PositionAxiale(lerp(a.getQ(), b.getQ(), t),
								  lerp(a.getR(), b.getR(), t));
	}
	/**
	 * Calcul de ligne 
	 * @param a
	 * @param b
	 */
	void calculerLigne(PositionAxiale a, PositionAxiale b) {
	    int n = (int)a.distance(b);
	    double pas = 1.0 / ((n > 1) ? n : 1);
	    for (int i = 0; i <= n; i++) {
	    	Position p = posAxialeLerp(a, b, pas * i).round().toPosition();
	        ligne.add(carte.getElement(p));
	    }
	}
	
	/**
	 * Dessine la ligne sur la carte
	 * @param g
	 */
	void seDessiner(Graphics2D g) {
		for (Element e : ligne)
			if (e != null) e.seDessinerCadre(g, COULEUR_LIGNE);
	}
}
