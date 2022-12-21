package wargameInterface;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauGrille extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	public final Carte CARTE;
	
	// Constructeurs
	public PanneauGrille(Carte carte) {
		super();
		this.CARTE = carte;
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(CARTE.getLargeurPixelCarteAffichee(), CARTE.getHauteurPixelCarteAffichee()));
	}
		
	// Méthodes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		CARTE.majVisibilite();
		CARTE.seDessinerCarteAffichee(g);
	}
}
