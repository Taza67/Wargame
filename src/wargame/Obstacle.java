package wargame;

public class Obstacle extends Element implements IConfig {
	// Type
	public enum TypeObstacle {
		// Liste
		ROCHER(2), FORET(1), EAU(3);
		
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
		this.pos = pos;
		creerHex();
		numTexture = type.getNUM_TEXTURE();
	}
	
	// Accesseurs
	public TypeObstacle getTYPE() { return TYPE; }
	
	// Méthodes
	// Retourne les infos sur l'obstacle
	public String toString() { 
		String desc = super.toString();
		if (visible == true)
			desc += " : [ Type : " + TYPE + " ]";
		return desc;
	}
}
