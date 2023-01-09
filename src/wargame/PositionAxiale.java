package wargame;

import java.io.Serializable;

public class PositionAxiale implements Serializable {
	private static final long serialVersionUID = -4157725586069134304L;
	// Constantes statiques
	// Voisins de la position
	public static PositionAxiale[] vecteurs_directions_axiales = {new PositionAxiale(+1, 0), new PositionAxiale(+1, -1), new PositionAxiale(0, -1),
														   		  new PositionAxiale(-1, 0), new PositionAxiale(-1, +1), new PositionAxiale(0, +1)};
	// Infos
	private double r, q;
	
	// Constructeurs
	PositionAxiale(double q, double r) { this.q = q; this.r = r; }
	
	// Accesseurs
	public double getR() { return r; }
	public double getQ() { return q; }
	public double getS() { return - r - q; }
	
	// Méthodes
	// Renvoie les infos
	public String toString() {
		return "(" + q + ", " + r + ")";
	}
	// Calcule la position
	public Position toPosition() {
		int x = (int)(q + (r - (r % 2)) / 2);
	    int y = (int)r;
	    return new Position(x, y);
	}
	// Calcule la position Cubique
	public PositionCubique toPositionCubique() {
	    double qCube = q,
	    	   rCube = r,
	    	   sCube = - q - r;
	    return new PositionCubique(qCube, rCube, sCube);
	}
	// Calcule le point
	public Point toPoint(int rayon, Point origine) {
		double x = rayon * (Math.sqrt(3) * q  +  Math.sqrt(3) / 2. * r),
			   y = rayon * 3 / 2. * r;
		return new Point(x + origine.getX(), y + origine.getY());
	}
	// Renvoie la position axiale d'une direction
	public PositionAxiale direction(int dir) {
		return vecteurs_directions_axiales[dir];
	}
	// Ajout les coordonnées du point vect à la position courante
	public PositionAxiale add(PositionAxiale vec) {
		return new PositionAxiale(q + vec.getQ(), r + vec.getR());
	}
	// Renvoie la position axiale du voisin dont la direction est donnée
	public PositionAxiale voisin(int direction) {
		return add(direction(direction));
	}
	// Vérifie si une position axiale est une voisine de la position courante
	public boolean estVoisin(PositionAxiale p) {
		boolean ret = false;
		for (int i = 0; i < 6 && !ret; i++)
			if (voisin(i).equals(p)) ret = true;
		return ret;
	}
	// Calcule la distance avec une position axiale donnée
	public double distance(PositionAxiale b) {
	    PositionCubique ac = this.toPositionCubique(),
	    				bc = b.toPositionCubique();
	    return ac.distance(bc);
	}
	// Arrondit une position axiale à des coordonnées exactes
	public PositionAxiale round() {
	    return this.toPositionCubique().round().toPositionAxiale();
	}
	// Égalité ?
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof PositionAxiale)) return false;
		PositionAxiale other = (PositionAxiale) obj;
		return Double.doubleToLongBits(q) == Double.doubleToLongBits(other.q)
			&& Double.doubleToLongBits(r) == Double.doubleToLongBits(other.r);
	}
	
}
