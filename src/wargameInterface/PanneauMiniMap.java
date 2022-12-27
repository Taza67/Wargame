package wargameInterface;

import java.awt.Graphics;

import wargame.Carte;
import wargame.IConfig;

public class PanneauMiniMap extends APanneau implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	public final Carte CARTE;
	
	// Constructeurs
	public PanneauMiniMap(Carte carte) {
		super();
		this.CARTE = carte;
		this.setBackground(COULEUR_VIDE);
		this.setDim(carte.getLargMM() + 10, CARTE.getHautMM() + 10);
	}
		
	// Méthodes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		CARTE.seDessinerMM(g);
	}
}
