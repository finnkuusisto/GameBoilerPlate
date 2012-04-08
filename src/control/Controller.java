package control;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener, FocusListener {

	// key events
	public static final int K_UP = 0;
	public static final int K_DOWN = 1;
	public static final int K_RIGHT = 2;
	public static final int K_LEFT = 3;
	public static final int K_SPACE = 4;
	public static final int K_ESCAPE = 5;
	public static final int K_ENTER = 6;
	public static final int K_W = 7;
	public static final int K_A = 8;
	public static final int K_S = 9;
	public static final int K_D = 10;
	private static final int NUM_KEYS = 11;

	private static boolean[] keys = new boolean[Controller.NUM_KEYS];
	private static Controller controller = new Controller();

	public static void init() {
	}

	public static Controller getListener() {
		return Controller.controller;
	}

	public static boolean isKeyDown(int key) {
		return (key >= Controller.K_UP && key <= Controller.K_D) ? Controller.keys[key]
				: false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			Controller.keys[Controller.K_UP] = true;
			break;
		case KeyEvent.VK_DOWN:
			Controller.keys[Controller.K_DOWN] = true;
			break;
		case KeyEvent.VK_RIGHT:
			Controller.keys[Controller.K_RIGHT] = true;
			break;
		case KeyEvent.VK_LEFT:
			Controller.keys[Controller.K_LEFT] = true;
			break;
		case KeyEvent.VK_W:
			Controller.keys[Controller.K_W] = true;
			break;
		case KeyEvent.VK_A:
			Controller.keys[Controller.K_A] = true;
			break;
		case KeyEvent.VK_S:
			Controller.keys[Controller.K_S] = true;
			break;
		case KeyEvent.VK_D:
			Controller.keys[Controller.K_D] = true;
			break;
		case KeyEvent.VK_SPACE:
			Controller.keys[Controller.K_SPACE] = true;
			break;
		case KeyEvent.VK_ENTER:
			Controller.keys[Controller.K_ENTER] = true;
			break;
		case KeyEvent.VK_ESCAPE:
			Controller.keys[Controller.K_ESCAPE] = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			Controller.keys[Controller.K_UP] = false;
			break;
		case KeyEvent.VK_DOWN:
			Controller.keys[Controller.K_DOWN] = false;
			break;
		case KeyEvent.VK_RIGHT:
			Controller.keys[Controller.K_RIGHT] = false;
			break;
		case KeyEvent.VK_LEFT:
			Controller.keys[Controller.K_LEFT] = false;
			break;
		case KeyEvent.VK_W:
			Controller.keys[Controller.K_W] = false;
			break;
		case KeyEvent.VK_A:
			Controller.keys[Controller.K_A] = false;
			break;
		case KeyEvent.VK_S:
			Controller.keys[Controller.K_S] = false;
			break;
		case KeyEvent.VK_D:
			Controller.keys[Controller.K_D] = false;
			break;
		case KeyEvent.VK_SPACE:
			Controller.keys[Controller.K_SPACE] = false;
			break;
		case KeyEvent.VK_ENTER:
			Controller.keys[Controller.K_ENTER] = false;
			break;
		case KeyEvent.VK_ESCAPE:
			Controller.keys[Controller.K_ESCAPE] = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) { /* unused */
	}


	@Override
	public void focusGained(FocusEvent e) { /* unused */
	}

	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < Controller.keys.length; i++) {
			Controller.keys[i] = false;
		}
	}

}