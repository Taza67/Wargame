package wargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import wargame.Obstacle.TypeObstacle;
import wargame.Sol.TypeSol;
import wargameInterface.PanneauPartie;

public class Carte implements IConfig , java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Infos
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
	
	private static Element[][] grille; 								// Grille du jeu
	private static ZoneR mapAff;									// Carte affichée
	private static Position centreAff;								// Centre de la carte affichée
	// Interactions
	private Element curseur, selection;
	private CheminDijkstra chemin;
	private int typeAttaque = CORPS_CORPS;							// Type de l'attaque du soldat (DISTANCE, CORPS_CORPS)
	// Infos sur la partie
	private InfoPartie infoPartie;
	// Liste des entités
	List<Element> listeMonstres, listeHeros;
	// Textures
	transient TexturePaint[] texturesPaint;
	// Processus
	List<Thread> listeThreads;
	
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
		origine = new Point(0, 0);
		origineMM = new Point(rayonMM + 5, rayonMM + 5);
		// Génération des éléments
		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++)
				grille[i][j] = new Sol(this, TypeSol.PLAINE ,new Position(j, i));
		nbHeros = nbMonstres = 6;
		genereObstacles();
		genereSol();
		genereHeros(nbHeros);
		genereMonstres(nbMonstres);
		// Éléments sélectionnés au départ
		selection = trouveHeros();
		// Infos sur la partie
		infoPartie = new InfoPartie(this, nbHeros, nbMonstres);
		// Textures
		chargerTextures(panPartie);
		// Threads
		listeThreads = new ArrayList<Thread>();
	}
	
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
	public Element[][] getGrille() { return grille; }
	public ZoneR getMapAff() { return mapAff; }
	public Position getCentreAff() { return centreAff; }
	public InfoPartie getInfoPartie() { return infoPartie; }
	public List<Element> getListeMonstres() { return listeMonstres; }
	public List<Element> getListeHeros() { return listeHeros; }
	public Element getCurseur() { return curseur; }
	public Element getSelection() { return selection; }
	//// Pseudo-accesseurs
	public Element getElement(Position pos) {
		return (pos.estValide(largC, hautC)) ? grille[pos.getY()][pos.getX()] : null;
	}
	
	// Mutateurs
	public void setCurseur(Element curseur) { this.curseur = curseur; }
	public void setSelection(Element selection) { this.selection = selection; }
	public void setTypeAttaque(int typeAttaque) { this.typeAttaque = typeAttaque; }
	//// Pseudo-mutateurs
	public void setElement(Position pos, Element elem) {
		if (pos.estValide(largC, hautC)) grille[pos.getY()][pos.getX()] = elem;
	}
	
	// Méthodes
	// Charge les textures
	public void chargerTextures(PanneauPartie p) {
		String dir = System.getProperty("user.dir");
		String[] sources = new String[11];
		sources[TEX_PLAINE] = dir + "/plaine.jpg";
		sources[TEX_MONTAGNE] = dir + "/montagne.jpg";
		sources[TEX_COLLINE] = dir + "/colline.jpg";
		sources[TEX_VILLAGE] = dir + "/village.jpg";
		sources[TEX_DESERT] = dir + "/desert.jpeg";
		sources[TEX_FORET] = dir + "/foret.jpg";
		sources[TEX_ROCHER] = dir + "/rocher.jpg";
		sources[TEX_EAU] = dir + "/eau.jpg";
		sources[TEX_NUAGE] = dir + "/nuage.jpg";
		sources[TEX_HEROS] = dir + "/heros.png";
		sources[TEX_MONSTRE] = dir + "/monstre.png";
		
		BufferedImage[] bufferedImages = MethodesTextures.getBufferedImages(sources, p);
		texturesPaint = MethodesTextures.getTexturesPaint(bufferedImages);
	}
	// Recalcules les dimensions de la carte affichées
	public static void recalculerMapAff() {
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
			this.setElement(posVide, h);
			listeHeros.add(h);
		}
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
				if (voisin.estValide(LARGEUR_MAP, HAUTEUR_MAP) && this.getElement(voisin) instanceof Sol) {
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
	public static void calculerHex() {
		for (Element[] liste : grille)
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
		Position p = c.toPositionAxiale(rayonHex, origine).toPosition().add(mapAff.getUpLeft());
		if (mapAff.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
			p = p.add(new Position(1, 0));
		curseur = getElement(p);
		if (selection instanceof Heros && curseur != null && ((Heros)selection).getZoneDeplacement().indexOf(curseur) != -1)
			chemin = new CheminDijkstra(selection, curseur, ((Heros)selection).getZoneDeplacement());
		else chemin = null;
	}
	// Déplace la sélection
	public void deplacerSelection(Point s) {
		Position p = s.toPositionAxiale(rayonHex, origine).toPosition().add(mapAff.getUpLeft());
		if (mapAff.getUpLeft().getY() % 2 != 0 && p.getY() % 2 == 0)
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
	// Actions du héros
	public void faireAgirHeros(Point p) {
		Element cible;
		if (selection instanceof Heros && !((Soldat)selection).getAJoue()) {
			Position posCible = p.toPositionAxiale(rayonHex, origine).toPosition().add(mapAff.getUpLeft());
			if (mapAff.getUpLeft().getY() % 2 != 0 && posCible.getY() % 2 == 0)
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
				DeplacementSoldat ds = new DeplacementSoldat(this, (Soldat)selection, ch.getChemin());
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
					AttaqueSoldatCorps as = new AttaqueSoldatCorps(this, (Soldat)selection, (Soldat)cible);
					selection = null;
					listeThreads.add(as);
					as.start();
				}
			} else if (typeAttaque == DISTANCE) {
				if (((Soldat)selection).verifieAttaqueDistance((Soldat)cible)) {
					chemin = null;
					AttaqueSoldatDistance as = new AttaqueSoldatDistance(this, (Soldat)selection, (Soldat)cible);
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
			TourOrdi to = new TourOrdi(this, listeThreads);
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
		mapAff.seDessiner(g);
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
		for (Element[] liste : grille)
			for (Element e : liste)
				e.seDessinerMM(g);
		if (selection != null)
			// if (selection instanceof Heros) ((Soldat)selection).dessinerZoneDeplacementMM(g);
			selection.seDessinerCadreMM(g, COULEUR_SELECTION);
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
