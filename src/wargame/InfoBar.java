package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class InfoBar {
	// Infos
	private Carte carte;
	private Element selection;
	private Element curseur;
	
	// Constructeurs
	public InfoBar(Carte carte, Element selection, Element curseur) {
		this.carte = carte;
		this.selection = selection;
		this.curseur = curseur;
	}
	
	// Accesseurs
	public Element getSelection() { return selection; }
	public Element getCurseur() { return curseur; }
	
	// Mutateurs
	public void setSelection(Element selection) { this.selection = selection; }
	public void setCurseur(Element curseur) { this.curseur = curseur; }
	
	// Méthodes graphiques
	public void seDessiner(Graphics g) {
		String affSelec = "Selectionné : " + selection,
			   affCur = "Curseur : " + curseur;
		g.setColor(Color.white);
		g.drawString(affSelec + " " + affCur, 15, 30);
	}
}
