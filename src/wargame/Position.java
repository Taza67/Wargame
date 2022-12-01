package wargame;

import java.util.Objects;

public class Position implements IConfig {
	// Infos
	private int x, y;
	
	// Constructeurs
	Position(int x, int y) { this.x = x; this.y = y; }
	
	// Accesseurs
	public int getX() { return x; }
	public int getY() { return y; }
	
	// Mutateurs
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	// Méthodes
	// Teste si les "coordonnées" de la position sont valides par rapport aux dimensions de la carte
	public boolean estValide(int largeur, int hauteur) {
		return x >= 0 && x < largeur && y >= 0 && y < hauteur; // x dans [ 0, largeur [ et y dans [ 0, hauteur [
	}
	// Retourne les infos de la position
	public String toString() { return "(" + x + ", " + y + ")"; }
	// Teste si la position en question est adjacente à la position pos
	public boolean estVoisine(Position pos) {
		return ((Math.abs(x - pos.x) <= 1) && (Math.abs(y - pos.y) <= 1));
	}
	// Vérifie si l'objet en paramètre est égal à la position courante
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}
	
}
