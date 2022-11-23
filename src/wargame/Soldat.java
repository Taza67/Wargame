package wargame;

public class Soldat extends Vivant implements ISoldat {
	// Infos
	private int puissance;
	private int tir;
	
	// Constructeurs
	public Soldat(Position pos, int points_de_vie, int portee_visuelle, int portee_deplacement, int puissance, int tir) {
		super(pos, points_de_vie, portee_visuelle, portee_deplacement);
		this.puissance = puissance;
		this.tir = tir;
	}
	public Soldat(Position pos, TypesSoldatH type) {
		this(pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
	}
	public Soldat(Position pos, TypesSoldatM type) {
		this(pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
	}
	
	// Accesseurs
	public int getPuissance() { return puissance; }
	public int getTir() { return tir; }

	// Mutateurs
	public void setPuissance(int puissance) { this.puissance = puissance; }
	public void setTir(int tir) { this.tir = tir; }

	// Méthodes
	// Fait combattre le soldat courant avec l'être vivant en paramètre
	public void combat(Vivant etre) {
		
	}

}
