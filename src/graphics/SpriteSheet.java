package graphics;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SpriteSheet {

	private Map<String,SpriteData> sprites;
	
	private SpriteSheet() {
		this.sprites = new HashMap<String,SpriteData>();
	}
	
	public SpriteData getSpriteData(String name) {
		return this.sprites.get(name);
	}
	
	public Sprite getSprite(String name) {
		SpriteData data = this.sprites.get(name);
		if (data == null) {
			return null;
		}
		return new Sprite(data);
	}
	
	////////////////
	//loading code//
	////////////////
	
	public static SpriteSheet loadSpriteSheet(String name) {
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		InputStream stream = Sprite.class.getResourceAsStream(name);
		Scanner in = new Scanner(stream);
		//first line is image filename
		String sheetname = in.nextLine();
		//lines until #-line are sprite definitions
		List<String> spriteDefs = new ArrayList<String>();
		String line = in.nextLine();
		while (!line.startsWith("#")) {
			spriteDefs.add(line);
			line = in.nextLine();
		}
		//remaining lines are images in sheet
		BufferedImage sheetImage = ImageUtils.loadImage(sheetname);
		Map<String,BufferedImage> images = new HashMap<String,BufferedImage>();
		while (in.hasNextLine()) {
			line = in.nextLine();
			String[] parts = line.split(",");
			String imageName = parts[0];
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int w = Integer.parseInt(parts[3]);
			int h = Integer.parseInt(parts[4]);
			BufferedImage image = ImageUtils.getSubImage(sheetImage, x, y,
					w, h);
			images.put(imageName, image);
		}
		
		//now that we have the map of images, build the sprites
		SpriteSheet ret = new SpriteSheet();
		for (int i = 0; i < spriteDefs.size(); i++) {
			String[] def = spriteDefs.get(i).split("#");
			String spriteName = def[0];
			SpriteData sprite =
					new SpriteData(SpriteSheet.getImages(def[1], images),
							SpriteSheet.getDurations(def[2]));
			ret.sprites.put(spriteName, sprite);
		}
		return ret;
	}
	
	private static BufferedImage[] getImages(String list,
			Map<String,BufferedImage> images) {
		String[] names = list.split(",");
		BufferedImage[] ret = new BufferedImage[names.length];
		for (int i = 0; i < names.length; i++) {
			ret[i] = images.get(names[i]);
		}
		return ret;
	}
	
	private static int[] getDurations(String list) {
		String[] durStrs = list.split(",");
		int[] ret = new int[durStrs.length];
		for (int i = 0; i < durStrs.length; i++) {
			ret[i] = Integer.parseInt(durStrs[i]);
		}
		return ret;
	}
	
}
