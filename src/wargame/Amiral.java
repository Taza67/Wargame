package wargame;

public class Amiral extends Soldat implements IAmiral {
	// Constantes statiques
	private static final int NBMAXAMIRAUX = 10;
	
	// Infos
	private static int nbInstancesAmirauxMalefique = 0;
	private int numeroAmiral;
	
	// Constructeurs
	public Amiral(Position pos, int points_de_vie, int portee_visuelle, int portee_deplacement, int puissance, int tir) {
		super(pos, points_de_vie, portee_visuelle, portee_deplacement, puissance, tir);
	}
}
