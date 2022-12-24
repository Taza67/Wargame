package wargameInterface;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import wargame.Carte;
import wargame.IConfig;

public class PanneauTableauBord extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private final Carte CARTE;
	protected PanneauMiniMap miniMap;
	protected PanneauBoutonsMiniMap boutonsMiniMap;
	protected PanneauInfoPartie infoPartie;
	protected PanneauTourJeu boutonsTour;
	protected PanneauActionsHeros actionsHeros;
	
	// Constructeurs
	public PanneauTableauBord(Carte carte) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.CARTE = carte;
		this.miniMap = new PanneauMiniMap(CARTE);
		this.boutonsMiniMap = new PanneauBoutonsMiniMap(CARTE);
		this.infoPartie = new PanneauInfoPartie(CARTE);
		this.boutonsTour = new PanneauTourJeu(CARTE);
		this.actionsHeros = new PanneauActionsHeros(CARTE);
		this.add(miniMap);
		this.add(boutonsMiniMap);
		this.add(infoPartie);
		this.add(Box.createRigidArea(new Dimension(carte.getLargMM() + 10, HAUTEUR_MAP - carte.getHautMM() - 135)));
		this.add(boutonsTour);
		this.add(actionsHeros);
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM() + 10, HAUTEUR_MAP + 55));
	}

	// Accesseurs
	public PanneauTourJeu getBoutonsTour() { return boutonsTour; }
	
	
}