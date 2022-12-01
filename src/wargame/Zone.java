package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Zone implements IConfig {
	// Infos
	private Carte carte;
	private Position extHautGauche, extBasDroit;
	private int largeurCase, hauteurCase;
	
	// Constructeurs
	public Zone(Carte carte, Position extHautGauche, Position extBasDroit) {
		this.carte = carte;
		this.extHautGauche = new Position(extHautGauche.getX(), extHautGauche.getY());
		this.extBasDroit = new Position(extBasDroit.getX(), extBasDroit.getY());
		this.largeurCase = calculerLargeurCase();
		this.hauteurCase = calculerHauteurCase();
	}
	
	public Zone(Carte carte, Position pos, int rayon_x, int rayon_y) {
		this.carte = carte;
		this.extHautGauche = calculerExtHautGauche(pos, rayon_x, rayon_y);
		this.extBasDroit = calculerExtBasDroit(pos, rayon_x, rayon_y);
		this.largeurCase = extHautGauche.getX() - extBasDroit.getX();
		this.hauteurCase = extHautGauche.getY() - extBasDroit.getY();
	}

	// Accesseurs
	public Position getExtHautGauche() { return extHautGauche; }
	public Position getExtBasDroit() { return extBasDroit; }
	public int getLargeurCase() { return largeurCase; }
	public int getHauteurCase() { return hauteurCase; }

	// Mutateurs
	public void setExtHautGauche(Position extHautGauche) { this.extHautGauche = extHautGauche; }
	public void setExtBasDroit(Position extBasDroit) { this.extBasDroit = extBasDroit; }
	public void setLargeurCase(int largeurCase) { this.largeurCase = largeurCase; }
	public void setHauteurCase(int hauteurCase) { this.hauteurCase = hauteurCase; }
	
	// Méthodes
	// Calcule l'extrémité en haut à gauche
	public Position calculerExtHautGauche(Position pos, int largeur, int hauteur) {
		int i = pos.getY(), j = pos.getX(),
			rayon_x = largeur / 2,
			rayon_y = hauteur / 2,
			extHautGaucheX = (j - rayon_x) >= 0 ? j - rayon_x : 0,
			extHautGaucheY = (i - rayon_y) >= 0 ? i - rayon_y : 0;
		return new Position(extHautGaucheX, extHautGaucheY);
	}
	// Calcule l'extrémité en bas à droite
	public Position calculerExtBasDroit(Position pos, int largeur, int hauteur) {
		int i = pos.getY(), j = pos.getX(),
			rayon_x = largeur / 2,
			rayon_y = hauteur / 2,
			extBasDroitX = (j + rayon_x) < carte.getLARGEUR_CASE_CARTE() ? j + rayon_x : carte.getLARGEUR_CASE_CARTE() - 1,
			extBasDroitY = (i + rayon_y) < carte.getHAUTEUR_CASE_CARTE() ? i + rayon_y : carte.getHAUTEUR_CASE_CARTE() - 1;
		return new Position(extBasDroitX, extBasDroitY);
	}
	// Calcule la largeur
	public int calculerLargeurCase() { return extBasDroit.getX() - extHautGauche.getX(); }
	// Calcule la hauteur
	public int calculerHauteurCase() { return extBasDroit.getY() - extHautGauche.getY(); }
	// Rend la zone visible sur la carte
	public void rendreVisible() {
		for (int i = extHautGauche.getY(); i <= extBasDroit.getY(); i++)
			for (int j = extHautGauche.getX(); j <= extBasDroit.getX(); j++)
				carte.getElement(new Position(j, i)).visible = true;
	}
	
	// Méthodes graphique
	// Dessine une zone de la carte
	public void seDessiner(Graphics g) {
		for (int i = extHautGauche.getY(); i <= extBasDroit.getY(); i++)
			for (int j = extHautGauche.getX(); j <= extBasDroit.getX(); j++)
				carte.getElement(new Position(j, i)).seDessiner(g, Element.ELEMENT_CARTE);
	}
	
}
