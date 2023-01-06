package wargameInterface;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauBoutonsMenu extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	protected final Carte CARTE;
	protected JButton menu;
	
	// Constructeurs
	public PanneauBoutonsMenu(Carte carte) {
		this.CARTE = carte;
		menu = new JButton("Menu");
		menu.setPreferredSize(new Dimension(carte.getLargMM(), 50));
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM(), 50));
		this.add(menu);
	}

	// Accesseurs
	public JButton getMenu() { return menu; }
}
