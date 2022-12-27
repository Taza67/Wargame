package wargameInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import wargame.Carte;
import wargame.IConfig;

public class PanneauActionsHeros extends Panneau implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected JButton attaque;
	protected JButton campement;
	protected JButton longueMarche;
	public final Carte CARTE;

	public PanneauActionsHeros(Carte carte) {
		super();
		CARTE = carte;
		attaque = new JButton("Attaque");
		campement = new JButton("Campement");
		longueMarche = new JButton("Longue Marche");		
		attaque.setPreferredSize(new Dimension(carte.getLargMM() / 3 - 3, 20));
		campement.setPreferredSize(new Dimension(carte.getLargMM() / 3 - 3, 20));
		longueMarche.setPreferredSize(new Dimension(carte.getLargMM() / 3 - 3, 20));
		add(attaque, BorderLayout.WEST);
		add(campement, BorderLayout.CENTER);
		add(longueMarche, BorderLayout.EAST);
		this.setBackground(COULEUR_VIDE);
		this.setDim(carte.getLargMM() + 10, 30);
	}
}
