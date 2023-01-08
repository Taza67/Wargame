package wargameInterface;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import wargame.Carte;
import wargame.IConfig;

public class Fenetre extends JFrame implements IConfig {
	public class BarreMenu extends JMenuBar {
		private static final long serialVersionUID = 1L;
		// Infos
		private JMenu partieM, affichageM, optionsM, aideM;
		private JMenuItem nouvelle, charger, quitter, pleinEcran;
		
		// Constructeurs
		public BarreMenu(Fenetre f) {
			super();
			partieM = new JMenu("Partie");
			partieM.setMnemonic(KeyEvent.VK_P);
			affichageM = new JMenu("Affichage");
			affichageM.setMnemonic(KeyEvent.VK_A);
			optionsM = new JMenu("Options");
			optionsM.setMnemonic(KeyEvent.VK_O);
			aideM = new JMenu("Aide");
			aideM.setMnemonic(KeyEvent.VK_H);
			
			nouvelle = new JMenuItem("Nouvelle partie");
			charger = new JMenuItem("Charger partie");
			quitter = new JMenuItem("Quitter");
			pleinEcran = new JMenuItem("Plein écran");
			
			partieM.add(nouvelle);
			partieM.add(charger);
			partieM.add(quitter);
			
			affichageM.add(pleinEcran);
			
			this.add(partieM);
			this.add(affichageM);
			this.add(optionsM);
			this.add(aideM);
			
			nouvelle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.nouvellePartie();
				}
			});
			quitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.quitter();
				}
			});
			pleinEcran.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.passerPleinEcran();
				}
			});
		}
	}
	
	private static final long serialVersionUID = 1L;
	// Infos
	protected PanneauPartie partie;
	protected PanneauMenu menu;
	private BarreMenu mb;
	private GraphicsDevice device;
	
	// Constructeurs
	public Fenetre(String name) {
		super(name);
		
		JLayeredPane panneau = new JLayeredPane();
		partie = new PanneauPartie(this);
		menu = new PanneauMenu(this);
		menu.setVisible(false);
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		mb = new BarreMenu(this);
		
		panneau.add(partie, JLayeredPane.DEFAULT_LAYER);
		panneau.add(menu, JLayeredPane.PALETTE_LAYER);
		
		panneau.setPreferredSize(partie.getPreferredSize());
		
		this.setJMenuBar(mb);
		this.setContentPane(partie);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(Carte.POSITION_X, Carte.POSITION_Y);
		this.pack();
		this.setVisible(true);
	}
	
	// Méthodes
	// Affiche le menu
	public void afficherMenu() {
		menu.setVisible(true);
	}
	// Lance une nouvelle partie
	public void nouvellePartie() {
		this.getContentPane().removeAll();
		this.invalidate();
		this.partie = new PanneauPartie(this);
		this.setContentPane(partie);
		this.validate();
	}
	// Quitte la partie
	public void quitter() {
		this.dispose();
	}
	// Passe en plein écran si c'est possible
	public void passerPleinEcran() {
        if (this.device.isFullScreenSupported()) {
        	// f.setUndecorated(true);
            this.device.setFullScreenWindow(this);
            int haut = this.device.getFullScreenWindow().getHeight(),
            	larg = this.device.getFullScreenWindow().getWidth();
            Carte.HAUTEUR_MAP = haut - 80;
            Carte.LARGEUR_MAP = larg - LARGEUR_MINI_MAP - 75;
            Carte.recalculerMapAff();
            // partie.setDimensions();
            this.revalidate();
            this.repaint();
        }
	}
	
	public void sauvegarderPartie() {
	}

	public void chargerPartie() {
	}

	public void afficherAide() {
	}

}
