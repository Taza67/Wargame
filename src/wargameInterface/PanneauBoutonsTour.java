package wargameInterface;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauBoutonsTour extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected final Carte CARTE;
	protected JButton finTour;
	protected JButton nextHeros;
	
	// Constructeurs
	public PanneauBoutonsTour(Carte carte) {
		super();
		int largB = carte.getLargMM() / 2 - 4,
				hautB = 30,
				haut = (hautB + 5) * 1;
		this.CARTE = carte;
		
		finTour = new JButton("Fin du tour");
		nextHeros = new JButton("Trouver héros");
		
		finTour.setPreferredSize(new Dimension(largB, hautB));
		nextHeros.setPreferredSize(new Dimension(largB, hautB));
		
		this.add(finTour);
		this.add(nextHeros);
		
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM(), haut));
		
		finTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carte.finirTour(GENTILS);
			}
		});;
	}

	// Accesseurs
	public JButton getFinTour() { return finTour; }
	
	
}
