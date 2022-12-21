package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class Element implements IConfig {
	// Sous-classes
	private class Point {
		// Infos
		private int x;
		private int y;
		
		// Constructeurs
		public Point(int x, int y) { this.x = x; this.y = y; }
	}
	private class Hexagone {
		// Infos de l'hexagone
		private final Point[] extremites;
		private Point centre;
		
		// Constructeurs
		public Hexagone(Point centre, Point unSommet) {
			this.extremites = new Point[6];
			this.centre = centre;
			this.extremites[0] = unSommet;
			calculerAutresSommets();
		}
		
		// Méthodes
		// Retourne le point d'indice donné à partir du centre, du nombre de côtés/points et du point de base
		private Point calculerAutreSommet(int indice) {
			Point p = null;
			int x, y, dx, dy, r, a,
			    xC = centre.x,
			    yC = centre.y,
			    xP = extremites[0].x,
			    yP = extremites[0].y;
			dx = Math.abs(xP - xC);
			dy = Math.abs(yP - yC);
			r = (int)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			a = (int)Math.atan2(dy, dx);
			x = (int)(xC + r * Math.cos(a + indice * 2 * Math.PI / 6));
			y = (int)(yC + r * Math.sin(a + indice * 2 * Math.PI / 6));
			p = new Point(x, y);
			return p;
		}
		// Calcule tous les autres sommets de l'hexagone
		private void calculerAutresSommets() {
			for (int i = 0; i < 6; i++)
				extremites[i] = calculerAutreSommet(i);
		}
	}
	
	// Constantes statiques
	public static final char ELEMENT_CARTE = 'c',
							 ELEMENT_MINI_MAP = 'm';
	
	// Infos
	protected Carte carte;
	protected Position pos;
	protected boolean visible = false;							// Élément visible pour le joueur
	
	// Méthodes
	// Méthodes graphiques
	// Dessine un rectangle plein de dimensions et couleur données
	public void dessinerRectangle(Graphics g, Color c, int x, int y, int larg, int haut) {
		// On stocke la couleur => reprise plus tard
		Color couleurCourante = g.getColor();
		g.setColor(c);
		g.fillRect(x, y, larg, haut);
		// Reprise de la couleur sauvegardée
		g.setColor(couleurCourante);
	}
	// Dessine un hexagone plein de dimensions
	public void dessinerHexagone(Graphics g, Color c, int x, int y, int rayon) {
		Hexagone h = new Hexagone(new Point(x, y), new Point(x + rayon, y));
		int tabx[] = new int[6];
		int taby[] = new int[6];
		// faire un tableau de point x et y
		for (int i = 0; i < 6; i++) {
			tabx[i] = h.extremites[i].x;
			taby[i] = h.extremites[i].y;
		}
		// appeler la methode qui dessine le polygone en fonction des tableaux
		// // On stocke la couleur => reprise plus tard
		Color couleurCourante = g.getColor();
		g.setColor(c);
		g.fillPolygon(tabx, taby, 6);
		// // Reprise de la couleur sauvegardée
		g.setColor(couleurCourante);
	}
	public void dessinerHexagone(Graphics g, int x, int y, int rayon) {
		dessinerHexagone(g, g.getColor(), x, y, rayon);
	}
	// Dessine l'élément sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		int taillePix, frontiere,
			xPix, yPix;					// Coordoonnées de l'origine de la case représentant l'élément (en pixels)
		if (type == ELEMENT_CARTE) {
			taillePix = carte.getTaillePixelCaseCarte();
			frontiere = carte.getFrontiereCase();
			xPix = (pos.getX() - carte.getCarteAffichee().getExtHautGauche().getX()) * taillePix + carte.getxOrigineCarteAffichee() + taillePix / 2;
			yPix = (pos.getY() - carte.getCarteAffichee().getExtHautGauche().getY()) * taillePix + carte.getyOrigineCarteAffichee() + taillePix / 2;
		} else if (type == ELEMENT_MINI_MAP) {
			taillePix = carte.getTAILLE_PIXEL_CASE_MINI_MAP();
			frontiere = 1;
			xPix = pos.getX() * taillePix + X_MINI_MAP  + taillePix / 2;
			yPix = pos.getY() * taillePix + Y_MINI_MAP  + taillePix / 2;
		} else taillePix = frontiere = xPix = yPix = 0;
		if (visible == false) g.setColor(COULEUR_INCONNU);
		// Dessin
		// g.fillRect(xPix + frontiere, yPix + frontiere, taillePix - frontiere, taillePix - frontiere);
		dessinerHexagone(g, xPix, yPix, taillePix);
	}
	// Dessine l'élément avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, char type, Color couleurCadre) {
		int taillePix, frontiere,
		xPix, yPix;					// Coordoonnées de l'origine de la case représentant l'élément (en pixels)
	if (type == ELEMENT_CARTE) {
		taillePix = carte.getTaillePixelCaseCarte();
		frontiere = carte.getFrontiereCase();
		xPix = (pos.getX() - carte.getCarteAffichee().getExtHautGauche().getX()) * taillePix + carte.getxOrigineCarteAffichee() + taillePix / 2;
		yPix = (pos.getY() - carte.getCarteAffichee().getExtHautGauche().getY()) * taillePix + carte.getyOrigineCarteAffichee() + taillePix / 2;
	} else if (type == ELEMENT_MINI_MAP) {
		taillePix = carte.getTAILLE_PIXEL_CASE_MINI_MAP();
		frontiere = 1;
		xPix = pos.getX() * taillePix + X_MINI_MAP + taillePix / 2;
		yPix = pos.getY() * taillePix + Y_MINI_MAP + taillePix / 2;
	} else taillePix = frontiere = xPix = yPix = 0;
		// Dessin du cadre
		dessinerHexagone(g, couleurCadre, xPix, yPix, taillePix - frontiere);
		// Dessin de l'élément
		if (visible == false) g.setColor(COULEUR_INCONNU);
		dessinerHexagone(g, xPix, yPix, taillePix);
	}
	
	// Renvoie les infos de l'élément
	public String toString() {
		String nomClasse = this.getClass().getSimpleName(),
			   desc;
		desc = pos + " ";
		if (visible == true)
			desc += nomClasse;
		return desc;
	}
}
