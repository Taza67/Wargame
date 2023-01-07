package wargame;

public class Monstre extends Soldat {
	// Infos
	private final TypesM TYPE;
	
	// Constructeur
	public Monstre(Carte carte, TypesM type, Position pos) {
		super(carte, pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
		TYPE = type;
		numTexture = TEX_MONSTRE;
	}
	
	// Accesseurs
	public TypesM getTYPE() { return TYPE; }
	
	// Méthodes
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
}
