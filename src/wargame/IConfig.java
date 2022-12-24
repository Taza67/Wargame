package wargame;

import java.awt.Color;

public interface IConfig {
	// Mini-map
	int LARGEUR_MINI_MAP = 300;
	// Map
	int LARGEUR_MAP = 1501,
		HAUTEUR_MAP = 760;
	// Position de la fenêtre
	int POSITION_X = 100, POSITION_Y = 50;
	// Camps
	char GENTILS = 'g',
		 MECHANT = 'm';
	// Couleurs
	Color COULEUR_VIDE = Color.darkGray, 
		  COULEUR_INCONNU = Color.lightGray,
		  COULEUR_TEXTE = Color.black,
		  // Vivants
		  COULEUR_MONSTRES = Color.black,
		  COULEUR_HEROS = Color.red, 
		  COULEUR_HEROS_DEJA_JOUE = Color.pink,
		  // Obstacles
		  COULEUR_EAU = new Color(102, 205, 170), 
		  COULEUR_FORET = new Color(34, 139, 34), 
		  COULEUR_ROCHER = new Color(112, 128, 144),
		  // Sol
		  COULEUR_SOL = new Color(194, 178, 128),
		  // Interaction
		  COULEUR_SELECTION = Color.orange,
		  COULEUR_CURSEUR = Color.yellow,
		  COULEUR_LIGNE = new Color(176, 196, 222);
}
