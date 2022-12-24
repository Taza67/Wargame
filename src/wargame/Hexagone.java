package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Hexagone {
	// Infos
	private final Point[] extremites;
	private Point centre;
	private int rayon;
	
	// Constructeurs
	public Hexagone(Point centre, int rayon) {
		this.extremites = new Point[6];
		this.centre = centre;
		this.rayon = rayon;
		calculerAutresSommets();
	}
	
	// Accesseurs
	public int getRayon() { return rayon; }
	
	// Mutateurs
	public void setRayon(int rayon) {
		this.rayon = rayon;
		calculerAutresSommets();
	}
	
	// Méthodes
	// Retourne le point d'indice donné à partir du centre, du nombre de côtés/points et du point de base
	private Point calculerAutreSommet(int indice) {
		double angle_deg = 60 * indice - 30;
	    double angle_rad = Math.PI / 180 * angle_deg;
	    return new Point(centre.getX() + rayon * Math.cos(angle_rad),
	                 	 centre.getY() + rayon * Math.sin(angle_rad));
	}
	// Calcule tous les autres sommets de l'hexagone
	private void calculerAutresSommets() {
		for (int i = 0; i < 6; i++)
			extremites[i] = calculerAutreSommet(i);
	}
	
	// Méthodes graphiques
	// Dessine l'hexagone
	public void seDessiner(Graphics g) {
		int tabX[], tabY[];
		tabX = new int[6];
		tabY = new int[6];
		for (int i = 0; i < 6; i++) {
			tabX[i] = (int)extremites[i].getX();
			tabY[i] = (int)extremites[i].getY();
		}
		g.fillPolygon(tabX, tabY, 6);
		g.setColor(Color.darkGray);
		g.drawPolygon(tabX, tabY, 6);
	}
}
