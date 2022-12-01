package wargame;

import java.awt.Graphics;

public class Sol extends Element {
	// Infos
	
	// Constructeurs
	public Sol(Carte carte, Position pos) { this.carte = carte; this.pos = pos; }
	
	// Méthodes graphiques
	// Dessine le sol sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		g.setColor(COULEUR_SOL);
		super.seDessiner(g, type);
	}
}
