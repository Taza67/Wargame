package wargame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Element implements IConfig{
	// Infos
	protected Carte carte;
	protected Position pos;
	
	// Méthodes
	
	// Méthodes graphiques
	// Dessine l'élément sous sa forme reelle sur la carte
	public void seDessiner(Graphics g) {
		int x = pos.getX() * NB_PIX_CASE,
			y = pos.getY() * NB_PIX_CASE;
		g.fillRect(x + 5, y + 5, NB_PIX_CASE - 5, NB_PIX_CASE - 5);
	}
	// Dessine l'élément sous sa forme miniature sur la mini-map
	public void seDessinerMiniMap(Graphics g) {
		int x = pos.getX() * NB_PIX_CASE_MINI_MAP,
			y = pos.getY() * NB_PIX_CASE_MINI_MAP;
		g.fillRect(x + 1, y + 1, NB_PIX_CASE_MINI_MAP - 1, NB_PIX_CASE_MINI_MAP - 1);
	}
}
