package wargameInterface;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import wargame.Carte;
import wargame.IConfig;

public class PanneauTourJeu extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected final Carte CARTE;
	protected JButton finTour;
	
	// Constructeurs
	public PanneauTourJeu(Carte carte) {
		this.CARTE = carte;
		finTour = new JButton("Fin du tour");
		this.setBackground(COULEUR_VIDE);
		finTour.setPreferredSize(new Dimension(carte.getLargMM(), 50));
		this.add(finTour);
	}

	// Accesseurs
	public JButton getFinTour() { return finTour; }
	
	
}
