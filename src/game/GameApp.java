package game;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import control.Controller;

public class GameApp {

	private Frame frame;
	private Game game;
	
	public GameApp() {
		//frame and canvas
		this.game = new Game();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frame = new Frame("GameApp");
		this.frame.setIgnoreRepaint(true);
		this.frame.setResizable(false);
		this.frame.add(this.game);
		this.frame.pack();
		this.frame.setLocation((screenSize.width / 2) - (Game.WIDTH / 2),
				(screenSize.height / 2) - (Game.HEIGHT / 2));
		//control
		this.frame.addKeyListener(Controller.getListener());
		//show the frame
		this.frame.setVisible(true);
	}
	
	public void run() {
		this.game.run();
	}
	
	public static void main(String[] args) {
		GameApp app = new GameApp();
		app.run();
	}
	
}
