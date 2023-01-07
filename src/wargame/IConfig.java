package wargame;

import java.awt.Color;

public interface IConfig {
	// Mini-map
	int LARGEUR_MINI_MAP = 300;
	// Camps
	char GENTILS = 'g',
		 MECHANT = 'm';
	// Couleurs
	Color COULEUR_TEXTE = Color.black,
		  // Interaction
		  COULEUR_SELECTION = Color.orange,
		  COULEUR_CURSEUR = Color.yellow,
		  COULEUR_LIGNE = new Color(176, 196, 222),
		  COULEUR_CHEMIN = new Color(64, 64, 90),
		  COULEUR_VIDE = Color.darkGray;
	// Textures
	int TEX_PLAINE = 0,
		TEX_MONTAGNE = 1,
		TEX_COLLINE = 2,
		TEX_VILLAGE = 3,
		TEX_DESERT = 4,
		TEX_FORET = 5,
		TEX_ROCHER = 6,
		TEX_EAU = 7,
		TEX_NUAGE = 8,
		TEX_HEROS = 9,
		TEX_MONSTRE = 10;
	// Type de l'attaque
	int CORPS_CORPS = 0, DISTANCE = 1;
	// Soldats
	int GUERISON_MIN = 3,
			MOVE_MIN = 1,
			POW_MIN = 4,
			TIR_MIN = 4,
			VISION_MIN = 1;
}
