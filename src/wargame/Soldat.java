package wargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import wargame.Obstacle.TypeObstacle;

public abstract class Soldat extends Element implements IConfig, ISoldat {
	// Infos
	private int POINTS_DE_VIE_MAX, PORTEE_DEPLACEMENT_MAX;;
	private int pointsDeVie,
				porteeVisuelle,
				porteeDeplacement,
				puissance,
				tir,
				guerison;
	private Sol sol;
	private ZoneH zoneVisuelle;
	private List<Element> zoneDeplacement;
	
	// Constructeurs
	public Soldat(Carte carte, Position pos, int pts, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
		this.carte = carte;
		this.pos = pos;
		POINTS_DE_VIE_MAX = pointsDeVie = pts;
		this.porteeVisuelle = porteeVisuelle;
		PORTEE_DEPLACEMENT_MAX = this.porteeDeplacement = porteeDeplacement;
		this.puissance = puissance;
		this.tir = tir;
		this.guerison = Math.min(pts / 10, 5);
		sol = (Sol)carte.getElement(pos);
		// Application des effets du sol
		sol.appliquerEffets(this);
		// Initialisation des zones d'action du soldat
		// // Zone Visuelle
		this.calculerZoneVisuelle();
		// // Zone Deplacement
		this.calculerZoneDeplacement();
		this.creerHex();
	}

	// Accesseurs
	public int getPOINTS_DE_VIE_MAX() { return POINTS_DE_VIE_MAX; }
	public int getPointsDeVie() { return pointsDeVie; }
	public int getPorteeVisuelle() { return porteeVisuelle; }
	public int getPORTEE_DEPLACEMENT_MAX() { return PORTEE_DEPLACEMENT_MAX; }
	public int getPorteeDeplacement() { return porteeDeplacement; }
	public int getPuissance() { return puissance; }
	public int getTir() { return tir; }
	public int getGuerison() { return guerison; }
	public Sol getSol() { return sol; }
	public ZoneH getZoneVisuelle() { return zoneVisuelle; }
	public List<Element> getZoneDeplacement() { return zoneDeplacement; }
	
