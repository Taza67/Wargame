package wargame;
import java.awt.Color;

public abstract class Element {
	// Infos
	protected Position pos;
	
	// Constructeurs
	public Element(int x, int y) { pos = new Position(x, y); }
}
