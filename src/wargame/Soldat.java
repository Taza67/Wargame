package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import wargame.Obstacle.TypeObstacle;

public abstract class Soldat extends Element implements IConfig, ISoldat {
	// Infos
	private final int POINTS_DE_VIE_MAX, PORTEE_VISUELLE, PORTEE_DEPLACEMENT, PUISSANCE, TIR;
	private int pointsDeVie;
	private ZoneH zoneVisuelle;
	private List<Element> zoneDeplacement;
	
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
		this.calculerZoneVisuelle();
		// // Zone Deplacement
		this.calculerZoneDeplacement();
		this.creerHex();
	}

	// Accesseurs
	public int getPOINTS_DE_VIE_MAX() { return POINTS_DE_VIE_MAX; }
	public int getPORTEE_VISUELLE() { return PORTEE_VISUELLE; }
	public int getPORTEE_DEPLACEMENT() { return PORTEE_DEPLACEMENT; }
	public int getPUISSANCE() { return PUISSANCE; }
	public int getTIR() { return TIR; }
	public int getPointsDeVie() { return pointsDeVie; }
	public ZoneH getZoneVisuelle() { return zoneVisuelle; }
	public List<Element> getZoneDeplacement() { return zoneDeplacement; }
	
	// Mutateurs
	public void setPointsDeVie(int pointsDeVie) { this.pointsDeVie = pointsDeVie; }

	// Méthodes
	// Met à jour la zone visuelle du soldat
	public void majZoneVisuelle() {
		calculerZoneVisuelle();
	}
	// Met à jour la zone de déplacement du soldat
	public void majZoneDeplacement() {
		calculerZoneDeplacement();
	}
	// Calcule la zone visuelle du soldat
	public void calculerZoneVisuelle() {
		zoneVisuelle = new ZoneH(pos.toPositionAxiale(), PORTEE_VISUELLE, carte);
		List<Element> realZoneV = new ArrayList<Element>();
		List<Element> zoneV = zoneVisuelle.getZone();
		for (Element e : zoneV) {
			LigneH ligne = new LigneH(this, e, carte);
			List<Element> ligneBis = ligne.getLigne();
			for (Element eL : ligneBis) {
				realZoneV.add(eL);
				if (eL instanceof Obstacle && ((Obstacle)eL).getTYPE() != TypeObstacle.EAU)
					break;
			}
		}
		zoneVisuelle.setZone(realZoneV);
	}
	// Calcule la zone de déplacement du soldat
	public void calculerZoneDeplacement() {
		zoneDeplacement = new ArrayList<Element>();
		List<List<Position>> zoneDeplacementBis = new ArrayList<List<Position>>();
		//// Couleurs : 0 = BLANC, 1 = NOIR
		int[][] couleurs = new int[carte.getHautC()][carte.getLargC()];
		for (int i = 0; i < carte.getHautC(); i++)
			for (int j = 0; j < carte.getLargC(); j++)
				couleurs[i][j] = 0;
		// Calcul de la zone de déplacement
		zoneDeplacementBis.add(new ArrayList<Position>());
		zoneDeplacementBis.get(0).add(pos);
		for (int k = 1; k < PORTEE_DEPLACEMENT; k++) {
			zoneDeplacementBis.add(new ArrayList<Position>());
			for (Position p : zoneDeplacementBis.get(k - 1)) {
				PositionAxiale pA = p.toPositionAxiale();
				for (int d = 0; d < 6; d++) {
					PositionAxiale vA = pA.voisin(d);
					Position v = vA.toPosition();
					if (carte.getElement(v) instanceof Sol && couleurs[v.getY()][v.getX()] != 1) {
						zoneDeplacementBis.get(k).add(v);
						couleurs[v.getY()][v.getX()] = 1;
					}
				}
			}
		}
		// Changement d'échelle
		for (List<Position> ligne : zoneDeplacementBis)
			for (Position pos : ligne)
				zoneDeplacement.add(carte.getElement(pos));
			
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
		possible = possible && (zoneDeplacement.indexOf(carte.getElement(cible)) != -1);
		if (possible) {
			carte.setElement(cible, this);												// Le soldat se déplace à la position où il doit être
			carte.setElement(pos, new Sol(carte, 										// L'ancienne position du soldat = sol										 			
										  new Position(pos.getX(), pos.getY())));
			// Les coordonnées du soldat doivent changer
			pos.setX(cible.getX());
			pos.setY(cible.getY());
			// Découverte de nouvelle terres :)
			majZoneVisuelle();
			majZoneDeplacement();
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
	// Retourne un des éléments de la liste donnée aléatoirement
	public Element aleaElem(List<Element> listeElem) {
		int t = listeElem.size(),
			alea = Carte.alea(0, t - 1);
		return listeElem.get(alea);
	}
	
	// Méthodes graphiques
	// Dessine un cadre autoure des éléments pour montrer la zone de déplacement du soldat
	public void dessinerZoneDeplacement(Graphics g) {
		for (Element e : zoneDeplacement)
			e.seDessinerCadre(g, Color.white);
	}
	// Dessine un cadre autour des éléments dans la mini-map
	public void dessinerZoneDeplacementMM(Graphics g) {
		for (Element e : zoneDeplacement)
			e.seDessinerCadreMM(g, Color.white);
	}
}
