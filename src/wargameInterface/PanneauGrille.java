package wargameInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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
