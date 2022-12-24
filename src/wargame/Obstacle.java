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
	public Obstacle(Carte carte, TypeObstacle type, Position pos) {
		this.carte = carte; 
		TYPE = type; 
		this.pos = pos;
		creerHex();
	}
	
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
	// Dessine l'obstacle
	public void seDessiner(Graphics g) {
		g.setColor(TYPE.getCOULEUR());
		super.seDessiner(g);
	}
	// Dessine l'obstacle avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, Color cadre) {
		g.setColor(TYPE.getCOULEUR());
		super.seDessinerCadre(g, cadre);
	}
	// Dessine l'obstacle dans la mini-map
	public void seDessinerMM(Graphics g) {
		g.setColor(TYPE.getCOULEUR());
		super.seDessinerMM(g);
	}
	// Dessine l'obstacle avec un cadre qui indique son état dans la mini-map
	public void seDessinerCadreMM(Graphics g, Color couleurCadre) { 
		g.setColor(TYPE.getCOULEUR());
		super.seDessinerCadreMM(g, couleurCadre);
	}
}
