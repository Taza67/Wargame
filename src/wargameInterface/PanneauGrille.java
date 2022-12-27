package wargameInterface;

import java.awt.Graphics;

import wargame.Carte;
import wargame.IConfig;

public class PanneauGrille extends APanneau implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	public final Carte CARTE;
	
	// Constructeurs
	public PanneauGrille(Carte carte) {
		super();
		this.CARTE = carte;
		this.setBackground(COULEUR_VIDE);
		setDim(Carte.LARGEUR_MAP, Carte.HAUTEUR_MAP);
	}
		
	// Méthodes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		CARTE.seDessiner(g);
	}
}
