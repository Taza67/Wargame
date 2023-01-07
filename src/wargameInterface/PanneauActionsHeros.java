package wargameInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	protected JButton campement;
	protected JButton distance;
	public final Carte CARTE;

	public PanneauActionsHeros(Carte carte) {
		super();
		CARTE = carte;
		frontale = new JButton("Attaque frontale");
		campement = new JButton("Camper");
		distance = new JButton("Attaque à distance");
		frontale.setPreferredSize(new Dimension(carte.getLargMM() / 3 - 3, 20));
		campement.setPreferredSize(new Dimension(carte.getLargMM() / 3 - 3, 20));
		distance.setPreferredSize(new Dimension(carte.getLargMM() / 3 - 3, 20));
		add(frontale, BorderLayout.WEST);
		add(campement, BorderLayout.CENTER);
		add(distance, BorderLayout.EAST);
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM() + 10, 30));
		
		frontale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CARTE.setTypeAttaque(CORPS_CORPS);
			}
		});
		distance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CARTE.setTypeAttaque(DISTANCE);
			}
		});
	}
}
