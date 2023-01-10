package wargameInterface;

import java.awt.BorderLayout;
import java.awt.TexturePaint;
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
import wargame.MethodesTextures;
import wargame.Point;
import wargame.Position;

public class PanneauPartie extends JPanel implements IConfig, KeyListener {
	private static final long serialVersionUID = 1L;
	// Infos
	private int zoom = 10;
	protected Carte carte;
	protected PanneauGrille grille;
	protected PanneauTableauBord tableauBord;
	protected Fenetre f;
	// Textures
	public static TexturePaint[] texturesPaint;
	
	// Constructeurs
	public PanneauPartie(Fenetre f, Carte carte) {
		super();
		this.f = f;
		if (isFocusable()) setFocusable(true);
		carte.setPanPartie(this);
		this.carte = carte;
		this.tableauBord = new PanneauTableauBord(carte, f);
		this.grille = new PanneauGrille(carte);
		this.add(grille, BorderLayout.WEST);
		this.add(tableauBord, BorderLayout.EAST);
		
		// Textures
		texturesPaint = MethodesTextures.chargerTextures(this, carte.getRayonHex());
		
		// Gestion des événéments
		// Souris
		tableauBord.miniMap.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { carte.deplacer(new Point(e.getX(), e.getY())); repaint(); }	
		});
		tableauBord.miniMap.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) { carte.deplacer(new Point(e.getX(), e.getY())); repaint(); }
		});
		grille.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				carte.deplacerCurseur(new Point(e.getX(), e.getY())); 
				grille.repaint();
				tableauBord.miniMap.repaint();
			}
		});
		grille.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (carte.getInfoPartie().getJoueur() == GENTILS) {
					if (SwingUtilities.isLeftMouseButton(e)) {
						carte.deplacerSelection(new Point(e.getX(), e.getY()));
						grille.repaint();
						tableauBord.miniMap.repaint();
						if (carte.getSelection() instanceof Heros) tableauBord.actionsHeros.setVisible(true);
						else tableauBord.actionsHeros.setVisible(false);
					} else if (SwingUtilities.isRightMouseButton(e)) {
						carte.faireAgirHeros(new Point(e.getX(), e.getY()));
						grille.repaint();
						tableauBord.miniMap.repaint();
					}
				}
			}
		});
		grille.addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoom = (e.getWheelRotation() < 0) ? Math.min(18, zoom + 1) : Math.max(6, zoom - 1); 
				tableauBord.boutonsMiniMap.slider.setValue(zoom);
				carte.zoomer(zoom);
				grille.repaint();
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
				grille.repaint();
				tableauBord.miniMap.repaint();
			};
		});
		tableauBord.boutonsTour.nextHeros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carte.setSelection(carte.trouveHeros());
				tableauBord.actionsHeros.setVisible(true);
				grille.repaint();
				tableauBord.miniMap.repaint();
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
			}
		});
		this.addKeyListener(this);
		
		
	}
	
	// Accesseurs
	public PanneauGrille getGrille() { return grille; }
	public PanneauTableauBord getTableauBord() { return tableauBord; }
	public Fenetre getF() { return f; }

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
			this.tableauBord.actionsHeros.setVisible(true);
			repaint();
			break;
		case KeyEvent.VK_ADD:
		case KeyEvent.VK_A:
			zoom = Math.min(18, zoom + 1); 
			tableauBord.boutonsMiniMap.slider.setValue(zoom);
			carte.zoomer(zoom);
			grille.repaint();
			tableauBord.miniMap.repaint();
			break;
		case KeyEvent.VK_SUBTRACT:
		case KeyEvent.VK_E:
			zoom = Math.max(6, zoom - 1); 
			tableauBord.boutonsMiniMap.slider.setValue(zoom);
			carte.zoomer(zoom);
			grille.repaint();
			tableauBord.miniMap.repaint();
			break;
		case KeyEvent.VK_ENTER:
			break;
		case KeyEvent.VK_SPACE:
			if (carte.getSelection() != null)
				carte.deplacer(carte.getSelection().getPos());
			else
				carte.deplacer(new Position(carte.getLARGC() / 2, carte.getHAUTC() / 2));
			repaint();
			break;
		case KeyEvent.VK_ESCAPE:
			carte.setSelection(null);
			this.tableauBord.actionsHeros.setVisible(false);
			repaint();
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
	}
}
