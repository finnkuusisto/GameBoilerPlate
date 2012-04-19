package graphics;

import java.awt.image.BufferedImage;

public class SpriteData {
	
	public final BufferedImage SHEET;
	public final SpriteFrame[] FRAMES;
	public final int[] DURATIONS;
	
	public SpriteData(BufferedImage sheet, SpriteFrame[] frames,
			int[] durations) {
		this.SHEET = sheet;
		this.FRAMES = frames;
		this.DURATIONS = durations;
	}
	
}
