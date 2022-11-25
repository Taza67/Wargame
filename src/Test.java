import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

import wargame.*;

public class Test {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test du jeu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panneau panel = new Panneau();
		panel.setOpaque(true);
		panel.setBackground(IConfig.COULEUR_INCONNU);
		panel.setPreferredSize(new Dimension(IConfig.LARGEUR_CARTE * IConfig.NB_PIX_CASE + 5, IConfig.HAUTEUR_CARTE * IConfig.NB_PIX_CASE + 5));
		
		frame.setContentPane(panel);
		frame.setLocation(IConfig.POSITION_X, IConfig.POSITION_Y);
		
		frame.pack();
		frame.setVisible(true);
	}

}
