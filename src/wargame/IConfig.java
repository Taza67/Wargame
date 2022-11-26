package wargame;

import java.awt.Color;

public interface IConfig {
	// Dimensions de la carte
	int LARGEUR_CARTE = 25, HAUTEUR_CARTE = 15; // en nombre de cases
	// Taille d'une case (Carte, mini-map)
	int NB_PIX_CASE = 50,
		NB_PIX_CASE_MINI_MAP = 10;
	// Position de la fenêtre
	int POSITION_X = 100, POSITION_Y = 50;
	// Limites
	int NB_HEROS = 6, 
		NB_MONSTRES = 15, 
		NB_OBSTACLES = 20;
	// Camps
	char VERTS = 'v', 	// Gentils
		 ROUGES = 'r';	// Méchants
	// Couleurs
	Color COULEUR_VIDE = Color.white, 
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
		  COULEUR_SOL = new Color(194, 178, 128);
	// Identifiants mini-map, carte
	char CARTE = 'c', 	// carte
		 MINI_MAP = 'm'; // mini-map 
}
