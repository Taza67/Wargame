package wargame;

import java.awt.Graphics;

public class Carte implements IConfig, ICarte {
	// Infos
	protected Element[][] grille;
	
	// Constructeurs
	public Carte() { 
		grille = new Element[HAUTEUR_CARTE][LARGEUR_CARTE]; 
		int nbObstacles = 0,
			nbHeros = 0,
			nbMonstres = 0;
		while (nbObstacles++ < NB_OBSTACLES) {
			Position posVide = trouvePositionVide();
			int j = posVide.getX(), i = posVide.getY();
			grille[i][j] = new Obstacle(Obstacle.TypeObstacle.getObstacleAlea(), posVide);
		}
		while (nbHeros++ < NB_HEROS) {
			int deb_ligne = 0,
				fin_ligne = HAUTEUR_CARTE - 1,
				deb_colonne = LARGEUR_CARTE / 2,
				fin_colonne = LARGEUR_CARTE - 1;
			Position posVide = trouvePositionVideZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne);
			int j = posVide.getX(), i = posVide.getY();
			grille[i][j] = new Heros(this, ISoldat.TypesH.getTypeHAlea(), "Toto", posVide);
		}
		while (nbMonstres++ < NB_MONSTRES) {
			int deb_ligne = 0,
				fin_ligne = HAUTEUR_CARTE - 1,
				deb_colonne = 0,
				fin_colonne = LARGEUR_CARTE / 2 - 1;
			Position posVide = trouvePositionVideZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne);
			int j = posVide.getX(), i = posVide.getY();
			grille[i][j] = new Monstre(this, ISoldat.TypesM.getTypeMAlea(), posVide);
		}
	}

	// Pseudo-accesseurs
	// Renvoie l'élément à la position donné
	public Element getElement(Position pos) { return pos.estValide() ? grille[pos.getY()][pos.getX()] : null; }
	
	// Pseudo-mutateurs
	// Place un élément à une position donnée de la carte
	public void setElement(Position pos, Element elem) {
		if (pos.estValide()) grille[pos.getY()][pos.getX()] = elem;
	}
	
	// Méthodes
	// Renvoie un nombre aléatoire compris entre inf et sup
	public static int retourneAlea(int inf, int sup) {
		return inf + (int)(Math.random() * ((sup - inf) + 1)); 
	}
	// Trouve aléatoirement une position vide dans une zone dont les extremités sont données en paramètres
	public Position trouvePositionVideZone(int deb_ligne, int fin_ligne, int deb_colonne, int fin_colonne) {
		Element elemVide = null;
		Position posElemVide = null;	
		do {
			int x = retourneAlea(deb_colonne, fin_colonne),
				y = retourneAlea(deb_ligne, fin_ligne);
			elemVide = grille[y][x];
			posElemVide = new Position(x, y);
		} while (elemVide != null); // Tant qu'un élément vide n'a pas été trouvé
		return posElemVide;
	}
	// Trouve aléatoirement une position vide sur la carte
	public Position trouvePositionVide() {
		Position posElemVide = null;	
		posElemVide = trouvePositionVideZone(0, HAUTEUR_CARTE - 1, 0, LARGEUR_CARTE - 1); // cf Méthodes auxiliaires
		return posElemVide;
	}
	// Trouve une position vide choisie aleatoirement parmi les 8 positions adjacentes de pos
	public Position trouvePositionVide(Position pos) {
		int xPos = pos.getX(),		// Indice de colonne de pos
			yPos = pos.getY(),		// Indice de ligne de pos
			deb_ligne = yPos - 1, 	// Indice début ligne
			deb_colonne = xPos - 1, // Indice début colonne
			fin_ligne = yPos + 1, 	// Indice fin ligne
			fin_colonne = xPos + 1; // Indice fin colonne
		Position posElemVide = null;		
		posElemVide = trouvePositionVideZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne); // cf Méthodes auxiliaires
		return posElemVide;
	}
	// Trouve aléatoirement un héros dans une zone dont les extremités sont données en paramètres
	public Heros trouveHerosZone(int deb_ligne, int fin_ligne, int deb_colonne, int fin_colonne) {
		Element h = null;
		do {
			int x = retourneAlea(deb_colonne, fin_colonne),
				y = retourneAlea(deb_ligne, fin_ligne);
			h = grille[y][x];
		} while (!(h instanceof Heros)); // Tant qu'un héros n'a pas été trouvé
		return (Heros)h;
	}
	// Trouve aléatoirement un héros sur la carte
	public Heros trouveHeros() {
		Heros h = null;	
		h = trouveHerosZone(0, HAUTEUR_CARTE - 1, 0, LARGEUR_CARTE - 1);
		return h;
	}
	// Trouve un héros choisi aleatoirement parmi les 8 positions adjacentes de pos
	public Heros trouveHeros(Position pos) {
		int xPos = pos.getX(),		// Indice de colonne de pos
			yPos = pos.getY(),		// Indice de ligne de pos
			deb_ligne = yPos - 1, 	// Indice début ligne
			deb_colonne = xPos - 1, // Indice début colonne
			fin_ligne = yPos + 1, 	// Indice fin ligne
			fin_colonne = xPos + 1; // Indice fin colonne
		Heros h = null;		
		h = trouveHerosZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne);
		return h;
	}
	// Déplace un soldat à la position pos
	public boolean deplaceSoldat(Position pos, Soldat soldat) {
		boolean ret = false,
				possible = pos.estValide() && !(soldat.pos.equals(pos)) && grille[pos.getY()][pos.getX()] == null;
		if (possible == true) {
			ret = true;
			grille[soldat.pos.getY()][soldat.pos.getX()] = null; // Le soldat sort de sa position avant déplacement
			grille[pos.getY()][pos.getX()] = soldat; // Le soldat se déplace à la position où il doit être
			// On fixe sa nouvelle position
			soldat.pos.setX(pos.getX());
			soldat.pos.setY(pos.getY());	
		}
		return ret;
	}
	
	// Méthodes graphiques
	public void seDessinerCoucheReelle(Graphics g) {
		for (int i = 0; i < HAUTEUR_CARTE; i++)
			for (int j = 0; j < LARGEUR_CARTE; j++)
				if (grille[i][j] != null) grille[i][j].seDessiner(g);
	}
	public void seDessinerCoucheVisuelle(Graphics g) {
		for (int i = 0; i < HAUTEUR_CARTE; i++)
			for (int j = 0; j < LARGEUR_CARTE; j++)
				if (grille[i][j] != null) {
					if (grille[i][j] instanceof Heros) {
						int porteeVisuelle = ((Heros)grille[i][j]).getPORTEE_VISUELLE(),
							extHautGaucheX = (j - porteeVisuelle) >= 0 ? j - porteeVisuelle : 0,
							extHautGaucheY = (i - porteeVisuelle) >= 0 ? i - porteeVisuelle : 0,
							extBasDroitX = (j + porteeVisuelle) < LARGEUR_CARTE ? j + porteeVisuelle : LARGEUR_CARTE - 1,
							extBasDroitY = (i + porteeVisuelle) < HAUTEUR_CARTE ? i + porteeVisuelle : HAUTEUR_CARTE - 1;
						Position extHautGauche = new Position(extHautGaucheX, extHautGaucheY),
								 extBasDroit = new Position(extBasDroitX, extBasDroitY);
						Zone zoneVisuelleHeros = new Zone(this, extHautGauche, extBasDroit);
						zoneVisuelleHeros.seDessiner(g);
					}
				}
	}
	
}
