package wargame;

import java.awt.Graphics;

public class Hexagone {
	// Infos
	protected final Point[] extremites;
	private Point centre;
	
	// Constructeurs
	public Hexagone(Point centre, Point unSommet) {
		this.extremites = new Point[6];
		this.centre = centre;
		this.extremites[0] = unSommet;
		calculerAutresSommets();
	}
	
	// Méthodes
	// Retourne le point d'indice donné à partir du centre, du nombre de côtés/points et du point de base
	private Point calculerAutreSommet(int indice) {
		Point p = null;
		double x, y, dx, dy, r, a,
			   xC = centre.getX(),
			   yC = centre.getY(),
			   xP = extremites[0].getX(),
			   yP = extremites[0].getY();
		dx = Math.abs(xP - xC);
		dy = Math.abs(yP - yC);
		r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		a = Math.atan2(dy, dx);
		x = xC + r * Math.cos(a + indice * 2 * Math.PI / 6);
		y = yC + r * Math.sin(a + indice * 2 * Math.PI / 6);
		p = new Point(x, y);
		return p;
	}
	// Calcule tous les autres sommets de l'hexagone
	private void calculerAutresSommets() {
		for (int i = 0; i < 6; i++)
			extremites[i] = calculerAutreSommet(i);
	}
	// Zoome sur l'hexagone
	public void zoom(int zoomX) {
		double xP = extremites[0].getX(),
			   yP = extremites[0].getY();
		extremites[0] = new Point(xP * zoomX, yP);
		calculerAutresSommets();
	}
    // Vérifie si un point est dans l'hexagone
    public boolean dedans(Point p) {
    	boolean ret = false;
    	int i, j;
    	for (i = 0, j = 6; i < 6; j = i++)
    		if ((extremites[i].getY() > p.getY()) != (extremites[j].getY() > p.getY()) && (p.getX() < (extremites[j].getX() - extremites[i].getX()) * (p.getY() - extremites[i].getY()) / (extremites[j].getY()-extremites[i].getY()) + extremites[i].getX()))
	            ret = !ret;
    	return ret;
    }
	// Dessine l'hexagone
	public void seDessiner(Graphics g) {
		int tabX[], tabY[];
		tabX = new int[6];
		tabY = new int[6];
		for (int i = 0; i < 6; i++) {
			tabX[i] = (int)extremites[i].getX();
			tabY[i] = (int)extremites[i].getY();
		}
		g.fillPolygon(tabX, tabY, 6);
	}
}
