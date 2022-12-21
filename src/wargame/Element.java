package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class Element implements IConfig {	
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
	

	int tabx[] = new int[6];
	int taby[] = new int[6];
	
	public void rempliTableauPoint(int[] tabx, int[] taby, int centrex, int centrey, int rayon, int nb_cote) {
		int x = centrex;
		int y = centrey;
		int n = nb_cote;
		int r = rayon;
		for(int i = 0; i < n; i++) {
			int xp = x;
			int yp = y;
			int dx = xp - x;
			int dy = yp - y;
			double a = Math.atan2(dy, dx);
			this.tabx[i]= (int) (x + ( r * Math.cos(a+(i+1)   *   2   *   Math.PI/n)  ));
			this.taby[i]= (int) (y + ( r * Math.sin(a+(i+1)   *   2   *   Math.PI/n) ));
		}

	}

	public void dessinerHexagone(Graphics g, int x, int y, int rayon) {
		/* faire un tableau de point x et y */



		rempliTableauPoint(this.tabx, this.taby, x, y, rayon, 6);
		/* appeler la methode qui dessine le polygone en fonction des tableaux */

		g.fillPolygon(this.tabx, this.taby, 6);


	}
	public void dessinerHexagone(Graphics g, Color c, int x, int y, int rayon) {
		/* faire un tableau de point x et y */



		rempliTableauPoint(this.tabx, this.taby, x, y, rayon, 6);
		/* appeler la methode qui dessine le polygone en fonction des tableaux */
		g.setColor(c);
		g.fillPolygon(this.tabx, this.taby, 6);


	}
	
	
	// Dessine l'élément sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		int taillePix, frontiere, decalageX, decalageY, rayon,
			xPix, yPix;					// Coordoonnées de l'origine de la case représentant l'élément (en pixels)
		if (type == ELEMENT_CARTE) {
			taillePix = carte.getTaillePixelCaseCarte();
			frontiere = carte.getFrontiereCase();
			xPix = (pos.getX() - carte.getCarteAffichee().getExtHautGauche().getX()) * taillePix + carte.getxOrigineCarteAffichee();
			yPix = (pos.getY() - carte.getCarteAffichee().getExtHautGauche().getY()) * taillePix + carte.getyOrigineCarteAffichee();
		} else if (type == ELEMENT_MINI_MAP) {
			taillePix = carte.getTAILLE_PIXEL_CASE_MINI_MAP();
			frontiere = 1;
			xPix = pos.getX() * taillePix + X_MINI_MAP;
			yPix = pos.getY() * taillePix + Y_MINI_MAP;
		} else taillePix = frontiere = xPix = yPix = 0;
		if (visible == false) g.setColor(COULEUR_INCONNU);
		
		rayon = taillePix / 2;
		decalageX = 0;
		decalageY = (pos.getX() % 2 == 0) ? 0 : rayon;
		
		// Dessin
		// g.fillRect(xPix + frontiere, yPix + frontiere, taillePix - frontiere, taillePix - frontiere);
		dessinerHexagone(g, xPix - decalageX, yPix + decalageY, rayon);
		// Affichage du nom du héros
		if (this instanceof Heros && type == ELEMENT_CARTE) {
			g.setColor(Color.black);
			g.setFont(new Font("Serif", Font.BOLD, taillePix + frontiere));
			g.drawString(((Heros)this).getNOM(), xPix + frontiere / 2, yPix + taillePix);
		}
	}
	// Dessine l'élément avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, char type, Color couleurCadre) {
		int taillePix, frontiere,
		xPix, yPix;					// Coordoonnées de l'origine de la case représentant l'élément (en pixels)
	if (type == ELEMENT_CARTE) {
		taillePix = carte.getTaillePixelCaseCarte();
		frontiere = carte.getFrontiereCase();
		xPix = (pos.getX() - carte.getCarteAffichee().getExtHautGauche().getX()) * taillePix + carte.getxOrigineCarteAffichee();
		yPix = (pos.getY() - carte.getCarteAffichee().getExtHautGauche().getY()) * taillePix + carte.getyOrigineCarteAffichee();
	} else if (type == ELEMENT_MINI_MAP) {
		taillePix = carte.getTAILLE_PIXEL_CASE_MINI_MAP();
		frontiere = 1;
		xPix = pos.getX() * taillePix + X_MINI_MAP;
		yPix = pos.getY() * taillePix + Y_MINI_MAP;
	} else taillePix = frontiere = xPix = yPix = 0;
		// Dessin du cadre
		dessinerHexagone(g, couleurCadre, xPix, yPix, taillePix);
		// dessinerRectangle(g, couleurCadre, xPix + frontiere, yPix + frontiere, taillePix - frontiere, taillePix - frontiere);
		// Dessin de l'élément
		if (visible == false) g.setColor(COULEUR_INCONNU);
		dessinerHexagone(g, couleurCadre, xPix, yPix, taillePix - frontiere);
		// g.fillRect(xPix + frontiere + 4, yPix + frontiere + 4, taillePix - frontiere - 8, taillePix - frontiere - 8);
		if (this instanceof Heros && type == ELEMENT_CARTE) {
			g.setColor(Color.black);
			g.setFont(new Font("Serif", Font.BOLD, taillePix + frontiere));
			g.drawString(((Heros)this).getNOM(), xPix + frontiere / 2, yPix + taillePix);
		}
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
