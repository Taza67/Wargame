package wargame;

import wargameInterface.PanneauPartie;

public abstract class AConfig {
	// Map
	public static int POSITION_X = 100, POSITION_Y = 50;			// Position de la fenêtre
	public static int LARGEUR_MAP = 1250, HAUTEUR_MAP = 760;
	protected PanneauPartie panPartie;
	protected int largC, hautC;										// Dimensions de la carte réelle
	protected static int largAffC, hautAffC;						// Dimensions de la carte affichée 
	protected int hautMM, largMM;									// Hauteur de la mini-map
	protected static int rayonHex = 25;								// Rayon d'un hexagone
	protected int rayonMM;
	// Positionnement
	protected Point origine, origineMM;								// Origine de la carte affichée
	// Limites
	protected int nbHeros, nbMonstres;								// Nombre de héros, monstres
	
	// Accesseurs
	public PanneauPartie getPanPartie() { return panPartie; }
	public int getLargC() { return largC; }
	public int getHautC() { return hautC; }
	public int getLargAffC() { return largAffC; }
	public int getHautAffC() { return hautAffC; }
	public int getLargMM() { return largMM; }
	public int getHautMM() { return hautMM; }
	public int getRayonHex() { return rayonHex; }
	public int getRayonMM() { return rayonMM; }
	public Point getOrigine() { return origine; }
	public Point getOrigineMM() { return origineMM; }
}
