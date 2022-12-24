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
	// Dessine le monstre
	public void seDessiner(Graphics g) {
		g.setColor(COULEUR_MONSTRES);
		super.seDessiner(g);
	}
	// Dessine le monstre avec un cadre qui indique son état (Curseur dessus, Sélectionné, dans Zone Deplacment)
	public void seDessinerCadre(Graphics g, Color couleurCadre) {
		g.setColor(COULEUR_MONSTRES);
		super.seDessinerCadre(g, couleurCadre);
	}
	// Dessine le monstre dans la mini-map
	public void seDessinerMM(Graphics g) {
		g.setColor(COULEUR_MONSTRES);
		super.seDessinerMM(g);
	}
	// Dessine le monstre avec un cadre qui indique son état dans la mini-map
	public void seDessinerCadreMM(Graphics g, Color couleurCadre) { 
		g.setColor(COULEUR_MONSTRES);
		super.seDessinerCadreMM(g, couleurCadre);
	}
}
