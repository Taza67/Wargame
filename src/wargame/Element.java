package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

import wargameInterface.Fenetre;
import wargameInterface.PanneauPartie;





/**
 * <b>Element est la classe responsable de la gestion des éléments hexagonaux de la carte.</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>La carte qui contient cet élément.</li>
 * <li>La position de cet élément dans la grille.</li>
 * </ul>
 * </p>
 * @see Carte
 * @see Hexagone
 * @see Position
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */




public abstract class Element implements IConfig, Serializable {
	private static final long serialVersionUID = -2180721218940141556L;
	
	/**
	 * Les indices dans le tableau infos de la methode getStringInfos().
	 * @see getStringInfos()
	 */
	private static final int POS = 0, TYPE = 1, SOL = 2, PDV = 3, DEP = 4, VISUEL = 5, POW = 6, TIR = 7;
	
	/**
	 * La carte qui contient l'élément.
	 * @see Carte#setElement(Position, Element)
	 * @see Carte#calculerHex()
	 */	
	protected Carte carte;
	
	/**
	 * La position d'un élément dans la carte.
	 * @see Carte#deplacer(Position)
	 * @see Carte#trouvePosVide()
	 * @see Carte#trouvePosType(int, int, int, int, char)
	 */
	protected Position pos;
	
	/**
	 * Hexagone de la map affichée.
	 * @see Element#creerHexM()
	 * @see Carte#calculerHex()
	 */
	protected Hexagone hex;
	
	/**
	 * Hexagone de la mini-map.
	 * @see Element#creerHexMM()
	 */
	protected Hexagone hexMM;
	
	/**
	 * Visibilité d'un hexagone dans la carte.
	 * @see Element#seDessinerBis(Hexagone, Graphics2D)
	 * @see Element#seDessinerCadreBis(Hexagone, Graphics2D, Color)
	 */
	protected boolean visible = false;
	
	/**
	 * Type de la texture d'un élément.
	 * @see Element#seDessinerBis(Hexagone, Graphics2D)
	 * @see Element#seDessinerCadreBis(Hexagone, Graphics2D, Color)
	 */
	protected int numTexture;
	
	 /**
     * Retourne la position d'un élément.
     * 
     * @return position d'un élément. 
     * @see Element#creerHexM()
     * @see Element#creerHexMM()
     */
	public Position getPos() { return pos; }
	
	/**
	 * Modifie la position de l'élement.
	 * 
	 * @param pos
	 * @see Obstacle#Obstacle(Carte, wargame.Obstacle.TypeObstacle, Position)
	 * @see Sol#Sol(Carte, wargame.Sol.TypeSol, Position)
	 */
	public void setPos(Position pos) { this.pos = pos; }
	
	
	/**
	 * Crée les deux hexagones.
	 * 
	 * @see Soldat#seDeplace(Position)
	 * @see Soldat#Soldat(Carte, Position, int, int, int, int, int)
	 */
	public void creerHex() {
		creerHexM();
		creerHexMM();
	}
	
	/**
	 * Crée l'hexagone de la map affichée.
	 * 
	 * @see Carte#calculerHex()
	 * @see Soldat#seDeplace(Position)
	 */
	public void creerHexM() {
		int rayon;
		Point centre;
		rayon = carte.getRayonHex();
		centre = getPos().substract(carte.getMapAff().getUpLeft()).toPositionAxiale().toPoint(rayon, carte.getOrigine());
		if (carte.getMapAff().getUpLeft().getY() % 2 != 0 && getPos().getY() % 2 == 0)
			centre = centre.substract(new Point(Math.sqrt(3) * rayon, 0));
		hex = new Hexagone(centre, rayon);
	}

	/**
	 * Renvoie les infos sur la position.
	 * 
	 */
	public String toString() {
		return getPos().toString();
	}
	
	/**
	 * Crée l'hexagone de la mini-map.
	 * 
	 */ 
	public void creerHexMM() {
		int rayon;
		Point centre;
		rayon = carte.getRayonMM();
		centre = getPos().toPositionAxiale().toPoint(rayon, carte.getOrigineMM());
		hexMM = new Hexagone(centre, rayon);
	}
	
	/**
	 * Dessine l'hexagone passé en paramètre.
	 * 
	 * @param h
	 * 		l'hexagone à dessiner
	 * @param g
	 * 		Graphics 2D 
	 * @see Element#seDessiner(Graphics2D)
	 * @see Element#seDessinerCadreMM(Graphics2D, Color)
	 */
	public void seDessinerBis(Hexagone h, Graphics2D g) {
		int numT = numTexture;
		if (visible == false) numT = TEX_NUAGE;
		h.seDessiner(g, PanneauPartie.texturesPaint[numT]);
	}
	
	/**
	 * Dessine l'élément.
	 * 
	 * @param g
	 * 		Graphics2D
	 */
	public void seDessiner(Graphics2D g) {
		seDessinerBis(hex, g);
	}
	
	/**
	 *  Dessine l'élément dans la mini-map.
	 * 
	 * @param g
	 * 		Graphics2D 
	 */
	public void seDessinerMM(Graphics2D g) {
		seDessinerBis(hexMM, g);
	}
	
