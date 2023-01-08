package wargameInterface;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import wargame.Carte;
import wargame.IConfig;

public class PanneauBoutonsMiniMap extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected JButton reinit;
	protected JSlider slider;
	public final Carte CARTE;

	public PanneauBoutonsMiniMap(Carte carte) {
		super();
		CARTE = carte;
		reinit = new JButton("Réinitialiser");
		slider = new JSlider(6, 18, 10);
		reinit.setPreferredSize(new Dimension(100, 20));
		add(reinit);
		add(slider);
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM() + 10, 30));
	}
}
