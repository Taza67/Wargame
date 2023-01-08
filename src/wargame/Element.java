package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public abstract class Element implements IConfig {
	// Constantes statiques
	private static final int POS = 0, TYPE = 1, SOL = 2, PDV = 3, DEP = 4, VISUEL = 5, POW = 6, TIR = 7;
	
	// Infos
	protected Carte carte;
	protected Position pos;
	protected Hexagone hex, hexMM;
	protected boolean visible = false;
	protected int numTexture;
	
	// Méthodes
	// Crée les deux hexagones
	public void creerHex() {
		creerHexM();
		creerHexMM();
	}
	// Crée l'hexagone de la map affichée
	public void creerHexM() {
		int rayon;
		Point centre;
		rayon = carte.getRayonHex();
		centre = pos.substract(carte.getMapAff().getUpLeft()).toPositionAxiale().toPoint(rayon, carte.getOrigine());
		if (carte.getMapAff().getUpLeft().getY() % 2 != 0 && pos.getY() % 2 == 0)
			centre = centre.substract(new Point(Math.sqrt(3) * rayon, 0));
		hex = new Hexagone(centre, rayon);
	}
	// Renvoie les infos
	public String toString() {
		return pos.toString();
	}
	
	// Méthodes graphiques
	// Crée l'hexagone de la mini-map
	public void creerHexMM() {
		int rayon;
		Point centre;
		rayon = carte.getRayonMM();
		centre = pos.toPositionAxiale().toPoint(rayon, carte.getOrigineMM());
		hexMM = new Hexagone(centre, rayon);
	}
	// Dessine l'hexagone passé en paramètre
	public void seDessinerBis(Hexagone h, Graphics2D g) {
		int numT = numTexture;
		if (visible == false) numT = TEX_NUAGE;
		h.seDessiner(g, carte.texturesPaint[numT]);
	}
	// Dessine l'élément
	public void seDessiner(Graphics2D g) {
		seDessinerBis(hex, g);
	}
	// Dessine l'élément dans la mini-map
	public void seDessinerMM(Graphics2D g) {
		seDessinerBis(hexMM, g);
	}
	// Dessine l'hexagone passé en paramètre avec un cadre
	public void seDessinerCadreBis(Hexagone h, Graphics2D g, Color cadre) {
		int rayon = h.getRayon(),
			numT = numTexture;
		if (visible == false) numT = TEX_NUAGE;
		h.seDessiner(g, carte.texturesPaint[numT]);
		
		h.setRayon((int)(rayon * 0.75));
		g.setColor(cadre);
		h.seDessiner(g);
		
		h.setRayon((int)(rayon * 0.5));
		h.seDessiner(g, carte.texturesPaint[numT]);
		h.setRayon(rayon);
	}
	// Dessine l'élément avec un cadre
	public void seDessinerCadre(Graphics2D g, Color cadre) {
		seDessinerCadreBis(hex, g, cadre);
	}
	// Dessine l'élément avec un cadre dans la mini-map
	public void seDessinerCadreMM(Graphics2D g, Color cadre) {
		seDessinerCadreBis(hexMM, g, cadre);
	}
	// Dessine un texte centré au sein d'un hexagone
	public void drawCenteredString(Graphics2D g, String text, Font font) {
		int larg, haut, rayon,
			x, y;
		Point centre;
		rayon = carte.getRayonHex();
		larg = (int)(Math.sqrt(3) * rayon);
		haut = 2 * rayon;
		centre = hex.getCentre();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X-Y coordinates for the text
	    x = (int)(centre.getX() - larg / 2);
	    y = (int)(centre.getY() - haut / 2);
	    x = x + (larg - metrics.stringWidth(text)) / 2;
	    y = y + ((haut - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	// Renvoie les infos sur un élément sous forme de chaines de caracteres
	public String[] getStringInfos() {
		int n = 1;
		String infos[];
		if (visible == true) {
			n++;
			if (this instanceof Soldat) n += 6;
		}
		infos = new String[n];
		// Insertion des infos
		infos[POS] = "Position : " + pos;
		if (visible == true) {
			infos[TYPE] = "Élément : " + this.getStringType();
			if (this instanceof Soldat) {
				infos[SOL] = "Sol : " + ((Soldat)this).getSol().getStringType();
				infos[PDV] = "Points de vie : " + ((Soldat)this).getStringPdv();
				infos[DEP] = "Portee de déplacement : " + ((Soldat)this).getStringDep();
				infos[VISUEL] = "Portée visuelle : " + ((Soldat)this).getStringVisuel();
				infos[POW] = "Puissance : " + ((Soldat)this).getStringPow();
				infos[TIR] = "Puissance de tir : " + ((Soldat)this).getStringTir();
			}
		}
		return infos;
	}
	// Renvoie la shape Rectangle de l'infobulle
	public RoundRectangle2D shapeInfoBulle(FontMetrics metrics, int nbInfos) {
		int rayon;
		double x, y, larg, haut;
		Point centre;
		// Calcul du centre, coordonnées de départ de l'infobulle
		rayon = carte.getRayonHex();
		centre = pos.substract(carte.getMapAff().getUpLeft()).toPositionAxiale().toPoint(rayon, carte.getOrigine());
		if (carte.getMapAff().getUpLeft().getY() % 2 != 0 && pos.getY() % 2 == 0)
			centre = centre.substract(new Point(Math.sqrt(3) * rayon, 0));
		// Calcul des dimensions
		larg = 200 + 10;
		haut = nbInfos * (metrics.getHeight() + 5) + 10;
		x = centre.getX();
		y = centre.getY();
		if (x + larg > carte.LARGEUR_MAP) x -= larg;
		if (y + haut > carte.HAUTEUR_MAP) y -= haut;
		// Retour
		return new RoundRectangle2D.Double(x, y, larg, haut, 10, 10);
	}
	// Dessiner l'info-bulle
	public void dessinerInfoBulle(Graphics2D g) {
		double x, y, larg, haut;
		boolean croisementInfobulles = false;
		String infos[] = getStringInfos();
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		RoundRectangle2D r = shapeInfoBulle(metrics, infos.length);
		if (carte.getCurseur() != null && !carte.getCurseur().pos.equals(this.pos)) {
			RoundRectangle2D rCurseur = carte.getCurseur().shapeInfoBulle(metrics, carte.getCurseur().getStringInfos().length);
			croisementInfobulles = r.intersects(rCurseur.getBounds2D());
		}
		x = r.getX();
		y = r.getY();
		larg = r.getWidth();
		haut = r.getHeight();
		
		if (carte.getCurseur() != null && !carte.getCurseur().pos.equals(this.pos) && carte.getCurseur().estDansShape(r)) return;
		if (croisementInfobulles) return;
		g.setColor(Color.gray);
		g.fill(r);
		g.setColor(Color.black);
		g.draw(r);
		
		x += 5;
		y += 5;
		r = new RoundRectangle2D.Double(x, y, larg - 10, haut - 10, 10, 10);
		g.setColor(Color.darkGray);
		g.fill(r);
		g.setColor(Color.black);
		g.draw(r);
		
		g.setColor(Color.white);
		
		y += metrics.getHeight();
		x += 5;
		for (int i = 0; i < infos.length; i++) {
			g.drawString(infos[i], (int)x, (int)y);
			y += metrics.getHeight() + 5;
		}
	}
	// Vérifie si l'élément en fonction de sa position dans la carte affichée est dans une shape
	public boolean estDansShape(Shape s) {
		Rectangle2D r = hex.toPolygon().getBounds2D();
		return s.intersects(r);
	}
	
	// Méthodes abstraites
	protected abstract String getStringType();
}
