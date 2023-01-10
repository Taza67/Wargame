package wargame;

import java.io.Serializable;



/**
 * <b>Point est la classe qui gère les points sur la map, les points étaient des positions sur la carte</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>La carte qui contient cet élément.</li>
 * <li>Une coordonnée x(int)</li>
 * <li>Une coordonnée y(int)</li>
 * </ul>
 * </p>
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */



public class Point implements Serializable {
	private static final long serialVersionUID = 1085181307746823815L;
	
	/**
	 * coordonnée x
	 */
	private double x;
	/**
	 * coordonnée y
	 */
	private double y;
	
	/**
	 * Constructeur d'un point
	 * @param x
	 * @param y
	 */
	public Point(double x, double y) { this.x = x; this.y = y; }
	
	/**
	 * Accesseur de la coordonnée x d'un point
	 * @return x(double)
	 */
	public double getX() { return x;}
	/**
	 * Accesseur de la coordonnée y d'un point
	 * @return y(double)
	 */
	public double getY() { return y;}
	
	/**
	 * Mutateur de la coordonnée x d'un point
	 * @param x
	 */
	public void setX(double x) { this.x = x; }
	/**
	 * Mutateur de la coordonnée y d'un point
	 * @param y
	 */
	public void setY(double y) { this.y = y; }
	
	/**
	 * Fonction toString permettant l'affichage des coordonnées d'un point de la forme (double x, double y)
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	/**
	 *  Calcule la distance d'un point avec un autre passé en paramètre
	 * @param p
	 * @return distance
	 */
	public double distance(Point p) {
		double terme1 = Math.pow(this.x - p.getX(), 2),
			   terme2 = Math.pow(this.y - p.getY(), 2);
		return Math.sqrt(terme1 + terme2);
	}
	/**
	 *  Calcule la position axiale
	 * @param rayon
	 * @param origine
	 * @return Position Axiale
	 */
	public PositionAxiale toPositionAxiale(int rayon, Point origine) {
		x = Math.round((x - origine.getX()) / rayon);
		y = Math.round((y - origine.getY()) / rayon);
	    double q = (Math.sqrt(3) / 3.) * x  +  (-1 / 3.) * y,
	    	   r = (2 / 3. * y);
	    return (new PositionAxiale(q, r)).round();
	}
	/**
	 * Ajoute un point
	 * @param p
	 * @return Point
	 */
	public Point add(Point p) {
		return new Point(x + p.getX(), y + p.getY());
	}
	/**
	 *  Réduit les coordonnées de celles du point donné
	 * @param p
	 * @return Point
	 */
	public Point substract(Point p) {
		return new Point(x - p.getX(), y - p.getY());
	}
	/**
	 * Vérifie si les coordonnées du point sont comprises entre celles des deux points données
	 * @param min
	 * @param max
	 * @return bool
	 */
	public boolean estValide(Point min, Point max) {
		return x >= min.getX() && x <= max.getX() && y >= min.getY() && y <= max.getY();
	}
}
