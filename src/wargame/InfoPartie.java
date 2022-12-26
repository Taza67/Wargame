package wargame;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

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
	
	// Méthodes graphiques
	public void seDessiner(Graphics g) {
		String nbToursS = "Nombre de tours : " + nbTours,
			   joueurS = "Joueur : " + ((joueur == GENTILS) ? "vous" : "adversaire"),
			   nbHerosS = "Nombre de héros : " + nbHeros,
			   nbMonstresS = "Nombre de Monstres : " + nbMonstres;
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(g.getFont());
		g.setColor(Color.white);
		g.drawString(nbToursS, 10, 10 * 2);
		g.drawString(joueurS, 10, 10 * 3 + metrics.getHeight() * 1);
		g.drawString(nbHerosS, 10, 10 * 4 + metrics.getHeight() * 2);
		g.drawString(nbMonstresS, 10, 10 * 5 + metrics.getHeight() * 3);
	}
	
}
