package wargame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Element implements IConfig {
	// Constantes statiques
	public static final char ELEMENT_CARTE = 'c',
							 ELEMENT_MINI_MAP = 'm';
	
	// Infos
	protected Carte carte;
	protected Position pos;
	protected boolean visible = false;							// Élément visible pour le joueur
	protected boolean selectionne = false;						// Élément sélectionné par le joueur
	protected boolean curseurDessus = false;					// Curseur du joueur au dessus de l'élément
	protected boolean affichageCurseurMiniMapFini = false,
					  affichageCurseurCarte = false;
	
	int tabx[] = new int[6];
	int taby[] = new int[6];
	// Méthodes
	// Méthodes graphiques
	// Dessine un rectangle plein de dimensions et couleur données
	public void dessinerRectangle(Graphics g, Color c, int x, int y, int larg, int haut) {
		g.setColor(c);
		g.fillRect(x, y, larg, haut);
	}
	
	public void rempliTableauPoint(int[] tabx, int[] taby, int centrex, int centrey, int rayon, int nb_cote) {
		int x= centrex;
		int y=centrey;
		int n= nb_cote;
		int r = rayon;
		for(int i =0; i<n; i++) {
			
			int xp = x+rayon;
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
		
		g.drawPolygon(this.tabx, this.taby, 6);
		
		
	}
	public void dessinerHexagone(Graphics g, Color c, int x, int y, int rayon) {
		/* faire un tableau de point x et y */
		
		
		
		rempliTableauPoint(this.tabx, this.taby, x, y, rayon, 6);
		/* appeler la methode qui dessine le polygone en fonction des tableaux */
		g.setColor(c);
		g.drawPolygon(this.tabx, this.taby, 6);
		
		
	}
	// Dessine l'élément sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		int taillePix, frontiere,
			xPix, yPix;					// Coordoonnées de l'origine de la case représentant l'élément (en pixels)
		if (type == ELEMENT_CARTE) {
			taillePix = carte.getTaillePixelCaseCarte();
			frontiere = carte.getFrontiereCase();
			xPix = (pos.getX() - carte.getCarteAffichee().getExtHautGauche().getX()) * taillePix + carte.getxOrigineCarteAffichee();
			yPix = (pos.getY() - carte.getCarteAffichee().getExtHautGauche().getY()) * taillePix + carte.getyOrigineCarteAffichee();
			affichageCurseurCarte = true;
		} else if (type == ELEMENT_MINI_MAP) {
			taillePix = carte.getTAILLE_PIXEL_CASE_MINI_MAP();
			frontiere = 1;
			xPix = pos.getX() * taillePix + X_MINI_MAP;
			yPix = pos.getY() * taillePix + Y_MINI_MAP;
			affichageCurseurMiniMapFini = true;
		} else taillePix = frontiere = xPix = yPix = 0;
		// On stocke la couleur => reprise plus tard
		Color couleurCourante = g.getColor();
		// Curseur dessus ?
		if (curseurDessus == true && selectionne == false)
			//dessinerRectangle(g, COULEUR_CURSEUR, xPix, yPix, taillePix + frontiere, taillePix + frontiere);
			dessinerHexagone(g, COULEUR_CURSEUR, xPix, yPix, taillePix);
	
		// Selectionné ?
		if (selectionne == true)
			//dessinerRectangle(g, COULEUR_SELECTION, xPix, yPix, taillePix + frontiere, taillePix + frontiere);
			
			dessinerHexagone(g, COULEUR_CURSEUR, xPix, yPix, taillePix);
		// Reprise de la couleur initiale si élément visible
		if (visible == false) g.setColor(COULEUR_INCONNU); 
		else g.setColor(couleurCourante);
		// Dessin
		//g.fillRect(xPix + frontiere, yPix + frontiere, taillePix - frontiere, taillePix - frontiere);
		dessinerHexagone(g,xPix, yPix, taillePix);
		if (affichageCurseurMiniMapFini && affichageCurseurCarte) {
			curseurDessus = false;
			affichageCurseurMiniMapFini = affichageCurseurCarte = false;
		}
	}
	// Renvoie les infos de l'élément
	public String toString() {
		String nomClasse = this.getClass().getSimpleName(),
			   desc = nomClasse;
		desc += " : " + pos + " ";
		return desc;
	}
}
