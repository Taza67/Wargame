package wargameInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauBoutonsMenu extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	protected final Carte CARTE;
	protected JButton menu;
	
	// Constructeurs
	public PanneauBoutonsMenu(Carte carte, Fenetre f) {
		this.CARTE = carte;
		menu = new JButton("Menu");
		menu.setPreferredSize(new Dimension(carte.getLargMM(), 50));
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM(), 50));
		this.add(menu);
		
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.afficherMenu();
			}
		});
	}

	// Accesseurs
	public JButton getMenu() { return menu; }
}
