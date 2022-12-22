package wargame;

public class Point {
	// Constantes statiques
	public static final int SAM = -1;
	public static final int ALIGNES = 0;
	public static final int SIAM = 1;
	
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
	// Calcule la distance d'un point depuis l'origine
	public double distance() {
		double terme1 = Math.pow(this.x, 2), 
			   terme2 = Math.pow(this.y, 2);
		return Math.sqrt(terme1 + terme2);
	}
	// Calcule la distance d'un point avec un autre passé en paramètre
	public double distance(Point p) {
		double terme1 = Math.pow(this.x - p.getX(), 2),
			   terme2 = Math.pow(this.y - p.getY(), 2);
		return Math.sqrt(terme1 + terme2);
	}
	// Retourne les infos sur un point
	public String toString() {
		return "(" + (float)x + ", " + (float)y + ")";
	}
	// Retourne le signe de l'angle formé par le point en question et les deux donnés
	public int signeAngle(Point b, Point c) {
        double toto = ((b.getX() - this.getX()) * (c.getY() - this.getY()) - 
        			   (c.getX() - this.getX()) * (b.getY() - this.getY()));
        if (toto < 0) return SIAM;
        if (toto > 0) return SAM;
        else return ALIGNES;
    }
	// Vérifie si deux points sont égaux ou pas
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}
}
