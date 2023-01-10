package wargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import wargame.Obstacle.TypeObstacle;
import wargame.Sol.TypeSol;
import wargameInterface.Fenetre;
import wargameInterface.PanneauActionsHeros;
import wargameInterface.PanneauBoutonsTour;
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
	protected int typeAttaque = CORPS_CORPS;
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
     * 
     * @see Carte#panPartie
     */
	public PanneauPartie getPanPartie() { return panPartie; }
    /**
     * Retourne la largeur de la carte.
     * 
     * @return Largeur de la carte. 
     * 
     * @see Carte#LARGC
     */
	public int getLARGC() { return LARGC; }
    /**
     * Retourne la hauteur de la carte.
     * 
     * @return Hauteur de la carte.
     * 
     *  @see Carte#HAUTC
     */
	public int getHAUTC() { return HAUTC; }
    /**
     * Retourne la largeur de la partie affichée de la carte.
     * 
     * @return Largeur de la partie affichée de la carte. 
     * 
     * @see Carte#largAffC
     */
	public int getLargAffC() { return largAffC; }
    /**
     * Retourne la hauteur de la partie affichée de la carte.
     * 
     * @return Hauteur de la partie affichée de la carte. 
     * 
     * @see Carte#hautAffC
     */
	public int getHautAffC() { return hautAffC; }
    /**
     * Retourne la largeur en pixels de la mini-map.
     * 
     * @return Largeur en pixels de la mini-map. 
     * 
     * @see Carte#LargMM
     */
	public int getLargMM() { return largMM; }
    /**
     * Retourne la hauteur en pixels de la mini-map.
     * 
     * @return Hauteur en pixels de la mini-map. 
     * 
     * @see Carte#HautMM
     */
	public int getHautMM() { return hautMM; }
    /**
     * Retourne le rayon d'un hexagone représentant une case de la partie affichée.
     * 
     * @return Rayon d'un hexagone représentant une case de la partie affichée.
     * 
     * @see Carte#rayonHex
     */
	public int getRayonHex() { return rayonHex; }
    /**
     * Retourne le rayon d'un hexagone représentant une case de la mini-map.
     * 
     * @return Rayon d'un hexagone représentant une case de la mini-map.
     * 
     * @see Carte#rayonMM
     */
	public int getRayonMM() { return rayonMM; }
    /**
     * Retourne l'origine de la partie affichée.
     * 
     * @return Origine de la partie affichée. 
     * 
     * @see Carte#origine
     */
	public Point getOrigine() { return origine; }
    /**
     * Retourne l'origine de la mini-map.
     * 
     * @return Origine de la mini-map.
     * 
     * @see Carte#origineMM
     */
	public Point getOrigineMM() { return origineMM; }
    /**
     * Retourne la zone rectangulaire représentant la partie affichée.
     * 
     * @return Zone rectangulaire représentant la partie affichée.
     * 
     * @see Carte#MAPAFF
     */
	public ZoneR getMapAff() { return MAPAFF; }
    /**
     * Retourne le centre de la partie affichée de la carte.
     * 
     * @return Centre de la partie affichée de la carte. 
     * 
     * @see Carte#centreAff
     */
	public Position getCentreAff() { return centreAff; }
    /**
     * Retourne les informations sur la partie.
     * 
     * @return Informations sur la partie. 
     * 
     * @see Carte#infoPartie
     */
	public InfoPartie getInfoPartie() { return infoPartie; }
    /**
     * Retourne la liste de monstres.
     * 
     * @return Liste de monstres. 
     * 
     * @see Carte#listeMonstres
     */
	public List<Element> getListeMonstres() { return listeMonstres; }
    /**
     * Retourne la liste des héros.
     * 
     * @return Liste des héros. 
     * 
     * @see Carte#listeHeros
     */
	public List<Element> getListeHeros() { return listeHeros; }
    /**
     * Retourne l'élément sur lequel est le curseur.
     * 
     * @return Elément sur lequel est le curseur. 
     * 
     * @see Carte#curseur
     */
	public Element getCurseur() { return curseur; }
    /**
     * Retourne l'élément sélectionné.
     * 
     * @return Elément sélectionné. 
     * 
     * @see Carte#selection
     */
	public Element getSelection() { return selection; }
    /**
     * Retourne la liste des threads en cours.
     * 
     * @return Liste des threads en cours. 
     * 
     * @see Carte#listeThreads
     */
	public List<Thread> getListeThreads() { return listeThreads; }
    /**
     * Retourne l'élément dont la position est donnée
     * 
     * @param Position de l'élément dans la grille
     * @return Élément dont la position est donnée. 
     * 
     * @see Position
     * @see Carte#GRILLE
     */
	public Element getElement(Position pos) {
		return (pos.estValide(LARGC, HAUTC)) ? GRILLE[pos.getY()][pos.getX()] : null;
	}
	
	/**
	 * Modifie l'élément représentant le curseur
	 * 
	 * @param curseur
	 * 			Nouvel élément représentant le curseur
	 * 
	 * @see Element
	 * @see Carte#curseur
	 */
	public void setCurseur(Element curseur) { this.curseur = curseur; }
	/**
	 * Modifie l'élement sélectionné
	 * 
	 * @param selection
	 * 			Nouvel élément sélectionné
	 * 
	 * @see Element
	 * @see Carte#selection
	 */
	public void setSelection(Element selection) { this.selection = selection; }
	/**
	 * Modifie le type de l'attaque (DISTANCE, CORPS_CORPS)
	 * 
	 * @param typeAttaque
	 * 			Nouveau type de l'attaque
	 * 
	 * @see Carte#typeAttaque
	 * @see IConfig#CORPS_CORPS
	 * @see IConfig#DISTANCE
	 */
	public void setTypeAttaque(int typeAttaque) { this.typeAttaque = typeAttaque; }
	/**
	 * Modifie le PanneauPartie sur lequel est la carte
	 * 
	 * @param panPartie
	 * 			Nouveau PanneauPartie
	 * 
	 * @see PanneauPartie
	 * @see Carte#panPartie
	 */
	public void setPanPartie(PanneauPartie panPartie) { this.panPartie = panPartie; }
	/**
	 * Change l'élément à la position donnée par l'élément donné
	 * 
	 * @param pos
	 * 			Position de la grille où sera placé l'élément
	 * @param elem
	 * 			Nouvel élément
	 * 
	 * @see Element
	 * @see Position
	 * @see Carte#GRILLE
	 */
	public void setElement(Position pos, Element elem) {
		if (pos.estValide(LARGC, HAUTC)) GRILLE[pos.getY()][pos.getX()] = elem;
	}
	
	/**
	 * Recalcules les extremités de la zone rectangulaire représentant la partie affichée de la carte
	 * 
	 * @see Carte#rayonHex
	 * @see Carte#largeurMap
	 * @see Carte#hauteurMap
	 * @see Carte#largAffC
	 * @see Carte#hautAffC
	 * @see Carte#MAPAFF
	 * @see Carte#calculerHex()
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
	/**
	 * Trouve aléatoirement une position de type donné dans une zone dont les extremités sont données en paramètres
	 * 
	 * @param debX
	 * 			Indice de colonne de départ
	 * @param finX
	 * 			Indice de colonne de fin
	 * @param debY
	 * 			Indice de ligne de départ
	 * @param finY
	 * 			Indice de ligne de fin
	 * @param type
	 * 			Type de l'élémént recherché ('h', 's')
	 * @return Position alétoire du type donné
	 * 
	 * @see Carte#typeof(Element, char)
	 * @see Carte#GRILLE
	 * @see Element
	 * @see Position
	 * @see MethodesAuxiliaires#alea(int, int)
	 */
	public Position trouvePosType(int debX, int finX, int debY, int finY, char type) {
		Element elemVide = null;
		Position posElemVide = null;
		boolean test;
		do {
			int x = MethodesAuxiliaires.alea(debX, finX), 
				y = MethodesAuxiliaires.alea(debY, finY);
			elemVide = GRILLE[y][x];
			posElemVide = new Position(x, y);
			test = typeof(elemVide, type);
		} while (!(test)); // Tant que l'élément du type recherche n'a pas été retrouvé
		return posElemVide;
	}
	/**
	 * Trouve aléatoirement une position vide sur la carte réelle
	 * 
	 * @return Position aléatoire d'un élément de type sol ('s')
	 * 
	 * @see Carte#trouvePosType(int, int, int, int, char)
	 * @see MethodesAuxiliaires#alea(int, int)
	 */
	public Position trouvePosVide() {
		return trouvePosType(0, LARGC - 1, 0, HAUTC - 1, 's');
	}
	/**
	 * Trouve aléatoirement un héros sur la carte réelle
	 * 
	 * @return Héros aléatoire
	 * 
	 * @see Carte#listeHeros
	 * @see Heros
	 */
	public Heros trouveHeros() {
		return (Heros)listeHeros.get((listeHeros.indexOf(selection) + 1) % listeHeros.size());
	}
	/**
	 * Tue le soldat
	 * 
	 * @param victime
	 * 			Soldat à tuer
	 * 
	 * @see Soldat
	 * @see Carte#setElement(Position, Element)
	 * @see Carte#setCurseur(Element)
	 * @see Carte#setSelection(Element)
	 * @see Carte#listeHeros
	 * @see Carte#listeMonstres
	 * @see Carte#infoPartie
	 * @see Carte#panPartie
	 * 
	 */
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
		
		if (listeHeros.size() == 0) {
			if (JOptionPane.showConfirmDialog(null, "Vous avez perdu ! Voulez-vous refaire une partie ?", "Défaite", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
				panPartie.getF().nouvellePartie();
			} else {
				panPartie.getF().quitter();
			}
		} else if (listeMonstres.size() == 0) {
			if (JOptionPane.showConfirmDialog(null, "Vous avez gagné ! Voulez-vous refaire une partie ?", "Victoire", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
				panPartie.getF().nouvellePartie();
			} else {
				panPartie.getF().quitter();
			}
		}
		
		panPartie.repaint();
	}
	/**
	 * Génère aléatoirement des héros
	 * 
	 * @param n
	 * 			Nombre de héros à générer
	 * 
	 * @see Carte#listeHeros
	 * @see 
	 * @see Position
	 * @see Carte#trouvePosType(int, int, int, int, char)
	 * @see Heros
	 * @see Carte#setElement(Position, Element)
	 * @see ISoldat
	 */
	public void genereHeros(int n) {
		int c = 0,
			debY = 0, finY = HAUTC - 1,
			debX = LARGC / 2 + 1, finX = LARGC - 1;
		listeHeros = new ArrayList<Element>();
		while (c++ < n) {
			String nom = "" + (char)('A' + MethodesAuxiliaires.alea(0, 26));
			Position posVide = trouvePosType(debX, finX, debY, finY, 's');
			Heros h = new Heros(this, ISoldat.TypesH.getTypeHAlea(), nom, posVide);
			this.setElement(posVide, h);
			listeHeros.add(h);
		}
	}
	/**
	 * Génère aléatoirement des monstres
	 * @param n
	 * 			Nombre de monstres à générer
	 * 
	 * @see Carte#listeMonstres
	 * @see Position
	 * @see Carte#trouvePosType(int, int, int, int, char)
	 * @see Monstre
	 * @see ISoldat
	 * @see Carte#setElement(Position, Element)
	 */
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
	/**
	 * Génère une zone aléatoire contenant un type d'obstacles donné
	 * 
	 * @param min
	 * 			Taille minimale de la zone
	 * @param max
	 * 			Taille maximale de la zone
	 * @param t
	 * 			Type de la zone (TypeSol ou TypeObstacle)
	 * 
	 * @see PositionAxiale
	 * @see Carte#trouvePosVide()
	 * @see Position#toPositionAxiale()
	 * @see MethodesAuxiliaires#alea(int, int)
	 * @see PositionAxiale#voisin(int)
	 * @see PositionAxiale#toPosition()
	 * @see Position#estValide(int, int)
	 * @see Carte#getElement(Position)
	 * @see Sol
	 * @see TypeObstacle
	 * @see Obstacle#Obstacle(Carte, TypeObstacle, Position)
	 * @see TypeSol
	 * @see Sol#getTYPE()
	 * @see Sol#Sol(Carte, TypeSol, Position)
	 */
	public void genereZoneType(int min, int max, Object t) {
		int nbVoisins, taille;
		PositionAxiale posA = trouvePosVide().toPositionAxiale();
		taille = MethodesAuxiliaires.alea(min, max);
		while(taille > 0) {
			nbVoisins = MethodesAuxiliaires.alea(1, 5);
			for (int i = 0; i < nbVoisins; i++) {
				Position voisin = posA.voisin(i).toPosition();
				if (voisin.estValide(largeurMap, hauteurMap) && this.getElement(voisin) instanceof Sol) {
					if (t instanceof TypeObstacle)
						this.setElement(voisin, new Obstacle(this, (TypeObstacle)t, voisin));
					else if (t instanceof TypeSol && ((Sol)this.getElement(voisin)).getTYPE() == TypeSol.PLAINE)
						this.setElement(voisin, new Sol(this, (TypeSol)t, voisin));
				}
			}
			posA = posA.voisin(MethodesAuxiliaires.alea(0, nbVoisins));
			taille -= nbVoisins;
		}
	}
	/**
	 * Génère aléatoirement des obstacles 
	 * 
	 * @see MethodesAuxiliaires#alea(int, int)
	 * @see Carte#genereZoneType(int, int, Object)
	 * @see TypeObstacle
	 */
	public void genereObstacles() {
		int nbZone = MethodesAuxiliaires.alea(10, 20);
		while (nbZone-- > 0)
			genereZoneType(5, 15, TypeObstacle.EAU);
		nbZone = MethodesAuxiliaires.alea(5, 10);
		while (nbZone-- > 0)
			genereZoneType(1, 2, TypeObstacle.ROCHER);
		nbZone = MethodesAuxiliaires.alea(5, 15);
		while (nbZone-- > 0)
			genereZoneType(4, 15, TypeObstacle.FORET);
	}
	/**
	 * Génère aléatoirement des sols
	 * 
	 * @see MethodesAuxiliaires#alea(int, int)
	 * @see TypeSol
	 */
	public void genereSol() {
		int nbZone = MethodesAuxiliaires.alea(10, 20);
		while (nbZone-- > 0)
			genereZoneType(10, 20, TypeSol.COLLINE);
		nbZone = MethodesAuxiliaires.alea(5, 10);
		while (nbZone-- > 0)
			genereZoneType(2, 3, TypeSol.VILLAGE);
		nbZone = MethodesAuxiliaires.alea(5, 10);
		while (nbZone-- > 0)
			genereZoneType(5, 10, TypeSol.MONTAGNE);
		nbZone = MethodesAuxiliaires.alea(10, 20);
		while (nbZone-- > 0)
			genereZoneType(10, 20, TypeSol.DESERT);
	}
	/**
	 * Calcul tous les hexagones des éléments de la carte
	 * @see Element
	 * @see Carte#GRILLE
	 * @see Element#creerHex()
	 */
	public void calculerHex() {
		for (Element[] liste : GRILLE)
			for (Element e : liste)
				e.creerHexM();
	}
	/**
	 * Réinitialise les portees de déplacement
	 * 
	 * @see Element
	 * @see Carte#listeHeros
	 * @see Soldat
	 * @see Soldat#setPorteeDeplacement(int)
	 * @see Soldat#getPORTEE_DEPLACEMENT_MAX()
	 * @see Carte#listeMonstres
	 * @see Carte#recalculerZonesDep()
	 */
	public void reinitPorteeDep() {
		for (Element e : listeHeros)
			((Soldat)e).setPorteeDeplacement(((Soldat)e).getPORTEE_DEPLACEMENT_MAX());
		for (Element e : listeMonstres)
			((Soldat)e).setPorteeDeplacement(((Soldat)e).getPORTEE_DEPLACEMENT_MAX());
		recalculerZonesDep();
	}
	/**
	 * Recalcules les zones de déplacement
	 * 
	 * @see Carte#listeHeros
	 * @see Carte#listeMonstres
	 * @see Soldat
	 * @see Soldat#majZoneDeplacement()
	 */
	public void recalculerZonesDep() {
		for (Element e : listeHeros)
			((Soldat)e).majZoneDeplacement();
		for (Element e : listeMonstres)
			((Soldat)e).majZoneDeplacement();
	}
	/**
	 * Recalcules les zones visuelles et les appliques
	 * 
	 * @see Element
	 * @see Carte#listeHeros
	 * @see Soldat
	 * @see Soldat#majZoneVisuelle()
	 * @see Heros
	 * @see Monstre
	 * @see Carte#listeMonstres
	 * @see ZoneH#rendreVisible()
	 */
	public void recalculerZonesVisuelles() {
		for (Element e : listeHeros) {
			((Soldat)e).majZoneVisuelle();
			((Heros)e).getZoneVisuelle().rendreVisible();
		}
		for (Element e : listeMonstres)
			((Soldat)e).majZoneVisuelle();
	}
	/**
	 * Applique les guérisons là où il faut
	 * 
	 * @param soldats
	 * 			Liste de soldats
	 * 
	 * @see Element
	 * @see Soldat
	 * @see Soldat#getAJoue()
	 * @see Soldat#guerir()
	 */
	public void appliquerGuerisons(List<Element> soldats) {
		for (Element s : soldats)
			if (!((Soldat)s).getAJoue()) ((Soldat)s).guerir();
	}
	/**
	 * Reinitialise les variables aJoue de la liste de soldats donnée
	 * 
	 * @param soldats
	 * 			Liste de soldats
	 * 
	 * @see Element
	 * @see Soldat
	 * @see Soldat#setAJoue(boolean)
	 */
	public void reinitiAJoue(List<Element> soldats) {
		for (Element s : soldats)
			((Soldat)s).setAJoue(false);
	}
	
	/**
	 * Déplace le curseur
	 * 
	 * @param c
	 * 			Point où le curseur de la souris est situé
	 * 
	 * @see Point
	 * @see Position
	 * @see Point#toPositionAxiale(int, Point)
	 * @see PositionAxiale#toPosition()
	 * @see Position#add(Position)
	 * @see Carte#MAPAFF
	 * @see ZoneR#getUpLeft()
	 * @see Position#getY()
	 * @see Position#getX()
	 * @see Carte#getElement(Position)
	 * @see Carte#selection
	 * @see Heros
	 * @see Carte#curseur
	 * @see Heros#getZoneDeplacement()
	 * @see Carte#chemin
	 * @see CheminDijkstra
	 */
	public void deplacerCurseur(Point c) {
		Position p = c.toPositionAxiale(rayonHex, origine).toPosition().add(MAPAFF.getUpLeft());
		if (MAPAFF.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
			p = p.add(new Position(1, 0));
		curseur = getElement(p);
		if (selection instanceof Heros && curseur != null && ((Heros)selection).getZoneDeplacement().indexOf(curseur) != -1)
			chemin = new CheminDijkstra(selection, curseur, ((Heros)selection).getZoneDeplacement());
		else chemin = null;
	}
	/**
	 * Déplace la sélection
	 * 
	 * @param s
	 * 			Point où le curseur de la souris est situé
	 * 
	 * @see Point
	 * @see Position
	 * @see Point#toPositionAxiale(int, Point)
	 * @see PositionAxiale#toPosition()
	 * @see Position#add(Position)
	 * @see Carte#MAPAFF
	 * @see ZoneR#getUpLeft()
	 * @see Position#getY()
	 * @see Position#getX()
	 * @see Carte#getElement(Position)
	 * @see Carte#selection
	 * @see Element#getPos()
	 * @see Carte#chemin
	 */
	public void deplacerSelection(Point s) {
		Position p = s.toPositionAxiale(rayonHex, origine).toPosition().add(MAPAFF.getUpLeft());
		if (MAPAFF.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
			p = p.add(new Position(1, 0));
		Element e = getElement(p);
		if (selection != null) selection = (p.equals(selection.getPos())) ? null : e;
		else selection = e;
		chemin = null;
	}
	/**
	 * Zoome la zone d'affichage
	 * 
	 * @param zoom
	 * 			Valeur du zoom (6 <= zoom <= 18)
	 * 
	 * @see Carte#rayonHex
	 * @see Carte#recalculerMapAff()
	 */
	public void zoomer(int zoom) {
		if (zoom >= 6 && zoom <= 18) {
			rayonHex = (int)(2.5 * zoom);
			recalculerMapAff();
		}
	}
	/**
	 * Déplace la zone affichée autour du point p
	 * 
	 * @param p
	 * 			Point où le curseur de la souris est situé
	 * 
	 * @see Point
	 * @see Point#estValide(Point, Point)
	 * @see Carte#origineMM
	 * @see Carte#deplacer(Position)
	 * @see Point#toPositionAxiale(int, Point)
	 * @see PositionAxiale#toPosition()
	 */
	public void deplacer(Point p) {
		if (p.estValide(origineMM, (new Point(largMM, hautMM))))
			deplacer(p.toPositionAxiale(rayonMM, origineMM).toPosition());
	}
	/**
	 * Déplace la zone affichée autour de la position p
	 * 
	 * @param p
	 * 			Position où le centre de la partie affichée de la carte doit être
	 * 
	 * @see Carte#centreAff
	 * @see Carte#MAPAFF
	 * @see ZoneR#setUpLeft(Position)
	 * @see ZoneR#calculerUpLeft(Position, int, int)
	 * @see ZoneR#setDownRight(Position)
	 * @see ZoneR#calculerDownRight(Position, int, int)
	 * @see Carte#largAffC
	 * @see Carte#hautAffC
	 * @see ZoneR#getHauteur()
	 * @see ZoneR#getLargeur()
	 * @see Position#getX()
	 * @see Position#getY()
	 * @see Position#Position(int, int)
	 * @see Carte#origine
	 * @see Position#toPoint(int)
	 * @see Carte#rayonHex
	 * @see Carte#calculerHex()
	 */
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
	/**
	 * Actions du héros
	 * 
	 * @param p
	 * 			Point où le curseur de la souris est situé
	 * 
	 * @see Element
	 * @see Carte#selection
	 * @see Heros
	 * @see Soldat#getAJoue()
	 * @see Position
	 * @see Point#toPositionAxiale(int, Point)
	 * @see PositionAxiale#toPosition()
	 * @see Position#add(Position)
	 * @see Carte#MAPAFF
	 * @see ZoneR#getUpLeft()
	 * @see Position#getY()
	 * @see Carte#getElement(Position)
	 * @see Monstre
	 * @see Carte#faireAttaquerHeros(Element)
	 * @see Carte#deplacerHeros(Element)
	 */
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
	/**
	 * Déplace l'élément sélectionné si c'est un héros
	 * 
	 * @param cible
	 * 			Elément de la carte que le héros (selection) doit remplacer
	 * 
	 * @see Element
	 * @see Carte#selection
	 * @see Heros
	 * @see Soldat#zoneDeplacementContient(Element)
	 * @see Carte#chemin
	 * @see CheminDijkstra
	 * @see Soldat#getZoneDeplacement()
	 * @see DeplacementSoldat
	 * @see CheminDijkstra#getChemin()
	 * @see Carte#listeThreads
	 */
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
	/**
	 * Fait attaquer le héros
	 * 
	 * @param cible
	 * 			Elément que le soldat doit attaquer
	 * 
	 * @see Element
	 * @see Carte#selection
	 * @see Carte#typeAttaque
	 * @see IConfig#CORPS_CORPS
	 * @see Soldat
	 * @see Soldat#zoneDeplacementContient(Element)
	 * @see Carte#chemin
	 * @see AttaqueSoldatCorps
	 * @see Carte#listeThreads
	 * @see IConfig#DISTANCE
	 * @see Soldat#verifieAttaqueDistance(Soldat)
	 * @see AttaqueSoldatDistance
	 */
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
	
	/**
	 * Mets fin au tour du joueur
	 * 
	 * @param side
	 * 			Côté de celui dont le tour doit se terminer
	 * 
	 * @see IConfig#GENTILS
	 * @see Carte#selection
	 * @see Carte#curseur
	 * @see Carte#chemin
	 * @see Carte#panPartie
	 * @see PanneauBoutonsTour
	 * @see PanneauActionsHeros
	 * @see Carte#appliquerGuerisons(List)
	 * @see Carte#reinitiAJoue(List)
	 * @see Carte#reinitPorteeDep()
	 * @see Carte#infoPartie
	 * @see TourOrdi
	 * @see Carte#listeThreads
	 * @see IConfig#MECHANT
	 */
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
	
	/**
	 * Dessine la partie de la carte affichée
	 * 
	 * @param g
	 * 			Graphics2D
	 * 
	 * @see Carte#MAPAFF
	 * @see Carte#selection
	 * @see Soldat
	 * @see Carte#chemin
	 * @see Carte#Curseur
	 * @see Element
	 */
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
	/**
	 * Dessine la carte reelle sous forme de mini-map
	 * 
	 * @param g
	 * 			Graphics2D
	 * 
	 * @see Element
	 * @see Carte#GRILLE
	 * @see Carte#selection
	 * @see Carte#curseur
	 * @see Carte#MAPAFF
	 * @see Position
	 * @see PositionAxiale
	 * @see Point
	 * @see Carte#rayonMM
	 * @see Carte#origineMM
	 */
	public void seDessinerMM(Graphics2D g) {
		for (Element[] liste : GRILLE)
			for (Element e : liste)
				e.seDessinerMM(g);
		if (selection != null)
			selection.seDessinerCadreMM(g, COULEUR_SELECTION);
		if (curseur != null) curseur.seDessinerCadreMM(g, COULEUR_CURSEUR);
		// Dessin d'un rectangle représentant la zone affichée
		Point ul = MAPAFF.getUpLeft().toPositionAxiale().toPoint(rayonMM, origineMM).substract(new Point(rayonMM, rayonMM)),
			  dr = MAPAFF.getDownRight().toPositionAxiale().toPoint(rayonMM, origineMM);
		g.setColor(Color.yellow);
		g.drawRect((int)ul.getX(), (int)ul.getY(), (int)(dr.getX() - ul.getX()), (int)(dr.getY() - ul.getY()));
	}
	
	/**
	 *  Auxiliaire à trouvePosType()
	 *  
	 * @param elem
	 * 		Un élément de la carte
	 * @param type
	 * 		Un caractère ('s' : sol, 'h' : héros)
	 * @return true si l'élément est du type donné, faux sinon
	 * 
	 * @see Element
	 * @see Sol
	 * @see Heros
	 */
	public boolean typeof(Element elem, char type) {
		switch (type) {
			case 's': return elem instanceof Sol;
			case 'h': return elem instanceof Heros;
			default : return false;
		}
	}
}
