package wargame;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

public class MethodesTextures {
	// Renvoie des textures d'images bufferisées
	public static TexturePaint[] getTexturesPaint(BufferedImage[] bufferedImages) {
		TexturePaint[] texturesPaint = new TexturePaint[bufferedImages.length];
		for (int i = 0; i < bufferedImages.length; i++)
			texturesPaint[i] = new TexturePaint(bufferedImages[i], new Rectangle(0, 0, 50, 50));
		return texturesPaint;
	}
	
	// Renvoie des images bufferisées
	public static BufferedImage[] getBufferedImages(String[] sources, Component c) {
		BufferedImage[] bufferedImages = new BufferedImage[sources.length];
		Image[] images = new Image[sources.length];
		for (int i = 0; i < sources.length; i++)
			images[i] = c.getToolkit().getImage(sources[i]);
	    waitForImages(images, c);
	    for (int i = 0; i < sources.length; i++) {
	    	bufferedImages[i] = new BufferedImage(images[i].getWidth(c), images[i].getHeight(c), BufferedImage.TYPE_INT_RGB);
	    	Graphics2D g2d = bufferedImages[i].createGraphics();
	    	g2d.drawImage(images[i], 0, 0, c);
	    }
	    return bufferedImages;
    }

	// Attend le chargement complet d'une image
	public static boolean waitForImage(Image image, Component c) {
	    MediaTracker tracker = new MediaTracker(c);
	    tracker.addImage(image, 0);
	    try {
	    	tracker.waitForAll();
	    } catch(InterruptedException ie) {}
	    return !tracker.isErrorAny();
	}

	// Attend le chargement complet de plusieurs images
	public static boolean waitForImages(Image[] images, Component c) {
		MediaTracker tracker = new MediaTracker(c);
		for(int i=0; i<images.length; i++)
			tracker.addImage(images[i], 0);
		try {
			tracker.waitForAll();
		} catch(InterruptedException ie) {}
		return(!tracker.isErrorAny());
	}
}
