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
		this(new HashMap<String,SpriteData>());
	}
	
	private SpriteSheet(Map<String,SpriteData> sprites) {
		this.sprites = sprites;
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
	
	private static final String MARK_START = "[";
	private static final String NAME_MARK = "[NAME]";
	private static final String SPRITE_MARK = "[SPRITES]";
	private static final String FRAME_MARK = "[FRAMES]";
	private static final String LIST_SEP = ",";
	private static final String BLOCK_SEP = "/";
	
	public static SpriteSheet loadSpriteSheet(String name) {
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		InputStream stream = Sprite.class.getResourceAsStream(name);
		Scanner in = new Scanner(stream);
		List<String> lines = SpriteSheet.readLines(in);
		in.close();
		//get the sheet, frames, and then SpriteDatas
		String sheetname = SpriteSheet.getSheetName(lines);
		BufferedImage sheet = GraphicsUtils.loadImage(sheetname);
		Map<String,SpriteFrame> frames = SpriteSheet.getFrames(lines);
		Map<String,SpriteData> sprites = SpriteSheet.getSprites(lines, frames,
				sheet);
		return new SpriteSheet(sprites);
	}
	
	private static String getSheetName(List<String> lines) {
		int index = lines.indexOf(SpriteSheet.NAME_MARK) + 1;
		return lines.get(index);
	}
	
	private static Map<String,SpriteData> getSprites(List<String> lines,
			Map<String,SpriteFrame> frameMap, BufferedImage sheet) {
		int begin = lines.indexOf(SpriteSheet.SPRITE_MARK) + 1;
		int end = SpriteSheet.indexOfStartsWith(lines, SpriteSheet.MARK_START,
				begin);
		if (end < 0) {
			end = lines.size();
		}
		Map<String,SpriteData> ret = new HashMap<String,SpriteData>();
		for (int i = begin; i < end; i++) {
			//name/frames/durations
			String[] blocks = lines.get(i).split(SpriteSheet.BLOCK_SEP);
			//first block is sprite name
			String name = blocks[0];
			//second block is frame names
			String[] frameNames = blocks[1].split(SpriteSheet.LIST_SEP);
			SpriteFrame[] frames = new SpriteFrame[frameNames.length];
			for (int k = 0; k < frameNames.length; k++) {
				frames[k] = frameMap.get(frameNames[k]);
			}
			//third block is frame durations
			int[] durations = SpriteSheet.getDurations(blocks[2]);
			ret.put(name, new SpriteData(sheet, frames, durations));
		}
		return ret;
	}
	
	private static Map<String,SpriteFrame> getFrames(List<String> lines) {
		int begin = lines.indexOf(SpriteSheet.FRAME_MARK) + 1;
		int end = SpriteSheet.indexOfStartsWith(lines, SpriteSheet.MARK_START,
				begin);
		if (end < 0) {
			end = lines.size();
		}
		HashMap<String,SpriteFrame> ret = new HashMap<String,SpriteFrame>();
		for (int i = begin; i < end; i++) {
			//name/x,y,width,height
			String[] blocks = lines.get(i).split(SpriteSheet.BLOCK_SEP);
			String[] parts = blocks[1].split(SpriteSheet.LIST_SEP);
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int w = Integer.parseInt(parts[2]);
			int h = Integer.parseInt(parts[3]);
			ret.put(blocks[0], new SpriteFrame(x, y, w, h));
		}
		return ret;
	}
	
	private static int[] getDurations(String list) {
		String[] durStrs = list.split(SpriteSheet.LIST_SEP);
		int[] ret = new int[durStrs.length];
		for (int i = 0; i < durStrs.length; i++) {
			ret[i] = Integer.parseInt(durStrs[i]);
		}
		return ret;
	}
	
	private static List<String> readLines(Scanner scan) {
		List<String> ret = new ArrayList<String>();
		while (scan.hasNextLine()) {
			ret.add(scan.nextLine());
		}
		return ret;
	}
	
	private static int indexOfStartsWith(List<String> list, String prefix,
			int from) {
		for (int i = from; i < list.size(); i++) {
			if (list.get(i).startsWith(prefix)) {
				return i;
			}
		}
		return -1;
	}
	
}
