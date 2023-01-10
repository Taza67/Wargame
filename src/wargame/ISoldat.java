package wargame;




/**<b>ISoldat est l'interface relative aux soldats en général</b>
* Elle est caractérisée par :
* <ul>
* <li>Une énumération du type de soldat(humain, nain, elfe, hobbit), avec leurs caractéristiques respectives</li>
* <li>Une variable de points de vie(int) </li>
* <li>Une variable de portée visuelle(int)</li>
* <li>Une variable de portée de déplacement(int)</li>
* <li>Une variable qui indique la puissance(int)</li>
* <li>Une variable qui indique le tir(int)</li>
* <li>Une variable qui indique le camp de l'unité</li>
* </ul>
* @author AKIL M., BAYAZID H., AMIROUCHE A.
*/
public interface ISoldat extends IConfig {
	
	
	public static enum TypesH {
		/**
		 * Enumeration des types de personnages avec leurs caractéristiques respectives
		 */
		HUMAIN(40, 4, 4, 10, 2), NAIN(80, 2, 3, 20, 0), ELF(70, 6, 5, 10, 6), HOBBIT(20, 2, 2, 5, 2);
		
		/*
		 * Points de vie
		 */
		private final int POINTS_DE_VIE, 
		/**
		 * Portée visuelle
		 */
		PORTEE_VISUELLE, 
		/**
		 * Portée de déplacement
		 */
		PORTEE_DEPLACEMENT, 
		/**
		 * Puissance
		 */
		PUISSANCE, 
		/**
		 * Tir
		 */
		TIR;
		 
		/**
		 * champ relatif au camp de l'unité
		 */
		private final char CAMP;
		
		/**
		 * Constructeur de type 
		 * @param points
		 * @param porteeVisuelle
		 * @param porteeDeplacement
		 * @param puissance
		 * @param tir
		 */
		TypesH(int points, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = porteeVisuelle;
			PORTEE_DEPLACEMENT = porteeDeplacement;
			PUISSANCE = puissance; 
			TIR = tir;
			CAMP = GENTILS;
		}
		
		/**
		 * Accesseur des points de vie
		 * @return points de vie(int)
		 */
		public int getPoints() { return POINTS_DE_VIE; }
		/**
		 * Accesseur de la portée visuelle
		 * @return portée visuelle(int)
		 */
		public int getPorteeVisuelle() { return PORTEE_VISUELLE; }
		/**
		 * accesseur de la portée de déplacement
		 * @return portée de déplacement(int)
		 */
		public int getPorteeDeplacement() { return PORTEE_DEPLACEMENT; }
		/**
		 * Accesseur de la puissance
		 * @return puissance (int)
		 */
		public int getPuissance() { return PUISSANCE; }
		
		/**
		 * Accesseur du tir
		 * @return tir (int)
		 */
		public int getTir() { return TIR; }
		/**
		 * Accesseur du camp de l'unité
		 * @return return camp (char 'g'/'m')
		 */
		public char getCAMP() { return CAMP; }
		/**
		 * Pseudo-accesseur,
		 * @return un type aléatoire
		 */
		public static TypesH getTypeHAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	
	public static enum TypesM {	
		/**
		 * Enumération des types de personnages ennemis
		 */	
		TROLL(100, 2, 4, 30, 0), ORC(40, 3, 5, 10, 3), GOBELIN(20, 3, 3, 5, 2);		
		/*
		 * Points de vie
		 */
		private final int POINTS_DE_VIE, 
		/**
		 * Portée visuelle
		 */
		PORTEE_VISUELLE, 
		/**
		 * Portée de déplacement
		 */
		PORTEE_DEPLACEMENT, 
		/**
		 * Puissance
		 */
		PUISSANCE, 
		/**
		 * Tir
		 */
		TIR;
		 
		/**
		 * champ relatif au camp de l'unité
		 */
		private final char CAMP;
		
		/**
		 * Constructeur de type 
		 * @param points
		 * @param porteeVisuelle
		 * @param porteeDeplacement
		 * @param puissance
		 * @param tir
		 */
		TypesM(int points, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = porteeVisuelle;
			PORTEE_DEPLACEMENT = porteeDeplacement;
			PUISSANCE = puissance; 
			TIR = tir;
			CAMP = MECHANT;
		}
		
		/**
		 * Accesseur des points de vie
		 * @return points de vie(int)
		 */
		public int getPoints() { return POINTS_DE_VIE; }
		/**
		 * Accesseur de la portée visuelle
		 * @return portée visuelle(int)
		 */
		public int getPorteeVisuelle() { return PORTEE_VISUELLE; }
		/**
		 * accesseur de la portée de déplacement
		 * @return portée de déplacement(int)
		 */
		public int getPorteeDeplacement() { return PORTEE_DEPLACEMENT; }
		/**
		 * Accesseur de la puissance
		 * @return puissance (int)
		 */
		public int getPuissance() { return PUISSANCE; }
		
		/**
		 * Accesseur du tir
		 * @return tir (int)
		 */
		public int getTir() { return TIR; }
		/**
		 * Accesseur du camp de l'unité
		 * @return return camp (char 'g'/'m')
		 */
		public char getCAMP() { return CAMP; }
		/**
		 * Pseudo-accesseur,
		 * @return un type aléatoire
		 */
		public static TypesM getTypeMAlea() {
         return values()[(int)(Math.random()*values().length)];
		}
	}


}
