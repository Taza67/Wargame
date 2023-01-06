package wargameInterface;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import wargame.Carte;
import wargame.IConfig;

public class Fenetre extends JFrame implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private PanneauPartie partie;
	private static JMenuBar mb;
	private static JMenu partieM, affichageM, optionsM, aideM;
	private static JMenuItem nouvelle, charger, quitter, pleinEcran;
	private static GraphicsDevice device;
	
	// Constructeurs
	public Fenetre(String name) {
		super(name);
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		mb = new JMenuBar();
		
		partieM = new JMenu("Partie");
		affichageM = new JMenu("Affichage");
		optionsM = new JMenu("Options");
		aideM = new JMenu("Aide");
		
		nouvelle = new JMenuItem("Nouvelle partie");
		charger = new JMenuItem("Charger partie");
		quitter = new JMenuItem("Quitter");
		pleinEcran = new JMenuItem("Plein écran");
		
		partieM.add(nouvelle);
		partieM.add(charger);
		partieM.add(quitter);
		
		affichageM.add(pleinEcran);
		
		mb.add(partieM);
		mb.add(affichageM);
		mb.add(optionsM);
		mb.add(aideM);
		
		this.setJMenuBar(mb);
		
		partie = new PanneauPartie();
		this.setContentPane(partie);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(Carte.POSITION_X, Carte.POSITION_Y);
		this.pack();
		this.setVisible(true);
		Fenetre f = this;
		nouvelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.getContentPane().removeAll();
				f.invalidate();
				partie = new PanneauPartie();
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
		        if (device.isFullScreenSupported()) {
		        	// f.setUndecorated(true);
		            device.setFullScreenWindow(f);
		            int haut = device.getFullScreenWindow().getHeight(),
		            	larg = device.getFullScreenWindow().getWidth();
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
	
	// Méthodes
}
