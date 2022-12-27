package wargameInterface;

import java.awt.Graphics;

import wargame.Carte;
import wargame.IConfig;
import wargame.InfoPartie;

public class PanneauInfoPartie extends Panneau implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private final InfoPartie infoPartie;
	
	// Constructeurs
	public PanneauInfoPartie(Carte carte) {
		this.infoPartie = carte.getInfoPartie();
		this.setBackground(COULEUR_VIDE);
		this.setDim(Carte.LARGEUR_MAP, 110);
	}
	
	// Méthodes graphiques
	public void paintComponent(Graphics g) {
		infoPartie.seDessiner(g);
	}
}
