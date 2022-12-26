package wargame;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class InfoBar {
	// Infos
	private Element selection;
	private Element curseur;
	
	// Constructeurs
	public InfoBar(Element selection, Element curseur) {
		this.selection = selection;
		this.curseur = curseur;
	}
	
	// Accesseurs
	
	// Mutateurs
	public void setSelection(Element selection) { this.selection = selection; }
	public void setCurseur(Element curseur) { this.curseur = curseur; }
	
	// Méthodes graphiques
	public void seDessiner(Graphics g) {
		String affSelec = "Selectionné : " + selection,
			   affCur = "Curseur : " + curseur;
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int largAffSelec = metrics.stringWidth(affSelec);
		g.setColor(Color.orange);
		g.drawString(affSelec, 15, 30);
		g.setColor(Color.yellow);
		g.drawString(affCur, 15 * 2 + largAffSelec, 30);
	}
}
