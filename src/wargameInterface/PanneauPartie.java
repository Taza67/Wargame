package wargameInterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import wargame.Carte;
import wargame.Point;

public class PanneauPartie extends JPanel {
	private static final long serialVersionUID = 1L;
	// Infos
	private Carte carte;
	private PanneauJeu jeu;
	private PanneauTableauBord tableauBord;
	
	// Constructeurs
	public PanneauPartie() {
		super();
		this.carte = new Carte(50, 40);
		this.tableauBord = new PanneauTableauBord(carte);
		this.jeu = new PanneauJeu(carte);
		this.add(jeu, BorderLayout.WEST);
		this.add(tableauBord, BorderLayout.EAST);
		// Gestion des événéments
		// Souris
		tableauBord.miniMap.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { carte.deplacer(new Point(e.getX(), e.getY())); repaint(); }	
		});
		tableauBord.miniMap.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) { carte.deplacer(new Point(e.getX(), e.getY())); repaint(); }
		});
		jeu.grille.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				carte.deplacerCurseur(new Point(e.getX(), e.getY())); 
				jeu.repaint();
				tableauBord.miniMap.repaint();
			}
		});
		jeu.grille.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					carte.deplacerSelection(new Point(e.getX(), e.getY()));
					jeu.repaint();
					tableauBord.miniMap.repaint();
				} else if (SwingUtilities.isRightMouseButton(e)) {
					carte.deplacerHeros(new Point(e.getX(), e.getY()));
					jeu.repaint();
					tableauBord.miniMap.repaint();
				} 
			}
		});
		jeu.grille.addMouseWheelListener(new MouseAdapter() {
			int zoom = 10;
			public void mouseWheelMoved(MouseWheelEvent e) { 
				zoom -= e.getWheelRotation(); 
				tableauBord.boutonsMiniMap.slider.setValue(zoom);
				carte.zoomer(zoom); 
				repaint(); 
			}
		});
		tableauBord.boutonsMiniMap.slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) { carte.zoomer(tableauBord.boutonsMiniMap.slider.getValue()); repaint(); }
		});
		tableauBord.boutonsMiniMap.reinit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				carte.zoomer(10);
				tableauBord.boutonsMiniMap.slider.setValue(10);
				repaint(); 
			};
		});
	}
}
