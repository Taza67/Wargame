package wargameInterface;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import wargame.Carte;

public class PanneauJeu extends Panneau {
	private static final long serialVersionUID = 1L;
	// Infos
	protected PanneauGrille grille;
	protected PanneauInfoBar infosCurseur;
	
	public PanneauJeu(Carte carte) {
		this.setLayout((LayoutManager)new BorderLayout());
		this.grille = new PanneauGrille(carte);
		this.infosCurseur = new PanneauInfoBar(carte);
		this.add(grille, BorderLayout.NORTH);
		this.add(infosCurseur, BorderLayout.SOUTH);
		this.setDim(Carte.LARGEUR_MAP, Carte.HAUTEUR_MAP + 55);
	}
}
