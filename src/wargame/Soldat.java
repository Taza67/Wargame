package wargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import wargame.Obstacle.TypeObstacle;

public abstract class Soldat extends Element implements IConfig, ISoldat {
	private static final long serialVersionUID = -7215342942160817886L;
	// Infos
	private final int POINTS_DE_VIE_MAX, PORTEE_DEPLACEMENT_MAX,
					  PORTEE_VISUELLE_BASE, PUISSANCE_BASE, TIR_BASE, GUERISON_BASE;
	private int pointsDeVie,
				porteeVisuelle,
				porteeDeplacement,
				puissance,
				tir,
				guerison;
	private Sol sol;
	private ZoneH zoneVisuelle;
	private List<Element> zoneDeplacement;
	private boolean aJoue = false;
	
	// Constructeurs
	public Soldat(Carte carte, Position pos, int pts, int porteeVisuelle, int porteeDeplacement, int puissance, int tir) {
		this.carte = carte;
		this.setPos(pos);
		this.POINTS_DE_VIE_MAX = this.pointsDeVie = pts;
		this.PORTEE_VISUELLE_BASE = this.porteeVisuelle = porteeVisuelle;
		this.PORTEE_DEPLACEMENT_MAX = this.porteeDeplacement = porteeDeplacement;
		this.PUISSANCE_BASE = this.puissance = puissance;
		this.TIR_BASE = this.tir = tir;
		this.GUERISON_BASE = this.guerison = Math.min(pts / 10, 5);
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
	public int getPORTEE_VISUELLE_BASE() { return PORTEE_VISUELLE_BASE; }
	public int getPUISSANCE_BASE() { return PUISSANCE_BASE; }
	public int getTIR_BASE() { return TIR_BASE; }
	public int getGUERISON_BASE() { return GUERISON_BASE;}
	public int getPORTEE_DEPLACEMENT_MAX() { return PORTEE_DEPLACEMENT_MAX; }
	public int getPointsDeVie() { return pointsDeVie; }
	public int getPorteeVisuelle() { return porteeVisuelle; }
	public int getPorteeDeplacement() { return porteeDeplacement; }
	public int getPuissance() { return puissance; }
	public int getTir() { return tir; }
	public int getGuerison() { return guerison; }
	public Sol getSol() { return sol; }
	public ZoneH getZoneVisuelle() { return zoneVisuelle; }
	public List<Element> getZoneDeplacement() { return zoneDeplacement; }
	public boolean getAJoue() { return aJoue; }
	// Pseudo-accesseurs
	// Retourne les points de vie du soldat sous forme de chaine de caractères
	public String getStringPdv() { return pointsDeVie + " / " + POINTS_DE_VIE_MAX; }
	// Retourne la portee de déplacement du soldat sous forme de chaine de caractères
	public String getStringDep() { return porteeDeplacement + " / " + PORTEE_DEPLACEMENT_MAX; }
	// Retourne la portee visuelle du soldat sous forme de chaine de caractères
	public String getStringVisuel() { return porteeVisuelle + " / " + PORTEE_VISUELLE_BASE; }
	// Retourne la puissance du soldat sous forme de chaine de caractères
	public String getStringPow() { return puissance + " / " + PUISSANCE_BASE; }
	// Retourne la puissance de tir du soldat sous forme de chaine de caractères
	public String getStringTir() { return tir + " / " + TIR_BASE; }
	
	// Mutateurs
	public void setPointsDeVie(int pointsDeVie) { this.pointsDeVie = pointsDeVie; }
	public void setPorteeVisuelle(int porteeVisuelle) { this.porteeVisuelle = porteeVisuelle; }
	public void setPorteeDeplacement(int porteeDeplacement) { this.porteeDeplacement = porteeDeplacement; }
	public void setPuissance(int puissance) { this.puissance = puissance; }
	public void setTir(int tir) { this.tir = tir; }
	public void setGuerison(int guerison) { this.guerison = guerison; }
	public void setAJoue(boolean aJoue) { this.aJoue = aJoue; }
	
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
		zoneVisuelle = new ZoneH(getPos().toPositionAxiale(), porteeVisuelle, carte);
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
		zoneDeplacement = new LinkedList<Element>();
		List<List<Position>> zoneDeplacementBis = new ArrayList<List<Position>>();
		//// Couleurs : 0 = BLANC, 1 = NOIR
		int[][] couleurs = new int[carte.getHAUTC()][carte.getLARGC()];
		for (int i = 0; i < carte.getHAUTC(); i++)
			for (int j = 0; j < carte.getLARGC(); j++)
				couleurs[i][j] = 0;
		// Calcul de la zone de déplacement
		zoneDeplacementBis.add(new ArrayList<Position>());
		zoneDeplacementBis.get(0).add(getPos());
		for (int k = 1; k < porteeDeplacement + 1; k++) {
			zoneDeplacementBis.add(new ArrayList<Position>());
			for (Position p : zoneDeplacementBis.get(k - 1)) {
				PositionAxiale pA = p.toPositionAxiale();
				for (int d = 0; d < 6; d++) {
					PositionAxiale vA = pA.voisin(d);
					Position v = vA.toPosition();
					if ((carte.getElement(v) instanceof Sol ||
						((this instanceof Heros && carte.getElement(v) instanceof Monstre) ||
						(this instanceof Monstre && carte.getElement(v) instanceof Heros))) && couleurs[v.getY()][v.getX()] != 1) {
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
	// Déplace le soldat à la position pos
	public boolean seDeplace(Position cible) {
		boolean possible = true;
		// Vérifications
		possible = possible && !(getPos().equals(cible));									// Pas la même position que l'actuelle ?
		possible = possible && carte.getElement(cible) instanceof Sol;					// Position cible libre ?
		possible = possible && (zoneDeplacement.indexOf(carte.getElement(cible)) != -1);
		if (possible) {
			carte.setElement(getPos(), sol);													// Position actuelle du soldat libre
			// L'élément sélectionné ou celui focalisé par le curseur doivent peut-être changé
			if (carte.getSelection() != null && carte.getSelection().getPos().equals(getPos())) carte.setSelection(sol);
			if (carte.getCurseur() != null && carte.getCurseur().getPos().equals(getPos())) carte.setCurseur(sol);
			sol.enleverEffetPorteeVisuelle(this);
			sol.creerHexM();
			this.sol = (Sol)carte.getElement(cible);									// On récupère le sol cible
			sol.appliquerEffetPorteeVisuelle(this);
			carte.setElement(cible, this);												// Le soldat se déplace à la position où il doit être
			if (this instanceof Monstre)
				visible = sol.visible;
			// Les coordonnées du soldat doivent changer
			getPos().setX(cible.getX());
			getPos().setY(cible.getY());
			// L'élément sélectionné ou celui focalisé par le curseur doivent peut-être changé
			if (carte.getSelection() != null && carte.getSelection().getPos().equals(this.getPos())) carte.setSelection(this);
			if (carte.getCurseur() != null && carte.getCurseur().getPos().equals(this.getPos())) carte.setCurseur(this);
			// Découverte de nouvelle terres :)
			majZoneVisuelle();
			creerHex();
		}
		return possible;
	}
	// Attaque le soldat au corps-à-corps
	public void attaqueSoldatCorps(Soldat adv) {
		int advPDV = adv.pointsDeVie,
			ptsAttaque = Carte.alea(3, puissance),
			advPtsAttaque = Carte.alea(3, adv.puissance);
		advPDV -= ptsAttaque;
		advPDV = Math.min(advPDV, adv.pointsDeVie);
		adv.pointsDeVie = advPDV;
		if (advPDV <= 0) {
			carte.mort(adv);
			this.seDeplace(adv.getPos());
			sol.appliquerEffets(this);
		} else
			pointsDeVie = Math.min(pointsDeVie - advPtsAttaque, pointsDeVie);
		if (pointsDeVie <= 0)
			carte.mort(this);
	}
	// Attaque le soldat à distance
	public void attaqueSoldatDistance(Soldat adv) {
		int advPDV = adv.pointsDeVie;
		advPDV -= Carte.alea(3, tir);
		advPDV = Math.min(advPDV, adv.pointsDeVie);
		adv.pointsDeVie = advPDV;
		if (advPDV <= 0) carte.mort(adv);
		else
			pointsDeVie = Math.min(pointsDeVie - Carte.alea(3, adv.tir), pointsDeVie);
		if (pointsDeVie <= 0)
			carte.mort(this);
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
	// Applique les points de guérison
	public void guerir() {
		pointsDeVie = Math.min(POINTS_DE_VIE_MAX, pointsDeVie + guerison);
	}
	// Vérifie si la zone de déplacement contient un élément cible
	public boolean zoneDeplacementContient(Element e) {
		return zoneDeplacement.indexOf(e) != -1;
	}
	// Vérifie si le soldat est mourrant ou pas
	public boolean estMourrant() {
		return pointsDeVie < POINTS_DE_VIE_MAX / 5;
	}
	
	// Méthodes graphiques
	// Dessine un cadre autoure des éléments pour montrer la zone de déplacement du soldat
	public void dessinerZoneDeplacement(Graphics2D g) {
		if (!aJoue) {
			for (Element e : zoneDeplacement)
				if (!e.getPos().equals(this.getPos())) {
					if (e instanceof Monstre)
						e.seDessinerCadre(g, Color.red);
					else
						e.seDessinerCadre(g, Color.white);
				}
		}
	}
	// Dessine le soldat
	public void seDessiner(Graphics2D g) {
		if (aJoue && visible)										// Si le soldat a déjà joué
			super.seDessinerCadre(g, Color.black);
		else
			super.seDessiner(g);
	}
}
