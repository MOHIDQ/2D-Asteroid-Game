import java.awt.Graphics;
import java.awt.image.BufferedImage;
//SpaceShip class, author Mohid Qureshi
//space ship class, holding all the attriutes of the player spaceship, rendering and bounds 

public class SpaceShip {
	private int x;
	private int y;
	private int xSpeed;
	private int ySpeed;
	private int health;
	private int points;
	private BufferedImage spaceShipImage;
	MainGame game;

	public SpaceShip(int x, int y, int health, MainGame game) {
		this.x = x;
		this.y = y;
		this.health = health;
		this.game = game;
		points = 0;
		SpriteSheet spriteSheet = new SpriteSheet(game.spriteSheet);
		spaceShipImage = spriteSheet.getImage(1, 1, 32, 32); // gets sub image
																// from sprite
																// sheet

	}

	// render method, that draws players spaceship
	public void render(Graphics g) {
		g.drawImage(spaceShipImage, x, y, null);
	}

	// checks if spaceship is moving out of window, setting it back in position
	public void boundaries() {
		if (x < 0) {
			x = 2;
		}
		if (y < 0) {
			y = 2;
		}
		if (y > 417) {
			y = 415;
		}
		if (x > 603) {
			x = 601;
		}
	}

	public void movement() {
		x += xSpeed;
		y += ySpeed;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
