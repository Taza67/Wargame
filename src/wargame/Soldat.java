package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class Soldat extends Element implements IConfig, ISoldat {
	// Infos
	private final int POINTS_DE_VIE_MAX, PORTEE_VISUELLE, PORTEE_DEPLACEMENT, PUISSANCE, TIR;
	private int pointsDeVie;
	private ZoneH zoneVisuelle;
	private List<Position> zoneDeplacement;
	
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
		zoneVisuelle = new ZoneH(pos.toPositionAxiale(), porteeVisuelle, carte);
		// // Zone Deplacement
		calculerZoneDeplacement();
		creerHex();
	}

	// Accesseurs
	public int getPOINTS_DE_VIE_MAX() { return POINTS_DE_VIE_MAX; }
	public int getPORTEE_VISUELLE() { return PORTEE_VISUELLE; }
	public int getPORTEE_DEPLACEMENT() { return PORTEE_DEPLACEMENT; }
	public int getPUISSANCE() { return PUISSANCE; }
	public int getTIR() { return TIR; }
	public int getPointsDeVie() { return pointsDeVie; }
	public ZoneH getZoneVisuelle() { return zoneVisuelle; }
	public List<Position> getZoneDeplacement() { return zoneDeplacement; }
	
	// Mutateurs
	public void setPointsDeVie(int pointsDeVie) { this.pointsDeVie = pointsDeVie; }

	// Méthodes
	// Met à jour la zone visuelle du soldat
	public void majZoneVisuelle() {
		zoneVisuelle.setCentre(pos.toPositionAxiale());
		zoneVisuelle.calculerZone();
	}
	// Met à jour la zone de déplacement du soldat
	public void majZoneDeplacement() {
		calculerZoneDeplacement();
	}
	// Calcule la zone de déplacement du soldat
	public void calculerZoneDeplacementBis(Position pivot, int nbPas, int[][] couleurs) {
		int x = pivot.getX(),
			y = pivot.getY();
		if (nbPas > 0 && couleurs[y][x] != 2) {
			for (int i = y - 1; i <= y + 1; i++)
				for (int j = x - 1; j <= x + 1; j++) {
					Position tmp = new Position(j, i);
					if (tmp.estValide(carte.getLargC(), carte.getHautC()) && carte.getElement(tmp) instanceof Sol) {
						couleurs[i][j] = 1;
						calculerZoneDeplacementBis(tmp, nbPas - 1, couleurs);
					}
				}
			couleurs[y][x] = 2;
			zoneDeplacement.add(pivot);
		}
	}
	public void calculerZoneDeplacement() {
		// Initialisations
		zoneDeplacement = new ArrayList<Position>();
		// Couleurs : 1 = BLANC, 2 = GRIS, 3 = NOIR
		int[][] couleurs = new int[carte.getHautC()][carte.getLargC()];
		for (int i = 0; i < carte.getHautC(); i++)
			for (int j = 0; j < carte.getLargC(); j++)
				couleurs[i][j] = 0;
		calculerZoneDeplacementBis(this.pos, PORTEE_DEPLACEMENT, couleurs);
	}
	// Renvoie les infos du soldat
	public String toString() {
		String desc = super.toString();
		if (visible == true) {
			desc += " : [ Vie : " + pointsDeVie + " / " + POINTS_DE_VIE_MAX + " ] | ";
			desc += "[ Puissance : " + PUISSANCE + " ] | ";
			desc += "[ Puissance de tir : " + TIR + " ]";
		}
		return desc;
	}
	
	// Déplace le soldat à la position pos
	public boolean seDeplace(Position cible) {
		boolean possible = true;
		// Vérifications
		possible = possible && !(pos.equals(cible));									// Pas la même position que l'actuelle ?
		possible = possible && carte.getElement(cible) instanceof Sol;					// Position cible libre ?
		possible = possible && (zoneDeplacement.indexOf(cible) != -1);
		if (possible) {
			carte.setElement(cible, this);												// Le soldat se déplace à la position où il doit être
			carte.setElement(pos, new Sol(carte, 										// L'ancienne position du soldat = sol										 			
										  new Position(pos.getX(), pos.getY())));
			carte.getElement(pos).visible = true;
			// Les coordonnées du soldat doivent changer
			pos.setX(cible.getX());
			pos.setY(cible.getY());
			// Découverte de nouvelle terres :)
			majZoneVisuelle();
			majZoneDeplacement();
			if (this instanceof Heros) this.getZoneVisuelle().rendreVisible();
			creerHex();
		}
		return possible;
	}
	// Attaque le soldat au corps-à-corps
	public void attaqueSoldatCorps(Soldat adv) {
		int advPDV = adv.getPointsDeVie(),
			advPow = adv.getPUISSANCE(),
			difPow = PUISSANCE - advPow;
		advPDV -= difPow;
		pointsDeVie += difPow;
		adv.setPointsDeVie(advPDV);
		if (advPDV <= 0) carte.mort(adv);
		if (pointsDeVie <= 0) carte.mort(this);
	}
	// Attaque le soldat à distance
	public void attaqueSoldatDistance(Soldat adv) {
		int advPDV = adv.getPointsDeVie();
		advPDV -= TIR;
		adv.setPointsDeVie(advPDV);
		if (advPDV <= 0) carte.mort(adv);
	}
	// Vérifie si une attaque à distance est possible
	public boolean verifieAttaqueDistance(Soldat adv) {
		return zoneVisuelle.contient(adv);
	}
	
	// Méthodes graphiques
	// Dessine un cadre autoure des éléments pour montrer la zone de déplacement du soldat
	public void dessinerZoneDeplacement(Graphics g) {
		for (Position pos : zoneDeplacement)
			carte.getElement(pos).seDessinerCadre(g, Color.white);
	}
	// Dessine un cadre autour des éléments dans la mini-map
	public void dessinerZoneDeplacementMM(Graphics g) {
		for (Position pos : zoneDeplacement)
			carte.getElement(pos).seDessinerCadreMM(g, Color.white);
	}
}
