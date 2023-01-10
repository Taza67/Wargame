package wargameInterface;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;
import wargame.InfoPartie;

public class PanneauInfoPartie extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private final InfoPartie infoPartie;
	
	/**
	 * Créer un panneau pour les informations de la partie
	 * @param carte
	 */
	public PanneauInfoPartie(Carte carte) {
		super();
		this.infoPartie = carte.getInfoPartie();
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(Carte.largeurMap, 140));
	}
	
	/**
	 * Methode paintcomponent responsable du dessin graphique
	 * Graphics g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		infoPartie.seDessiner(g);
	}
}
