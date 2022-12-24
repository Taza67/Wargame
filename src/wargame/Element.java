package wargame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Element implements IConfig {
	// Infos
	protected Carte carte;
	protected Position pos;
	protected Hexagone hex, hexMM;
	protected boolean visible;
	
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
		hex = new Hexagone(centre, rayon);
	}
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
		g.setColor(cadre);
		h.seDessiner(g);
		h.setRayon((int)(rayon * 0.75));
		if (visible == false) courante = COULEUR_INCONNU;
		g.setColor(courante);
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
	
	public String toString() {
		return getClass().getSimpleName();
	}
}
