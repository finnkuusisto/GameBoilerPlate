package game;

import java.applet.Applet;
import java.awt.BorderLayout;

public class GameApplet extends Applet {

	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	@Override
	public void init() {
		//canvas
		this.game = new Game();
		this.setLayout(new BorderLayout());
        this.add(this.game, BorderLayout.CENTER);
	}
	
	@Override
	public void start() {
		Thread gameThread = new Thread(new GameRunner(this.game));
		gameThread.start();
	}
	
	@Override
	public void stop() {
		this.game.stop();
	}
	
	private class GameRunner implements Runnable {
		
		private Game game;
		
		public GameRunner(Game game) {
			this.game = game;
		}

		@Override
		public void run() {
			this.game.run();
		}
		
	}

}
