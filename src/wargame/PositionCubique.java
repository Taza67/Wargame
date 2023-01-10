package wargame;




/**
 * <b>PositionCubique est une classe intermédiaire dans la gestion des positions entre des positions rectangulaires et des positions hexagonales</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une variable r</li>
 * <li>Une variable q</li>
 * <li>Une variable s</li>
 * </ul>
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */


public class PositionCubique {
	/**
	 * variable r
	 */
	private double r, 
	/**
	 * variable q
	 */
	q,
	/**
	 * variable s
	 */
	s;
	
	/**
	 *  Constructeurs
	 * @param r
	 * @param q
	 * @param s
	 */
	public PositionCubique(double r, double q, double s) {
		this.r = r;
		this.q = q;
		this.s = s;
	}
	
	/**
	 * Accesseur de r
	 * @return double
	 */
	public double getR() { return r; }
	/**
	 * accesseur de q
	 * @return double
	 */
	public double getQ() { return q; }
	/**
	 * accesseur de s
	 * @return double
	 */
	public double getS() { return s; }
	
	
	/**
	 *  Convertit la position cubique courante en position axiale
	 * @return PositionAxiale
	 */
	public PositionAxiale toPositionAxiale() {
		return new PositionAxiale(q, r);
	}
	/**
	 * Réduit les coordonnées de la position cubique courante de celles de la position cubique donnée
	 * @param b
	 * @return PositionCubique
	 */
	public PositionCubique substract(PositionCubique b) {
	    return new PositionCubique(q - b.q, r - b.r, s - b.s);
	}
	/**
	 * Calcule la distance entre la position cubique courante et celle donnée
	 * @param b
	 * @return distance (double)
	 */
	public double distance(PositionCubique b) {
	    PositionCubique vec = substract(b);
	    return (Math.abs(vec.q) + Math.abs(vec.r) + Math.abs(vec.s)) / 2;
	}
	/**
	 * Arrondi les coordonnées de la position cubique à des coordonnées exactes
	 * @return PositionCubique
	 */
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
