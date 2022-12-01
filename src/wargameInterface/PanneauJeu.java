package wargameInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauJeu extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private final Carte CARTE;
	protected PanneauGrille grille;
	protected PanneauInfoBar infosCurseur;
	
	public PanneauJeu(Carte carte) {
		this.setLayout((LayoutManager)new BorderLayout());
		this.CARTE = carte;
		this.grille = new PanneauGrille(carte);
		this.infosCurseur = new PanneauInfoBar(carte);
		this.add(grille, BorderLayout.NORTH);
		this.add(infosCurseur, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(CARTE.getLargeurPixelCarteAffichee(), CARTE.getHauteurPixelCarteAffichee() + 55));
		
	}
}
