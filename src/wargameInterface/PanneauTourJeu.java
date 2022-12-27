package wargameInterface;


import javax.swing.JButton;
import wargame.Carte;
import wargame.IConfig;

public class PanneauTourJeu extends Panneau implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected final Carte CARTE;
	protected JButton finTour;
	
	// Constructeurs
	public PanneauTourJeu(Carte carte) {
		this.CARTE = carte;
		finTour = new JButton("Fin du tour");
		this.setBackground(COULEUR_VIDE);
		this.setDim(carte.getLargMM(), 50);
		this.add(finTour);
	}

	// Accesseurs
	public JButton getFinTour() { return finTour; }
	
	
}
