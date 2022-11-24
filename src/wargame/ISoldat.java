package wargame;

public interface ISoldat extends IVivant {
	// Type
	// // Soldats bons
	static enum TypesSoldatH {
		// Liste
		HUMAIN(40, 3, 5, 10, 2), NAIN(80, 1, 4, 20, 0), ELF(70, 5, 7, 10, 6), HOBBIT(20, 3, 5, 5, 2);
		
		// Infos
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PORTEE_DEPLACEMENT, PUISSANCE, TIR;
		private final char CAMP;
		
		// Constructeurs
		TypesSoldatH(int points, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = porteeVisuelle;
			PORTEE_DEPLACEMENT = porteeDeplacement;
			PUISSANCE = puissance; 
			TIR = tir;
			CAMP = VERTS;
		}
		
		// Accesseurs
		public int getPoints() { return POINTS_DE_VIE; }
		public int getPorteeVisuelle() { return PORTEE_VISUELLE; }
		public int getPorteeDeplacement() { return PORTEE_DEPLACEMENT; }
		public int getPuissance() { return PUISSANCE; }
		public int getTir() { return TIR; }
		public char getCAMP() { return CAMP; }
		// Pseudo-accesseurs
		public static TypesSoldatH getTypeHAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	// // Soldats maléfiques
	public static enum TypesSoldatM {
		// Liste
		TROLL (100, 1, 4 , 30, 0), ORC (40, 2, 5, 10, 3), GOBELIN (20, 2, 3, 5, 2);
      
		// Infos
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PORTEE_DEPLACEMENT, PUISSANCE, TIR;
		private final char CAMP;
      
		// Constructeurs
		TypesSoldatM(int points, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = porteeVisuelle;
			PORTEE_DEPLACEMENT = porteeDeplacement;
			PUISSANCE = puissance; 
			TIR = tir;
			CAMP = ROUGES;
		}
		
		// Accesseurs
		public int getPoints() { return POINTS_DE_VIE; }
		public int getPorteeVisuelle() { return PORTEE_VISUELLE; }
		public int getPorteeDeplacement() { return PORTEE_DEPLACEMENT; }
		public int getPuissance() { return PUISSANCE; }
		public int getTir() { return TIR; }
		public char getCAMP() { return CAMP; }
		// Pseudo-accesseurs
		public static TypesSoldatM getTypeMAlea() {
         return values()[(int)(Math.random()*values().length)];
		}
	}
	
	// Méthodes
	// Fait combattre le soldat courant avec l'être vivant en paramètre
	void combat(Vivant adversaire);
}
