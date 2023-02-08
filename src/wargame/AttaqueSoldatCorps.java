package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;



/**
 * <b>AttaqueSoldatCorps est responsable des attaques de corps.</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>La carte qui contient le soldat.</li>
 * <li>Le soldat attaquant.</li>
 * <li>Le soldat attaqué.</li>
 * <li>La liste des chemins du soldat.</li>
 * </ul>
 * @see Carte
 * @see Soldat
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */


public class AttaqueSoldatCorps extends APatient implements IConfig {
	
	/**
	 * La carte du soldat.
	 * @see Element#carte
	 */
	Carte carte;
	
	/**
	 * Le soldat attaquant.
	 * @see Soldat#attaqueSoldatCorps(Soldat)
	 */
	Soldat soldat;
	
	/**
	 * Le soldat attaqué.
	 * @see Soldat#attaqueSoldatCorps(Soldat)
	 */
	Soldat cible;
	
	/**
	 * Liste des chemins du soldat attaquant.
	 * @see Carte#mort(Soldat)
	 */
	List<Element> chemin;
	
	
	 /**
     * Constructeur AttaqueSoldatCorps.
     * <p>
     * A la construction, les chemins sont construits et ajoutés à la liste des chemins.
     * Les éléments interactifs sont générés.
     * </p>
     * 
     * @param carte
     * 				Le la carte qui contient le soldat.
     * @param soldat
     *            	Le soldat attaquant.
     * @param cible
     * 			  	Le soldat ciblé.
     * @param processusAttendre
     * 				Liste des processus
     * 				
     * @see Carte#faireAttaquerHeros(Element)
     * @see TourOrdi#reflechirStrategie(Soldat)
     */
	public AttaqueSoldatCorps(Carte carte, Soldat soldat, Soldat cible, List<Thread> processusAttendre) {
		super();
		this.processusAttendre = processusAttendre;
		this.carte = carte;
		this.soldat = soldat;
		this.cible = cible;
		this.chemin = new CheminDijkstra(soldat, cible, soldat.getZoneDeplacement()).getChemin();
		this.chemin.remove(chemin.size() - 1);
	}
	
	 /**
     * Fais attaquer le soldat
     * 
     * @see AttaqueSoldatCorps#faireAttaquerSoldat()
     */
	public void run() {
		faireAttaquerSoldat();
	}
	
	 /**
     * Déplace le soldat case par case
     * 
     * @see AttaqueSoldatCorps#faireAttaquerSoldat()
     * @see AttaqueSoldatDistance#AttaqueSoldatDistance(Carte, Soldat, Soldat, List)
     */
	public void faireAttaquerSoldat() {
		DeplacementSoldat ds = new DeplacementSoldat(carte, soldat, chemin, processusAttendre);
		ds.run();
		while (ds.isAlive());
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e1) {}
		soldat.attaqueSoldatCorps(cible);
		soldat.setAJoue(true);
		carte.getPanPartie().repaint();
	}
}