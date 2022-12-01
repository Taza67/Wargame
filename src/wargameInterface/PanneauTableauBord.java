package wargameInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauTableauBord extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private final Carte CARTE;
	protected PanneauMiniMap miniMap;
	
	// Constructeurs
	public PanneauTableauBord(Carte carte) {
		this.setLayout((LayoutManager)new BorderLayout());
		this.CARTE = carte;
		this.miniMap = new PanneauMiniMap(carte);
		this.add(miniMap, BorderLayout.NORTH);
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(CARTE.getLARGEUR_PIXEL_MINI_MAP() + 10, CARTE.getHauteurPixelCarteAffichee() + 55));
	}
}