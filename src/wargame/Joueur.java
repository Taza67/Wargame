package wargame;

public class Joueur implements IConfig{
	// Infos
	private String pseudo;
	private final char CAMP;
	private Armee sonArmee = new Armee(NB_SOLDATS_CAMP + NB_AMIRAUX_CAMP);
	
	// Constructeurs
	public Joueur(String pseudo, char camp) {
		this.pseudo = pseudo;
		CAMP = camp;
	}
}
