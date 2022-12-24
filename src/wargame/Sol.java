package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Sol extends Element implements IConfig {
	// Infos
	
	// Constructeurs
	public Sol(Carte carte, Position pos) { 
		this.carte = carte;
		this.pos = pos;
		creerHex();
	}
	
	// Méthodes graphiques
	// Dessine le sol
	public void seDessiner(Graphics g) {
		g.setColor(COULEUR_SOL);
		super.seDessiner(g);
	}
	// Dessine le sol avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, Color couleurCadre) {
		g.setColor(COULEUR_SOL);
		super.seDessinerCadre(g, couleurCadre);
	}
	// Dessine le sol dans la mini-map
	public void seDessinerMM(Graphics g) {
		g.setColor(COULEUR_SOL);
		super.seDessinerMM(g);
	}
	// Dessine le sol avec un cadre qui indique son état dans la mini-map
	public void seDessinerCadreMM(Graphics g, Color couleurCadre) { 
		g.setColor(COULEUR_SOL);
		super.seDessinerCadreMM(g, couleurCadre);
	}
}
