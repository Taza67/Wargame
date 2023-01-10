package wargame;

import java.awt.Color;


/**<b>IConfig est l'interface qui répertorie et initialise les variables liées aux couleurs des curseurs,
 * aux textures des personnages, et à leurs caractéristiques propres et de déplacement.</b>
* Elle est caractérisée par :
* <ul>
* <li>Une liste de processus.</li>
* </ul>
* @see AttaqueSoldatCorps
* @author AKIL M., BAYAZID H., AMIROUCHE A.
*/
public interface IConfig {
	
	
	/**
	 * Largeur de la minimap
	 */
	int LARGEUR_MINI_MAP = 300;
	
	
	/**
	 * Camp du personnage, géré avec un char, 'g' si c'est un gentil, 'm' si c'est un méchant.
	 */
	char GENTILS = 'g',
		 MECHANT = 'm';
	
	
	/**
	 * Variables liées aux couleurs de l'interface graphique, le texte, le curseur, les lignes et chemins ainsi que les cases non explorées.
	 */
	
	/**
	 * couleur du texte
	 */
	Color COULEUR_TEXTE = Color.black,
		 
			/**
			 * Couleur de la selection
			 */
		  COULEUR_SELECTION = Color.orange,
		  /**
		   * couleur du curseur de base
		   */
		  COULEUR_CURSEUR = Color.yellow,
		  /**
		   * couleur des lignes 
		   */
		  COULEUR_LIGNE = new Color(176, 196, 222),
		  /**
		   * couleur du chemin pour un déplacement
		   */
		  COULEUR_CHEMIN = new Color(64, 64, 90),
		  /**
		   * couleur des cases non explorées
		   */
		  COULEUR_VIDE = Color.darkGray;
	/**
	 * Texture des plaines
	 */
	int TEX_PLAINE = 0,
		TEX_MONTAGNE = 1,
		/**
		 * texture des collines
		 */
		TEX_COLLINE = 2,
		/**
		 * texture des villages
		 */
		TEX_VILLAGE = 3,
		/**
		 * texture des deserts
		 */
		TEX_DESERT = 4,
		/**
		 * Texture des forets
		 */
		TEX_FORET = 5,
		/**
		 * texture des rochers
		 */
		TEX_ROCHER = 6,
		/**
		 * Texture des zones d'eau
		 */
		TEX_EAU = 7,
		/**
		 * Textures nuages
		 */
		TEX_NUAGE = 8,
		/**
		 * texture des héros
		 */
		TEX_HEROS = 9,
		/**
		 * Texture des monstres
		 */
		TEX_MONSTRE = 10;
	/**
	 * type d'attaque corps à corps
	 */
	int CORPS_CORPS = 0,
			/**
			 * type d'attaque à distance 
			 */
			DISTANCE = 1;
	/**
	 * Caracteristique de guérison de base
	 */
	int GUERISON_MIN = 3,
			/**
			 * caracteristique de déplacement de base
			 */
			MOVE_MIN = 1,
			/**
			 * point de pouvoir
			 */
			POW_MIN = 4,
			/**
			 * point de tir
			 */
			TIR_MIN = 4,
			/**
			 *portée visuelle
			 */
			VISION_MIN = 1;
}
