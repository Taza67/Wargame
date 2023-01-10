package wargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wargame.Obstacle.TypeObstacle;
import wargame.Sol.TypeSol;
import wargameInterface.Fenetre;
import wargameInterface.PanneauPartie;

/**
 * <b>Carte est la classe principale du programme, elle représente toute la grille de jeu.</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Des dimensions en pixels et en cases.</li>
 * <li>Les dimesions des hexagones représentant les cases de la grille.</li>
 * <li>Les nombres de certains types d'éléments comme les monstres ou héros.</li>
 * <li>Des éléments "interactifs" qui changent en fonction des actions de l'utilisateurs.</li>
 * <li>Une zone rectangulaire représentant la partie affichée de la carte entière.</li>
 * </ul>
 * </p>
 * @see Element 
 * @see ZoneR 
 * @see InfoPartie
 * @see Fenetre 
 * @see PanneauPartie
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */
public class Carte implements IConfig , Serializable {
	private static final long serialVersionUID = -7225736178980752155L;
	/**
	 * Les dimensions en pixels de la fenêtre. Ces dimensions peuvent changer.
	 * @see Fenetre#passerPleinEcran()
	 */
	public static int largeurFenetre = 1500, hauteurFen = 760;
	/**
	 * Les dimensions en pixels de la zone affichée. Ces dimensions peuvent changer.
	 * @see Fenetre#passerPleinEcran()
	 */
	public static int largeurMap = 1250, hauteurMap = 760;
	/**
	 * Le panneau qui contiendra la carte de jeu. Il peut changer.
	 * @see Carte#Carte(PanneauPartie, int, int, int, int)
	 * @see PanneaPartie
	 */
	private PanneauPartie panPartie;
	/**
	 * Les dimensions en cases de la carte. Ces dimensions ne peuvent pas changer.
	 * @see Carte#Carte(PanneauPartie, int, int, int, int)
	 */
	private final int LARGC, HAUTC;
	/**
	 * Les dimensions en cases de la partie affichée. Ces dimensions peuvent changer.
	 * @see Carte#Carte(PanneauPartie, int, int, int, int)
	 * @see Carte#zoomer(int)
	 */
	private int largAffC, hautAffC;
	/**
	 * Les dimensions en pixels de la mini-map. Ces dimensions peuvent changer.
	 * @see Fenetre#passerPleinEcran()
	 * @see Carte#zoomer(int)
	 */
	private int hautMM, largMM;
	/**
	 * Le rayon d'une case représenté par un hexagone sur la map affichée. Il peut changer.
	 * @see Fenetre#passerPleinEcran()
	 */
	private int rayonHex = 25;
	/**
	 * Le rayon d'une case représenté par un hexagone sur la mini-map. Il peut changer.
	 * @see Fenetre#passerPleinEcran()
	 */
	private int rayonMM = 4;
	/**
	 * Le point d'origine de la map affichée. Il peut changer.
	 * @see Carte#deplacer(Position)
	 * @see Point
	 */
	private Point origine;
	/**
	 * Le point d'origine de la mini-map. Il peut changer.
	 * @see Fenetre#passerPleinEcran()
	 * @see Point
	 */
	private Point origineMM;
	/**
	 * Les nombres de héros et monstres sur la grille. Ils peuvent changer.
	 * @see Carte#mort(Soldat)
	 */
	private int nbHeros, nbMonstres;
	/**
	 * La grille représentant toute la carte. Ces éléments peuvent changer, la grille elle-même, non.
	 * @see Element
	 */
	private final Element[][] GRILLE;
	/**
	 * Une zone rectangulaire représentant la partie de la carte qui sera affichée, elle peut changer.
	 * @see Carte#recalculerMapAff()
	 * @see ZoneR
	 */
	private final ZoneR MAPAFF;
	/**
	 * La position du centre de la zone rectangulaire représentant la carte qui sera affichée, elle peut changer
	 * @see Carte#deplacer(Position)
	 */
	private Position centreAff;
	/**
	 * L'élément sélectionné par l'utilisateur et celui sur lequel est le curseur. Ils peuvent changer
	 * @see Carte#setCurseur(Element)
	 * @see Carte#setSelection(Element)
	 * @see Carte#deplacerCurseur(Point)
	 * @see Carte#deplacerSelection(Point)
	 */
	private Element curseur, selection;
	/**
	 * Le chemin entre l'élément du curseur et celui sélectionné, si ce dernier est un héros. Il peut changer
	 * @see Carte#deplacerCurseur(Point)
	 * @see Carte#deplacerSelection(Point)
	 * @see Carte#finirTour(char)
	 */
	private CheminDijkstra chemin;
	/**
	 * Le type de l'attaque choisie par l'utilisateur (DISTANCE, CORPS_CORPS). Il peut changer
	 * @see Carte#setTypeAttaque(int)
	 */
	private int typeAttaque = CORPS_CORPS;
	/**
	 * Les informations sur la partie. Elles peuvent changer
	 * @see Carte#mort(Soldat)
	 * @see Carte#finirTour(char)
	 */
	private InfoPartie infoPartie;
	/**
	 * Les listes des monstres et héros. Elles peuvent changer
	 * @see Carte#mort(Soldat)
	 */
	List<Element> listeMonstres, listeHeros;
	/**
	 * La liste des processus lancés par l'utilisateur (actions, déplacements, tours de l'ordi). Elle peut changer
	 * @see Carte#deplacerHeros(Element)
	 * @see Carte#faireAttaquerHeros(Element)
	 * @see Carte#finirTour(char)
	 */
	transient List<Thread> listeThreads;
	
