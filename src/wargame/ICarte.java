package wargame;

public interface ICarte {
	// Pseudo-accesseur
	// Renvoie l'élément à la position donné
	Element getElement(Position pos);
	
	// Méthodes
	// Trouve aléatoirement une position vide sur la carte
	Position trouvePositionVide();
	// Trouve une position vide choisie aleatoirement parmi les 8 positions adjacentes de pos
	Position trouvePositionVide(Position pos);
	// Trouve aléatoirement un heros sur la carte
	Heros trouveHeros();
	// Trouve un heros choisi aléatoirement parmi les 8 positions adjacentes de pos
	Heros trouveHeros(Position pos);
	// Tue le soldat
	void mort(Soldat victime);
	// À voir
	// boolean actionHeros(Position pos, Position pos2);
	// À voir
	// void jouerSoldats(PanneauJeu pj);
	// À voir
	// void toutDessiner(Graphics g);
}
