package wargame;

public class Armee {
	// Infos
	private final int TAILLEMAX;
	private int taille = 0;
	private Vivant[] ensemble;
	
	// Constructeurs
	public Armee(int tailleMax) {
		this.TAILLEMAX = tailleMax;
		ensemble = new Vivant[TAILLEMAX];
	}
	
	// Accesseurs
	public int getTAILLEMAX() { return TAILLEMAX; }
	public int getTaille() { return taille; }
	// Pseudo-accesseurs
	public Vivant getVivant(int indice) { return existe(indice) ? ensemble[indice] : null; }
	
	// Pseudo-mutateurs
	public void ajouteVivant(Vivant v) {
		if (taille != TAILLEMAX) ensemble[taille++] = v;
	}
	
	// Méthodes
	// Vérifie si l'indice en paramètre est valide
	public boolean estIndiceValide(int indice) { return indice >= 0 && indice < TAILLEMAX; }
	// Vérifie si l'être vivant à l'indice donné est initialisé
	public boolean existe(int indice) {
		if (estIndiceValide(indice)) return ensemble[indice] != null;
		return false;
	}
	
}
