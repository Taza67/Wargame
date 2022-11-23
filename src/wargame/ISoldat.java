package wargame;

public interface ISoldat {
	// Type
	// // Soldats bons
	static enum TypesH {
		// Liste
		HUMAIN(40, 3, 10, 2), NAIN(80, 1, 20, 0), ELF(70, 5, 10, 6), HOBBIT(20, 3, 5, 2);
		
		// Infos
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PUISSANCE, TIR;
		
		// Constructeurs
		TypesH(int points, int portee, int puissance, int tir) {
			POINTS_DE_VIE = points; 
			PORTEE_VISUELLE = portee;
			PUISSANCE = puissance; 
			TIR = tir;
		}
		
		// Accesseurs
		public int getPoints() { return POINTS_DE_VIE; }
		public int getPortee() { return PORTEE_VISUELLE; }
		public int getPuissance() { return PUISSANCE; }
		public int getTir() { return TIR; }
		// Pseudo-accesseurs
		public static TypesH getTypeHAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	// // Soldats maléfiques
	public static enum TypesM {
		// Liste
		TROLL (100,1,30,0), ORC (40,2,10,3), GOBELIN (20,2,5,2);
      
		// Infos
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PUISSANCE, TIR;
      
		// Constructeurs
		TypesM(int points, int portee, int puissance, int tir) {
			POINTS_DE_VIE = points; 
			PORTEE_VISUELLE = portee;
			PUISSANCE = puissance; 
			TIR = tir;
		}
		
		// Accesseurs
		public int getPoints() { return POINTS_DE_VIE; }
		public int getPortee() { return PORTEE_VISUELLE; }
		public int getPuissance() { return PUISSANCE; }
		public int getTir() { return TIR; }
		// Pseudo-accesseurs
		public static TypesM getTypeMAlea() {
         return values()[(int)(Math.random()*values().length)];
		}
	}
	
	// Méthodes
	// Retourne le nombre de points de vie du soldat
	int getPoints();
	// Retourne la portée du soldat
	int getTour(); int getPortee();
	// 
	void joueTour(int tour);
	// Fait combattre le soldat courant avec le soldat en paramètre
	void combat(Soldat soldat);
	// Déplace le soldat à la position en paramètre
	void seDeplace(Position newPos);
}
