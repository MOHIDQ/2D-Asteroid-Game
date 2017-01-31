import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//Enemy SpaceShip class, author Mohid Qureshi
//enemy spaceship class, holding all attributes of enemy space, rendering, +
//follow of player spaceship and bounds

public class EnemySpaceShip {
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private int health;
	private MainGame game;
	private Boolean isAlive;
	private BufferedImage enemyImage;
	private SpriteSheet spritesheet;

	public EnemySpaceShip(double x, double y, int health, Boolean isAlive,
			MainGame game) {
		this.x = x;
		this.y = y;
		this.health = health;
		this.game = game;
		xSpeed = 0.3;
		ySpeed = 0.3;
		this.isAlive = isAlive;
		spritesheet = new SpriteSheet(game.spriteSheet);
		enemyImage = spritesheet.getImage(8, 1, 32, 32);
	}

	// draws enemy space ship image if alive
	public void render(Graphics g) {
		if (isAlive)
			g.drawImage(enemyImage, (int)x, (int)y, null);
	}

	// method that gets direction of spaceship and moves towards it, given
	// coordinates
	public void FollowSS(SpaceShip spaceship) {
		if (isAlive) {
			if (y > spaceship.getY()) {
				y -= ySpeed;
			}
			if (x > spaceship.getX()) {
				x -= xSpeed;
			}
			if (y < spaceship.getY()) {
				y += ySpeed;
			}
			if (x < spaceship.getX()) {
				x += xSpeed;
			}
		}
	}

	// method to ensure enemy spaceship is being placed outside window
	public void enemySpaceShipBound() {
		if (x > 603) {
			x = 601;
		}
		if (y > 417) {
			y = 415;
		}
		if (y < 0) {
			y = 2;
		}
		if (x < 0) {
			x = 2;
		}
	}

	// checks if enemy space ship is alive, returning false if not alive
	public void Alive() {
		if (health <= 0) {
			isAlive = false;
		}
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	
	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

}
