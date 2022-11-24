package wargame;

public class Amiral extends Soldat implements IAmiral {	
	// Infos
	private static int nbAmirauxMalefiques = 0;
	private static int nbAmirauxBons = 0;
	
	// Constructeurs
	public Amiral(Position pos, char cote, int points_de_vie, int portee_visuelle, int portee_deplacement, int puissance, int tir) {
		super(pos, cote, points_de_vie, portee_visuelle, portee_deplacement, puissance, tir);
		if (cote == ROUGES) nbAmirauxMalefiques++;
		if (cote == VERTS) nbAmirauxBons++;
	}
}
