package wargameInterface;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauMiniMap extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	public final Carte CARTE;
	
	// Constructeurs
	public PanneauMiniMap(Carte carte) {
		super();
		this.CARTE = carte;
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(CARTE.getLARGEUR_PIXEL_MINI_MAP() + 10, CARTE.getHAUTEUR_PIXEL_MINI_MAP() + 10));
	}
		
	// Méthodes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		CARTE.majVisibilite();
		CARTE.seDessinerMiniMap(g);
	}
}
