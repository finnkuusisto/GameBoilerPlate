package graphics;

import java.awt.image.BufferedImage;

public class SpriteData {
	
	public final BufferedImage[] IMAGES;
	public final int[] DURATIONS;
	
	public SpriteData(BufferedImage[] images, int[] durations) {
		this.IMAGES = images;
		this.DURATIONS = durations;
	}
	
}
