import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
//Asteroid class, made by mohid qureshi
//asteroid class that holds all the attributes of an asteroid, rendering and bounds 

public class Asteroid {
	private int x;
	private int y;
	private int xSpeed;
	private int ySpeed;
	private int health;
	private int powerUp;
	private BufferedImage asteroidImage;
	MainGame game;

	public Asteroid(int x, int y, int health, int powerUp, MainGame game) {
		this.x = x;
		this.y = y;
		this.health = health;
		this.game = game;
		this.powerUp = powerUp;
		xSpeed = 1;
		ySpeed = 1;
		SpriteSheet spritesheet = new SpriteSheet(game.spriteSheet);
		asteroidImage = spritesheet.getImage(3, 1, 32, 32);
	}

	// renders an image of the bullet
	public void render(Graphics g) {
		g.drawImage(asteroidImage, x, y, null);
	}

	// bounds for the asteroid, correctly being moved when hit with the windows
	// end
	public void asteroidBound(CopyOnWriteArrayList<Asteroid> asteroidList) {
		// loops through the list of asteroids
		for (Asteroid asteroid : asteroidList) {
			asteroid.x += asteroid.xSpeed;
			asteroid.y += asteroid.ySpeed;
			if (asteroid.x > 603) {
				asteroid.xSpeed = -game.getSpeedChange();
			} else if (asteroid.y > 417) {
				asteroid.ySpeed = -game.getSpeedChange();
			} else if (asteroid.y < 0) {
				asteroid.ySpeed = game.getSpeedChange();
			} else if (asteroid.x < 0) {
				asteroid.xSpeed = game.getSpeedChange();
			}
		}
	}

	// spawns asteroid adding to list
	public void asteroidSpawn(int randX, int randY, int randPower,
			int astHealth, List<Asteroid> asteroidList) {
		asteroidList
				.add(new Asteroid(randX, randY, astHealth, randPower, game));
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
