package wargameInterface;

import java.awt.Dimension;



import javax.swing.JPanel;

import wargame.Carte;



/**
 * <b>Classe PanneauJeu extention de JPanel</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Un PanneauGrille grille</li>
 * </ul>
 * </p>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */

public class PanneauJeu extends JPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * PanneauGrille grille
	 */
	protected PanneauGrille grille;
	
	/**
	 * Constructeur PanneauJeu
	 * @param carte
	 */
	public PanneauJeu(Carte carte) {
		super();
		this.grille = new PanneauGrille(carte);
		this.add(grille);
		this.setPreferredSize(new Dimension(Carte.largeurMap, Carte.hauteurMap));
	}
}
