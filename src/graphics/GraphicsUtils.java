package graphics;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GraphicsUtils {
	
	private GraphicsUtils() {}
	
	public static void drawSprite(Graphics g, Sprite sprite, int dx, int dy) {
		SpriteFrame frame = sprite.getFrame();
		int w = frame.WIDTH;
		int h = frame.HEIGHT;
		GraphicsUtils.drawSprite(g, sprite, dx, dy, dx + w, dy + h);
	}
	
	public static void drawSprite(Graphics g, Sprite sprite, int dx1, int dy1,
			int dx2, int dy2) {
		SpriteFrame frame = sprite.getFrame();
		int x = frame.X;
		int y = frame.Y;
		int w = frame.WIDTH;
		int h = frame.HEIGHT;
		g.drawImage(sprite.getSheet(),
					dx1, dy1, dx2, dy2,
					x, y, x + w, y + h,
					null);
	}
	
	public static BufferedImage loadImage(String name) {
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		InputStream in = GraphicsUtils.class.getResourceAsStream(name);
		BufferedImage ret = null;
		try {
			BufferedImage tmp = ImageIO.read(in);
			ret = GraphicsUtils.toCompatibleImage(tmp);
		} catch (IOException e) {
			System.err.println("Couldn't load " + name + "!");
			e.printStackTrace();
		}
		return ret;
	}
	
	public static BufferedImage getSubImage(BufferedImage image, int x, int y,
			int width, int height) {
		BufferedImage newImage = GraphicsUtils.newCompatibleImage(width, height,
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
