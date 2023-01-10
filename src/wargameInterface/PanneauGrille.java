package wargameInterface;

import java.awt.Dimension;


import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;



/**
 * <b>Classe PanneauGrille extension du JPanel et implementation de IConfig</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une Carte</li>
 * </ul>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */

public class PanneauGrille extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	/**
	 * Carte CARTE
	 */
	public final Carte CARTE;
	
	/**
	 * Constructeur du PanneauGrille
	 * @param carte
	 */
	public PanneauGrille(Carte carte) {
		super();
		this.CARTE = carte;
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(Carte.largeurMap, Carte.hauteurMap));
	}
		
	/**
	 * peint le composant PanneauGrille
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		CARTE.seDessiner(g2d);
	}
}
