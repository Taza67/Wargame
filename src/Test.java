import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import wargame.IConfig;
import wargameInterface.PanneauPartie;

public class Test {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test du jeu");
		PanneauPartie panel = new PanneauPartie();
		
		frame.setContentPane(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(IConfig.POSITION_X, IConfig.POSITION_Y);
		frame.pack();
		frame.setVisible(true);
	}

}
