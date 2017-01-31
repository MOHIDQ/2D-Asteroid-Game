import java.awt.Graphics;
import java.awt.image.BufferedImage;
//power up class, author Mohid qureshi
//power up class that determines which power up is to be dropped, rendering the type of image + 
//according to power up

public class PowerUp {
	// power up images
	public BufferedImage powerUpImageH;
	public BufferedImage powerUpImageC;
	public BufferedImage powerUpImageS;
	public BufferedImage powerUpImageN;
	public MainGame game;
	public int x;
	public int y;
	public int randomNum;

	public PowerUp(Asteroid asteroid, MainGame game, int randomNum) {
		this.x = asteroid.getX();
		this.y = asteroid.getY();
		this.randomNum = randomNum;
		this.game = game;
		// loads up images from sprite sheet
		SpriteSheet spritesheet = new SpriteSheet(game.spriteSheet);
		// power up images init
		powerUpImageH = spritesheet.getImage(4, 1, 32, 32);
		powerUpImageC = spritesheet.getImage(5, 1, 32, 32);
		powerUpImageS = spritesheet.getImage(6, 1, 32, 32);
		powerUpImageN = spritesheet.getImage(7, 1, 32, 32);
	}

	// render method to draw graphics of the power up images
	public void renderH(Graphics g) {
		// if random number greater than 0, draw health image
		if (randomNum < 2) {
			g.drawImage(powerUpImageH, x, y, null);
		}
		// if random number greater than 8, draw coin image
		else if (randomNum > 8) {
			g.drawImage(powerUpImageC, x, y, null);
		}
		// if random number equal to 3, draw speed image
		else if (randomNum == 3) {
			g.drawImage(powerUpImageS, x, y, null);
		}
		// if random number equal to 4, draw nuke image
		else if (randomNum == 4) {
			g.drawImage(powerUpImageN, x, y, null);
		}
	}

	public int getRandomNum() {
		return randomNum;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
