package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class Element implements IConfig {	
	
	// Infos
	protected Carte carte;
	protected Position pos;
	protected Hexagone hexMap, hexMiniMap;
	protected boolean visible = false;							// Élément visible pour le joueur
	
	// Méthodes
	public void creerHexagone(char type) {
		int frontiere, rayon, decalageY, xPix, yPix, haut, larg, horiz, vert;
		Point centre, unSommet;
		if (type == ELEMENT_CARTE) {
			frontiere = carte.getFrontiere();
			larg = carte.getDiametreHexagone();
			rayon = (int)(larg / 2.0);
			horiz = (int)(3 * larg / 4.0);
			vert = haut = (int)(Math.sqrt(3) * rayon);
			
			xPix = (pos.getX() - carte.getCarteAffichee().getExtHautGauche().getX()) * horiz + carte.getxOrigineCarteAffichee();
			yPix = (pos.getY() - carte.getCarteAffichee().getExtHautGauche().getY()) * vert + carte.getyOrigineCarteAffichee();
		} else {
			frontiere = 1;
			larg = carte.getTAILLE_PIXEL_CASE_MINI_MAP();
			rayon = (int)(larg / 2.0);
			horiz = (int)(3 * larg / 4.0);
			vert = haut = (int)(Math.sqrt(3) * rayon);
			
			xPix = pos.getX() * horiz + X_MINI_MAP;
			yPix = pos.getX() * vert + Y_MINI_MAP;
		}
		decalageY = (pos.getX() % 2 == 0) ? 0 : vert / 2;
		yPix += rayon + decalageY;
		xPix += rayon;	
		centre = new Point(xPix, yPix);
		unSommet = new Point(xPix + rayon - frontiere, yPix);	
		if (type == ELEMENT_CARTE) hexMap = new Hexagone(centre, unSommet);
		else if (type == ELEMENT_MINI_MAP) hexMiniMap = new Hexagone(centre, unSommet);
	}
	
	// Méthodes graphiques
	// Dessine l'élément sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		creerHexagone(type);
		if (visible == false) g.setColor(COULEUR_INCONNU);
		if (type == ELEMENT_CARTE) hexMap.seDessiner(g);
		else if (type == ELEMENT_MINI_MAP) hexMiniMap.seDessiner(g);
	}
	
	// Dessine l'élément avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, char type, Color couleurCadre) {
		if (type == ELEMENT_CARTE)
			hexMap.seDessiner(g);
		else if (type == ELEMENT_MINI_MAP)
			hexMiniMap.seDessiner(g);
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
