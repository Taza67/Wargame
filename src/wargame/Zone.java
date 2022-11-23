package wargame;

public class Zone {
	// Infos
	private Position extHautGauche,
					 extBasDroit;
	
	// Constructeurs
	public Zone(int xHg, int yHg, int xBd, int yBd) { extHautGauche = new Position(xHg, yHg); extBasDroit = new Position(xBd, yBd); }

	// Accesseurs
	public Position getExtHautGauche() { return extHautGauche; }
	public Position getExtBasDroit() { return extBasDroit; }
	
	// Mutateurs
	public void setExtHautGauche(Position extHautGauche) { this.extHautGauche = extHautGauche; }
	public void setExtBasDroit(Position extBasDroit) { this.extBasDroit = extBasDroit; }
	
}
