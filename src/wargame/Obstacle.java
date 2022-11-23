package wargame;
import java.awt.Graphics;
import java.awt.Color;

public class Obstacle extends Element{
	// Type
	public enum TypeObstacle{
		// Liste
		ROCHER(COULEUR_ROCHER), FORET (COULEUR_FORET), EAU (COULEUR_EAU);
		
		// Infos
		private final Color COULEUR ;
		
		// Constructeurs
		private TypeObstacle(Color couleur) {COULEUR = couleur;}
		
		// Accesseurs
		public static TypeObstacle getObsacleAlea() { 
			return values() [(int) (Math.random() * values().length)];
		}
	}
	
	// Infos
	private  TypeObstacle TYPE;
	
	// Constructeurs
	public Obstacle(Position pos, TypeObstacle type) { super(pos); TYPE = type; }
	
	// Accesseurs
	public TypeObstacle getTYPE() { return TYPE; }
	
	// Méthodes
	public String toString() { return "" + TYPE; }
}


// utilisation dans la classe carte : 
// Obstacle o = new Obstacle(Obstacle.TypeObstacle.getObstacleAlea(), new Position(...)) 