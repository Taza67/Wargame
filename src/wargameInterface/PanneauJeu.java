package wargameInterface;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;

import wargame.AConfig;
import wargame.Carte;

public class PanneauJeu extends JPanel {
	private static final long serialVersionUID = 1L;
	// Infos
	protected PanneauGrille grille;
	protected PanneauInfoBulle infosBulle;
	
	public PanneauJeu(Carte carte) {
		this.grille = new PanneauGrille(carte);
		this.infosBulle = new PanneauInfoBulle(carte);
		this.add(grille);
		this.add(infosBulle);
		Insets insets = this.getInsets();
		Dimension size;
		this.setLayout(null);
		// this.setLayout((LayoutManager)new BorderLayout());
		// this.add(grille, BorderLayout.NORTH);
		// this.add(infosCurseur, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(AConfig.LARGEUR_MAP, AConfig.HAUTEUR_MAP));
		size = grille.getPreferredSize();
		grille.setBounds(0 + insets.left, 0 + insets.top, size.width, size.height);
		size = infosBulle.getPreferredSize();
		infosBulle.setBounds(0 + insets.left, 0 + insets.top, size.width, size.height);;
	}
}
