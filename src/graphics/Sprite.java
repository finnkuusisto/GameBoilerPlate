package graphics;
import java.awt.image.BufferedImage;

public class Sprite {

	private final SpriteData DATA;

	private int position;
	private int countAtPosition;
	
	public Sprite(SpriteData data) {
		this.DATA = data;
		this.position = 0;
		this.countAtPosition = 0;
	}
	
	public int numFrames() {
		return this.DATA.FRAMES.length;
	}
	
	public void update() {
		this.countAtPosition++;
		//check for image change
		if (this.countAtPosition >= this.DATA.DURATIONS[position]) {
			this.position = (this.position + 1) % this.DATA.FRAMES.length;
			this.countAtPosition = 0;
		}
	}
	
	public void reset() {
		this.position = 0;
		this.countAtPosition = 0;
	}
	
	public BufferedImage getSheet() {
		return this.DATA.SHEET;
	}
	
	public SpriteFrame getFrame() {
		return this.DATA.FRAMES[this.position];
	}
	
}
