package wargame;

import java.awt.Color;

public interface IConfig {
	// Mini-map
	int TAILLE_MAX_MINI_MAP = 300,			// en pixels
		X_MINI_MAP = 5,
		Y_MINI_MAP = 5;
	// Position de la fenêtre
	int POSITION_X = 100, POSITION_Y = 50;
	// Camps
	char VERTS = 'v', 	// Gentils
		 ROUGES = 'r';	// Méchants
	// Constantes statiques
	char ELEMENT_CARTE = 'c',
		 ELEMENT_MINI_MAP = 'm';
	// Couleurs
	Color COULEUR_VIDE = Color.darkGray, 
		  COULEUR_INCONNU = Color.lightGray,
		  COULEUR_TEXTE = Color.black,
		  // Vivants
		  COULEUR_MONSTRES = Color.black,
		  COULEUR_HEROS = Color.red, 
		  COULEUR_HEROS_DEJA_JOUE = Color.pink,
		  // Obstacles
		  COULEUR_EAU = Color.blue, 
		  COULEUR_FORET = Color.green, 
		  COULEUR_ROCHER = Color.gray,
		  // Sol
		  COULEUR_SOL = new Color(194, 178, 128),
		  // Interaction
		  COULEUR_SELECTION = Color.orange,
		  COULEUR_CURSEUR = Color.yellow;
}
