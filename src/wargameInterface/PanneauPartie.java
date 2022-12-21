package wargameInterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import wargame.Carte;

public class PanneauPartie extends JPanel {
	private static final long serialVersionUID = 1L;
	// Infos
	private Carte carte;
	private PanneauJeu jeu;
	private PanneauTableauBord tableauBord;
	
	// Constructeurs
	public PanneauPartie() {
		super();
		this.carte = new Carte(30, 50, 10);
		this.jeu = new PanneauJeu(carte);
		this.tableauBord = new PanneauTableauBord(carte);
		this.add(jeu, BorderLayout.WEST);
		this.add(tableauBord, BorderLayout.EAST);
		// Gestion des événéments
		// // Souris
		tableauBord.miniMap.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { carte.deplacerCarteAffichee(e.getX(), e.getY()); repaint(); }	
		});
		tableauBord.miniMap.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) { carte.deplacerCarteAffichee(e.getX(), e.getY()); repaint(); }
		});
		jeu.grille.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				carte.deplacerSelection(e.getX(), e.getY());
				jeu.repaint();
				tableauBord.miniMap.repaint();
			}
		});
		jeu.grille.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				carte.deplacerCurseur(e.getX(), e.getY()); 
				jeu.repaint();
				tableauBord.miniMap.repaint();
			}
		});
		jeu.grille.addMouseWheelListener(new MouseAdapter() {
			int zoom = 10;
			public void mouseWheelMoved(MouseWheelEvent e) { 
				zoom -= e.getWheelRotation(); 
				tableauBord.boutonsMiniMap.slider.setValue(zoom);
				carte.zoomerCarteAffichee(zoom); 
				repaint(); 
			}
		});
		tableauBord.boutonsMiniMap.slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) { carte.zoomerCarteAffichee(tableauBord.boutonsMiniMap.slider.getValue()); repaint(); }
		});
		tableauBord.boutonsMiniMap.reinit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				carte.zoomerCarteAffichee(10);
				tableauBord.boutonsMiniMap.slider.setValue(10);
				repaint(); 
			};
		});
	}
}
