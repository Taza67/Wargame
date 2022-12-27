package wargameInterface;

import java.awt.Graphics;

import wargame.Carte;
import wargame.IConfig;

public class PanneauInfoBar extends Panneau implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	public final Carte CARTE;
	
	// Constructeurs
	public PanneauInfoBar(Carte carte) {
		this.CARTE = carte;
		this.setBackground(COULEUR_VIDE);
		this.setDim(Carte.LARGEUR_MAP, 50);
	}
		
	// Méthodes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		CARTE.getInfoBar().seDessiner(g);
	}
}
