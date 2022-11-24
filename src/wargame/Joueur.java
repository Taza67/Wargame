package wargame;

public class Joueur {
	// Infos
	private String pseudo;
	private final char CAMP = 'M'; // méchant pour l'instant pour pas avoir d'erreur
	private Armee arm;
	
	// Getters
	public String getPseudo() {return pseudo;}
	public Armee getArm() {return arm;}
	public char getCAMP() {return CAMP;}
	
	// Setters
	public void setPseudo(String pseudo) {this.pseudo = pseudo;}
	public void setArm(Armee arm) {this.arm = arm;}
	
	
	
	
}
