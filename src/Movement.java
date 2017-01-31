import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//movement class, made by mohid qureshi
//movement class to determine key that is being pressed to be given to main class

public class Movement implements KeyListener {
	private MainGame game;

	public Movement(MainGame game) {
		this.game = game;
	}

	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}

	// when key is released call key released method from main game class
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {

	}

}