	/**
	 * Dessine l'hexagone passé en paramètre avec un cadre.
	 * 
	 * @param h
	 * 		hexagone à dessiner
	 * @param g
	 * 		Graphics2D
	 * @param cadre
	 * 		couleur du cadre
	 */
	public void seDessinerCadreBis(Hexagone h, Graphics2D g, Color cadre) {
		int rayon = h.getRayon(),
			numT = numTexture;
		if (visible == false) numT = TEX_NUAGE;
		h.seDessiner(g, PanneauPartie.texturesPaint[numT]);
		
		h.setRayon((int)(rayon * 0.75));
		g.setColor(cadre);
		h.seDessiner(g);
		
		h.setRayon((int)(rayon * 0.5));
		h.seDessiner(g, PanneauPartie.texturesPaint[numT]);
		h.setRayon(rayon);
	}
	
	/**
	 * Dessine l'élément avec un cadre
	 * 
	 * @param g
	 * 		Graphics2D
	 * @param cadre
	 * 		Couleur du cadre
	 */
	public void seDessinerCadre(Graphics2D g, Color cadre) {
		seDessinerCadreBis(hex, g, cadre);
	}
	
	/**
	 * Dessine l'élément avec un cadre dans la mini-map
	 * 
	 * @param g
	 * 		paramètre Graphics2D pour dessiner
	 * @param cadre
	 * 		couleur du cadre
	 * @see Element#seDessinerCadre(Graphics2D, Color)
	 */
	public void seDessinerCadreMM(Graphics2D g, Color cadre) {
		seDessinerCadreBis(hexMM, g, cadre);
	}
	
	/**
	 * Dessine un texte centré au sein d'un hexagone
	 * 
	 * @param g
	 * 		Graphics2D
	 * @param text
	 * 		chaine de caractère à écrire
	 * @param font
	 * 		font de la chaine à écrire
	 * @see Heros#seDessiner(Graphics2D)
	 */
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
	
	/**
	 * Renvoie les infos sur un élément sous forme de chaines de caracteres
	 * 
	 * @see Element#dessinerInfoBulle
	 * 
	 */
	public String[] getStringInfos() {
		int n = 1;
		String infos[];
		if (visible == true) {
			n++;
			if (this instanceof Soldat) n += 6;
		}
		infos = new String[n];
		// Insertion des infos
		infos[POS] = "Position : " + getPos();
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
	
	/**
	 * Renvoie la shape Rectangle de l'infobulle
	 * 
	 * @param metrics
	 * 		FontMetrics
	 * @param nbInfos
	 * 		nombre d'informations à écrire
	 * @see Element#dessinerInfoBulle(Graphics2D)
	 */
	public RoundRectangle2D shapeInfoBulle(FontMetrics metrics, int nbInfos) {
		int rayon;
		double x, y, larg, haut;
		Point centre;
		// Calcul du centre, coordonnées de départ de l'infobulle
		rayon = carte.getRayonHex();
		centre = getPos().substract(carte.getMapAff().getUpLeft()).toPositionAxiale().toPoint(rayon, carte.getOrigine());
		if (carte.getMapAff().getUpLeft().getY() % 2 != 0 && getPos().getY() % 2 == 0)
			centre = centre.substract(new Point(Math.sqrt(3) * rayon, 0));
		// Calcul des dimensions
		larg = 250 + 10;
		haut = nbInfos * (metrics.getHeight() + 5) + 15;
		x = centre.getX();
		y = centre.getY();
		if (x + larg > Carte.largeurMap) x -= larg;
		if (y + haut > Carte.hauteurMap) y -= haut;
		// Retour
		return new RoundRectangle2D.Double(x, y, larg, haut, 10, 10);
	}
	
	/**
	 * Dessiner l'info-bulle
	 * 
	 * @param g
	 * 		Graphics2D
	 * @see Carte#seDessiner(Graphics2D)
	 * @see Element#shapeInfoBulle(FontMetrics, int)
	 */
	public void dessinerInfoBulle(Graphics2D g) {
		g.setFont(new Font("Courier", Font.BOLD, 13));
		double x, y, larg, haut;
		boolean croisementInfobulles = false;
		String infos[] = getStringInfos();
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		RoundRectangle2D r = shapeInfoBulle(metrics, infos.length);
		if (carte.getCurseur() != null && !carte.getCurseur().getPos().equals(this.getPos())) {
			RoundRectangle2D rCurseur = carte.getCurseur().shapeInfoBulle(metrics, carte.getCurseur().getStringInfos().length);
			croisementInfobulles = r.intersects(rCurseur.getBounds2D());
		}
		x = r.getX();
		y = r.getY();
		larg = r.getWidth();
		haut = r.getHeight();
		
		if (carte.getCurseur() != null && !carte.getCurseur().getPos().equals(this.getPos()) && carte.getCurseur().estDansShape(r)) return;
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
	
	/**
	 * Vérifie si l'élément en fonction de sa position dans la carte affichée est dans une shape
	 * 
	 * @param s
	 * 		Shape de java.awt.Shape
	 * @see Element#dessinerInfoBulle(Graphics2D)
	 */
	public boolean estDansShape(Shape s) {
		Rectangle2D r = hex.toPolygon().getBounds2D();
		return s.intersects(r);
	}
	
	/**
	 * Renvoie le type de l'élément sous forme de chaine de caractère.
	 * 
	 */
	protected abstract String getStringType();
}
