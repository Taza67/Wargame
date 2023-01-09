package wargame;

import java.awt.Font;
import java.awt.Graphics2D;

public class Heros extends Soldat {
	private static final long serialVersionUID = -3558011063868665290L;
	// Infos
	private final String NOM;
	private final TypesH TYPE;
	
	// Constructeur
	public Heros(Carte carte, TypesH type, String nom, Position pos) {
		super(carte, pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
		NOM = nom; TYPE = type;
		visible = true;
		numTexture = TEX_HEROS;
		this.getZoneVisuelle().rendreVisible();
	}
	
	// Accesseurs
	public String getNOM() { return NOM; }
	public TypesH getTYPE() { return TYPE; }
	
	// Méthodes
	// Déplace le soldat à la position pos
	public boolean seDeplace(Position cible) {
		boolean possible = super.seDeplace(cible);
		if (possible) {
			carte.getElement(getPos()).visible = true;
			this.getZoneVisuelle().rendreVisible();
		}
		return possible;
	}
	// Renvoie le type de l'élément sous forme de chaine de caractère
	public String getStringType() {
		return ("" + TYPE).toLowerCase();
	}
	
	// Méthodes graphiques
	// Dessine le héros avec son nom
	public void seDessiner(Graphics2D g) {
		super.seDessiner(g);
		drawCenteredString(g, NOM, new Font("Courier", Font.BOLD, carte.getRayonHex()));
	}
	
}
