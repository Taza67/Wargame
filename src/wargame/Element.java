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
	// Dessine l'élément sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
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
		if (visible == false) g.setColor(COULEUR_INCONNU);
		// Dessin
		g.fillRect(xPix + frontiere, yPix + frontiere, taillePix - frontiere, taillePix - frontiere);
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
		dessinerRectangle(g, couleurCadre, xPix + frontiere, yPix + frontiere, taillePix - frontiere, taillePix - frontiere);
		// Dessin de l'élément
		if (visible == false) g.setColor(COULEUR_INCONNU);
		g.fillRect(xPix + frontiere + 4, yPix + frontiere + 4, taillePix - frontiere - 8, taillePix - frontiere - 8);
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
