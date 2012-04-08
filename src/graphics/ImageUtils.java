package graphics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static BufferedImage loadImage(String name) {
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		InputStream in = ImageUtils.class.getResourceAsStream(name);
		BufferedImage ret = null;
		try {
			BufferedImage tmp = ImageIO.read(in);
			ret = ImageUtils.toCompatibleImage(tmp);
		} catch (IOException e) {
			System.err.println("Couldn't load " + name + "!");
			e.printStackTrace();
		}
		return ret;
	}
	
	public static BufferedImage getSubImage(BufferedImage image, int x, int y,
			int width, int height) {
		BufferedImage newImage = ImageUtils.newCompatibleImage(width, height,
				image.getTransparency());
		Graphics g = newImage.getGraphics();
		g.drawImage(image, 0, 0, width - 1, height - 1, 
				x, y, x + width - 1, y + height - 1, null);
		g.dispose();
		newImage.setAccelerationPriority(1.0F); //hint to put it in VRAM
		return newImage;
	}
	
	public static BufferedImage newCompatibleImage(int width, int height,
			int transparency) {
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(width, height,
				transparency);
		return image;
	}
	
	private static BufferedImage toCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		//check if it's already compatible
		if (image.getColorModel().equals(gc.getColorModel())) {
			image.setAccelerationPriority(1.0F); //hint to put it in VRAM
			return image;
		}
		//create a compatible image
		BufferedImage newImage = gc.createCompatibleImage(image.getWidth(),
				image.getHeight(), image.getTransparency());
		//get the graphics to draw the old image on
		Graphics g = newImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		newImage.setAccelerationPriority(1.0F); //hint to put it in VRAM
		return newImage;
	}
	
}
