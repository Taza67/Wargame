package wargame;

public interface IVivant {
	// Constantes statiques
	public final static char VERTS = 'v'; 	// Gentils
	public final static char ROUGES = 'r';	// Méchants
	
	// Méthodes
	// Soigne l'être vivant
	public void soigne(int pointsDeGuerison, int pointsDeVieMax);
	// Déplace l'être vivant à la position en paramètre
	void seDeplace(Position newPos);
	// Augmente le niveau d'un être vivant
	void augmenteNiveau();
}
