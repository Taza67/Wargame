package wargameInterface;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import wargame.IConfig;

public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;
	// Infos
	private PanneauPartie partie;
	private static JMenuBar mb;
	private static JMenu partieM, affichageM, optionsM, aideM;
	private static JMenuItem nouvelle, charger, quitter;
	
	// Constructeurs
	public Fenetre(String name) {
		super(name);
		mb = new JMenuBar();
		
		partieM = new JMenu("Partie");
		affichageM = new JMenu("Affichage");
		optionsM = new JMenu("Options");
		aideM = new JMenu("Aide");
		
		nouvelle = new JMenuItem("Nouvelle partie");
		charger = new JMenuItem("Charger partie");
		quitter = new JMenuItem("Quitter");
		
		partieM.add(nouvelle);
		partieM.add(charger);
		partieM.add(quitter);
		
		mb.add(partieM);
		mb.add(affichageM);
		mb.add(optionsM);
		mb.add(aideM);
		
		this.setJMenuBar(mb);
		
		partie = new PanneauPartie();
		this.setContentPane(partie);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(IConfig.POSITION_X, IConfig.POSITION_Y);
		this.pack();
		this.setVisible(true);
	}
	
	// Méthodes
}
