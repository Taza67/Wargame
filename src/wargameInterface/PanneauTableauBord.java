package wargameInterface;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import wargame.Carte;
import wargame.IConfig;

public class PanneauTableauBord extends APanneau implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private final Carte CARTE;
	protected PanneauMiniMap miniMap;
	protected PanneauBoutonsMiniMap boutonsMiniMap;
	protected PanneauInfoPartie infoPartie;
	protected PanneauBoutonsMenu boutonsMenu;
	protected PanneauBoutonsTour boutonsTour;
	protected PanneauActionsHeros actionsHeros;
	protected Component blanc;
	
	// Constructeurs
	public PanneauTableauBord(Carte carte) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.CARTE = carte;
		this.miniMap = new PanneauMiniMap(CARTE);
		this.boutonsMiniMap = new PanneauBoutonsMiniMap(CARTE);
		this.infoPartie = new PanneauInfoPartie(CARTE);
		this.boutonsMenu = new PanneauBoutonsMenu(CARTE);
		this.boutonsTour = new PanneauBoutonsTour(CARTE);
		this.actionsHeros = new PanneauActionsHeros(CARTE);
		this.add(miniMap);
		this.add(boutonsMiniMap);
		this.add(infoPartie);
		blanc = Box.createRigidArea(new Dimension(carte.getLargMM() + 10, Carte.HAUTEUR_MAP - carte.getHautMM() - 200));
		this.add(blanc);
		this.add(boutonsMenu);
		this.add(boutonsTour);
		this.add(actionsHeros);
		this.setBackground(COULEUR_VIDE);
		this.setDim(carte.getLargMM() + 10, Carte.HAUTEUR_MAP + 55);
	}

	// Accesseurs
	public PanneauBoutonsTour getBoutonsTour() { return boutonsTour; }
	public PanneauActionsHeros getActionsHeros() { return actionsHeros; }
	
	// Méthodes
	public void setDimBlanc() {
		blanc.setPreferredSize(new Dimension(CARTE.getLargMM() + 10, Carte.HAUTEUR_MAP - CARTE.getHautMM() - 175));
	}
}