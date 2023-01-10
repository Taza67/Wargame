package wargameInterface;

import java.awt.Component;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;




/**
 * <b>Classe PanneauTableauBord extension de JPanel implémentation de IConfig/b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une Carte CARTE</li>
 * <li>Un PanneauMiniMap MiniMap</li>
 * <li>Un PanneauBoutonsMiniMap boutonsMiniMap</li>
 * <li>Un PanneauInfoPartie infoPartie</li>
 * <li>Un PanneauBoutonsMenu boutonsMenu</li>
 * <li>Un PanneauBoutonsTour boutonsTour</li>
 * <li>Un PanneauActionsHeros actionsHeros</li>
 * <li>Un Component blanc</li>
 * </ul>
 * </p>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */

public class PanneauTableauBord extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	/**
	 * Carte CARTE
	 */
	private final Carte CARTE;
	/**
	 * PanneauMiniMap miniMap
	 */
	protected PanneauMiniMap miniMap;
	/**
	 * PanneauBoutonsMiniMap boutonsMiniMap
	 */
	protected PanneauBoutonsMiniMap boutonsMiniMap;
	/**
	 * PanneauInfoPartie infoPartie
	 */
	protected PanneauInfoPartie infoPartie;
	/**
	 * PanneauBoutonsMenu boutonsMenu
	 */
	protected PanneauBoutonsMenu boutonsMenu;
	/**
	 * PanneauBoutonsTour boutonsTour
	 */
	protected PanneauBoutonsTour boutonsTour;
	/**
	 * PanneauActionHeros actionsHeros
	 */
	protected PanneauActionsHeros actionsHeros;
	/**
	 * Component blanc
	 */
	protected Component blanc;
	
	/**
	 * Constructeur PanneauTableauBord
	 * @param carte
	 * @param f
	 */
	public PanneauTableauBord(Carte carte, Fenetre f) {
		super();
		int hautBlanc;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.CARTE = carte;
		this.miniMap = new PanneauMiniMap(CARTE);
		this.boutonsMiniMap = new PanneauBoutonsMiniMap(CARTE);
		this.infoPartie = new PanneauInfoPartie(CARTE);
		this.boutonsMenu = new PanneauBoutonsMenu(CARTE, f);
		this.boutonsTour = new PanneauBoutonsTour(CARTE);
		this.actionsHeros = new PanneauActionsHeros(CARTE);
		this.add(miniMap);
		this.add(boutonsMiniMap);
		this.add(infoPartie);
		
		hautBlanc = Math.max(0, Carte.hauteurMap - miniMap.getPreferredSize().height - boutonsMiniMap.getPreferredSize().height
				  - infoPartie.getPreferredSize().height  - boutonsMenu.getPreferredSize().height
				  - boutonsTour.getPreferredSize().height  - actionsHeros.getPreferredSize().height - 10);
		blanc = Box.createRigidArea(new Dimension(carte.getLargMM() + 10, hautBlanc));
		this.add(blanc);
		this.add(boutonsMenu);
		this.add(boutonsTour);
		this.add(actionsHeros);
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM() + 10, Carte.hauteurMap));
	}

	/**
	 * Accesseur boutonTour
	 * @return PanneauBoutonsTour
	 */
	public PanneauBoutonsTour getBoutonsTour() { return boutonsTour; }
	/**
	 * Accesseur ActionHeros
	 * @return PanneauActionsHeros
	 */
	public PanneauActionsHeros getActionsHeros() { return actionsHeros; }
}