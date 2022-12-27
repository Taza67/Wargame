package wargameInterface;

import java.awt.Dimension;
import javax.swing.JPanel;

public abstract class Panneau extends JPanel {
	private static final long serialVersionUID = 1L;

	// Change les dimensions du panneau
	public void setDim(int largeur, int hauteur) {
		this.setPreferredSize(new Dimension(largeur, hauteur));
	}
}
