package wargame;

public class PositionCubique {
	// Infos
	private double r, q, s;
	
	// Constructeurs
	public PositionCubique(double r, double q, double s) {
		this.r = r;
		this.q = q;
		this.s = s;
	}
	
	// Accesseurs
	public double getR() { return r; }
	public double getQ() { return q; }
	public double getS() { return s; }
	
	// Méthodes
	// Convertit la position cubique courante en position axiale
	public PositionAxiale toPositionAxiale() {
		return new PositionAxiale(q, r);
	}
	// Réduit les coordonnées de la position cubique courante de celles de la position cubique donnée
	public PositionCubique substract(PositionCubique b) {
	    return new PositionCubique(q - b.q, r - b.r, s - b.s);
	}
	// Calcule la distance entre la position cubique courante et celle donnée
	public double distance(PositionCubique b) {
	    PositionCubique vec = substract(b);
	    return (Math.abs(vec.q) + Math.abs(vec.r) + Math.abs(vec.s)) / 2;
	}
	// Arrondi les coordonnées de la position cubique à des coordonnées exactes
	public PositionCubique round() {
	    int qR = (int)Math.round(q),
	    	rR = (int)Math.round(r),
	    	sR = (int)Math.round(s);
	    double q_diff = Math.abs(qR - q),
	    	   r_diff = Math.abs(rR - r),
	    	   s_diff = Math.abs(sR - s);

	    if (q_diff > r_diff && q_diff > s_diff)
	        qR = - rR - sR;    
	    else if (r_diff > s_diff)
	        rR = - qR - sR;
	    else
	        sR = - qR - rR;
	    return new PositionCubique(qR, rR, sR);
	}
}
