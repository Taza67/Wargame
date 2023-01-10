package wargame;

import java.util.List;
import java.util.concurrent.TimeUnit;

import wargameInterface.Fenetre;
import wargameInterface.PanneauPartie;


/**
 * <b>AttaqueSoldatDistance est responsable de la gestion des attaques à distance.</b>
 * <p>
 * Elle est caractérisée par :
 * <ul>
 * <li>Une carte sur laquelle se situe le soldat.</li>
 * <li>Le soldat attaquant .</li>
 * <li>Le soldat attaqué.</li>
 * </ul>
 * </p>
 * @see Element 
 * @see Carte 
 * @see Soldat
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */


public class AttaqueSoldatDistance extends APatient implements IConfig {

	/**
	 * La carte du soldat.
	 * @see Element#carte
	 */
	Carte carte;
	
	/**
	 * Le soldat attaquant.
	 * @see Soldat#attaqueSoldatDistance(Soldat)
	 */
	Soldat soldat;
	
	/**
	 * Le soldat attaqué.
	 * @see Soldat#attaqueSoldatDistance(Soldat)
	 */
	Soldat cible;
	

	/**
	 * Constructeur AttaqueSoldatDistance.
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
	 */
	public AttaqueSoldatDistance(Carte carte, Soldat soldat, Soldat cible, List<Thread> processusAttendre) {
		super();
		this.processusAttendre = processusAttendre;
		this.carte = carte;
		this.soldat = soldat;
		this.cible = cible;
	}
	
	/**
     * Fais attaquer le soldat
     * 
     * @see AttaqueSoldatDistance#faireAttaquerSoldat
     */
	public void run() {
		attendre();
		faireAttaquerSoldat();
	}
	

	/**
     * Déplace le soldat case par case
     * 
     * @see AttaqueSoldatDistance#AttaqueSoldatDistance(Carte, Soldat, Soldat, List)
     */
	public void faireAttaquerSoldat() {
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e1) {}
		soldat.attaqueSoldatDistance(cible);
		soldat.setAJoue(true);
		carte.getPanPartie().repaint();
	}
}
