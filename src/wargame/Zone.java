package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Zone implements IConfig {
	// Infos
	private Carte carte;
	private Position extHautGauche, extBasDroit;
	
	// Constructeurs
	public Zone(Carte carte, Position extHautGauche, Position extBasDroit) {
		this.carte = carte;
		this.extHautGauche = extHautGauche;
		this.extBasDroit = extBasDroit;
	}

	// Accesseurs
	public Position getExtHautGauche() { return extHautGauche; }
	public Position getExtBasDroit() { return extBasDroit; }
	
	// Mutateurs
	public void setExtHautGauche(Position extHautGauche) { this.extHautGauche = extHautGauche; }
	public void setExtBasDroit(Position extBasDroit) { this.extBasDroit = extBasDroit; }
	
	// Méthodes graphique
	// Dessine une zone de la carte sous sa forme reelle sur la carte
	public void seDessiner(Graphics g) {
		for (int i = extHautGauche.getY(); i <= extBasDroit.getY(); i++) {
			for (int j = extHautGauche.getX(); j <= extBasDroit.getX(); j++) {
				if (carte.grille[i][j] != null) { carte.grille[i][j].seDessiner(g); }
				else {
					int x = j * NB_PIX_CASE,
						y = i * NB_PIX_CASE;
					g.setColor(COULEUR_SOL);
					g.fillRect(x + 5, y + 5, NB_PIX_CASE - 5, NB_PIX_CASE - 5);
				}
			}
		}
	}
	// Dessine une zone de la carte sous sa forme miniature sur la carte mini-map
	public void seDessinerMiniMap(Graphics g) {
		for (int i = extHautGauche.getY(); i <= extBasDroit.getY(); i++) {
			for (int j = extHautGauche.getX(); j <= extBasDroit.getX(); j++) {
				if (carte.grille[i][j] != null) { carte.grille[i][j].seDessinerMiniMap(g); }
				else {
					int x = j * NB_PIX_CASE_MINI_MAP,
						y = i * NB_PIX_CASE_MINI_MAP;
					g.setColor(COULEUR_SOL);
					g.fillRect(x + 1, y + 1, NB_PIX_CASE_MINI_MAP - 1, NB_PIX_CASE_MINI_MAP - 1);
				}
			}
		}
	}
	
}
