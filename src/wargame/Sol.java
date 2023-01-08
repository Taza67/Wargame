package wargame;

public class Sol extends Element implements IConfig {
	// Type
	public enum TypeSol {
		// Liste
		PLAINE(TEX_PLAINE, 0, 0, 0, 0, 0),
		MONTAGNE(TEX_MONTAGNE, 0, -1, -1, 0, -1),
		COLLINE(TEX_COLLINE, 0, 1, 0, 0, 0),
		VILLAGE(TEX_VILLAGE, 1, 0, 0, 1, 1),
		DESERT(TEX_DESERT, -1, -2, -2, -1, -3);
		
		// Infos
		private final int NUM_TEXTURE;
		private final int EFFET_POINTS_DE_VIE, EFFET_PORTEE_VISUELLE, EFFET_PORTEE_DEPLACEMENT, EFFET_PUISSANCE, EFFET_TIR;
		
		// Constructeurs
		private TypeSol(int numTexture, int effetPoinsDeVie, int effetPorteeVisuelle, int effetPorteeDeplacement, int effetPuissance, int effetTir) { 
			NUM_TEXTURE = numTexture; 
			EFFET_POINTS_DE_VIE = effetPoinsDeVie;
			EFFET_PORTEE_VISUELLE = effetPorteeVisuelle;
			EFFET_PORTEE_DEPLACEMENT = effetPorteeDeplacement;
			EFFET_PUISSANCE = effetPuissance;
			EFFET_TIR = effetTir;
		}
		
		// Accesseur
		public int getNUM_TEXTURE() { return NUM_TEXTURE; }
		public int getEFFET_POINTS_DE_VIE() { return EFFET_POINTS_DE_VIE; }
		public int getEFFET_PORTEE_VISUELLE() { return EFFET_PORTEE_VISUELLE; }
		public int getEFFET_PORTEE_DEPLACEMENT() { return EFFET_PORTEE_DEPLACEMENT; }
		public int getEFFET_PUISSANCE() { return EFFET_PUISSANCE; }
		public int getEFFET_TIR() { return EFFET_TIR; }

		// Pseudo-accesseurs
		public static TypeSol getSolAlea() { 
			return values() [(int) (Math.random() * values().length)];
		}
	}
	
	// Infos
	private final int EFFET_GUERISON, EFFET_PORTEE_VISUELLE, EFFET_PORTEE_DEPLACEMENT, EFFET_PUISSANCE, EFFET_TIR;
	private final TypeSol TYPE;
	
	// Constructeurs
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
	
	// Accesseurs
	public TypeSol getTYPE() { return TYPE; }
	
	// Méthodes
	// Renvoie le type de l'élément sous forme de chaine de caractère
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
	// Applique les effets du sol au soldat
	public void appliquerEffets(Soldat soldat) {
		// Points de vie
		soldat.setGuerison(Math.max(GUERISON_MIN, soldat.getGuerison() + EFFET_GUERISON));
		// Portee visuelle
		appliquerEffetPorteeVisuelle(soldat);
		// Portee deplacement
		soldat.setPorteeDeplacement(Math.max(MOVE_MIN, soldat.getPorteeDeplacement() + EFFET_PORTEE_DEPLACEMENT));
		// Puissance
		soldat.setPuissance(Math.max(POW_MIN, soldat.getPuissance() + EFFET_PUISSANCE));
		// Tir
		if (soldat.getTir() > 0)
			soldat.setTir(Math.max(TIR_MIN, soldat.getTir() + EFFET_TIR));
	}
	// Applique l'effet de la portee visuelle
	public void appliquerEffetPorteeVisuelle(Soldat soldat) {
		soldat.setPorteeVisuelle(Math.max(VISION_MIN, soldat.getPorteeVisuelle() + EFFET_PORTEE_VISUELLE));
	}
	// Enlève les effets du sol au soldat
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
	// Enlève l'effet de la portee visuelle
	public void enleverEffetPorteeVisuelle(Soldat soldat) {
		soldat.setPorteeVisuelle(soldat.getPORTEE_VISUELLE_BASE());
	}
}
