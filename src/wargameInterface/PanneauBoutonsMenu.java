package wargameInterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauBoutonsMenu extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	protected JButton newGame, saveGame, loadGame, help, options, quit;
	
	// Constructeurs
	public PanneauBoutonsMenu(Carte carte, Fenetre f) {
		super();
		int largB = carte.getLargMM() / 2 - 4,
			hautB = 30,
			haut = (hautB + 5) * 3;
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		newGame = new JButton("Nouvelle Partie");
		saveGame = new JButton("Sauvegarder");
		loadGame = new JButton("Charger");
		help = new JButton("Aide");
		options = new JButton("Options");
		quit = new JButton("Quitter");
		
		newGame.setPreferredSize(new Dimension(largB, hautB));
		saveGame.setPreferredSize(new Dimension(largB, hautB));
		loadGame.setPreferredSize(new Dimension(largB, hautB));
		help.setPreferredSize(new Dimension(largB, hautB));
		options.setPreferredSize(new Dimension(largB, hautB));
		quit.setPreferredSize(new Dimension(largB, hautB));

		add(newGame); add(saveGame); add(loadGame); add(help); add(options); add(quit);

		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM(), haut));
		
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.nouvellePartie();
			}
		});
		saveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.sauvegarderPartie();
			}
		});
		loadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.demanderQuelleSauvegarde();
			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.afficherAide();
			}
		});
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.afficherAide();
			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.quitter();
			}
		});
	}
}
