package wargame;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Panneau extends JPanel {
	private static final long serialVersionUID = 1L;
	// Infos
	private Carte c;
	
	// Constructeurs
	public Panneau() {
		super();
		c = new Carte();
	}
	
	// Méthodes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		c.seDessinerCoucheReelle(g);
	}
}
