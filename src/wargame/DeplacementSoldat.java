package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <b>DeplacementSoldat est la classe qui gère les déplacements des troupes </b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une carte</li>
 * <li>Un soldat</li>
 * <li>Une liste de processus</li>
 * </ul>
 * @see Carte
 * @see Soldat
 * @see CheminDijkstra
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */

public class DeplacementSoldat extends APatient implements IConfig {

	
	/**
	 * La carte qui contient le soldat.
	 * @see Carte#deplacerHeros(Element)
	 */	
	Carte carte;
	

	/**
	 * Le soldat à déplacer.
	 * @see Heros#calculerZoneDeplacement()
	 */	
	Soldat soldat;
	

	/**
	 * La Liste contenant les cases pour le déplacment
	 * @see Carte#deplacerHeros(Element)
	 */	
	private List<Element> chemin;
	
	/**
	 * Crée l'hexagone de la map affichée.
	 * 
	 * @see Carte#calculerHex()
	 * @see Soldat#seDeplace(Position)
	 */
	public DeplacementSoldat(Carte carte, Soldat soldat, List<Element> chemin, List<Thread> processusAttendre) {
		super();
		this.processusAttendre = processusAttendre;
		this.carte = carte;
		this.soldat = soldat;
		this.chemin = chemin;
	}
	

	/**
	 * Attente de la fin des processus puis déplacement des soldats 
	 * 
	 */	
	public void run() {
		attendre();
		deplacerSoldat();
	}
	

	/**
	 * Déplace le soldat case par case.
	 * @see run
	 */	
	public void deplacerSoldat() {
		soldat.getSol().enleverEffets(soldat);
		for (Element e : chemin) {
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {}
			soldat.seDeplace(e.getPos());
			soldat.setPorteeDeplacement(soldat.getPorteeDeplacement() - 1);
			carte.recalculerZonesDep();
			carte.recalculerZonesVisuelles();
			carte.getPanPartie().repaint();
		}
		soldat.getSol().enleverEffetPorteeVisuelle(soldat);
		soldat.getSol().appliquerEffets(soldat);
		soldat.majZoneDeplacement();
		soldat.majZoneVisuelle();
		if (soldat.getPorteeDeplacement() <= 0)
			soldat.setAJoue(true);
		carte.getPanPartie().repaint();
	}
}
