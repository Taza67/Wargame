package wargame;

import java.awt.Color;
import java.awt.Font;
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
	// Déplace le soldat à la position pos
	public boolean seDeplace(Position cible) {
		boolean possible = super.seDeplace(cible);
		if (possible) {
			carte.getElement(pos).visible = true;
			this.getZoneVisuelle().rendreVisible();
		}
		return possible;
	}
	
	// Méthodes graphiques
	// Dessine l'héros
	public void seDessiner(Graphics g) {
		g.setColor(COULEUR_HEROS);
		super.seDessiner(g);
		drawCenteredString(g, NOM, (new Font("Courier", Font.BOLD, (int)(hex.getRayon() * 0.75))));
	}
	// Dessine l'héros avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, Color couleurCadre) { 
		g.setColor(COULEUR_HEROS);
		super.seDessinerCadre(g, couleurCadre);
		drawCenteredString(g, NOM, (new Font("Courier", Font.BOLD, (int)(hex.getRayon() * 0.5))));
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
