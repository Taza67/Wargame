package wargameInterface;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
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
					f.getContentPane().removeAll();
					f.invalidate();
					f.partie = new PanneauPartie();
					f.setContentPane(partie);
					f.validate();
				}
			});
			quitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.dispose();
				}
			});
			pleinEcran.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
			        if (f.device.isFullScreenSupported()) {
			        	// f.setUndecorated(true);
			            f.device.setFullScreenWindow(f);
			            int haut = f.device.getFullScreenWindow().getHeight(),
			            	larg = f.device.getFullScreenWindow().getWidth();
			            Carte.HAUTEUR_MAP = haut - 80;
			            Carte.LARGEUR_MAP = larg - LARGEUR_MINI_MAP - 75;
			            Carte.recalculerMapAff();
			            // partie.setDimensions();
			            f.revalidate();
			            f.repaint();
			        }
				}
			});
		}
	}
	
	private static final long serialVersionUID = 1L;
	// Infos
	private PanneauPartie partie;
	private BarreMenu mb;
	private GraphicsDevice device;
	
	// Constructeurs
	public Fenetre(String name) {
		super(name);
		partie = new PanneauPartie();
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		mb = new BarreMenu(this);
		
		this.setJMenuBar(mb);
		this.setContentPane(partie);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(Carte.POSITION_X, Carte.POSITION_Y);
		this.pack();
		this.setVisible(true);
	}
	
	// Méthodes
}
