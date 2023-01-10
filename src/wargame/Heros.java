package wargame;

import java.awt.Font;
import java.awt.Graphics2D;


/**
 * <b> Heros est la classe qui gère les personnages joueur, les positionne sur la map, les dessine, leur permet de se déplacer.</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Un nom</li>
 * <li>Un type</li>
 * <li>Une texture</li>
 * </ul>
 * @author AKIL M., BAYAZID H., AMIROUCHE A.
 *
 */



public class Heros extends Soldat {
	private static final long serialVersionUID = -3558011063868665290L;
	
	
	/**
	 * Nom du héros
	 **/	
	
	private final String NOM;
	 /**
	  * Type du Héros, elfe, nain, humain, hobbit
	  * 
	  */
	private final TypesH TYPE;
	
	/**
	 * Constructeur d'un héros
	 * @param carte
	 * @param type
	 * @param nom
	 * @param pos
	 */
	public Heros(Carte carte, TypesH type, String nom, Position pos) {
		super(carte, pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
		NOM = nom; TYPE = type;
		visible = true;
		numTexture = TEX_HEROS;
		this.getZoneVisuelle().rendreVisible();
	}
	
	/**
	 * Accesseur du nom 
	 * @return String
	 */
	public String getNOM() { return NOM; }
	/**
	 * Accesseur du type du héro
	 * @return TypesH
	 */
	public TypesH getTYPE() { return TYPE; }
	
	
	/**
	 * Renvoie vrai si le déplacement est possible 
	 */
	public boolean seDeplace(Position cible) {
		boolean possible = super.seDeplace(cible);
		if (possible) {
			carte.getElement(getPos()).visible = true;
			this.getZoneVisuelle().rendreVisible();
		}
		return possible;
	}
	
	
	/**
	 * Retourne le type du héros sous forme de chaine
	 * @return String
	 */
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
	
	// Méthodes graphiques
	// Dessine le héros avec son nom
	
	/**
	 * Dessine le héros
	 */
	public void seDessiner(Graphics2D g) {
		super.seDessiner(g);
		drawCenteredString(g, NOM, new Font("Courier", Font.BOLD, carte.getRayonHex()));
	}
	
}
