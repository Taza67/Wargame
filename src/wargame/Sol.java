package wargame;

public class Sol extends Element implements IConfig {
	// Infos
	
	// Constructeurs
	public Sol(Carte carte, Position pos) { 
		this.carte = carte;
		this.pos = pos;
		this.numTexture = 0;
		creerHex();
	}
}
