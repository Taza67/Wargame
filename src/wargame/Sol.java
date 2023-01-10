package wargame;




/**
 * <b>Classe sol extension de la classe Element</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Un type de sol</li>
 * <li>Un numéro de texture</li>
 * <li>Un effet de points de vie(int)</li>
 * <li>Un effet de portee visuelle(int)</li>
 * <li>Un effet de portee de déplacement(int)</li>
 * <li>Un effet de puissance (int)</li>
 * <li>Un effet de tir (int)</li>
 * </ul>
 * </p>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */


public class Sol extends Element implements IConfig {
	private static final long serialVersionUID = -124005016652526706L;
	/**
	 * Enumeration de types de sols
	 */
	public enum TypeSol {
		
		PLAINE(TEX_PLAINE, 0, 0, 0, 0, 0),
		MONTAGNE(TEX_MONTAGNE, 0, -1, -1, 0, -1),
		COLLINE(TEX_COLLINE, 0, 1, 0, 0, 0),
		VILLAGE(TEX_VILLAGE, 1, 0, 0, 1, 1),
		DESERT(TEX_DESERT, -1, -2, -2, -1, -3);
		
		/**
		 * Numero de Texture
		 */
		private final int NUM_TEXTURE;
		/**
		 * Effet sur les points de vie
		 */
		private final int EFFET_POINTS_DE_VIE,
		/**
		 * effet sur la portée visuelle
		 */
		EFFET_PORTEE_VISUELLE,
		/**
		 * effet sur la portée de déplacement
		 */
		EFFET_PORTEE_DEPLACEMENT,
		/**
		 * effet sur la puissance
		 */
		EFFET_PUISSANCE, 
		/**
		 * effet sur le tir
		 */
		EFFET_TIR;
		
		/**
		 * Constructeurs
		 * @param numTexture
		 * @param effetPoinsDeVie
		 * @param effetPorteeVisuelle
		 * @param effetPorteeDeplacement
		 * @param effetPuissance
		 * @param effetTir
		 */
		private TypeSol(int numTexture, int effetPoinsDeVie, int effetPorteeVisuelle, int effetPorteeDeplacement, int effetPuissance, int effetTir) { 
			NUM_TEXTURE = numTexture; 
			EFFET_POINTS_DE_VIE = effetPoinsDeVie;
			EFFET_PORTEE_VISUELLE = effetPorteeVisuelle;
			EFFET_PORTEE_DEPLACEMENT = effetPorteeDeplacement;
			EFFET_PUISSANCE = effetPuissance;
			EFFET_TIR = effetTir;
		}
	
		/**
		 *  Accesseur numero de texture
		 * @return int
		 */
		public int getNUM_TEXTURE() { return NUM_TEXTURE; }
		
		/**
		 * accesseur effet point de vie
		 * @return int
		 */
		public int getEFFET_POINTS_DE_VIE() { return EFFET_POINTS_DE_VIE; }
		
		/**
		 * accesseur effet portée visuelle
		 * @return int
		 */
		public int getEFFET_PORTEE_VISUELLE() { return EFFET_PORTEE_VISUELLE; }
		
		/**
		 * accesseur effet portée de déplacement
		 * @return int
		 */
		public int getEFFET_PORTEE_DEPLACEMENT() { return EFFET_PORTEE_DEPLACEMENT; }
		
		
		/**
		 * accesseur effet puissance
		 * @return int 
		 */
		public int getEFFET_PUISSANCE() { return EFFET_PUISSANCE; }
		
		/** 
		 * accesseur effet tir
		 * @return int
		 */
		public int getEFFET_TIR() { return EFFET_TIR; }

		/**
		 *  Pseudo-accesseur type de sol aléatoire
		 * @return TypeSol
		 */
		public static TypeSol getSolAlea() { 
			return values() [(int) (Math.random() * values().length)];
		}
	}
	
