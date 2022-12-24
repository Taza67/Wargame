package wargameInterface;

import java.awt.Graphics;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.InfoPartie;

public class PanneauInfoPartie extends JPanel {
	private static final long serialVersionUID = 1L;
	// Infos
	private final InfoPartie infoPartie;
	
	// Constructeurs
	public PanneauInfoPartie(Carte carte) {
		this.infoPartie = carte.getInfoPartie();
	}
	
	// Méthodes graphiques
	public void paintComponent(Graphics g) {
		// infoPartie.seDessiner(g);
	}
}
