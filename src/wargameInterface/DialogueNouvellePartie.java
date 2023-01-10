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




/**
 * <b>Classe DialogueNouvellePartie extension de JDialog et implémentation de IConfig</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Un JSpinner nbHeros</li>
 * <li>Un JSpinner nbMonstres</li>
 * <li>Un JSpinner Largeur</li>
 * <li>Un JSpinner Hauteur</li>
 * <li>Un tableau de String labels</li>
 * <li>Une larg(int)</li>
 * <li>Une haut (int)</li>
 * <li>Une nbH (int)</li>
 * <li>Une nbM (int)</li>
 * </ul>
 * </p>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */

public class DialogueNouvellePartie extends JDialog implements IConfig {
	private static final long serialVersionUID = 1L;
	/**
	 * JSpinner nbHeros
	 */
	protected JSpinner nbHeros, 
	/**
	 * Jspinner nbMonstres
	 */
	nbMonstres,
	/**
	 * Jspinner largeur
	 */
	largeur,
	/**
	 * Jspinner hauteur
	 */
	hauteur;
	
	/**
	 * Tableau de labels
	 */
	protected String[] labels = {"Nombre d'héros", "Nombre de monstres", "Largeur de la carte", "Hauteur de la carte"};
	/**
	 * largeur (int)
	 */
	private int larg, 
	/**
	 * hauteur (int)
	 */
	haut, 
	/**
	 * nbH
	 */
	nbH, 
	/**
	 * nbM
	 */
	nbM;
	/**
	 * ret (bool)
	 */
	private boolean ret = false;
	
	
	/**
	 *  Constructeur
	 * @param f
	 */
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
		SpinnerModel modele = new SpinnerNumberModel(5, 1, 15, 1);
		nbHeros = new JSpinner(modele);
		nbHeros.setPreferredSize(new Dimension(largS, hautS));
		this.add(nbHeros);
		
		label = new JLabel(labels[1], JLabel.CENTER);
		label.setPreferredSize(new Dimension(largL, hautL));
		this.add(label);
		modele = new SpinnerNumberModel(5, 1, 15, 1);
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

	/**
	 *  Accesseur Larg
	 * @return int
	 */
	public int getLarg() { return larg; }
	/**
	 * Accesseur Haut
	 * @return int
	 */
	public int getHaut() { return haut; }
	/**
	 * Accesseur NbH
	 * @return int
	 */
	public int getNbH() { return nbH; }
	/**
	 * Accesseur NbM
	 * @return int
	 */
	public int getNbM() { return nbM; }
	
	/**
	 * rend visible la boite dialogue et retourne true
	 * @return bool
	 */
	public boolean showDialogue() {
		this.setVisible(true);
		return ret;
	}
	
}
