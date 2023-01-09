package wargameInterface;

import java.awt.Dimension;
import java.awt.FlowLayout;

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
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		int larg = carte.getLargMM(),
			largS = (int)((larg - 6) * 0.65),
			largR = larg - 6 - largS,
			haut = 25;
		
		CARTE = carte;
		reinit = new JButton("Réinitialiser");
		slider = new JSlider(6, 18, 10);
		reinit.setPreferredSize(new Dimension(largR, haut));
		slider.setPreferredSize(new Dimension(largS, haut));
		add(reinit);
		add(slider);
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM() + 10, haut + 10));
	}
}
