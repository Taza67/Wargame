package wargameInterface;

import java.awt.Dimension;

import javax.swing.JPanel;

import wargame.Carte;

public class PanneauJeu extends JPanel {
	private static final long serialVersionUID = 1L;
	// Infos
	protected PanneauGrille grille;
	
	public PanneauJeu(Carte carte) {
		super();
		this.grille = new PanneauGrille(carte);
		this.add(grille);
		// this.setLayout((LayoutManager)new BorderLayout());
		// this.add(grille, BorderLayout.NORTH);
		// this.add(infosCurseur, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(Carte.largeurMap, Carte.hauteurMap));
	}
}
