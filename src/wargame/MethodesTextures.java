package wargame;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import wargameInterface.PanneauPartie;





/**<b>Classe répertoriant les méthodes propres aux textures</b>
*/

public class MethodesTextures implements IConfig {
	/**
	 * Renvoie les textures d'image bufferisées 
	 * @param bufferedImages
	 * @param rayon
	 * @return TexturePaint[]
	 */
	public static TexturePaint[] getTexturesPaint(BufferedImage[] bufferedImages, int rayon) {
		int horiz, vert;
		horiz = (int)(Math.sqrt(3.) * rayon);
		vert = (int)(3 / 2. * rayon);
		TexturePaint[] texturesPaint = new TexturePaint[bufferedImages.length];
		for (int i = 0; i < bufferedImages.length; i++)
			texturesPaint[i] = new TexturePaint(bufferedImages[i], new Rectangle(0, 0, horiz, vert));
		return texturesPaint;
	}
	
	/**
	 * Charge les images
	 * @param sources
	 * @param c
	 * @return Image[]
	 */
	public static Image[] getImages(String[] sources, Component c) {
		Image[] images = new Image[sources.length];
		for (int i = 0; i < sources.length; i++)
			images[i] = c.getToolkit().getImage(sources[i]);
	    waitForImages(images, c);
	    return images;
	}
	
	/**
	 * Renvoie des images bufferisées
	 * @param images
	 * @param c
	 * @return BufferedImage[]
	 */
	public static BufferedImage[] getBufferedImages(Image[] images, Component c) {
		BufferedImage[] bufferedImages = new BufferedImage[images.length];
	    for (int i = 0; i < images.length; i++) {
	    	bufferedImages[i] = new BufferedImage(images[i].getWidth(c), images[i].getHeight(c), BufferedImage.TYPE_INT_RGB);
	    	Graphics2D g2d = bufferedImages[i].createGraphics();
	    	g2d.drawImage(images[i], 0, 0, c);
	    }
	    return bufferedImages;
	}

	/**
	 * Attend le chargement complet de plusieurs images 
	 * @param images
	 * @param c
	 * @return boolean
	 */
	public static boolean waitForImages(Image[] images, Component c) {
		MediaTracker tracker = new MediaTracker(c);
		for(int i=0; i<images.length; i++)
			tracker.addImage(images[i], 0);
		try {
			tracker.waitForAll();
		} catch(InterruptedException ie) {}
		return(!tracker.isErrorAny());
	}
	/**
	 * Charge les textures
	 * @param p
	 * @param rayon
	 * @return TexturePaint[]
	 */
	public static TexturePaint[] chargerTextures(PanneauPartie p, int rayon) {
		String dir = System.getProperty("user.dir");
		String[] sources = new String[11];
		sources[TEX_PLAINE] = dir + "/plaine.jpg";
		sources[TEX_MONTAGNE] = dir + "/montagne.jpg";
		sources[TEX_COLLINE] = dir + "/colline.jpg";
		sources[TEX_VILLAGE] = dir + "/village.jpg";
		sources[TEX_DESERT] = dir + "/desert.jpeg";
		sources[TEX_FORET] = dir + "/foret.jpg";
		sources[TEX_ROCHER] = dir + "/rocher.jpg";
		sources[TEX_EAU] = dir + "/eau.jpg";
		sources[TEX_NUAGE] = dir + "/nuage.jpg";
		sources[TEX_HEROS] = dir + "/heros.png";
		sources[TEX_MONSTRE] = dir + "/monstre.png";
		
		Image[] images = MethodesTextures.getImages(sources, p);
		BufferedImage[] bufferedImages = MethodesTextures.getBufferedImages(images, p);
		return MethodesTextures.getTexturesPaint(bufferedImages, rayon);
	}
}
