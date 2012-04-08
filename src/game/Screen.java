package game;

import java.awt.Graphics;

public interface Screen {

	public void update();
	
	public void render(Graphics g);
	
	public void pause();
	
	public void resume();
	
}
