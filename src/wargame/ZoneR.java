package wargame;

import java.awt.Graphics2D;


import java.io.Serializable;





/**
 * <b>Classe ZoneR implémente Serializable</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une carte</li>
 * <li>Une position upLeft(Position)</li>
 * <li>Une position downRight(Position)</li>
 * <li>Une Largeur(int)</li>
 * <li>Une hauteur(int)</li>
 * </ul>
 * </p>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */
public class ZoneR implements Serializable {
	private static final long serialVersionUID = 2672155084010399992L;
	/**
	 * Carte
	 */
	private Carte carte;
	/**
	 * Position upLeft
	 */
	private Position upLeft, 
	/**
	 * Position downRight
	 */
	downRight;
	/**
	 * largeur (int)
	 */
	private int largeur, 
	/**
	 * hauteur (int)
	 */
	hauteur;
	
	/**
	 * Premier Constructeur
	 * @param carte
	 * @param upLeft
	 * @param downRight
	 */
	public ZoneR(Carte carte, Position upLeft, Position downRight) {
		this.carte = carte;
		this.upLeft = new Position(upLeft.getX(), upLeft.getY());
		this.downRight = new Position(downRight.getX(), downRight.getY());
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}
	
	/**
	 * Deuxième constructeur avec une position un rayon x et un rayon y
	 * @param carte
	 * @param pos
	 * @param rayon_x
	 * @param rayon_y
	 */
	public ZoneR(Carte carte, Position pos, int rayon_x, int rayon_y) {
		this.carte = carte;
		this.upLeft = calculerUpLeft(pos, rayon_x, rayon_y);
		this.downRight = calculerDownRight(pos, rayon_x, rayon_y);
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}

	/**
	 *  Accesseur de UpLeft
	 * @return Position
	 */
	public Position getUpLeft() { return upLeft; }
	/**
	 * Accesseur de DownRight
	 * @return Position
	 */
	public Position getDownRight() { return downRight; }
	/**
	 * Accesseur de Largeur 
	 * @return int
	 */
	public int getLargeur() { return largeur; }
	/**
	 * Accesseur de Hauteur
	 * @return int
	 */
	public int getHauteur() { return hauteur; }

	/**
	 *  Mutateur de UpLeft
	 * @param upLeft
	 */
	public void setUpLeft(Position upLeft) { 
		this.upLeft = upLeft; 
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}
	/**
	 * Mutateur de downRight
	 * @param downRight
	 */
	public void setDownRight(Position downRight) { 
		this.downRight = downRight; 
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}
	
	/**
	 * Mutateur de Largeur
	 * @param largeur
	 */
	public void setLargeur(int largeur) { this.largeur = largeur; }
	/**
	 * Mutateur de Hauteur
	 * @param hauteur
	 */
	public void setHauteur(int hauteur) { this.hauteur = hauteur; }
	
	
	/**
	 *  Calcule l'extrémité en haut à gauche
	 * @param pos
	 * @param largeur
	 * @param hauteur
	 * @return Position
	 */
	public Position calculerUpLeft(Position pos, int largeur, int hauteur) {
		int i = pos.getY(), j = pos.getX(),
			rayon_x = largeur / 2,
			rayon_y = hauteur / 2,
			upLeftX = (j - rayon_x) >= 0 ? j - rayon_x : 0,
			upLeftY = (i - rayon_y) >= 0 ? i - rayon_y : 0;
		return new Position(upLeftX, upLeftY);
	}
	/**
	 * Calcule l'extrémité en bas à droite
	 * @param pos
	 * @param largeur
	 * @param hauteur
	 * @return Position
	 */
	public Position calculerDownRight(Position pos, int largeur, int hauteur) {
		int i = pos.getY(), j = pos.getX(),
			rayon_x = largeur / 2,
			rayon_y = hauteur / 2,
			downRightX = (j + rayon_x) < carte.getLARGC() ? j + rayon_x : carte.getLARGC() - 1,
			downRightY = (i + rayon_y) < carte.getHAUTC() ? i + rayon_y : carte.getHAUTC() - 1;
		return new Position(downRightX, downRightY);
	}
	/**
	 *  Calcule la largeur
	 * @return int
	 */
	public int calculerLargeur() { return downRight.getX() - upLeft.getX(); }
	/**
	 * Calcule la hauteur
	 * @return int
	 */
	public int calculerHauteur() { return downRight.getY() - upLeft.getY(); }
	/**
	 * Rend la zone visible sur la carte
	 */
	public void rendreVisible() {
		for (int i = upLeft.getY(); i <= downRight.getY(); i++)
			for (int j = upLeft.getX(); j <= downRight.getX(); j++)
				carte.getElement(new Position(j, i)).visible = true;
	}
	
	
	/**
	 * Dessine une zone de la carte
	 * @param g
	 */
	public void seDessiner(Graphics2D g) {
		for (int i = upLeft.getY(); i <= Math.min(carte.getHAUTC() - 1, downRight.getY() + 2); i++)
			for (int j = upLeft.getX(); j <= Math.min(carte.getLARGC() - 1, downRight.getX() + 2); j++)
				carte.getElement(new Position(j, i)).seDessiner(g);
	}
}
