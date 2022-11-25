package wargame;
import java.awt.Graphics;
import java.awt.Color;

public class Obstacle extends Element implements IConfig {
	// Type
	public enum TypeObstacle {
		// Liste
		ROCHER(COULEUR_ROCHER), FORET(COULEUR_FORET), EAU(COULEUR_EAU);
		
		// Infos
		private final Color COULEUR;
		
		// Constructeurs
		private TypeObstacle(Color couleur) { COULEUR = couleur; }
		
		// Pseudo-accesseurs
		public static TypeObstacle getObstacleAlea() { 
			return values() [(int) (Math.random() * values().length)];
		}
	}
	
	// Infos
	private final TypeObstacle TYPE;
	
	// Constructeurs
	public Obstacle(Position pos, TypeObstacle type) { super(pos.getX(), pos.getY()); TYPE = type; }
	
	// Pseudo-accesseurs
	public TypeObstacle getTYPE() { return TYPE; }
	
	// Méthodes
	public String toString() { return "" + TYPE; }
}

// utilisation dans la classe carte : 
// Obstacle o = new Obstacle(Obstacle.TypeObstacle.getObstacleAlea(), new Position(...)) 