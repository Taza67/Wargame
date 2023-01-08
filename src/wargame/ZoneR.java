package wargame;

import java.awt.Graphics2D;

public class ZoneR {
	// Infos
	private Carte carte;
	private Position upLeft, downRight;
	private int largeur, hauteur;
	
	// Constructeurs
	public ZoneR(Carte carte, Position upLeft, Position downRight) {
		this.carte = carte;
		this.upLeft = new Position(upLeft.getX(), upLeft.getY());
		this.downRight = new Position(downRight.getX(), downRight.getY());
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}
	
	public ZoneR(Carte carte, Position pos, int rayon_x, int rayon_y) {
		this.carte = carte;
		this.upLeft = calculerUpLeft(pos, rayon_x, rayon_y);
		this.downRight = calculerDownRight(pos, rayon_x, rayon_y);
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}

	// Accesseurs
	public Position getUpLeft() { return upLeft; }
	public Position getDownRight() { return downRight; }
	public int getLargeur() { return largeur; }
	public int getHauteur() { return hauteur; }

	// Mutateurs
	public void setUpLeft(Position upLeft) { 
		this.upLeft = upLeft; 
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}
	public void setDownRight(Position downRight) { 
		this.downRight = downRight; 
		this.largeur = calculerLargeur();
		this.hauteur = calculerHauteur();
	}
	public void setLargeur(int largeur) { this.largeur = largeur; }
	public void setHauteur(int hauteur) { this.hauteur = hauteur; }
	
	// Méthodes
	// Calcule l'extrémité en haut à gauche
	public Position calculerUpLeft(Position pos, int largeur, int hauteur) {
		int i = pos.getY(), j = pos.getX(),
			rayon_x = largeur / 2,
			rayon_y = hauteur / 2,
			upLeftX = (j - rayon_x) >= 0 ? j - rayon_x : 0,
			upLeftY = (i - rayon_y) >= 0 ? i - rayon_y : 0;
		return new Position(upLeftX, upLeftY);
	}
	// Calcule l'extrémité en bas à droite
	public Position calculerDownRight(Position pos, int largeur, int hauteur) {
		int i = pos.getY(), j = pos.getX(),
			rayon_x = largeur / 2,
			rayon_y = hauteur / 2,
			downRightX = (j + rayon_x) < carte.getLargC() ? j + rayon_x : carte.getLargC() - 1,
			downRightY = (i + rayon_y) < carte.getHautC() ? i + rayon_y : carte.getHautC() - 1;
		return new Position(downRightX, downRightY);
	}
	// Calcule la largeur
	public int calculerLargeur() { return downRight.getX() - upLeft.getX(); }
	// Calcule la hauteur
	public int calculerHauteur() { return downRight.getY() - upLeft.getY(); }
	// Rend la zone visible sur la carte
	public void rendreVisible() {
		for (int i = upLeft.getY(); i <= downRight.getY(); i++)
			for (int j = upLeft.getX(); j <= downRight.getX(); j++)
				carte.getElement(new Position(j, i)).visible = true;
	}
	
	// Méthodes graphiques
	// Dessine une zone de la carte
	public void seDessiner(Graphics2D g) {
		for (int i = upLeft.getY(); i <= Math.min(carte.getHautC() - 1, downRight.getY() + 2); i++)
			for (int j = upLeft.getX(); j <= Math.min(carte.getLargC() - 1, downRight.getX() + 2); j++)
				carte.getElement(new Position(j, i)).seDessiner(g);
	}
}
