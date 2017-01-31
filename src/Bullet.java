import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
//bullet class, author Mohid Qureshi
//bullet claass holding all the attributes of a bullet, rendering and bounds 

public class Bullet {
	private int x;
	private int y;
	private int xSpeed;
	private int ySpeed;
	private int direction; // 1 is north, 2 is south, 3 is left, 4 is right
	private BufferedImage bulletImage;
	MainGame game;

	public Bullet(SpaceShip spaceship, MainGame game, int direction) {
		this.game = game;
		this.x = spaceship.getX();
		this.y = spaceship.getY();
		xSpeed = 4;
		ySpeed = 4;
		this.direction = direction;
		SpriteSheet spritesheet = new SpriteSheet(game.spriteSheet);
		bulletImage = spritesheet.getImage(2, 1, 32, 32);
	}

	// draws image of bullet
	public void render(Graphics g) {
		g.drawImage(bulletImage, x, y, null);
	}

	// checks if amount of bullets on window is less than 5, not letting more be added
	public boolean playerBullet(List<Bullet> bulletList) {
		if (bulletList.size() < 5) {
			return true;
		} else
			return false;
	}
	//bounds for bullet, correctly being moves depending on direction
	public void Bounds(Bullet bullet) {
		if (bullet.getDirection() == 1) {
			bullet.setY(bullet.getY() - bullet.getySpeed());
		}
		// if direction is south
		else if (bullet.getDirection() == 2) {
			bullet.setY(bullet.getY() + bullet.getySpeed());
		}
		// if direction is left
		else if (bullet.getDirection() == 3) {
			bullet.setX(bullet.getX() - bullet.getxSpeed());
		}
		// if direction is right
		else if (bullet.getDirection() == 4) {
			bullet.setX(bullet.getX() + bullet.getxSpeed());
		}
	}

	public int getX() {
		return x;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public int getY() {
		return y;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public int getDirection() {
		return direction;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

}