    /**
     * Constructeur Carte.
     * <p>
     * A la construction, les dimensions en pixels et en cases sont calculées, la partie affichée est construite et la grille de jeu est générée aléatoirement.
     * Les éléments interactifs sont générés.
     * </p>
     * 
     * @param panPartie
     * 				Le panneauPartie qui contiendra la carte.
     * @param largeur
     *            	La largeur de la grille.
     * @param hauteur
     * 			  	La hauteur de la grile.
     * @param nbHeros
     * 				Le nombre de héros
     * @param nbMonstre
     * 				Le nombre de monstres
     * @see Carte#panPartie
     * @see Carte#LARGC
     * @see Carte#HAUTC
     * @see Carte#nbHeros
     * @see Carte#nbMonstres
     */
	public Carte(PanneauPartie panPartie, int largeur, int hauteur, int nbHeros, int nbMonstres) {
		int horiz, vert, horizMM, vertMM;
		horiz = (int)(Math.sqrt(3.) * rayonHex);
		vert = (int)(3 / 2. * rayonHex);
		// Initialisations
		//// Mini-map
		horizMM = (int)(Math.sqrt(3.) * rayonMM); 
		vertMM = (int)(3 / 2. * rayonMM);
		hautMM = hauteur * vertMM + hauteur / 10;
		largMM = largeur * horizMM + largeur;
		
		largeurMap = largeurFenetre - largMM;
		this.panPartie = panPartie;
		LARGC = largeur;
		HAUTC = hauteur;
		hautAffC = hauteurMap / vert;
		largAffC = largeurMap / horiz;
		
		//// Grille + Map affichée
		GRILLE = new Element[hauteur][largeur];
		centreAff = new Position(largeur / 2, hauteur / 2);
		MAPAFF = new ZoneR(this, centreAff, largAffC, hautAffC);
		// Calcul des origines
		origine = new Point(0, 0);
		origineMM = new Point(rayonMM + 5, rayonMM + 5);
		// Génération des éléments
		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++)
				GRILLE[i][j] = new Sol(this, TypeSol.PLAINE ,new Position(j, i));
		this.nbHeros = nbHeros;
		this.nbMonstres = nbMonstres;
		genereObstacles();
		genereSol();
		genereHeros(nbHeros);
		genereMonstres(nbMonstres);
		// Éléments sélectionnés au départ
		selection = trouveHeros();
		// Infos sur la partie
		infoPartie = new InfoPartie(this, nbHeros, nbMonstres);
		// Threads
		listeThreads = new ArrayList<Thread>();
	}
	

    /**
     * Retourne le PanneauPartie de la carte.
     * 
     * @return PanneauPartie de la carte. 
     */
	public PanneauPartie getPanPartie() { return panPartie; }
    /**
     * Retourne la largeur de la carte.
     * 
     * @return Largeur de la carte. 
     */
	public int getLARGC() { return LARGC; }
    /**
     * Retourne la hauteur de la carte.
     * 
     * @return Hauteur de la carte. 
     */
	public int getHAUTC() { return HAUTC; }
    /**
     * Retourne la largeur de la partie affichée de la carte.
     * 
     * @return Largeur de la partie affichée de la carte. 
     */
	public int getLargAffC() { return largAffC; }
    /**
     * Retourne la hauteur de la partie affichée de la carte.
     * 
     * @return Hauteur de la partie affichée de la carte. 
     */
	public int getHautAffC() { return hautAffC; }
    /**
     * Retourne la largeur en pixels de la mini-map.
     * 
     * @return Largeur en pixels de la mini-map. 
     */
	public int getLargMM() { return largMM; }
    /**
     * Retourne la hauteur en pixels de la mini-map.
     * 
     * @return Hauteur en pixels de la mini-map. 
     */
	public int getHautMM() { return hautMM; }
    /**
     * Retourne le rayon d'un hexagone représentant une case de la partie affichée.
     * 
     * @return Rayon d'un hexagone représentant une case de la partie affichée.
     */
	public int getRayonHex() { return rayonHex; }
    /**
     * Retourne le rayon d'un hexagone représentant une case de la mini-map.
     * 
     * @return Rayon d'un hexagone représentant une case de la mini-map.
     */
	public int getRayonMM() { return rayonMM; }
    /**
     * Retourne l'origine de la partie affichée.
     * 
     * @return Origine de la partie affichée. 
     */
	public Point getOrigine() { return origine; }
    /**
     * Retourne l'origine de la mini-map.
     * 
     * @return Origine de la mini-map.
     */
	public Point getOrigineMM() { return origineMM; }
    /**
     * Retourne la zone rectangulaire représentant la partie affichée.
     * 
     * @return Zone rectangulaire représentant la partie affichée. 
     */
	public ZoneR getMapAff() { return MAPAFF; }
    /**
     * Retourne le centre de la partie affichée de la carte.
     * 
     * @return Centre de la partie affichée de la carte. 
     */
	public Position getCentreAff() { return centreAff; }
    /**
     * Retourne les informations sur la partie.
     * 
     * @return Informations sur la partie. 
     */
	public InfoPartie getInfoPartie() { return infoPartie; }
    /**
     * Retourne la liste de monstres.
     * 
     * @return Liste de monstres. 
     */
	public List<Element> getListeMonstres() { return listeMonstres; }
    /**
     * Retourne la liste des héros.
     * 
     * @return Liste des héros. 
     */
	public List<Element> getListeHeros() { return listeHeros; }
    /**
     * Retourne l'élément sur lequel est le curseur.
     * 
     * @return Elément sur lequel est le curseur. 
     */
	public Element getCurseur() { return curseur; }
    /**
     * Retourne l'élément sélectionné.
     * 
     * @return Elément sélectionné. 
     */
	public Element getSelection() { return selection; }
    /**
     * Retourne la liste des threads en cours.
     * 
     * @return Liste des threads en cours. 
     */
	public List<Thread> getListeThreads() { return listeThreads; }
    /**
     * Retourne l'élément dont la position est donnée
     * 
     * @param Position de l'élément dans la grille
     * @return Élément dont la position est donnée. 
     */
	public Element getElement(Position pos) {
		return (pos.estValide(LARGC, HAUTC)) ? GRILLE[pos.getY()][pos.getX()] : null;
	}
	
	/**
	 * Modifie l'élément représentant le curseur
	 * 
	 * @param curseur
	 */
	public void setCurseur(Element curseur) { this.curseur = curseur; }
	/**
	 * Modifie l'élement sélectionné
	 * 
	 * @param selection
	 */
	public void setSelection(Element selection) { this.selection = selection; }
	/**
	 * Modifie le type de l'attaque
	 * 
	 * @param typeAttaque
	 */
	public void setTypeAttaque(int typeAttaque) { this.typeAttaque = typeAttaque; }
	/**
	 * Modifie le panneauPartie sur lequel est la carte
	 * 
	 * @param panPartie
	 */
	public void setPanPartie(PanneauPartie panPartie) { this.panPartie = panPartie; }
	/**
	 * Change l'élément à la position donnée par l'élément donné
	 * 
	 * @param pos
	 * @param elem
	 */
	public void setElement(Position pos, Element elem) {
		if (pos.estValide(LARGC, HAUTC)) GRILLE[pos.getY()][pos.getX()] = elem;
	}
	
	/**
	 * Recalcules la 
	 */
	public void recalculerMapAff() {
		int horiz, vert;
		horiz = (int)(Math.sqrt(3.) * rayonHex);
		vert = (int)(3 / 2. * rayonHex);
		largAffC = largeurMap / horiz;
		hautAffC = hauteurMap / vert;
		// Modification des extremités de la zone de la carte affichée
		MAPAFF.setUpLeft(MAPAFF.calculerUpLeft(centreAff, largAffC, hautAffC));
		MAPAFF.setDownRight(MAPAFF.calculerDownRight(centreAff, largAffC, hautAffC));
		// Modification des dimensions de cette dernière
		MAPAFF.setLargeur(MAPAFF.calculerLargeur());
		MAPAFF.setHauteur(MAPAFF.calculerHauteur());
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
			elemVide = GRILLE[y][x];
			posElemVide = new Position(x, y);
			test = typeof(elemVide, type);
		} while (!(test)); // Tant que l'élément du type recherche n'a pas été retrouvé
		return posElemVide;
	}
	// Trouve aléatoirement une position vide sur la carte réelle
	public Position trouvePosVide() {
		return trouvePosType(0, LARGC - 1, 0, HAUTC - 1, 's');
	}
	// Trouve aléatoirement un héros sur la carte réelle
	public Heros trouveHeros() {
		return (Heros)listeHeros.get((listeHeros.indexOf(selection) + 1) % listeHeros.size());
	}
	// Tue le soldat
	public void mort(Soldat victime) {
		setElement(victime.getPos(), victime.getSol());
		// L'élément sélectionné ou celui focalisé par le curseur doivent peut-être changé
		if (selection != null && selection.getPos().equals(victime.getPos())) selection = victime.getSol();
		if (curseur != null && curseur.getPos().equals(victime.getPos())) curseur = victime.getSol();
		if (victime instanceof Heros) {
			listeHeros.remove(listeHeros.indexOf((Element)victime));
			infoPartie.setNbHeros(--nbHeros);
		} else if (victime instanceof Monstre) {
			listeMonstres.remove(listeMonstres.indexOf((Element)victime));
			infoPartie.setNbMonstres(--nbMonstres);
		}
		victime = null;
		panPartie.repaint();
	}
	// Génère aléatoirement des héros
	public void genereHeros(int n) {
		int c = 0,
			debY = 0, finY = HAUTC - 1,
			debX = LARGC / 2 + 1, finX = LARGC - 1;
		listeHeros = new ArrayList<Element>();
		while (c++ < n) {
			String nom = "" + (char)('A' + alea(0, 26));
			Position posVide = trouvePosType(debX, finX, debY, finY, 's');
			Heros h = new Heros(this, ISoldat.TypesH.getTypeHAlea(), nom, posVide);
			this.setElement(posVide, h);
			listeHeros.add(h);
		}
	}
	// Génère aléatoirement des monstres
	public void genereMonstres(int n) {
		int c = 0,
			debY = 0, finY = HAUTC - 1,
			debX = 0, finX = LARGC / 2;
		listeMonstres = new ArrayList<Element>();
		while (c++ < n) {
			Position posVide = trouvePosType(debX, finX, debY, finY, 's');
			Monstre m = new Monstre(this, ISoldat.TypesM.getTypeMAlea(), posVide);
			this.setElement(posVide, m);
			listeMonstres.add(m);
		}
	}
	// Génère une zone contenant un type d'obstacles donné
	public void genereZoneType(int min, int max, Object t) {
		int nbVoisins, taille;
		PositionAxiale posA = trouvePosVide().toPositionAxiale();
		taille = alea(min, max);
		while(taille > 0) {
			nbVoisins = alea(1, 5);
			for (int i = 0; i < nbVoisins; i++) {
				Position voisin = posA.voisin(i).toPosition();
				if (voisin.estValide(largeurMap, hauteurMap) && this.getElement(voisin) instanceof Sol) {
					if (t instanceof TypeObstacle)
						this.setElement(voisin, new Obstacle(this, (TypeObstacle)t, voisin));
					else if (t instanceof TypeSol && ((Sol)this.getElement(voisin)).getTYPE() == TypeSol.PLAINE)
						this.setElement(voisin, new Sol(this, (TypeSol)t, voisin));
				}
			}
			posA = posA.voisin(alea(0, nbVoisins));
			taille -= nbVoisins;
		}
	}
	// Génère aléatoirement des obstacles 
	public void genereObstacles() {
		int nbZone = alea(10, 20);
		while (nbZone-- > 0)
			genereZoneType(5, 15, TypeObstacle.EAU);
		nbZone = alea(5, 10);
		while (nbZone-- > 0)
			genereZoneType(1, 2, TypeObstacle.ROCHER);
		nbZone = alea(5, 15);
		while (nbZone-- > 0)
			genereZoneType(4, 15, TypeObstacle.FORET);
	}
	// Génère aléatoirement des sols
	public void genereSol() {
		int nbZone = alea(10, 20);
		while (nbZone-- > 0)
			genereZoneType(10, 20, TypeSol.COLLINE);
		nbZone = alea(5, 10);
		while (nbZone-- > 0)
			genereZoneType(2, 3, TypeSol.VILLAGE);
		nbZone = alea(5, 10);
		while (nbZone-- > 0)
			genereZoneType(5, 10, TypeSol.MONTAGNE);
		nbZone = alea(10, 20);
		while (nbZone-- > 0)
			genereZoneType(10, 20, TypeSol.DESERT);
	}
	
	// Calcul tous les hexagones des éléments de la carte
	public void calculerHex() {
		for (Element[] liste : GRILLE)
			for (Element e : liste)
				e.creerHexM();
	}
	// Réinitialise les portees de déplacement
	public void reinitPorteeDep() {
		for (Element e : listeHeros)
			((Soldat)e).setPorteeDeplacement(((Soldat)e).getPORTEE_DEPLACEMENT_MAX());
		for (Element e : listeMonstres)
			((Soldat)e).setPorteeDeplacement(((Soldat)e).getPORTEE_DEPLACEMENT_MAX());
		recalculerZonesDep();
	}
	// Recalcules les zones de déplacement
	public void recalculerZonesDep() {
		for (Element e : listeHeros)
			((Soldat)e).majZoneDeplacement();
		for (Element e : listeMonstres)
			((Soldat)e).majZoneDeplacement();
	}
	// Recalcules les zones visuelles et les appliques
	public void recalculerZonesVisuelles() {
		for (Element e : listeHeros) {
			((Soldat)e).majZoneVisuelle();
			((Heros)e).getZoneVisuelle().rendreVisible();
		}
		for (Element e : listeMonstres)
			((Soldat)e).majZoneVisuelle();
	}
	// Applique les guérisons là où il faut
	public void appliquerGuerisons(List<Element> soldats) {
		for (Element s : soldats)
			if (!((Soldat)s).getAJoue()) ((Soldat)s).guerir();
	}
	// Reinitialise les variables aJoue de la liste de soldats donnée
	public void reinitiAJoue(List<Element> soldats) {
		for (Element s : soldats)
			((Soldat)s).setAJoue(false);
	}
	
	// Méthodes d'interaction
	// Déplace le curseur
	public void deplacerCurseur(Point c) {
		Position p = c.toPositionAxiale(rayonHex, origine).toPosition().add(MAPAFF.getUpLeft());
		if (MAPAFF.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
			p = p.add(new Position(1, 0));
		curseur = getElement(p);
		if (selection instanceof Heros && curseur != null && ((Heros)selection).getZoneDeplacement().indexOf(curseur) != -1)
			chemin = new CheminDijkstra(selection, curseur, ((Heros)selection).getZoneDeplacement());
		else chemin = null;
	}
	// Déplace la sélection
	public void deplacerSelection(Point s) {
		Position p = s.toPositionAxiale(rayonHex, origine).toPosition().add(MAPAFF.getUpLeft());
		if (MAPAFF.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
			p = p.add(new Position(1, 0));
		Element e = getElement(p);
		if (selection != null) selection = (p.equals(selection.getPos())) ? null : e;
		else selection = e;
		chemin = null;
	}
	// Zoome la zone d'affichage
	public void zoomer(int zoom) {
		if (zoom >= 6 && zoom <= 18) {
			rayonHex = (int)(2.5 * zoom);
			recalculerMapAff();
		}
	}
	// Déplace la zone affichée autour du point p
	public void deplacer(Point p) {
		if (p.estValide(origineMM, (new Point(largMM, hautMM))))
			deplacer(p.toPositionAxiale(rayonMM, origineMM).toPosition());
	}
	// Déplace la zone affichée autour de la position p
	public void deplacer(Position p) {
		centreAff = p;
		MAPAFF.setUpLeft(MAPAFF.calculerUpLeft(centreAff, largAffC, hautAffC));
		MAPAFF.setDownRight(MAPAFF.calculerDownRight(centreAff, largAffC, hautAffC));
		// Calcul de l'origine
		int largMAC = MAPAFF.getLargeur(),							// largMAC = largAffC => pas toujours !
			hautMAC = MAPAFF.getHauteur(),							// Idem
			xC = centreAff.getX(),									// C = Centre de la carte (= zone) affichée
			yC = centreAff.getY();
		// Calcul des coordonnées de l'origine de la carte
		Position po = new Position(xC - largAffC / 2 < 0 ? largAffC - largMAC : 0,
								   yC - hautAffC / 2 < 0 ? hautAffC - hautMAC : 0);
		origine = po.toPoint(rayonHex);
		// On recalcule tous les hexagones
		calculerHex();
	}
	// Actions du héros
	public void faireAgirHeros(Point p) {
		Element cible;
		if (selection instanceof Heros && !((Soldat)selection).getAJoue()) {
			Position posCible = p.toPositionAxiale(rayonHex, origine).toPosition().add(MAPAFF.getUpLeft());
			if (MAPAFF.getUpLeft().getY() % 2 != 0 && posCible.getY() % 2 == 0)
				posCible = posCible.add(new Position(1, 0));
			cible = getElement(posCible);
			if (cible instanceof Monstre)
				faireAttaquerHeros(cible);
			else
				deplacerHeros(cible);
		}
	}
	// Déplace l'élément sélectionné si c'est un héros
	public void deplacerHeros(Element cible) {
		if (selection instanceof Heros) {
			if (((Soldat)selection).zoneDeplacementContient(cible)) {
				chemin = null;
				CheminDijkstra ch = new CheminDijkstra(selection, cible, ((Soldat)selection).getZoneDeplacement());
				DeplacementSoldat ds = new DeplacementSoldat(this, (Soldat)selection, ch.getChemin(), new ArrayList<Thread>(listeThreads));
				selection = null;
				listeThreads.add(ds);
				ds.start();
			}
		}
	}
	// Fait attaquer le héros
	public void faireAttaquerHeros(Element cible) {
		if (selection instanceof Heros) {
			if (typeAttaque == CORPS_CORPS) {
				if (((Soldat)selection).zoneDeplacementContient(cible)) {
					chemin = null;
					AttaqueSoldatCorps as = new AttaqueSoldatCorps(this, (Soldat)selection, (Soldat)cible, new ArrayList<Thread>(listeThreads));
					selection = null;
					listeThreads.add(as);
					as.start();
				}
			} else if (typeAttaque == DISTANCE) {
				if (((Soldat)selection).verifieAttaqueDistance((Soldat)cible)) {
					chemin = null;
					AttaqueSoldatDistance as = new AttaqueSoldatDistance(this, (Soldat)selection, (Soldat)cible, new ArrayList<Thread>(listeThreads));
					selection = null;
					listeThreads.add(as);
					as.start();
				}
			}
		}
	}
	
	// Mets fin au tour du joueur
	public void finirTour(char side) {
		if (side == GENTILS) {
			selection = null;
			curseur = null;
			chemin = null;
			// On enlève les boutons destinés au joueur
			panPartie.getTableauBord().getBoutonsTour().setVisible(false);
			panPartie.getTableauBord().getActionsHeros().setVisible(false);
			// On réinitialise les points de guérison, déplacement des monstres
			appliquerGuerisons(listeMonstres);
			reinitiAJoue(listeMonstres);
			reinitPorteeDep();
			// On change les infos sur la partie
			infoPartie.setNbTours(infoPartie.getNbTours() + 1);
			infoPartie.setJoueur(MECHANT);
			panPartie.repaint();
			// Lancement du thread du général adversaire
			TourOrdi to = new TourOrdi(this, new ArrayList<Thread>(listeThreads));
			listeThreads.add(to);
			to.start();
		} else if (side == MECHANT) {
			// On remet les boutons destinés au joueur
			panPartie.getTableauBord().getBoutonsTour().setVisible(true);
			// On réinitialise les points de guérison, déplacement des héros
			appliquerGuerisons(listeHeros);
			reinitiAJoue(listeHeros);
			reinitPorteeDep();
			// On change les infos sur la partie
			infoPartie.setJoueur(GENTILS);
			panPartie.repaint();
		}
	}
	
	// Méthodes graphiques
	public void seDessiner(Graphics2D g) {
		MAPAFF.seDessiner(g);
		if (selection != null)
			if (selection instanceof Heros) ((Soldat)selection).dessinerZoneDeplacement(g);
		if (chemin != null) chemin.seDessiner(g);
		if (selection instanceof Heros && typeAttaque == DISTANCE) ((Soldat)selection).getZoneVisuelle().seDessiner(g);
		if (selection != null) selection.seDessinerCadre(g, COULEUR_SELECTION);
		if (curseur != null) curseur.seDessinerCadre(g, COULEUR_CURSEUR);
		if (curseur != null) curseur.dessinerInfoBulle(g);
		if (selection != null) selection.dessinerInfoBulle(g);
	}
	
	// Dessine la carte reelle sous forme de mini-map
	public void seDessinerMM(Graphics2D g) {
		for (Element[] liste : GRILLE)
			for (Element e : liste)
				e.seDessinerMM(g);
		if (selection != null)
			// if (selection instanceof Heros) ((Soldat)selection).dessinerZoneDeplacementMM(g);
			selection.seDessinerCadreMM(g, COULEUR_SELECTION);
		if (curseur != null) curseur.seDessinerCadreMM(g, COULEUR_CURSEUR);
		// Dessin d'un rectangle représentant la zone affichée
		Point ul = MAPAFF.getUpLeft().toPositionAxiale().toPoint(rayonMM, origineMM).substract(new Point(rayonMM, rayonMM)),
			  dr = MAPAFF.getDownRight().toPositionAxiale().toPoint(rayonMM, origineMM);
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
