package wargame;

import java.io.Serializable;



/**
 * <b>Position est la classe qui gère la position d'un élément</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une variable x</li>
 * <li>Une variable y</li>
 * </ul>
 * @see Carte
 * @see Hexagone
 * @see Position
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */



public class Position implements IConfig, Serializable {
	private static final long serialVersionUID = 4047705877020738793L;
	/**
	 * Coordonnée x
	 */
	private int x, 
	/**
	 * Coordonnée y
	 */
	y;
	
	/**
	 * Constructeur d'une position;
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) { this.x = x; this.y = y; }
	
	/**
	 * Accesseur du point x
	 * @return int
	 */
	public int getX() { return x; }
	
	/**
	 * Accesseur du point y
	 * @return y
	 */
	public int getY() { return y; }
	
	/**
	 * Mutateur du paramètre x
	 * @param x
	 */
	public void setX(int x) { this.x = x; }
	/**
	 * Mutateur du paramètre y
	 * @param y
	 */
	public void setY(int y) { this.y = y; }
	

	/**
	 * Teste si les "coordonnées" de la position sont valides par rapport aux dimensions données
	 * @param largeur
	 * @param hauteur
	 * @return bool
	 */
	public boolean estValide(int largeur, int hauteur) {
		return x >= 0 && x < largeur && y >= 0 && y < hauteur; 
	}
	/**
	 * Retourne les infos de la position sous forme d'une chaine de caractère
	 */
	public String toString() { return "(" + x + ", " + y + ")"; }
	/**
	 * Teste si la position en question est adjacente à la position pos
	 * @param pos
	 * @return bool
	 */
	public boolean estVoisine(Position pos) {
		return ((Math.abs(x - pos.x) <= 1) && (Math.abs(y - pos.y) <= 1));
	}
	/**
	 *  Vérifie si l'objet en paramètre est égal à la position courante
	 *  @return bool
	 */
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}
	/**
	 *  Calcule les coordonnées axiales
	 * @return PositionAxiale
	 */
	public PositionAxiale toPositionAxiale() {
		int q = x - (y - (y & 1)) / 2,
			r = y;
		return new PositionAxiale(q, r);
	}
	/**
	 *  Calcule le point
	 * @param rayon
	 * @return Point
	 */
	public Point toPoint(int rayon) {
	    double xP = rayon * Math.sqrt(3) * (x + 0.5 * (y & 1)),
	    	   yP = rayon * 3/2 * y;
	    return new Point(xP, yP);
	}
	/**
	 * Ajoute aux coordonnées celles de la position donnée
	 * @param pos
	 * @return Position
	 */
	public Position add(Position pos) {
		return new Position (x + pos.getX(), y + pos.getY());
	}
	/**
	 * Reduit les coordonnées de celles de la position donnée
	 * @param pos
	 * @return Position
	 */
	public Position substract(Position pos) {
		return new Position (x - pos.getX(), y - pos.getY());
	}
}
