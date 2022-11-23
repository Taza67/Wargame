package wargame;

import java.awt.Color;

public class Vivant extends Element implements IVivant {
	// Infos
	private int points_de_vie,
				portee_visuelle,
				portee_deplacement,
				niveau;
	
	// Constructeurs
	public Vivant(Position pos, int points_de_vie, int portee_visuelle, int portee_deplacement) { 
		super(pos.getX(), pos.getY()); 
		this.points_de_vie = points_de_vie;
		this.portee_visuelle = portee_visuelle;
		this.portee_deplacement = portee_deplacement;
		niveau = 0;
	}

	// Accesseurs
	public int getPoints_de_vie() { return points_de_vie; }
	public int getPortee_visuelle() { return portee_visuelle; }
	public int getPortee_deplacement() { return portee_deplacement; }
	public int getNiveau() { return niveau; }

	// Mutateurs
	public void setPoints_de_vie(int points_de_vie) { this.points_de_vie = points_de_vie; }
	public void setPortee_visuelle(int portee_visuelle) { this.portee_visuelle = portee_visuelle; }
	public void setPortee_deplacement(int portee_deplacement) { this.portee_deplacement = portee_deplacement; }

	// Méthodes
	// Soigne l'être vivant
	public void soigne(int pointsDeGuerison, int pointsDeVieMax) {
		if (points_de_vie < pointsDeVieMax) {
			if (points_de_vie + pointsDeGuerison < pointsDeVieMax)
				points_de_vie += pointsDeGuerison;
			else
				points_de_vie += pointsDeVieMax - points_de_vie;
		}
	}
	// Déplace l'être vivant à la position en paramètre
	public void seDeplace(Position newPos) {
		int xP = this.pos.getX(),
			yP = this.pos.getY(),
			xNP = newPos.getX(),
			yNP = newPos.getY();
		if (xNP < xP + portee_deplacement && yNP < yP + portee_deplacement) {
			this.pos.setX(newPos.getX());
			this.pos.setY(newPos.getY());
		}
	}
	// Augmente le niveau d'un être vivant
	public void augmenteNiveau() {
		if (niveau < 10)
			niveau++;
		
		points_de_vie += niveau * 10;
	}
}
