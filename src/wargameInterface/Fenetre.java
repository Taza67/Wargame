package wargameInterface;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
		private JMenuItem nouvelle, charger, quitter, pleinEcran,sauvegarder;

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
			sauvegarder = new JMenuItem("Sauvgarder la partie");
			charger = new JMenuItem("Charger partie");
			quitter = new JMenuItem("Quitter");
			pleinEcran = new JMenuItem("Plein écran");


			partieM.add(nouvelle);
			partieM.add(sauvegarder);
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
			sauvegarder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.sauvegarderPartie();
				}
			});
			charger.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.chargerPartie();
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
		Carte carte = new Carte(null, 50, 40);
		partie = new PanneauPartie(this, carte);
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
	// Change le panneau de la partie
	public void changerPartie(PanneauPartie p) {
		this.getContentPane().removeAll();
		this.invalidate();
		this.partie = p;
		this.setContentPane(partie);
		this.validate();
	}
	// Lance une nouvelle partie
	public void nouvellePartie() {
		Carte carte = new Carte(null, 50, 40);
		changerPartie(new PanneauPartie(this, carte));
	}
	// Sauvegarde la partie
	public void sauvegarderPartie() {
		String nom = "sauvegarde.ser";
		FileOutputStream fichier = null;
		ObjectOutputStream output = null;
		try {
			fichier = new FileOutputStream(nom);
			output = new ObjectOutputStream(fichier);
			output.writeObject(this.partie.carte);
			output.close();
			fichier.close();
			System.out.printf("Les données ont été sauvgardés dans le fichier " + nom);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Charge une sauvegarde
	public void chargerPartie() {
		String nom = "sauvegarde.ser";
		FileInputStream fichier = null;
		ObjectInputStream lecture = null;
		Carte carte = null;
		PanneauPartie p = null;
		try {
			fichier = new FileInputStream(nom);
			lecture = new ObjectInputStream(fichier);
			carte = (Carte)lecture.readObject();
			lecture.close();
			fichier.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		catch (ClassNotFoundException ex) {
			System.out.println("La class Carte n'existe pas");
			ex.printStackTrace();
		}
		p = new PanneauPartie(this, carte);
		changerPartie(p);
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
			// Carte.recalculerMapAff();
			// partie.setDimensions();
			this.revalidate();
			this.repaint();
		}
	}

	public void afficherAide() {
	}

}
