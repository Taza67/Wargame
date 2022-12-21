package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Monstre extends Soldat {
	// Infos
	private final TypesM TYPE;
	
	// Constructeur
	public Monstre(Carte carte, TypesM type, Position pos) {
		super(carte, pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
		TYPE = type;
	}
	
	// Accesseurs
	public TypesM getTYPE() { return TYPE; }
	
	// Méthodes graphiques
	// Dessine le monstre sous sa forme reelle sur la carte ou miniature sur la mini-map en fonction de <type>
	public void seDessiner(Graphics g, char type) {
		g.setColor(COULEUR_MONSTRES);
		super.seDessiner(g, type);
	}
	// Dessine le monstre avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, char type, Color couleurCadre) {
		g.setColor(COULEUR_MONSTRES);
		super.seDessinerCadre(g, type, couleurCadre);
	}
}
