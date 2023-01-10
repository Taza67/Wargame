package wargame;

import java.util.List;

import wargameInterface.Fenetre;


/**<b>Apatient est l'interface qui gère les processus .</b>
* <p>
* Elle est caractérisée par :
* <ul>
* <li>Une liste de processus.</li>
* </ul>
* </p>
* @see AttaqueSoldatCorps
* @author AKIL M., BAYAZID H., AMIROUCHE A.
*/


public abstract class APatient extends Thread {
	/**
	 * La liste des processus
	 * @see DeplacementSoldat
	 */
	protected List<Thread> processusAttendre;
	
	
	/**
	 * Attend la fin des threads
	 * 
	 * @see TourOrdi
	 */
	public void attendre() {
		while(MethodesAuxiliaires.threadVivant(processusAttendre));
	}
}
