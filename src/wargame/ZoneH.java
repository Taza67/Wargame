package wargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class ZoneH {
	// Infos
	private Carte carte;
	private PositionAxiale centre;
	private int rayon;
	List<Element> zone;
	
	// Constructeurs
	public ZoneH(PositionAxiale centre, int rayon, Carte carte) {
		this.carte = carte;
		this.centre = centre;
		this.rayon = rayon;
		zone = new ArrayList<Element>();
		calculerZone();
	}
	
	// Accesseurs
	public PositionAxiale getCentre() { return centre; }
	public int getRayon() { return rayon; }
	public List<Element> getZone() { return zone; }
	
	// Mutateurs
	public void setCentre(PositionAxiale centre) { this.centre = centre; }
	public void setRayon(int rayon) { this.rayon = rayon; }
	public void setZone(List<Element> zone) { this.zone = zone; }
	
	// Méthodes
	// Calcule la zone
	public void calculerZone() {
		for (int q = -rayon; q <= rayon; q++) {
			int borneInf = (-rayon > -q - rayon) ? -rayon : -q - rayon,
				borneSup = (+rayon < -q + rayon) ? +rayon : -q + rayon;
			for (int r = borneInf; r <= borneSup; r++) {
				PositionAxiale tmp = centre.add(new PositionAxiale(q, r));
				Position tmp2 = tmp.toPosition();
				if (carte.getElement(tmp2) != null) zone.add(carte.getElement(tmp2));
			}
		}
	}
	// Mets à jour la zone
	public void maj() {
		zone = new ArrayList<Element>();
		calculerZone();
	}
	// Vérifie si l'élément donné est dans la zone ou pas
	public boolean contient(Element elem) {
		return zone.contains(elem);
	}
	// Rend la zone visible sur la carte
	public void rendreVisible() {
		for (Element e : zone)
			if (e != null) e.visible = true;
	}
	
	// Méthodes graphiques
	// Dessine un cadre autoure des éléments pour montrer la zone de déplacement du soldat
	public void seDessiner(Graphics2D g) {
		for (Element e : zone)
			if (e instanceof Monstre)
				e.seDessinerCadre(g, Color.red);
	}
	
}
