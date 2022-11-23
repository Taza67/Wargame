package wargame;
import java.awt.Color;

public interface IConfig {
	// Paramètres du jeu
	int LARGEUR_CARTE = 25, HAUTEUR_CARTE = 15; // Dimensions de la carte en nombre de cases
	// Taille d'une case
	int NB_PIX_CASE = 20;
	// Position de la fênetre
	int POSITION_X = 100, POSITION_Y = 50;
	// Limites
	int NB_HEROS = 6; int NB_MONSTRES = 15; int NB_OBSTACLES = 20;
	// Couleurs
	Color COULEUR_VIDE = Color.white, 
	 	  COULEUR_INCONNU = Color.lightGray,
	 	  COULEUR_TEXTE = Color.black,
	 	  COULEUR_MONSTRES = Color.black,
	 	  COULEUR_HEROS = Color.red, 
	 	  COULEUR_HEROS_DEJA_JOUE = Color.pink,
	 	  COULEUR_EAU = Color.blue, 
	 	  COULEUR_FORET = Color.green, 
	 	  COULEUR_ROCHER = Color.gray;
}