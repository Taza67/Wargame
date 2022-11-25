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
	
	// Méthodes graphiques
	public void seDessiner(Graphics g) {
		for (int i = extHautGauche.getY(); i <= extBasDroit.getY(); i++) {
			for (int j = extHautGauche.getX(); j <= extBasDroit.getX(); j++) {
				if (carte.grille[i][j] != null) { carte.grille[i][j].seDessiner(g); }
				else {
					int x = j * NB_PIX_CASE,
						y = i * NB_PIX_CASE;
					g.setColor(Color.white);
					g.fillRect(x + 5, y + 5, NB_PIX_CASE - 5, NB_PIX_CASE - 5);
				}
			}
		}
	}
	
}
