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
	public Obstacle(TypeObstacle type, Position pos) { TYPE = type; this.pos = pos; }
	
	// Accesseurs
	public TypeObstacle getTYPE() { return TYPE; }
	
	// Méthodes
	public String toString() { return "" + TYPE; }
	
	// Méthodes graphiques
	// Dessine l'obstacle sous sa forme reelle sur la carte
	public void seDessiner(Graphics g) {
		g.setColor(TYPE.getCOULEUR());
		super.seDessiner(g);
	}
	// Dessine l'obstacle sous sa forme miniature sur la mini-map
	public void seDessinerMiniMap(Graphics g) {
		g.setColor(TYPE.getCOULEUR());
		super.seDessinerMiniMap(g);
	}
}
