package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Obstacle extends Element implements IConfig {
	// Type
	public enum TypeObstacle {
		// Liste
		ROCHER(COULEUR_ROCHER), FORET(COULEUR_FORET), EAU(COULEUR_EAU);
		
		// Infos
		private final Color COULEUR;
		
		// Constructeurs
		private TypeObstacle(Color couleur) { COULEUR = couleur; }
		
		// Accesseur
		public Color getCOULEUR() { return COULEUR; }
		// Pseudo-accesseurs
		public static TypeObstacle getObstacleAlea() { 
			return values() [(int) (Math.random() * values().length)];
		}
	}
	
	// Infos
	private final TypeObstacle TYPE;
	
	// Constructeurs
	public Obstacle(Carte carte, TypeObstacle type, Position pos) {this.carte = carte; TYPE = type; this.pos = pos; }
	
	// Accesseurs
	public TypeObstacle getTYPE() { return TYPE; }
	
	// Méthodes
	// Retourne les infos sur l'obstacle
	public String toString() { 
		String desc = super.toString();
		if (visible == true)
			desc += " : [ Type : " + TYPE + " ]";
		return desc;
	}
	
	// Méthodes graphiques
	// Dessine l'obstacle sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		g.setColor(TYPE.getCOULEUR());
		super.seDessiner(g, type);
	}
	// Dessine l'obstacle avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, char type, Color couleurCadre) {
		g.setColor(TYPE.getCOULEUR());
		super.seDessinerCadre(g, type, couleurCadre);
	}
}
