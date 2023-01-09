package wargame;

public class Obstacle extends Element implements IConfig {
	private static final long serialVersionUID = 459512988497610851L;
	// Type
	public enum TypeObstacle {
		// Liste
		ROCHER(TEX_ROCHER), FORET(TEX_FORET), EAU(TEX_EAU);
		
		// Infos
		private final int NUM_TEXTURE;
		
		// Constructeurs
		private TypeObstacle(int numTexture) { NUM_TEXTURE = numTexture; }
		
		// Accesseur
		public int getNUM_TEXTURE() { return NUM_TEXTURE; }
		// Pseudo-accesseurs
		public static TypeObstacle getObstacleAlea() { 
			return values() [(int) (Math.random() * values().length)];
		}
	}
	
	// Infos
	private final TypeObstacle TYPE;
	
	// Constructeurs
	public Obstacle(Carte carte, TypeObstacle type, Position pos) {
		this.carte = carte; 
		TYPE = type; 
		this.setPos(pos);
		creerHex();
		numTexture = type.getNUM_TEXTURE();
	}
	
	// Accesseurs
	public TypeObstacle getTYPE() { return TYPE; }
	// Pseudo-accesseur
	// Renvoie le type sous forme de chaine de caractères
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
}
