package wargameInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanneauMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	// Infos
	private JButton newGame, saveGame, loadGame, help, options, quit;
	
	// Constructeurs
	public PanneauMenu(Fenetre f) {
		newGame = new JButton("Nouvelle Partie");
		saveGame = new JButton("Sauvegarder la partie");
		loadGame = new JButton("Charger une partie");
		help = new JButton("Aide");
		options = new JButton("Options");
		quit = new JButton("Quitter");
		
		newGame.setPreferredSize(new Dimension(190, 50));
		saveGame.setPreferredSize(new Dimension(190, 50));
		loadGame.setPreferredSize(new Dimension(190, 50));
		help.setPreferredSize(new Dimension(190, 50));
		options.setPreferredSize(new Dimension(190, 50));
		quit.setPreferredSize(new Dimension(190, 50));
		
		add(newGame); add(saveGame); add(loadGame); add(help); add(options); add(quit);
		
		this.setPreferredSize(new Dimension(200, 300));
		
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
				f.chargerPartie();
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
