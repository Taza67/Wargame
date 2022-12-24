package wargameInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import wargame.Carte;
import wargame.IConfig;

public class PanneauActionsHeros extends JPanel implements IConfig {
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
		attaque.setPreferredSize(new Dimension(96, 20));
		campement.setPreferredSize(new Dimension(96, 20));
		longueMarche.setPreferredSize(new Dimension(96, 20));
		add(attaque, BorderLayout.WEST);
		add(campement, BorderLayout.CENTER);
		add(longueMarche, BorderLayout.EAST);
		this.setBackground(COULEUR_VIDE);
		this.setSize(new Dimension(carte.getLargMM() + 10, 30));
	}
	
}
