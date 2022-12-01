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
	
	// Méthodes
	// Méthodes graphiques
	// Dessine un rectangle plein de dimensions et couleur données
	public void dessinerRectangle(Graphics g, Color c, int x, int y, int larg, int haut) {
		g.setColor(c);
		g.fillRect(x, y, larg, haut);
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
			dessinerRectangle(g, COULEUR_CURSEUR, xPix, yPix, taillePix + frontiere, taillePix + frontiere);
		// Selectionné ?
		if (selectionne == true)
			dessinerRectangle(g, COULEUR_SELECTION, xPix, yPix, taillePix + frontiere, taillePix + frontiere);
		// Reprise de la couleur initiale si élément visible
		if (visible == false) g.setColor(COULEUR_INCONNU); 
		else g.setColor(couleurCourante);
		// Dessin
		g.fillRect(xPix + frontiere, yPix + frontiere, taillePix - frontiere, taillePix - frontiere);
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
