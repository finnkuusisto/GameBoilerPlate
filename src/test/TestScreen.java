package test;

import java.awt.Graphics;
import java.util.Random;

import control.Controller;

import game.Game;
import game.Screen;
import graphics.GraphicsUtils;
import graphics.SpriteSheet;
import graphics.Sprite;

public class TestScreen implements Screen {
	
	private Sprite[] sprites;
	private int[] spriteX;
	private int[] spriteY;
	
	public TestScreen() {
		//init Sprites
		Random rand = new Random();
		SpriteSheet sprites = SpriteSheet.loadSpriteSheet("testsheet.txt");
		int numSprites = 1000;
		this.sprites = new Sprite[numSprites];
		this.spriteX = new int[numSprites];
		this.spriteY = new int[numSprites];
		for (int i = 0; i < this.sprites.length; i++) {
			this.sprites[i] = sprites.getSprite("smile");
			this.spriteX[i] = rand.nextInt(Game.WIDTH) + 1;
			this.spriteY[i] = rand.nextInt(Game.HEIGHT) + 1;
			int burnIn = rand.nextInt(this.sprites[i].numFrames());
			for (int j = 0; j < burnIn; j++) {
				this.sprites[i].update();
			}
		}
	}

	@Override
	public void update() {
		int deltaY = 0;
		int deltaX = 0;
		if (Controller.isKeyDown(Controller.K_W)) {
			deltaY -= 2;
		}
		if (Controller.isKeyDown(Controller.K_A)) {
			deltaX -= 2;
		}
		if (Controller.isKeyDown(Controller.K_S)) {
			deltaY += 2;
		}
		if (Controller.isKeyDown(Controller.K_D)) {
			deltaX += 2;
		}
		if (deltaX != 0 || deltaY != 0) {
			for (int i = 0; i < this.sprites.length; i++) {
				this.spriteY[i] += deltaY;
				this.spriteX[i] += deltaX;
				this.sprites[i].update();
			}
		}
		
		if (Controller.isKeyDown(Controller.K_ESCAPE)) {
			System.exit(0);
		}
	}
	
	@Override
	public void render(Graphics g) {
		for (int i = 0; i < this.sprites.length; i++) {
			GraphicsUtils.drawSprite(g, this.sprites[i], this.spriteX[i],
					this.spriteY[i]);
		}		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
