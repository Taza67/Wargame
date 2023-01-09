package wargameInterface;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import wargame.IConfig;

public class DialogueNouvellePartie extends JDialog implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected JSpinner nbHeros, nbMonstres, largeur, hauteur;
	protected String[] labels = {"Nombre d'héros", "Nombre de monstres", "Largeur de la carte", "Hauteur de la carte"};
	private int larg, haut, nbH, nbM;
	private boolean ret = false;
	
	
	// Constructeurs
	public DialogueNouvellePartie(Fenetre f) {
		super(f, "Nouvelle Partie", Dialog.ModalityType.APPLICATION_MODAL);
		int hautS = 20, 
			largS = 50, 
			hautL = 20, 
			largL = 170;
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel(labels[0], JLabel.CENTER);
		label.setPreferredSize(new Dimension(largL, hautL));
		this.add(label);
		SpinnerModel modele = new SpinnerNumberModel(5, 5, 15, 1);
		nbHeros = new JSpinner(modele);
		nbHeros.setPreferredSize(new Dimension(largS, hautS));
		this.add(nbHeros);
		
		label = new JLabel(labels[1], JLabel.CENTER);
		label.setPreferredSize(new Dimension(largL, hautL));
		this.add(label);
		modele = new SpinnerNumberModel(5, 5, 15, 1);
		nbMonstres = new JSpinner(modele);
		nbMonstres.setPreferredSize(new Dimension(largS, hautS));
		this.add(nbMonstres);
		
		label = new JLabel(labels[2], JLabel.CENTER);
		label.setPreferredSize(new Dimension(largL, hautL));
		this.add(label);
		modele = new SpinnerNumberModel(45, 45, 65, 1);
		largeur = new JSpinner(modele);
		largeur.setPreferredSize(new Dimension(largS, hautS));
		this.add(largeur);
		
		label = new JLabel(labels[3], JLabel.CENTER);
		label.setPreferredSize(new Dimension(largL, hautL));
		this.add(label);
		modele = new SpinnerNumberModel(45, 45, 70, 1);
		hauteur = new JSpinner(modele);
		hauteur.setPreferredSize(new Dimension(largS, hautS));
		this.add(hauteur);

		JButton lancer = new JButton("Lancer");
		JButton annuler = new JButton("Annuler");
		
		lancer.setPreferredSize(new Dimension(110, 30));
		annuler.setPreferredSize(new Dimension(110, 30));
		
		this.add(lancer);
		this.add(annuler);
		
		DialogueNouvellePartie d = this;
		lancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				larg = (int)largeur.getValue();
				haut = (int)hauteur.getValue();
				nbH = (int)nbHeros.getValue();
				nbM = (int)nbMonstres.getValue();
				ret = true;
		        d.setVisible(false);
			}
		});
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				d.setVisible(false);
			}
		});
		this.setSize(new Dimension(230, 175));
		this.setLocationRelativeTo(null); 
	}

	// Accesseurs
	public int getLarg() { return larg; }
	public int getHaut() { return haut; }
	public int getNbH() { return nbH; }
	public int getNbM() { return nbM; }
	
	// Méthodes
	public boolean showDialogue() {
		this.setVisible(true);
		return ret;
	}
	
}
