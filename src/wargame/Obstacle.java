package wargame;



/**
 * <b>Obstacle est la classe qui gère les obstacles type montagne, foret ou eau Extension de Element</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une enumération de types d'obstacles</li>
 * <li>Un numéro de texture</li>
 * </ul>
 * </p>
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */
public class Obstacle extends Element implements IConfig {
	private static final long serialVersionUID = 459512988497610851L;
	/**
	 * Enumeration de types d'obstacles
	 *
	 */
	public enum TypeObstacle {
		ROCHER(TEX_ROCHER), FORET(TEX_FORET), EAU(TEX_EAU);
		
		/**
		 * Numero de texture
		 */
		private final int NUM_TEXTURE;
		
		/**
		 * Constructeur TypeObstacle 
		 * @param numTexture
		 */
		private TypeObstacle(int numTexture) { NUM_TEXTURE = numTexture; }
		
		/**
		 * Accesseur du numéro de texture
		 * @return int
		 */
		public int getNUM_TEXTURE() { return NUM_TEXTURE; }
		/**
		 * Pseudo-accesseur 
		 * @return retourne un type d'obstacle aléatoire
		 */
		public static TypeObstacle getObstacleAlea() { 
			return values() [(int) (Math.random() * values().length)];
		}
	}
	
	// Infos
	private final TypeObstacle TYPE;
	
	/**
	 * Constructeur d'obstacle
	 * @param carte
	 * @param type
	 * @param pos
	 */
	public Obstacle(Carte carte, TypeObstacle type, Position pos) {
		this.carte = carte; 
		TYPE = type; 
		this.setPos(pos);
		creerHex();
		numTexture = type.getNUM_TEXTURE();
	}
	
	/**
	 * Accesseur du Type d'un obstacle
	 * @return TypeObstacle
	 */
	public TypeObstacle getTYPE() { return TYPE; }
	
	
	/**
	 * Retourne le type d'un obstacle sous forme de chaine de caractère
	 * @return String
	 */
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
}
