package wargame;

public class InfoPartie implements IConfig {
	// Infos
	private Carte carte;
	private int nbTours;
	private char joueur;
	private int nbHeros, nbMonstres;
	
	// Constructeurs
	public InfoPartie(Carte carte, int nbHeros, int nbMonstres) {
		this.carte = carte;
		this.nbTours = 1;
		this.joueur = GENTILS;
		this.nbHeros = nbHeros;
		this.nbMonstres = nbMonstres;
	}

	// Accesseurs
	public int getNbTours() { return nbTours; }
	public char getJoueur() { return joueur; }
	public int getNbHeros() { return nbHeros; }
	public int getNbMonstres() { return nbMonstres; }

	// Mutateurs
	public void setNbTours(int nbTours) { this.nbTours = nbTours; }
	public void setJoueur(char joueur) { this.joueur = joueur; }
	public void setNbHeros(int nbHeros) { this.nbHeros = nbHeros; }
	public void setNbMonstres(int nbMonstres) { this.nbMonstres = nbMonstres; }
}
