package wargame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Element implements IConfig{
	// Infos
	protected Carte carte;
	protected Position pos;
	
	// Méthodes
	
	// Méthodes graphiques
	public void seDessiner(Graphics g) {
		int x = pos.getX() * NB_PIX_CASE,
			y = pos.getY() * NB_PIX_CASE;
		g.fillRect(x + 5, y + 5, NB_PIX_CASE - 5, NB_PIX_CASE - 5);
	}
}
