package wargame;

/**
 * Classe monstre extension de la classe Soldat
 * <p>
 * Est caractérisé par :
 * <ul>
 * <li>Un type de monstre </li>
 * </ul>
 * </p>
 *
 */

public class Monstre extends Soldat {
    private static final long serialVersionUID = -4592881709041950606L;
    /**
     * Type du Monstre
     */
    private final TypesM TYPE;
    
    /**
     * Constructeur de Monstre
     * @param carte
     * @param type
     * @param pos
     */
    public Monstre(Carte carte, TypesM type, Position pos) {
        super(carte, pos, type.getPoints(), type.getPorteeVisuelle(), type.getPorteeDeplacement(), type.getPuissance(), type.getTir());
        TYPE = type;
        numTexture = TEX_MONSTRE;
    }
    
    /**
     * Accesseur du type
     * @return TypesM
     */
    public TypesM getTYPE() { return TYPE; }
    
    /**
     * Retourne le type du personnage sous forme de chaine de caractère
     */
    public String getStringType() {
        return ("" + TYPE).toLowerCase();
    }
}