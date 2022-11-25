package wargame;

import java.awt.Graphics;

public abstract class Soldat extends Element implements IConfig, ISoldat {
	// Infos
	private final int POINTS_DE_VIE_MAX, 
					  PORTEE_VISUELLE,
					  PORTE_DEPLACEMENT,
					  PUISSANCE, 
					  TIR;
	private int pointsDeVie;
	
	// Constructeurs
	Soldat(Carte carte, Position pos, int pts, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
		this.carte = carte;
		this.pos = pos;
		POINTS_DE_VIE_MAX = pointsDeVie = pts;
		PORTEE_VISUELLE = porteeVisuelle; 
		PORTE_DEPLACEMENT = porteeDeplacement;
		PUISSANCE = puissance;
		TIR = tir;
	}

	// Accesseurs
	public int getPOINTS_DE_VIE_MAX() { return POINTS_DE_VIE_MAX; }
	public int getPORTEE_VISUELLE() { return PORTEE_VISUELLE; }
	public int getPORTE_DEPLACEMENT() { return PORTE_DEPLACEMENT; }
	public int getPUISSANCE() { return PUISSANCE; }
	public int getTIR() { return TIR; }
	public int getPointsDeVie() { return pointsDeVie; }
	
	// Méthodes
	
}
