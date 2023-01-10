package wargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




/**
 * <b>Classe ZoneH implémente Serializable</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une carte</li>
 * <li>Un centre sous forme de positionAxiale</li>
 * <li>Un rayon(int)</li>
 * <li>Une Zone sous la forme d'une liste d'éléments</li>
 * </ul>
 * 
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */
public class ZoneH implements Serializable {
	private static final long serialVersionUID = 4780267211740884717L;
	/**
	 * Carte
	 */
	private Carte carte;
	/**
	 * Centre (PositionAxiale)
	 */
	private PositionAxiale centre;
	/**
	 * rayon (int)
	 */
	private int rayon;
	/**
	 * zone Liste des élements dans une zone
	 */
	List<Element> zone;
	
	/**
	 *  Constructeur
	 * @param centre
	 * @param rayon
	 * @param carte
	 */
	public ZoneH(PositionAxiale centre, int rayon, Carte carte) {
		this.carte = carte;
		this.centre = centre;
		this.rayon = rayon;
		zone = new ArrayList<Element>();
		calculerZone();
	}
	
	/**
	 * Accesseur du centre
	 * @return centre (PositionAxiale)
	 */
	public PositionAxiale getCentre() { return centre; }
	/**
	 * Accesseur du rayon
	 * @return rayon (int)
	 */
	public int getRayon() { return rayon; }
	/**
	 * Accesseur de la zone
	 * @return liste des éléments dans une zone
	 */
	public List<Element> getZone() { return zone; }
	
	/**
	 * Mutateur du centre
	 * @param centre
	 */
	public void setCentre(PositionAxiale centre) { this.centre = centre; }
	
	/**
	 * Mutateur du rayon
	 * @param rayon
	 */
	public void setRayon(int rayon) { this.rayon = rayon; }
	
	/**
	 * Mutatateur de la zone
	 * @param zone
	 */
	public void setZone(List<Element> zone) { this.zone = zone; }
	
	
	/**
	 *  Calcule la zone
	 */
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
	/**
	 *  Mets à jour la zone
	 */
	public void maj() {
		zone = new ArrayList<Element>();
		calculerZone();
	}
	/**
	 *  Vérifie si l'élément donné est dans la zone ou pas
	 * @param elem
	 * @return bool
	 */
	public boolean contient(Element elem) {
		return zone.contains(elem);
	}
	/**
	 * Rend la zone visible sur la carte
	 */
	public void rendreVisible() {
		for (Element e : zone)
			if (e != null) e.visible = true;
	}
	

	/**
	 *  Dessine un cadre autour des éléments pour montrer la zone de déplacement du soldat
	 * @param g
	 */
	public void seDessiner(Graphics2D g) {
		for (Element e : zone)
			if (e instanceof Monstre)
				e.seDessinerCadre(g, Color.red);
	}
	
}
