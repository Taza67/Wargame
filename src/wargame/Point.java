package wargame;

public class Point implements java.io.Serializable{
	// Infos
	private double x;
	private double y;
	
	// Constructeurs
	public Point(double x, double y) { this.x = x; this.y = y; }
	
	// Accesseurs
	public double getX() { return x;}
	public double getY() { return y;}
	
	// Mutateurs
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	
	// Méthodes
	// Renvoie les infos
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	// Calcule la distance d'un point avec un autre passé en paramètre
	public double distance(Point p) {
		double terme1 = Math.pow(this.x - p.getX(), 2),
			   terme2 = Math.pow(this.y - p.getY(), 2);
		return Math.sqrt(terme1 + terme2);
	}
	// Calcule la position axiale
	public PositionAxiale toPositionAxiale(int rayon, Point origine) {
		x = Math.round((x - origine.getX()) / rayon);
		y = Math.round((y - origine.getY()) / rayon);
	    double q = (Math.sqrt(3) / 3.) * x  +  (-1 / 3.) * y,
	    	   r = (2 / 3. * y);
	    return (new PositionAxiale(q, r)).round();
	}
	// Ajoute aux coordonnées celles du point donné
	public Point add(Point p) {
		return new Point(x + p.getX(), y + p.getY());
	}
	// Réduit les coordonnées de celles du point donné
	public Point substract(Point p) {
		return new Point(x - p.getX(), y - p.getY());
	}
	// Vérifie si les coordonnées du point sont comprises entre celles des deux points données
	public boolean estValide(Point min, Point max) {
		return x >= min.getX() && x <= max.getX() && y >= min.getY() && y <= max.getY();
	}
}