	/**
	 * effet de guerison
	 */
	private final int EFFET_GUERISON, 
	/**
	 * effet portee visuelle
	 */
	EFFET_PORTEE_VISUELLE, 
	
	/**
	 * effet portee de déplacement
	 */
	EFFET_PORTEE_DEPLACEMENT, 
	/**
	 * effet puissance 
	 */
	EFFET_PUISSANCE, 
	
	/**
	 * effet tir
	 */
	EFFET_TIR;
	/**
	 * type de sol
	 */
	private final TypeSol TYPE;
	
	/**
	 * Constructeur d'un sol
	 * @param carte
	 * @param type
	 * @param pos
	 */
	public Sol(Carte carte, TypeSol type, Position pos) { 
		this.carte = carte;
		this.setPos(pos);
		this.numTexture = type.getNUM_TEXTURE();
		this.EFFET_GUERISON = type.getEFFET_POINTS_DE_VIE();
		this.EFFET_PORTEE_VISUELLE = type.getEFFET_PORTEE_VISUELLE();
		this.EFFET_PORTEE_DEPLACEMENT = type.getEFFET_PORTEE_DEPLACEMENT();
		this.EFFET_PUISSANCE = type.getEFFET_PUISSANCE();
		this.EFFET_TIR = type.getEFFET_TIR();
		this.TYPE = type;
		creerHex();
	}
	
	/**
	 *  Accesseurs
	 * @return TypeSol
	 */
	public TypeSol getTYPE() { return TYPE; }
	
	
	/**
	 *  Renvoie le type de l'élément sous forme de chaine de caractère
	 */
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
	/**
	 *  Applique les effets du sol au soldat
	 * @param soldat
	 */
	public void appliquerEffets(Soldat soldat) {
		// Points de vie
		soldat.setGuerison(Math.max(GUERISON_MIN, soldat.getGuerison() + EFFET_GUERISON));
		// Portee visuelle
		appliquerEffetPorteeVisuelle(soldat);
		// Portee deplacement
		if (soldat.getPorteeDeplacement() > 0)
			soldat.setPorteeDeplacement(Math.max(MOVE_MIN, soldat.getPorteeDeplacement() + EFFET_PORTEE_DEPLACEMENT));
		else
			soldat.setPorteeDeplacement(Math.max(0, soldat.getPorteeDeplacement() + EFFET_PORTEE_DEPLACEMENT));
		// Puissance
		soldat.setPuissance(Math.max(POW_MIN, soldat.getPuissance() + EFFET_PUISSANCE));
		// Tir
		if (soldat.getTir() > 0)
			soldat.setTir(Math.max(TIR_MIN, soldat.getTir() + EFFET_TIR));
	}
	/**
	 * Applique l'effet de la portee visuelle
	 * @param soldat
	 */
	public void appliquerEffetPorteeVisuelle(Soldat soldat) {
		soldat.setPorteeVisuelle(Math.max(VISION_MIN, soldat.getPorteeVisuelle() + EFFET_PORTEE_VISUELLE));
	}
	/**
	 * Enlève les effets du sol au soldat
	 * @param soldat
	 */
	public void enleverEffets(Soldat soldat) {
		// Points de vie
		soldat.setGuerison(soldat.getGUERISON_BASE());
		// Portee visuelle
		enleverEffetPorteeVisuelle(soldat);
		// Portee deplacement
		soldat.setPorteeDeplacement(Math.max(0, soldat.getPorteeDeplacement() - EFFET_PORTEE_DEPLACEMENT));
		// Puissance
		soldat.setPuissance(soldat.getPUISSANCE_BASE());
		// Tir
		if (soldat.getTir() > 0) 
			soldat.setTir(soldat.getTIR_BASE());
	}
	/**
	 *  Enlève l'effet de la portee visuelle
	 * @param soldat
	 */
	public void enleverEffetPorteeVisuelle(Soldat soldat) {
		soldat.setPorteeVisuelle(soldat.getPORTEE_VISUELLE_BASE());
	}
}
