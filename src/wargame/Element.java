package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public abstract class Element implements IConfig {
	// Infos
	protected Carte carte;
	protected Position pos;
	protected Hexagone hex, hexMM;
	protected boolean visible = true;
	
	// Méthodes
	// Crée les deux hexagones
	public void creerHex() {
		creerHexM();
		creerHexMM();
	}
	// Crée l'hexagone de la map affichée
	public void creerHexM() {
		int rayon;
		Point centre;
		rayon = carte.getRayonHex();
		centre = pos.substract(carte.getMapAff().getUpLeft()).toPositionAxiale().toPoint(rayon, carte.getOrigine());
		if (carte.getMapAff().getUpLeft().getY() % 2 != 0 && pos.getY() % 2 == 0)
			centre = centre.substract(new Point(Math.sqrt(3) * rayon, 0));
		hex = new Hexagone(centre, rayon);
	}
	// Renvoie les infos
	public String toString() {
		return pos.toString();
	}
	
	// Méthodes graphiques
	// Crée l'hexagone de la mini-map
	public void creerHexMM() {
		int rayon;
		Point centre;
		rayon = carte.getRayonMM();
		centre = pos.toPositionAxiale().toPoint(rayon, carte.getOrigineMM());
		hexMM = new Hexagone(centre, rayon);
	}
	// Dessine l'hexagone passé en paramètre
	public void seDessinerBis(Hexagone h, Graphics g) {
		if (visible == false) g.setColor(COULEUR_INCONNU);
		h.seDessiner(g);
	}
	// Dessine l'élément
	public void seDessiner(Graphics g) {
		seDessinerBis(hex, g);
	}
	// Dessine l'élément dans la mini-map
	public void seDessinerMM(Graphics g) {
		seDessinerBis(hexMM, g);
	}
	// Dessine l'hexagone passé en paramètre avec un cadre
	public void seDessinerCadreBis(Hexagone h, Graphics g, Color cadre) {
		int rayon = h.getRayon();
		Color courante = g.getColor();
		if (visible == false) courante = COULEUR_INCONNU;
		g.setColor(courante);
		h.seDessiner(g);
		g.setColor(cadre);
		h.setRayon((int)(rayon * 0.75));
		h.seDessiner(g);
		g.setColor(courante);
		h.setRayon((int)(rayon * 0.5));
		h.seDessiner(g);
		h.setRayon(rayon);
	}
	// Dessine l'élément avec un cadre
	public void seDessinerCadre(Graphics g, Color cadre) {
		seDessinerCadreBis(hex, g, cadre);
	}
	// Dessine l'élément avec un cadre dans la mini-map
	public void seDessinerCadreMM(Graphics g, Color cadre) {
		seDessinerCadreBis(hexMM, g, cadre);
	}
	// Dessine un texte centré au sein d'un hexagone
	public void drawCenteredString(Graphics g, String text, Font font) {
		int larg, haut, rayon,
			x, y;
		Point centre;
		rayon = carte.getRayonHex();
		larg = (int)(Math.sqrt(3) * rayon);
		haut = 2 * rayon;
		centre = hex.getCentre();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X-Y coordinates for the text
	    x = (int)(centre.getX() - larg / 2);
	    y = (int)(centre.getY() - haut / 2);
	    x = x + (larg - metrics.stringWidth(text)) / 2;
	    y = y + ((haut - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
}
