package wargame;

public interface ISoldat extends IConfig {
	// Type
	// // Soldats bons
	public static enum TypesH {
		// Liste
		HUMAIN(40, 4, 4, 10, 2), NAIN(80, 2, 3, 20, 0), ELF(70, 6, 5, 10, 6), HOBBIT(20, 2, 2, 5, 2);
		
		// Infos
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PORTEE_DEPLACEMENT, PUISSANCE, TIR;
		private final char CAMP;
		
		// Constructeurs
		TypesH(int points, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
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
		public static TypesH getTypeHAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	// // Soldats maléfiques
	public static enum TypesM {
		// Liste
		TROLL (100, 1, 4 , 30, 0), ORC (40, 2, 5, 10, 3), GOBELIN (20, 2, 3, 5, 2);
      
		// Infos
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PORTEE_DEPLACEMENT, PUISSANCE, TIR;
		private final char CAMP;
      
		// Constructeurs
		TypesM(int points, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
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
		public static TypesM getTypeMAlea() {
         return values()[(int)(Math.random()*values().length)];
		}
	}

	// Méthodes
	// Déplace un soldat à la position pos
	boolean seDeplace(Position pos);
	// int getTour(); 
	// void joueTour(int tour);
	// void combat(Soldat soldat);
	// void seDeplace(Position newPos);
}
