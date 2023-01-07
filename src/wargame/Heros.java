package wargame;

public class Heros extends Soldat {
	// Infos
	private final String NOM;
	private final TypesH TYPE;
	
	// Constructeur
	public Heros(Carte carte, TypesH type, String nom, Position pos) {
		super(carte, pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
		NOM = nom; TYPE = type;
		visible = true;
		numTexture = 5;
		this.getZoneVisuelle().rendreVisible();
	}
	
	// Accesseurs
	public String getNOM() { return NOM; }
	public TypesH getTYPE() { return TYPE; }
	
	// Méthodes
	// Renvoie les infos du héros
	public String toString() {
		String desc = super.toString();
		desc += " | [ Type : " + TYPE + " ]";
		return desc;
	}
	// Déplace le soldat à la position pos
	public boolean seDeplace(Position cible) {
		boolean possible = super.seDeplace(cible);
		if (possible) {
			carte.getElement(pos).visible = true;
			this.getZoneVisuelle().rendreVisible();
		}
		return possible;
	}
	// Renvoie le type de l'élément sous forme de chaine de caractère
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
}
