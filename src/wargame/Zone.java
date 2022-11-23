package wargame;

public class Zone {
	// Infos
	private Position extHautGauche,
					 extBasDroit;
	
	// Constructeurs
	public Zone(int xHg, int yHg, int xBd, int yBd) { extHautGauche = new Position(xHg, yHg); extBasDroit = new Position(xBd, yBd); }
	
	
}
