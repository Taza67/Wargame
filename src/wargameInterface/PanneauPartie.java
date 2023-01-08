package wargameInterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import wargame.Carte;
import wargame.Heros;
import wargame.IConfig;
import wargame.Point;
import wargame.Position;

public class PanneauPartie extends JPanel implements IConfig, KeyListener , java.io.Serializable{
	private static final long serialVersionUID = 1L;
	// Infos
	private int zoom = 10;
	private Carte carte;
	private PanneauJeu jeu;
	private PanneauTableauBord tableauBord;
	
	// Constructeurs
	public PanneauPartie(Fenetre f) {
		super();
		if (isFocusable()) setFocusable(true);
		this.carte = new Carte(this, 50, 40);
		this.tableauBord = new PanneauTableauBord(carte, f);
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
				if (carte.getInfoPartie().getJoueur() == GENTILS) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						carte.deplacerSelection(new Point(e.getX(), e.getY()));
						jeu.repaint();
						tableauBord.miniMap.repaint();
						if (carte.getSelection() instanceof Heros) tableauBord.actionsHeros.setVisible(true);
						else tableauBord.actionsHeros.setVisible(false);
					} else if (SwingUtilities.isRightMouseButton(e)) {
						carte.faireAgirHeros(new Point(e.getX(), e.getY()));
						jeu.repaint();
						tableauBord.miniMap.repaint();
					}
				}
			}
		});
		jeu.grille.addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoom = (e.getWheelRotation() < 0) ? Math.min(18, zoom + 1) : Math.max(6, zoom - 1); 
				tableauBord.boutonsMiniMap.slider.setValue(zoom);
				carte.zoomer(zoom);
				jeu.repaint();
				tableauBord.miniMap.repaint();
			}
		});
		tableauBord.boutonsMiniMap.slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) { carte.zoomer(tableauBord.boutonsMiniMap.slider.getValue()); repaint(); }
		});
		tableauBord.boutonsMiniMap.reinit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carte.zoomer(10);
				tableauBord.boutonsMiniMap.slider.setValue(10);
				jeu.repaint();
				tableauBord.miniMap.repaint();
			};
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
			}
		});
		this.addKeyListener(this);
	}
	
	// Accesseurs
	public PanneauJeu getJeu() { return jeu; }
	public PanneauTableauBord getTableauBord() { return tableauBord; }

	// Méthodes d'écoute
	public void keyTyped(KeyEvent e) { }
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_Z:
			carte.deplacer(carte.getCentreAff().substract(new Position(0, 1)));
			repaint();
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			carte.deplacer(carte.getCentreAff().add(new Position(0, 1)));
			repaint();
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_Q:
			carte.deplacer(carte.getCentreAff().substract(new Position(1, 0)));
			repaint();
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			carte.deplacer(carte.getCentreAff().add(new Position(1, 0)));
			repaint();
			break;
		case KeyEvent.VK_R:
			zoom = 10;
			carte.zoomer(10);
			tableauBord.boutonsMiniMap.slider.setValue(10);
			repaint();
			break;
		case KeyEvent.VK_H:
			carte.setSelection(carte.trouveHeros());
			repaint();
			break;
		case KeyEvent.VK_ADD:
		case KeyEvent.VK_A:
			zoom = Math.min(18, zoom + 1); 
			tableauBord.boutonsMiniMap.slider.setValue(zoom);
			carte.zoomer(zoom);
			jeu.repaint();
			tableauBord.miniMap.repaint();
			break;
		case KeyEvent.VK_SUBTRACT:
		case KeyEvent.VK_E:
			zoom = Math.max(6, zoom - 1); 
			tableauBord.boutonsMiniMap.slider.setValue(zoom);
			carte.zoomer(zoom);
			jeu.repaint();
			tableauBord.miniMap.repaint();
			break;
		case KeyEvent.VK_ENTER:
			break;
		case KeyEvent.VK_SPACE:
			carte.deplacer(carte.getSelection().getPos());
			repaint();
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
	}
}
