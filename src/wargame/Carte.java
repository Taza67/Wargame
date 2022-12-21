package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Carte implements IConfig, ICarte {
	// Infos
	// // Dimensions en cases
	// // // Carte Affichée
	private int largeurCaseCarteAffichee = 30, 
			   	hauteurCaseCarteAffichee = 15;
	// // // Carte réelle
	private final int LARGEUR_CASE_CARTE, HAUTEUR_CASE_CARTE;
	// // Dimensions en pixels
	// // // Case
	private int taillePixelCaseCarte = 50, 				// Dans la carte
			    frontiereCase = 10;
	private final int TAILLE_PIXEL_CASE_MINI_MAP;		// Dans la mini-map
	// // // Carte Affichée
	private int largeurPixelCarteAffichee = largeurCaseCarteAffichee * taillePixelCaseCarte + frontiereCase,
				hauteurPixelCarteAffichee = hauteurCaseCarteAffichee * taillePixelCaseCarte + frontiereCase;
	// // // Mini-map
	private final int LARGEUR_PIXEL_MINI_MAP, HAUTEUR_PIXEL_MINI_MAP;
	// // Carte en elle-même
	private Element[][] grille; 						// Grille du jeu
	private int xOrigineCarteAffichee = 0, 				// Coordonnées de l'origine de la carte (en pixels)
				yOrigineCarteAffichee = 0;
	private Zone carteAffichee;							// Zone de la carte affichée
	private Position centreCarteAffichee;				// Centre de cette dernière
	// // Éléments
	private Element elemSelecActuel,
					elemSelecPost;
	// // Infos curseur, selection
	private InfoBar infoBar;
	// Limites
	private final int NB_HEROS, 
					  NB_MONSTRES, 
					  NB_OBSTACLES;
	
	// Constructeurs
	public Carte(int hauteur, int largeur, int nbHeros) {
		// Initialisations
		TAILLE_PIXEL_CASE_MINI_MAP = TAILLE_MAX_MINI_MAP / (hauteur > largeur ? hauteur : largeur);
		grille = new Element[hauteur][largeur];
		HAUTEUR_CASE_CARTE = hauteur;
		LARGEUR_CASE_CARTE = largeur;
		HAUTEUR_PIXEL_MINI_MAP = hauteur * TAILLE_PIXEL_CASE_MINI_MAP;
		LARGEUR_PIXEL_MINI_MAP = largeur * TAILLE_PIXEL_CASE_MINI_MAP;
		NB_OBSTACLES = (int)(HAUTEUR_CASE_CARTE * LARGEUR_CASE_CARTE * 0.25);
		NB_HEROS = nbHeros;
		NB_MONSTRES = nbHeros * 2;
		// // Initialisation des éléments de la grille (= sol)
		for (int i = 0; i < hauteur; i++) 
			for (int j = 0; j < largeur; j++) 
				grille[i][j] = new Sol(this, new Position(j, i));
		
		// // Zone à afficher
		centreCarteAffichee = new Position(largeur / 2, hauteur / 2);
		carteAffichee = new Zone(this, centreCarteAffichee, largeurCaseCarteAffichee, hauteurCaseCarteAffichee);
		// // Génération alétoire d'obstacles, héros, monstres
		genereObstacles(NB_OBSTACLES);
		genereHeros(NB_HEROS, 0, hauteur - 1, largeur / 2, largeur - 1);
		genereMonstres(NB_MONSTRES, 0, hauteur - 1, 0, largeur / 2 - 1);
		// Éléments sélectionnés au départ
		elemSelecPost = elemSelecActuel = getElement(trouvePositionVideZone(carteAffichee.getExtHautGauche().getY(), 
	 												 						carteAffichee.getExtBasDroit().getY(),
 												 							carteAffichee.getExtHautGauche().getX(),
	 												 						carteAffichee.getExtBasDroit().getX()));
		elemSelecPost.selectionne = true;
		// InfoBar
		infoBar = new InfoBar(this, elemSelecActuel, null);
	}
	
	// Accesseurs
	public int getLargeurCaseCarteAffichee() { return largeurCaseCarteAffichee; }
	public int getHauteurCaseCarteAffichee() { return hauteurCaseCarteAffichee; }
	public int getLARGEUR_CASE_CARTE() { return LARGEUR_CASE_CARTE; }
	public int getHAUTEUR_CASE_CARTE() { return HAUTEUR_CASE_CARTE; }
	public int getTaillePixelCaseCarte() { return taillePixelCaseCarte; }
	public int getFrontiereCase() { return frontiereCase; }
	public int getTAILLE_PIXEL_CASE_MINI_MAP() { return TAILLE_PIXEL_CASE_MINI_MAP; }
	public int getLargeurPixelCarteAffichee() { return largeurPixelCarteAffichee; }
	public int getHauteurPixelCarteAffichee() { return hauteurPixelCarteAffichee; }
	public int getLARGEUR_PIXEL_MINI_MAP() { return LARGEUR_PIXEL_MINI_MAP; }
	public int getHAUTEUR_PIXEL_MINI_MAP() { return HAUTEUR_PIXEL_MINI_MAP; }
	public Element[][] getGrille() { return grille; }
	public int getxOrigineCarteAffichee() { return xOrigineCarteAffichee; }
	public int getyOrigineCarteAffichee() { return yOrigineCarteAffichee; }
	public Zone getCarteAffichee() { return carteAffichee; }
	public Position getCentreCarteAffichee() { return centreCarteAffichee; }
	public InfoBar getInfoBar() { return infoBar; }
	// // Pseudo-accesseurs
	// Renvoie l'élément à la position donné
	public Element getElement(Position pos) { return pos.estValide(LARGEUR_CASE_CARTE, HAUTEUR_CASE_CARTE) ? grille[pos.getY()][pos.getX()] : null; }
	
	// Mutateurs
	public void setLargeurCaseCarteAffichee(int largeurCaseCarteAffichee) { this.largeurCaseCarteAffichee = largeurCaseCarteAffichee; }
	public void setHauteurCaseCarteAffichee(int hauteurCaseCarteAffichee) {	this.hauteurCaseCarteAffichee = hauteurCaseCarteAffichee; }
	public void setTaillePixelCaseCarte(int taillePixelCaseCarte) { this.taillePixelCaseCarte = taillePixelCaseCarte; }
	public void setFrontiereCase(int frontiereCase) { this.frontiereCase = frontiereCase; }
	public void setLargeurPixelCarteAffichee(int largeurPixelCarteAffichee) { this.largeurPixelCarteAffichee = largeurPixelCarteAffichee; }
	public void setHauteurPixelCarteAffichee(int hauteurPixelCarteAffichee) { this.hauteurPixelCarteAffichee = hauteurPixelCarteAffichee; }
	public void setxOrigineCarteAffichee(int xOrigineCarteAffichee) { this.xOrigineCarteAffichee = xOrigineCarteAffichee; }
	public void setyOrigineCarteAffichee(int yOrigineCarteAffichee) { this.yOrigineCarteAffichee = yOrigineCarteAffichee; }
	public void setCarteAffichee(Zone carteAffichee) { this.carteAffichee = carteAffichee; }
	public void setCentreCarteAffichee(Position centreCarteAffichee) { this.centreCarteAffichee = centreCarteAffichee; }
	// // Pseudo-mutateurs
	// Place un élément à une position donnée de la carte
	public void setElement(Position pos, Element elem) { if (pos.estValide(LARGEUR_CASE_CARTE, HAUTEUR_CASE_CARTE)) grille[pos.getY()][pos.getX()] = elem; }
	
	// Méthodes
	// Trouve aléatoirement une position vide dans une zone dont les extremités sont données en paramètres
	public Position trouvePositionVideZone(int deb_ligne, int fin_ligne, int deb_colonne, int fin_colonne) {
		Element elemVide = null;
		Position posElemVide = null;	
		do {
			int x = retourneEntierAleatoire(deb_colonne, fin_colonne), y = retourneEntierAleatoire(deb_ligne, fin_ligne);
			elemVide = grille[y][x];
			posElemVide = new Position(x, y);
		} while (!(elemVide instanceof Sol)); // Tant qu'un élément vide n'a pas été trouvé
		return posElemVide;
	}
	// Trouve aléatoirement une position vide sur la carte réelle
	public Position trouvePositionVide() { return trouvePositionVideZone(0, HAUTEUR_CASE_CARTE - 1, 0, LARGEUR_CASE_CARTE - 1); }
	// Trouve une position vide choisie aleatoirement parmi les 8 positions adjacentes de pos
	public Position trouvePositionVide(Position pos) {
		int xPos = pos.getX(), yPos = pos.getY(),			// Indices de pos
			deb_ligne = yPos - 1, fin_ligne = yPos + 1,		// Indices de ligne
			deb_colonne = xPos - 1, fin_colonne = xPos + 1; // Indices de colonne
		return trouvePositionVideZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne);
	}
	// Trouve aléatoirement un héros dans une zone dont les extremités sont données en paramètres
	public Heros trouveHerosZone(int deb_ligne, int fin_ligne, int deb_colonne, int fin_colonne) {
		Element h = null;
		do {
			int x = retourneEntierAleatoire(deb_colonne, fin_colonne), y = retourneEntierAleatoire(deb_ligne, fin_ligne);
			h = grille[y][x];
		} while (!(h instanceof Heros)); // Tant qu'un héros n'a pas été trouvé
		return (Heros)h;
	}
	// Trouve aléatoirement un héros sur la carte réelle
	public Heros trouveHeros() { return trouveHerosZone(0, HAUTEUR_CASE_CARTE - 1, 0, LARGEUR_CASE_CARTE - 1); }
	// Trouve un héros choisi aleatoirement parmi les 8 positions adjacentes de pos
	public Heros trouveHeros(Position pos) {
		int xPos = pos.getX(), yPos = pos.getY(),			// Indices de pos
			deb_ligne = yPos - 1, fin_ligne = yPos + 1,		// Indices de ligne
			deb_colonne = xPos - 1, fin_colonne = xPos + 1; // Indices de colonne
		return trouveHerosZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne);
	}
	// Génère aléatoirement des héros 
	public void genereHeros(int nb, int deb_ligne, int fin_ligne, int deb_colonne, int fin_colonne) {
		int c = 0;
		while (c++ < nb) {
			Position posVide = trouvePositionVideZone(deb_ligne, fin_ligne, deb_colonne, fin_colonne);
			grille[posVide.getY()][posVide.getX()] = new Heros(this, ISoldat.TypesH.getTypeHAlea(), "Toto", posVide);
		}
	}
	// Génère aléatoirement des obstacles 
	public void genereObstacles(int nb) {
		int c = 0;
		while (c++ < nb) {
			Position posVide = trouvePositionVide();
			grille[posVide.getY()][posVide.getX()] = new Obstacle(this, Obstacle.TypeObstacle.getObstacleAlea(), posVide);
		}
	}
	// Génère aléatoirement des monstres
	public void genereMonstres(int nb, int deb_ligne, int fin_ligne, int deb_colonne, int fin_colonne) {
		int c = 0;
		while (c++ < nb) {
			Position posVide = trouvePositionVide();
			grille[posVide.getY()][posVide.getX()] = new Monstre(this, ISoldat.TypesM.getTypeMAlea(), posVide);
		}
	}
	// Déplace un soldat à la position pos
	public boolean deplaceSoldat(Position pos, Soldat soldat) {
		boolean possible = true;
		int xPos = pos.getX(), yPos = pos.getY(), 
			xSold = soldat.pos.getX(), ySold = soldat.pos.getY();
		// Vérifications
		possible = possible && pos.estValide(LARGEUR_CASE_CARTE, HAUTEUR_CASE_CARTE);	// Position cible valide ?
		possible = possible && !(soldat.pos.equals(pos));								// Pas la même position que l'actuelle ?
		possible = possible && grille[yPos][xPos] instanceof Sol;						// Position cible libre ?
		if (possible) {
			grille[yPos][xPos] = soldat;												// Le soldat se déplace à la position où il doit être
			grille[ySold][xSold] = new Sol(this, new Position(xSold, ySold));			// L'ancienne position du soldat = sol
			grille[ySold][xSold].visible = true;
			// Les coordonnées du soldat doivent changer
			grille[yPos][xPos].pos.setX(xPos);
			grille[yPos][xPos].pos.setY(yPos);
			// Découverte de nouvelle terres :)
			soldat.majZoneVisuelle(pos);
		}
		return possible;
	}
	// Met à jour la visibilité (Inconnu ou pas)
	public void majVisibilite() {
		for (int i = 0; i < HAUTEUR_CASE_CARTE; i++) 
			for (int j = 0; j < LARGEUR_CASE_CARTE; j++)
				if (grille[i][j] instanceof Heros) ((Heros)grille[i][j]).getZoneVisuelle().rendreVisible();
	}
	
	// Méthodes graphiques
	// Dessine la carte reelle sous forme de mini-map
	public void seDessinerMiniMap(Graphics g) {
		// Dessin du cadre
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, LARGEUR_PIXEL_MINI_MAP + 10, HAUTEUR_PIXEL_MINI_MAP + 10);
		// Dessin des éléments
		for (int i = 0; i < HAUTEUR_CASE_CARTE; i++)
			for (int j = 0; j < LARGEUR_CASE_CARTE; j++)
				grille[i][j].seDessiner(g, Element.ELEMENT_MINI_MAP);
		// Dessin d'un rectangle représentant la zone affichée
		g.setColor(Color.yellow);
		int xCaseEHG = carteAffichee.getExtHautGauche().getX(),					// EHG = Extrémité haute gauche
			yCaseEHG = carteAffichee.getExtHautGauche().getY(),
			xCaseCCA = centreCarteAffichee.getX(),								// CCA = Centre de la carte (= zone) affichée
			yCaseCCA = centreCarteAffichee.getY(),
			largCase = xCaseEHG + largeurCaseCarteAffichee > LARGEUR_CASE_CARTE ? LARGEUR_CASE_CARTE - xCaseEHG : largeurCaseCarteAffichee,
			hautCase = yCaseEHG + hauteurCaseCarteAffichee > HAUTEUR_CASE_CARTE ? HAUTEUR_CASE_CARTE - yCaseEHG : hauteurCaseCarteAffichee,
			xPix = xCaseEHG * TAILLE_PIXEL_CASE_MINI_MAP + X_MINI_MAP + 2,		// Coordonnées de l'origine du rectangle
			yPix = yCaseEHG * TAILLE_PIXEL_CASE_MINI_MAP + Y_MINI_MAP + 2;
		largCase += xCaseCCA - largeurCaseCarteAffichee / 2 < 0 ? xCaseCCA - largeurCaseCarteAffichee / 2 : 0;
		hautCase += yCaseCCA - hauteurCaseCarteAffichee / 2 < 0 ? yCaseCCA - hauteurCaseCarteAffichee / 2 : 0;
		g.drawRect(xPix - 2, yPix - 2, largCase * TAILLE_PIXEL_CASE_MINI_MAP, hautCase * TAILLE_PIXEL_CASE_MINI_MAP);	
	}	
	// Dessine la zone de la carte à afficher
	public void seDessinerCarteAffichee(Graphics g) {
		int largCaseZoneAffichee = carteAffichee.getLargeurCase(),			// largCaseZoneAffichee = largeurCaseCarteAffichee => pas toujours !
			hautCaseZoneAffichee = carteAffichee.getHauteurCase(),			// Idem
			xCaseCCA = centreCarteAffichee.getX(),							// CCA = Centre de la carte (= zone) affichée
			yCaseCCA = centreCarteAffichee.getY();
		// Calcul des coordonnées de l'origine de la carte
		// En cases
		xOrigineCarteAffichee = xCaseCCA - largeurCaseCarteAffichee / 2 < 0 ? largeurCaseCarteAffichee - largCaseZoneAffichee : 0;
		yOrigineCarteAffichee = yCaseCCA - hauteurCaseCarteAffichee / 2 < 0 ? hauteurCaseCarteAffichee - hautCaseZoneAffichee : 0;
		// En pixels
		xOrigineCarteAffichee *= taillePixelCaseCarte;
		yOrigineCarteAffichee *= taillePixelCaseCarte;
		// Dessin
		carteAffichee.seDessiner(g);
	}
	// Déplace la zone de la carte à afficher en fonction d'un point dont les coordonnées sont en pixels
	public boolean deplacerCarteAffichee(int xPix, int yPix) {
		boolean possible = true;
		// Vérifications
		possible = possible && (xPix >= X_MINI_MAP && xPix <= X_MINI_MAP + LARGEUR_PIXEL_MINI_MAP);
		possible = possible && (yPix >= Y_MINI_MAP && yPix <= Y_MINI_MAP + HAUTEUR_PIXEL_MINI_MAP);
		if (possible) {
			int indiceCurseurLigne = (yPix - Y_MINI_MAP) / TAILLE_PIXEL_CASE_MINI_MAP,
				indiceCurseurColonne = (xPix - X_MINI_MAP) / TAILLE_PIXEL_CASE_MINI_MAP;
			// Modification du centre de la zone de la carte affichée
			centreCarteAffichee.setX(indiceCurseurColonne);
			centreCarteAffichee.setY(indiceCurseurLigne);
			// Modification des extremités de la zone de la carte affichée
			carteAffichee.setExtHautGauche(carteAffichee.calculerExtHautGauche(centreCarteAffichee, largeurCaseCarteAffichee + 1, hauteurCaseCarteAffichee + 1));
			carteAffichee.setExtBasDroit(carteAffichee.calculerExtBasDroit(centreCarteAffichee, largeurCaseCarteAffichee + 1, hauteurCaseCarteAffichee + 1));
			// Modification des dimensions de cette dernière
			carteAffichee.setLargeurCase(carteAffichee.calculerLargeurCase());
			carteAffichee.setHauteurCase(carteAffichee.calculerHauteurCase());
		}
		return possible;
	}
	// Déplace le curseur
	public boolean deplacerCurseur(int xPix, int yPix) {
		boolean possible = true;
		// Vérifications
		possible = possible && (xPix >= xOrigineCarteAffichee + frontiereCase && xPix < largeurPixelCarteAffichee - frontiereCase);
		possible = possible && (yPix >= yOrigineCarteAffichee + frontiereCase && yPix < hauteurPixelCarteAffichee - frontiereCase);
		if (possible) {
			int indiceCurseurLigne = (yPix - yOrigineCarteAffichee) / taillePixelCaseCarte,
				indiceCurseurColonne = (xPix - xOrigineCarteAffichee) / taillePixelCaseCarte;
				Position posSelec = new Position(indiceCurseurColonne + carteAffichee.getExtHautGauche().getX(), 
												 indiceCurseurLigne + carteAffichee.getExtHautGauche().getY());
			if (posSelec.estValide(LARGEUR_CASE_CARTE, HAUTEUR_CASE_CARTE)) {
				grille[posSelec.getY()][posSelec.getX()].curseurDessus = true;
				infoBar.setCurseur(grille[posSelec.getY()][posSelec.getX()]);
			}
		}
		return possible;
	}
	// Déplace le curseur de sélection
	public boolean deplacerSelection(int xPix, int yPix) {
		boolean possible = true;
		// Vérifications
		possible = possible && (xPix >= xOrigineCarteAffichee + frontiereCase && xPix < largeurPixelCarteAffichee - frontiereCase);
		possible = possible && (yPix >= yOrigineCarteAffichee + frontiereCase && yPix < hauteurPixelCarteAffichee - frontiereCase);
		if (possible) {
			int indiceCurseurLigne = (yPix - yOrigineCarteAffichee) / taillePixelCaseCarte,
				indiceCurseurColonne = (xPix - xOrigineCarteAffichee) / taillePixelCaseCarte;
				Position posSelec = new Position(indiceCurseurColonne + carteAffichee.getExtHautGauche().getX(), 
												 indiceCurseurLigne + carteAffichee.getExtHautGauche().getY());
			if (posSelec.estValide(LARGEUR_CASE_CARTE, HAUTEUR_CASE_CARTE)) {
 				if (elemSelecActuel != null && elemSelecActuel.pos.equals(posSelec)) {
 					elemSelecActuel.selectionne = false;
 					elemSelecActuel = null;
 				} else if (elemSelecActuel instanceof Heros)
					deplaceSoldat(posSelec, (Soldat)elemSelecActuel);
				else {
					elemSelecPost = elemSelecActuel;
					elemSelecActuel = grille[posSelec.getY()][posSelec.getX()];
					if (elemSelecPost != null) elemSelecPost.selectionne = false;
					elemSelecActuel.selectionne = true;
				}
			}
			infoBar.setSelection(elemSelecActuel);
		}
		return possible;
	}
	// Zoome la zone d'affichage
	public void zoomerCarteAffichee(int zoom) {
		largeurCaseCarteAffichee += zoom;
		hauteurCaseCarteAffichee += zoom;
		taillePixelCaseCarte -= 5 * zoom;
		frontiereCase -= zoom;
		largeurPixelCarteAffichee = largeurCaseCarteAffichee * taillePixelCaseCarte + frontiereCase;
		hauteurPixelCarteAffichee = hauteurCaseCarteAffichee * taillePixelCaseCarte + frontiereCase;
		// Modification des extremités de la zone de la carte affichée
		carteAffichee.setExtHautGauche(carteAffichee.calculerExtHautGauche(centreCarteAffichee, largeurCaseCarteAffichee + 1, hauteurCaseCarteAffichee + 1));
		carteAffichee.setExtBasDroit(carteAffichee.calculerExtBasDroit(centreCarteAffichee, largeurCaseCarteAffichee + 1, hauteurCaseCarteAffichee + 1));
		// Modification des dimensions de cette dernière
		carteAffichee.setLargeurCase(carteAffichee.calculerLargeurCase());
		carteAffichee.setHauteurCase(carteAffichee.calculerHauteurCase());
	}
	
	// Autres méthodes
	// Renvoie un nombre aléatoire compris entre inf et sup
	public static int retourneEntierAleatoire(int inf, int sup) { return inf + (int)(Math.random() * ((sup - inf) + 1)); }
}
