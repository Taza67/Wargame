package wargame;

import java.awt.Graphics;

public class Carte implements IConfig, ICarte {
	// Infos
	Element[][] grilleCarte = new Element[HAUTEUR_CARTE][LARGEUR_CARTE];
	
	// Constructeurs
	public Carte() {
		for (int i = 0; i < HAUTEUR_CARTE; i++)
			for (int j = 0; j < LARGEUR_CARTE; j++)
				grilleCarte[i][j] = randomElement();
	}
	
	// Accesseurs
	// Renvoie l'élément à la position donné
	public Element getElement(Position pos) { return grilleCarte[pos.getY()][pos.getX()]; }
	
	// Méthodes
	// Crée aléatoirement des éléments
	Element randomElement() { return null; }
	// Trouve aléatoirement une position vide sur la carte
	public Position trouvePositionVide() {
		Position posElemVide = null;
		
		posElemVide = trouvePositionVideZone(0, HAUTEUR_CARTE - 1, 0, LARGEUR_CARTE); // cf Méthodes auxiliaires
		
		return posElemVide;
	}
	// Trouve une position vide choisie aleatoirement parmi les 8 positions adjacentes de pos
	public Position trouvePositionVide(Position pos) {
		int xPos = pos.getX(),		// Indice de colonne de pos
			yPos = pos.getY(),		// Indice de ligne de pos
			deb_ligne = yPos - 1, 	// Indice début ligne
			deb_colonne = xPos - 1, // Indice début colonne
			fin_ligne = yPos + 1, 	// Indice fin ligne
			fin_colonne = xPos + 1; // Indice fin colonne
		Position posElemVide = null;
		
		posElemVide = trouvePositionVideZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne); // cf Méthodes auxiliaires
		
		return posElemVide;
	}
	
	// Méthodes auxiliaires
	// Renvoie un nombre aléatoire compris entre inf et sup
	public static int retourneAlea(int inf, int sup) {
		return inf + (int)(Math.random() * ((sup - inf) + 1)); 
	}
	// Trouve aléatoirement une position vide dans une zone donnée de la carte
	public Position trouvePositionVideZone(int deb_ligne, int fin_ligne, int deb_colonne, int fin_colonne) {
		Element elemVide = null;
		Position posElemVide = null;
		
		do {
			int x = retourneAlea(deb_colonne, fin_colonne),
				y = retourneAlea(deb_ligne, fin_ligne);
			
			elemVide = grilleCarte[y][x];
			posElemVide = elemVide.pos;
		} while (elemVide != null); // Tant qu'un élément vide n'a pas été trouvé
		
		return posElemVide;
	}
}
