package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Heros extends Soldat {
	// Infos
	private final String NOM;
	private final TypesH TYPE;
	
	// Constructeur
	public Heros(Carte carte, TypesH type, String nom, Position pos) {
		super(carte, pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
		NOM = nom; TYPE = type;
		visible = true;
		this.getZoneVisuelle().rendreVisible();
	}
	
	// Accesseurs
	public String getNOM() { return NOM; }
	public TypesH getTYPE() { return TYPE; }
	
	// Méthodes
	// Renvoie les infos du héros
	public String toString() {
		String desc = super.toString();
		desc += " | [ Type : " + TYPE + " ]";
		return desc;
	}
	
	// Méthodes graphiques
	// Dessine l'héros
	public void seDessiner(Graphics g) {
		g.setColor(COULEUR_HEROS);
		super.seDessiner(g);
		// super.dessinerZoneDeplacement(g);
	}
	// Dessine l'héros avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, Color couleurCadre) { 
		g.setColor(COULEUR_HEROS);
		super.seDessinerCadre(g, couleurCadre);
	}
	// Dessine l'héros dans la mini-map
	public void seDessinerMM(Graphics g) {
		g.setColor(COULEUR_HEROS);
		super.seDessinerMM(g);
	}
	// Dessine l'héros avec un cadre qui indique son état dans la mini-map
	public void seDessinerCadreMM(Graphics g, Color couleurCadre) { 
		g.setColor(COULEUR_HEROS);
		super.seDessinerCadreMM(g, couleurCadre);
	}
}
