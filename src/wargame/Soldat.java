package wargame;

import java.awt.Graphics;

public abstract class Soldat extends Element implements IConfig, ISoldat {
	// Infos
	private final int POINTS_DE_VIE_MAX, PORTEE_VISUELLE, PORTEE_DEPLACEMENT, PUISSANCE, TIR;
	private int pointsDeVie;
	private Zone zoneVisuelle;
	private Zone zoneDeplacement;
	
	// Constructeurs
	Soldat(Carte carte, Position pos, int pts, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
		this.carte = carte;
		this.pos = pos;
		POINTS_DE_VIE_MAX = pointsDeVie = pts;
		PORTEE_VISUELLE = porteeVisuelle; 
		PORTEE_DEPLACEMENT = porteeDeplacement;
		PUISSANCE = puissance;
		TIR = tir;
		// Initialisation des zones d'action du soldat
		// // Zone Visuelle
		zoneVisuelle = new Zone(carte, pos, porteeVisuelle, porteeVisuelle);
		// // Zone Deplacement
		zoneDeplacement = new Zone(carte, pos, porteeDeplacement, porteeDeplacement);
	}

	// Accesseurs
	public int getPOINTS_DE_VIE_MAX() { return POINTS_DE_VIE_MAX; }
	public int getPORTEE_VISUELLE() { return PORTEE_VISUELLE; }
	public int getPORTEE_DEPLACEMENT() { return PORTEE_DEPLACEMENT; }
	public int getPUISSANCE() { return PUISSANCE; }
	public int getTIR() { return TIR; }
	public int getPointsDeVie() { return pointsDeVie; }
	public Zone getZoneVisuelle() { return zoneVisuelle; }
	public Zone getZoneDeplacement() { return zoneDeplacement; }
	
	// Mutateurs
	public void setPointsDeVie(int pointsDeVie) { this.pointsDeVie = pointsDeVie; }
	public void setZoneVisuelle(Zone zoneVisuelle) { this.zoneVisuelle = zoneVisuelle; }
	public void setZoneDeplacement(Zone zoneDeplacement) { this.zoneDeplacement = zoneDeplacement; }
	
	// Méthodes
	// Met à jour la zone visuelle du soldat
	public void majZoneVisuelle(Position pos) {
		zoneVisuelle.setExtHautGauche(zoneVisuelle.calculerExtHautGauche(pos, PORTEE_VISUELLE, PORTEE_VISUELLE));
		zoneVisuelle.setExtBasDroit(zoneVisuelle.calculerExtBasDroit(pos, PORTEE_VISUELLE, PORTEE_VISUELLE));
	}
	// Met à jour la zone de déplacement du soldat
	public void majZoneDeplacement(Position pos) {
		zoneDeplacement.setExtHautGauche(zoneVisuelle.calculerExtHautGauche(pos, PORTEE_DEPLACEMENT, PORTEE_DEPLACEMENT));
		zoneDeplacement.setExtBasDroit(zoneVisuelle.calculerExtBasDroit(pos, PORTEE_DEPLACEMENT, PORTEE_DEPLACEMENT));
	}
	// Renvoie les infos du soldat
	public String toString() {
		String desc = super.toString();
		desc += "[ Vie : " + pointsDeVie + " / " + POINTS_DE_VIE_MAX + " ] | ";
		desc += "[ Puissance : " + PUISSANCE + " ] | ";
		desc += "[ Puissance de tir : " + TIR + " ]";
		return desc;
	}
}
