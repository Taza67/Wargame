package wargameInterface;


import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauBoutonsTour extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected final Carte CARTE;
	protected JButton finTour;
	
	// Constructeurs
	public PanneauBoutonsTour(Carte carte) {
		this.CARTE = carte;
		finTour = new JButton("Fin du tour");
		finTour.setPreferredSize(new Dimension(carte.getLargMM(), 50));
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM(), 50));
		this.add(finTour);
	}

	// Accesseurs
	public JButton getFinTour() { return finTour; }
	
	
}
