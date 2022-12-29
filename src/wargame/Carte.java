package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import wargameInterface.PanneauPartie;

public class Carte implements IConfig {
	// Map
	public static int LARGEUR_MAP = 1250, HAUTEUR_MAP = 760;
	// Position de la fenêtre
	public static int POSITION_X = 100, POSITION_Y = 50;
	// Infos
	private final PanneauPartie panPartie;
	private int largC, hautC;						// Dimensions de la carte réelle
	private static int largAffC;					// Dimensions de la carte affichée 
	private static int hautAffC;
	private int hautMM, largMM;						// Hauteur de la mini-map
	private static int rayonHex = 25;				// Rayon d'un hexagone
	private int rayonMM;
	private static Element[][] grille; 					// Grille du jeu
	private static ZoneR mapAff;							// Carte affichée
	private static Position centreAff;						// Centre de la carte affichée
	private InfoBar infoBar;						// Barre d'info
	// Positionnement
	private Point origine, origineMM;				// Origine de la carte affichée
	// Interactions
	private Element curseur, selection;
	private LigneH ligne;
	// Limites
	private int nbHeros, nbMonstres;
	// Infos sur la partie
	private InfoPartie infoPartie;
	// Liste des entités
	List<Element> listeMonstres, listeHeros;
	
	// Constructeurs
	public Carte(PanneauPartie panPartie, int largeur, int hauteur) {
		int horiz, vert, horizMM, vertMM;
		horiz = (int)(Math.sqrt(3.) * rayonHex);
		vert = (int)(3 / 2. * rayonHex);
		// Initialisations
		this.panPartie = panPartie;
		largC = largeur;
		hautC = hauteur;
		largAffC = LARGEUR_MAP / horiz;
		hautAffC = HAUTEUR_MAP / vert;
		//// Mini-map
		horizMM = LARGEUR_MINI_MAP / largeur + 1; 
		rayonMM = (int)(horizMM / Math.sqrt(3));
		vertMM = (int)(3 / 2. * rayonMM);
		hautMM = hauteur * vertMM;
		largMM = largeur * horizMM;
		//// Grille + Map affichée
		grille = new Element[hauteur][largeur];
		centreAff = new Position(largeur / 2, hauteur / 2);
		mapAff = new ZoneR(this, centreAff, largAffC, hautAffC);
		// Calcul des origines
		origine = new Point(rayonHex, rayonHex - 2);
		origineMM = new Point(rayonMM + 5, rayonMM + 5);
		// Génération des éléments
		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++)
				grille[i][j] = new Sol(this, new Position(j, i));	
		nbHeros = nbMonstres = 6;
		genereObstacles();
		genereHeros(nbHeros);
		genereMonstres(nbMonstres);
		// Éléments sélectionnés au départ
		selection = trouveHeros();
		// InfoBar
		infoBar = new InfoBar(selection, null);
		// Infos sur la partie
		infoPartie = new InfoPartie(this, nbHeros, nbMonstres);
	}
	
	// Accesseurs
	public int getLargC() { return largC; }
	public int getHautC() { return hautC; }
	public int getLargAffC() { return largAffC; }
	public int getHautAffC() { return hautAffC; }
	public int getLargMM() { return largMM; }
	public int getHautMM() { return hautMM; }
	public int getRayonHex() { return rayonHex; }
	public int getRayonMM() { return rayonMM; }
	public Element[][] getGrille() { return grille; }
	public ZoneR getMapAff() { return mapAff; }
	public Position getCentreAff() { return centreAff; }
	public Point getOrigine() { return origine; }
	public Point getOrigineMM() { return origineMM; }
	public InfoBar getInfoBar() { return infoBar; }
	public Element getSelection() { return selection; }
	public InfoPartie getInfoPartie() { return infoPartie; }
	public List<Element> getListeMonstres() { return listeMonstres; }
	public List<Element> getListeHeros() { return listeHeros; }
	public PanneauPartie getPanPartie() { return panPartie; }
	//// Pseudo-accesseurs
	public Element getElement(Position pos) {
		return (pos.estValide(largC, hautC)) ? grille[pos.getY()][pos.getX()] : null;
	}
	
	// Mutateurs
	//// Pseudo-mutateurs
	public void setElement(Position pos, Element elem) {
		if (pos.estValide(largC, hautC)) grille[pos.getY()][pos.getX()] = elem;
	}
	
	// Méthodes
	// Recalcules les dimensions de la carte affichées
	public static void recalculerDimensions() {
		int horiz, vert;
		horiz = (int)(Math.sqrt(3.) * rayonHex);
		vert = (int)(3 / 2. * rayonHex);
		largAffC = LARGEUR_MAP / horiz;
		hautAffC = HAUTEUR_MAP / vert;
		// Modification des extremités de la zone de la carte affichée
		mapAff.setUpLeft(mapAff.calculerUpLeft(centreAff, largAffC, hautAffC));
		mapAff.setDownRight(mapAff.calculerDownRight(centreAff, largAffC, hautAffC));
		// Modification des dimensions de cette dernière
		mapAff.setLargeur(mapAff.calculerLargeur());
		mapAff.setHauteur(mapAff.calculerHauteur());
		// Calcul des hexagones
		calculerHex();
	}
	// Trouve aléatoirement une position de type donné dans une zone dont les extremités sont données en paramètres
	public Position trouvePosType(int debX, int finX, int debY, int finY, char type) {
		Element elemVide = null;
		Position posElemVide = null;
		boolean test;
		do {
			int x = alea(debX, finX), 
				y = alea(debY, finY);
			elemVide = grille[y][x];
			posElemVide = new Position(x, y);
			test = typeof(elemVide, type);
		} while (!(test)); // Tant que l'élément du type recherche n'a pas été retrouvé
		return posElemVide;
	}
	// Trouve aléatoirement une position vide sur la carte réelle
	public Position trouvePosVide() {
		return trouvePosType(0, largC - 1, 0, hautC - 1, 's');
	}
	// Trouve une position vide choisie aleatoirement parmi les 8 positions adjacentes de pos
	public Position trouvePosVide(Position pos) {
		int xPos = pos.getX(), yPos = pos.getY(),			// Indices de pos
			debY = yPos - 1, finY = yPos + 1,				// Indices de ligne
			debX = xPos - 1, finX = xPos + 1; 				// Indices de colonne
		return trouvePosType(debY, finY, debX, finX, 's');
	}
	// Trouve aléatoirement un héros sur la carte réelle
	public Heros trouveHeros() {
		return (Heros)getElement(trouvePosType(0, largC - 1, 0, hautC - 1, 'h'));
	}
	// Trouve un héros choisi aleatoirement parmi les 8 positions adjacentes de pos
	public Heros trouveHeros(Position pos) {
		int xPos = pos.getX(), yPos = pos.getY(),			// Indices de pos
			debY = yPos - 1, finY = yPos + 1,		// Indices de ligne
			debX = xPos - 1, finX = xPos + 1; // Indices de colonne
		return (Heros)getElement(trouvePosType(debY, finY, debX, finX, 'h'));
	}
	// Tue le soldat
	public void mort(Soldat victime) {
		setElement(victime.pos, new Sol(this, new Position(victime.pos.getX(), victime.pos.getY())));
		victime = null;
	}
	// Génère aléatoirement des héros 
	public void genereHeros(int n) {
		int c = 0,
			debY = 0, finY = hautC - 1,
			debX = largC / 2 + 1, finX = largC - 1;
		listeHeros = new ArrayList<Element>();
		while (c++ < n) {
			String nom = "" + (char)('A' + alea(0, 26));
			Position posVide = trouvePosType(debX, finX, debY, finY, 's');
			Heros h = new Heros(this, ISoldat.TypesH.getTypeHAlea(), nom, posVide);
			grille[posVide.getY()][posVide.getX()] = h;
			listeHeros.add(h);
		}
	}
	// Génère une zone contenant un type d'obstacles donné
	public void genereObsType(int n, Obstacle.TypeObstacle t) {
		int voisin = 0;
		Position pos = trouvePosVide();
		PositionAxiale posa = new PositionAxiale(pos.getX(),pos.getY());
		pos = posa.toPosition();
		while( n > 0) {
			voisin = alea(0, 5);
			posa = pos.toPositionAxiale();
			posa = posa.voisin(voisin);
			pos = posa.toPosition();
			if( (pos.getX() >= 0 && pos.getX() <= 49) && (pos.getY() >=0 && pos.getY() <=39 ) ) {
				grille[pos.getY()][pos.getX()] = new Obstacle(this, t, pos);
			}
			n--;
		}
	}
	// Génère aléatoirement des obstacles 
	public void genereObstacles() {
		int nbL,nbR,nbF,nbZone;
		nbL = alea(100,300);
		nbZone = alea(1,3);
		while(nbZone-- > 0) {
			genereObsType(nbL, Obstacle.TypeObstacle.EAU);
		}
		nbZone = alea(1,3);
		nbR = alea(100,300);
		while(nbZone-- > 0) {
			genereObsType(nbR, Obstacle.TypeObstacle.ROCHER);
		}
		nbZone = alea(1,3);
		nbF = alea(100,300);
		while(nbZone-- > 0) {
			genereObsType(nbR, Obstacle.TypeObstacle.FORET);
		}
		
	}
	
	public int faireVoisinBis(Position p, int nb,Obstacle.TypeObstacle t) {
		int a = 0;
		PositionAxiale posVoisin = new PositionAxiale(p.getX(), p.getY());
		if(nb > 0) {
			if( (p.getX() >= 0 && p.getX() <= 49) && (p.getY() >=0 && p.getY() <=39 ) ) {
				grille[p.getY()][p.getX()] = new Obstacle(this, t, p);
				a = alea(0,5);
				nb-=a;
				for (int i = 0; i < a; i++) {
					posVoisin = p.toPositionAxiale();
					posVoisin = posVoisin.voisin(i);
					p = posVoisin.toPosition();
					if( (p.getX() >= 0 && p.getX() <= 49) && (p.getY() >=0 && p.getY() <=39 ) )
						grille[p.getY()][p.getX()] = new Obstacle(this, t, p);
				}
				for (int i = 0; i < a; i++) {
					posVoisin = posVoisin.voisin(i);
					p = posVoisin.toPosition();
					nb = faireVoisinBis(p, nb,t);
				}
			}
		}
		return nb;
	}
	
	public void genereObstaclesrec() {
		Position p = trouvePosVide();
		faireVoisinBis(p,40,Obstacle.TypeObstacle.FORET);
		
	}
	// Génère aléatoirement des monstres
	public void genereMonstres(int n) {
		int c = 0,
			debY = 0, finY = hautC - 1,
			debX = 0, finX = largC / 2;
		listeMonstres = new ArrayList<Element>();
		while (c++ < n) {
			Position posVide = trouvePosType(debX, finX, debY, finY, 's');
			Monstre m = new Monstre(this, ISoldat.TypesM.getTypeMAlea(), posVide);
			grille[posVide.getY()][posVide.getX()] = m;
			listeMonstres.add(m);
		}
	}
	// Calcul tous les hexagones des éléments de la carte
	public static void calculerHex() {
		for (Element[] liste : grille)
			for (Element e : liste)
				e.creerHexM();
	}
	
	// Méthodes d'interaction
	// Déplace le curseur
	public void deplacerCurseur(Point c) {
		Position p = c.toPositionAxiale(rayonHex, origine).toPosition().add(mapAff.getUpLeft());
		if (mapAff.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
			p = p.add(new Position(1, 0));
		curseur = getElement(p);
		infoBar.setCurseur(curseur);
		if (selection instanceof Heros && curseur != null) ligne = new LigneH(selection, curseur, this);
	}
	// Déplace la sélection
	public void deplacerSelection(Point s) {
		Position p = s.toPositionAxiale(rayonHex, origine).toPosition().add(mapAff.getUpLeft());
		if (mapAff.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
			p = p.add(new Position(1, 0));
		Element e = getElement(p);
		if (selection != null) selection = (p.equals(selection.pos)) ? null : e;
		else selection = e;
		infoBar.setSelection(selection);
		ligne = null;
	}
	// Zoome la zone d'affichage
	public void zoomer(int zoom) {
		int horiz, vert;
		if (zoom >= 6 && zoom <= 18) {
			rayonHex = (int)(2.5 * zoom);
			horiz = (int)(Math.sqrt(3.) * rayonHex);
			vert = (int)(3 / 2. * rayonHex);
			largAffC = LARGEUR_MAP / horiz;
			hautAffC = HAUTEUR_MAP / vert;
			// Modification des extremités de la zone de la carte affichée
			mapAff.setUpLeft(mapAff.calculerUpLeft(centreAff, largAffC, hautAffC));
			mapAff.setDownRight(mapAff.calculerDownRight(centreAff, largAffC, hautAffC));
			// Modification des dimensions de cette dernière
			mapAff.setLargeur(mapAff.calculerLargeur());
			mapAff.setHauteur(mapAff.calculerHauteur());
			// Calcul des hexagones
			calculerHex();
		}
	}
	// Déplace la zone affichée autour du point p
	public void deplacer(Point p) {
		if (p.estValide(origineMM, (new Point(largMM, hautMM)))) {
			centreAff = p.toPositionAxiale(rayonMM, origineMM).toPosition();
			mapAff.setUpLeft(mapAff.calculerUpLeft(centreAff, largAffC, hautAffC));
			mapAff.setDownRight(mapAff.calculerDownRight(centreAff, largAffC, hautAffC));
			// Calcul de l'origine
			int largMAC = mapAff.getLargeur(),							// largMAC = largAffC => pas toujours !
				hautMAC = mapAff.getHauteur(),							// Idem
				xC = centreAff.getX(),									// C = Centre de la carte (= zone) affichée
				yC = centreAff.getY();
			// Calcul des coordonnées de l'origine de la carte
			Position po = new Position(xC - largAffC / 2 < 0 ? largAffC - largMAC : 0,
									   yC - hautAffC / 2 < 0 ? hautAffC - hautMAC : 0);
			origine = po.toPoint(rayonHex);
			// On recalcule tous les hexagones
			calculerHex();
		}
	}
	// Déplace l'élément sélectionné si c'est un héros
	public void deplacerHeros(Point p) {
		if (selection instanceof Heros) {
			Position cible = p.toPositionAxiale(rayonHex, origine).toPosition().add(mapAff.getUpLeft());
			if (mapAff.getUpLeft().getY() % 2 != 0 && cible.getY() % 2 == 0)
				cible = cible.add(new Position(1, 0));
			((Heros)selection).seDeplace(cible);
		}
	}
	// Mets fin au tour du joueur
	public void finirTour(char side) {
		if (side == GENTILS) {
			panPartie.getTableauBord().getBoutonsTour().setVisible(false);
			selection = null;
			curseur = null;
			ligne = null;
			infoPartie.setNbTours(infoPartie.getNbTours() + 1);
			infoPartie.setJoueur(MECHANT);
			panPartie.getTableauBord().getActionsHeros().setVisible(false);
			panPartie.repaint();
			TourOrdi to = new TourOrdi(this);
			to.start();
		} else if (side == MECHANT) {
			panPartie.getTableauBord().getBoutonsTour().setVisible(true);
			infoPartie.setJoueur(GENTILS);
		}
	}
	
	// Méthodes graphiques
	public void seDessiner(Graphics g) {
		mapAff.seDessiner(g);
		if (selection != null) {
			if (selection instanceof Heros) ((Soldat)selection).dessinerZoneDeplacement(g);
			selection.seDessinerCadre(g, COULEUR_SELECTION);
		}
		if (ligne != null) ligne.seDessiner(g);
		if (curseur != null) curseur.seDessinerCadre(g, COULEUR_CURSEUR);
	}
	
	// Dessine la carte reelle sous forme de mini-map
	public void seDessinerMM(Graphics g) {
		for (Element[] liste : grille)
			for (Element e : liste)
				e.seDessinerMM(g);
		if (selection != null) {
			// if (selection instanceof Heros) ((Soldat)selection).dessinerZoneDeplacementMM(g);
			selection.seDessinerCadreMM(g, COULEUR_SELECTION);
		}
		if (curseur != null) curseur.seDessinerCadreMM(g, COULEUR_CURSEUR);
		// Dessin d'un rectangle représentant la zone affichée
		Point ul = mapAff.getUpLeft().toPositionAxiale().toPoint(rayonMM, origineMM).substract(new Point(rayonMM, rayonMM)),
			  dr = mapAff.getDownRight().toPositionAxiale().toPoint(rayonMM, origineMM);
		g.setColor(Color.yellow);
		g.drawRect((int)ul.getX(), (int)ul.getY(), (int)(dr.getX() - ul.getX()), (int)(dr.getY() - ul.getY()));
	}
	
	// Autres méthodes
	// Renvoie un nombre aléatoire compris entre inf et sup
	public static int alea(int inf, int sup) {
		return inf + (int)(Math.random() * ((sup - inf) + 1));
	}
	// Auxiliaire à trouvePosType()
	public boolean typeof(Element elem, char type) {
		switch (type) {
			case 's': return elem instanceof Sol;
			case 'h': return elem instanceof Heros;
			default : return false;
		}
	}
}