	// Mutateurs
	public void setPointsDeVie(int pointsDeVie) { this.pointsDeVie = pointsDeVie; }
	public void setPorteeVisuelle(int porteeVisuelle) { this.porteeVisuelle = porteeVisuelle; }
	public void setPorteeDeplacement(int porteeDeplacement) { this.porteeDeplacement = porteeDeplacement; }
	public void setPuissance(int puissance) { this.puissance = puissance; }
	public void setTir(int tir) { this.tir = tir; }
	public void setGuerison(int guerison) { this.guerison = guerison; }
	
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
		zoneVisuelle = new ZoneH(pos.toPositionAxiale(), porteeVisuelle, carte);
		List<Element> realZoneV = new ArrayList<Element>();
		List<Element> zoneV = zoneVisuelle.getZone();
		for (Element e : zoneV) {
			LigneH ligne = new LigneH(this, e, carte);
			List<Element> ligneBis = ligne.getLigne();
			boolean obsTrouve = false;
			for (Element eL : ligneBis) {
				if (!obsTrouve && eL instanceof Obstacle && ((Obstacle)eL).getTYPE() != TypeObstacle.EAU)
					obsTrouve = true;
				if (obsTrouve && !(eL instanceof Obstacle && ((Obstacle)eL).getTYPE() != TypeObstacle.EAU)) break;
				realZoneV.add(eL);
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
		for (int k = 1; k < porteeDeplacement + 1; k++) {
			zoneDeplacementBis.add(new ArrayList<Position>());
			for (Position p : zoneDeplacementBis.get(k - 1)) {
				PositionAxiale pA = p.toPositionAxiale();
				for (int d = 0; d < 6; d++) {
					PositionAxiale vA = pA.voisin(d);
					Position v = vA.toPosition();
					if ((carte.getElement(v) instanceof Sol || carte.getElement(v) instanceof Monstre) && couleurs[v.getY()][v.getX()] != 1) {
						zoneDeplacementBis.get(k).add(v);
						couleurs[v.getY()][v.getX()] = 1;
					}
				}
			}
		}
		// Changement d'échelle
		zoneDeplacement.add(this);
		for (List<Position> ligne : zoneDeplacementBis)
			for (Position pos : ligne)
				zoneDeplacement.add(carte.getElement(pos));
			
	}
	// Renvoie les infos du soldat
	public String toString() {
		String desc = super.toString();
		if (visible == true) {
			desc += " : [ Vie : " + pointsDeVie + " / " + POINTS_DE_VIE_MAX + " ] | ";
			desc += "[ Puissance : " + puissance + " ] | ";
			desc += "[ Puissance de tir : " + tir + " ]";
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
			carte.setElement(pos, sol);													// Position actuelle du soldat libre
			sol.creerHexM();
			sol.enleverEffets(this);													// On enlève d'abord les effets du sol actuel
			this.sol = (Sol)carte.getElement(cible);									// On récupère le sol cible
			carte.setElement(cible, this);												// Le soldat se déplace à la position où il doit être
			sol.appliquerEffets(this);													// On applique les effets du sol cible au soldat
			// Les coordonnées du soldat doivent changer
			pos.setX(cible.getX());
			pos.setY(cible.getY());
			// Découverte de nouvelle terres :)
			majZoneVisuelle();
			creerHex();
		}
		return possible;
	}
	// Attaque le soldat au corps-à-corps
	public void attaqueSoldatCorps(Soldat adv) {
		int advPDV = adv.pointsDeVie,
			advPow = adv.puissance,
			difPow = puissance - advPow;
		advPDV -= difPow;
		pointsDeVie += difPow;
		
		advPDV = Math.min(advPDV, adv.pointsDeVie);
		pointsDeVie = Math.min(pointsDeVie, POINTS_DE_VIE_MAX);
		
		adv.pointsDeVie = advPDV;
		if (advPDV <= 0) {
			carte.mort(adv);
			this.seDeplace(adv.pos);
		}
		if (pointsDeVie <= 0) carte.mort(this);
	}
	// Attaque le soldat à distance
	public void attaqueSoldatDistance(Soldat adv) {
		int advPDV = adv.pointsDeVie;
		advPDV -= tir;
		adv.pointsDeVie = advPDV;
		if (advPDV <= 0) carte.mort(adv);
		else adv.attaqueSoldatDistance(this);
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
	// Retourne les points de vie du soldat sous forme de chaine de caractères
	public String getStringPdv() {
		return pointsDeVie + " / " + POINTS_DE_VIE_MAX;
	}
	// Retourne la portee de déplacement du soldat sous forme de chaine de caractères
	public String getStringDep() {
		return porteeDeplacement + " / " + (porteeDeplacement - sol.getTYPE().getEFFET_PORTEE_DEPLACEMENT());
	}
	// Retourne la portee visuelle du soldat sous forme de chaine de caractères
	public String getStringVisuel() {
		return porteeVisuelle + " / " + (porteeVisuelle - sol.getTYPE().getEFFET_PORTEE_VISUELLE());
	}
	// Retourne la puissance du soldat sous forme de chaine de caractères
	public String getStringPow() {
		return puissance + " / " + (puissance - sol.getTYPE().getEFFET_PUISSANCE());
	}
	// Retourne la puissance de tir du soldat sous forme de chaine de caractères
	public String getStringTir() {
		return tir + " / " + (tir - sol.getTYPE().getEFFET_TIR());
	}
	
	// Méthodes graphiques
	// Dessine un cadre autoure des éléments pour montrer la zone de déplacement du soldat
	public void dessinerZoneDeplacement(Graphics2D g) {
		int n = 0, m = 0;
		for (Element e : zoneDeplacement)
			if (! e.pos.equals(this.pos))
				e.seDessinerCadre(g, Color.white);
	}
	// Dessine un cadre autour des éléments dans la mini-map
	public void dessinerZoneDeplacementMM(Graphics2D g) {
		for (Element e : zoneDeplacement)
			if (!e.pos.equals(this.pos))
				e.seDessinerCadreMM(g, Color.white);
	}
}
