package wargame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.io.Serializable;


/**
 * <b>Hexagone est la classe qui permet de dessiner les cases de la carte</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Un tableau de point</li>
 * <li>Un point centre de l'hexagone</li>
 * <li>un entier qui représente le rayon</li>
 * </ul>
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */


public class Hexagone implements Serializable {
	private static final long serialVersionUID = -7787070189042594340L;
	/**
	 * Tableau d'extremités
	 */
	private final Point[] extremites;
	/**
	 * Centre de l'hexagone
	 */
	private Point centre;
	/**
	 * rayon de l'hexagone
	 */
	private int rayon;
	
	/**
	 * Constructeur de la classe hexagone
	 * @param centre
	 * @param rayon
	 */
	public Hexagone(Point centre, int rayon) {
		this.extremites = new Point[6];
		this.centre = centre;
		this.rayon = rayon;
		calculerAutresSommets();
	}
	
	/**
	 * Accesseur du point central
	 * @return Point
	 */
	public Point getCentre() { return centre; }
	
	/**
	 * Accesseur de la taille du rayon
	 * @return int
	 */
	public int getRayon() { return rayon; }
	
	/**
	 * Permet de modifier le rayon de l'hexagone
	 * @param rayon
	 */
	public void setRayon(int rayon) {
		this.rayon = rayon;
		calculerAutresSommets();
	}
	
	/**
	 * Calcule le sommet d'un hexagone en fonction de l'indice passé en paramètre
	 * @param indice
	 * @return Point
	 */
	private Point calculerAutreSommet(int indice) {
		double angle_deg = 60 * indice - 30;
	    double angle_rad = Math.PI / 180 * angle_deg;
	    return new Point(centre.getX() + rayon * Math.cos(angle_rad),
	                 	 centre.getY() + rayon * Math.sin(angle_rad));
	}
	
	/**
	 * Calcule l'ensemble des sommets de l'hexagone
	 */
	private void calculerAutresSommets() {
		for (int i = 0; i < 6; i++)
			extremites[i] = calculerAutreSommet(i);
	}
	
	/**
	 * Transforme l'hexagone en java.awt.Polygon
	 * @return Polygon
	 */
	
	public Polygon toPolygon() {
		int tabX[], tabY[];
		tabX = new int[6];
		tabY = new int[6];
		for (int i = 0; i < 6; i++) {
			tabX[i] = (int)extremites[i].getX();
			tabY[i] = (int)extremites[i].getY();
		}
		Polygon p = new Polygon(tabX, tabY, 6);
		return p;
	}
	
	
	/**
	 * Dessine l'hexagone
	 * @param g
	 */
	public void seDessiner(Graphics2D g) {
		Polygon p = this.toPolygon();
		g.fill(p);
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.black);
		g.draw(p);
	}
	
	/**
	 * Dessine la texture de la case en fontion de l'image imagePaint
	 * @param g
	 * @param imagePaint
	 */
	
	public void seDessiner(Graphics2D g, TexturePaint imagePaint) {
		g.setPaint(imagePaint);
		seDessiner(g);
	}
}
