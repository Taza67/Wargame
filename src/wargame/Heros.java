package wargame;

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
	
	// Méthodes graphiques
	public void seDessiner(Graphics g) {
		g.setColor(COULEUR_HEROS);
		super.seDessiner(g);
	}
}
