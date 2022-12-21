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
	// Dessine l'héros sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		g.setColor(COULEUR_HEROS);
		super.seDessiner(g, type);
	}
	// Dessine l'héros avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, char type, Color couleurCadre) {
		g.setColor(COULEUR_HEROS);
		super.seDessinerCadre(g, type, couleurCadre);
	}
}
