package wargameInterface;

import java.awt.Dimension;


import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import wargame.Carte;
import wargame.IConfig;




/**
 * <b>Classe PanneauBoutonMiniMap extension de JPanel et implémentation de IConfig</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Un JButton reinit</li>
 * <li>Un JSlider slider</li>
 * <li>Une carte CARTE</li>
 * </ul>
 * </p>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */

public class PanneauBoutonsMiniMap extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	/**
	 * JButton reinit
	 */
	protected JButton reinit;
	/**
	 * JSlider slider
	 */
	protected JSlider slider;
	/**
	 * Carte CARTE
	 */
	public final Carte CARTE;

	/**
	 * Constructeur du PanneauBoutonMiniMap
	 * @param carte
	 */
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
