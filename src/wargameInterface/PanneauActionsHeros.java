package wargameInterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauActionsHeros extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	protected JButton frontale;
	protected JButton distance;
	public final Carte CARTE;

	/**
	 * Constructeur du Panneau qui gère l'action des héros
	 * @param carte
	 */
	public PanneauActionsHeros(Carte carte) {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		int largB = carte.getLargMM() / 2 - 4,
			hautB = 30,
			haut = (hautB + 5) * 1;
		
		CARTE = carte;
		
		frontale = new JButton("Attaque frontale");
		distance = new JButton("Attaque à distance");
		
		frontale.setPreferredSize(new Dimension(largB, hautB));
		distance.setPreferredSize(new Dimension(largB, hautB));
		
		add(frontale);
		add(distance);
		
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM() + 10, haut));
		
		frontale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CARTE.setTypeAttaque(CORPS_CORPS);
				carte.getPanPartie().tableauBord.infoPartie.repaint();
			}
		});
		distance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CARTE.setTypeAttaque(DISTANCE);
				carte.getPanPartie().tableauBord.infoPartie.repaint();
			}
		});
	}
}
