package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import control.Controller;

public class Game extends Canvas {

	private static final long serialVersionUID = 1L;
	private static final long NANOSEC_PER_SEC = 1000000000L;
	public static final long FRAMES_PER_SEC = 60L;
	public static final long TICKS_PER_SEC = 40L;
	public static final long NANOSEC_PER_TICK = NANOSEC_PER_SEC / TICKS_PER_SEC;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private ScreenManager screenManager;
	private boolean running;
	
	public Game() {
		this.screenManager = new ScreenManager();
		this.running = false;
		this.setIgnoreRepaint(true);
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setBackground(Color.BLACK);
		//control
		this.addKeyListener(Controller.getListener());
		this.addFocusListener(Controller.getListener());
	}
	
	public void stop() {
		this.running = false;
	}
	
	public int run() {
		//create our buffer
		this.createBufferStrategy(2);
		//set ourselves off and running
		this.running = true;
		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long frameCount = 0;
		while (this.running) {
			// get the current screen //
			Screen currScreen = this.screenManager.getActiveScreen();
			if (currScreen == null) {
				return -1;
			}
			// get the current time //
			long currTime = System.nanoTime();
			// compute FPS //
			currTime = System.nanoTime();
			if (currTime - lastFrame >= NANOSEC_PER_SEC) {
				System.out.println("FPS: " + frameCount);
				lastFrame = currTime;
				frameCount = 0;
			} else {
				frameCount++;
			}
			// update //
			if ((currTime - lastUpdate) > NANOSEC_PER_TICK) {
				currScreen.update();
				lastUpdate = currTime;
			}
			// render //
			BufferStrategy buffer = this.getBufferStrategy();
			Graphics g = null;
			try {
				g = buffer.getDrawGraphics();
				g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
				currScreen.render(g);
			} finally {
				g.dispose();
				buffer.show();
				this.getToolkit().sync();
				// give the cpu back to the OS for a bit
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) { }
			}
		}
		return 0;
	}

}
