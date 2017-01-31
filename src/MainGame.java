import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
//Main game logic class, Author Mohid Qureshi
//class that handles game logic, with all game states

public class MainGame extends Canvas implements Runnable {
	private final static int width = 320;
	private final static int height = 240;
	private BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	private BufferedImage backgroundImage = null;
	private BufferedImage menuImage = null;
	private BufferedImage optionImage = null;
	private BufferedImage endGameImage = null;
	private boolean running = false;
	private Thread thread;
	private int fps = 0;
	public BufferedImage spriteSheet = null;
	// lists to store asteroid bullets and power ups
	private CopyOnWriteArrayList<Asteroid> asteroidList = new CopyOnWriteArrayList<Asteroid>();
	private CopyOnWriteArrayList<Asteroid> asteroidListTemp = new CopyOnWriteArrayList<Asteroid>();

	private CopyOnWriteArrayList<Bullet> bulletList = new CopyOnWriteArrayList<Bullet>();
	private CopyOnWriteArrayList<Bullet> bulletListTemp = new CopyOnWriteArrayList<Bullet>();

	private CopyOnWriteArrayList<PowerUp> powerupList = new CopyOnWriteArrayList<PowerUp>();
	private CopyOnWriteArrayList<PowerUp> powerupListTemp = new CopyOnWriteArrayList<PowerUp>();

	private SpaceShip spaceship;
	private Bullet bullet;
	private Asteroid asteroid;
	private PowerUp powerup;
	private HighScore highscore;
	private EnemySpaceShip enemySpaceShip;
	private Random random = new Random();
	private int randomX = random.nextInt(600);
	private int randomY = random.nextInt(415);
	private int randomPower = random.nextInt(10);
	private int counter = 0; // variable to check how many rounds have went by
	private int points = 0; // variable to track amount of points user earned
	private int speedChange = 1;
	private int astHealth = 100;
	private int spaceshipHealth = 1000;
	private int enemySpaceShipHealth = 2500;
	private int averageSpeedSS = 3;
	private int asteroidSpawn = 3000;
	private Rectangle b; // rectangle figure for bullets
	private Rectangle e; // rectangle figure for asteroids
	private Rectangle s; // rectangle figure for player controlled spaceship
	private Rectangle p; // rectangle figure for power up image
	private Rectangle es; // rectangle figure for enemy space ship
	private MainMenu menu;

	// game state to determine which state the game is in
	public enum gameState {
		game, menu, option, gameOver
	};

	// game starts off as menu
	public gameState state = gameState.menu;

	public static void main(String args[]) {
		MainGame game = new MainGame();

		// setting up main game frame
		JFrame mainFrame = new JFrame("Mohid's Asteroid Game");
		mainFrame.setSize(width * 2, height * 2); // temp
		mainFrame.add(game);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		game.start();

	}

	// start method creates new thread for game to start
	public void start() {
		thread = new Thread(this);
		addKeyListener(new Movement(this)); // adds key listener from movement
											// class
		addMouseListener(new MainMenu(this)); // adds mouse listener from menu
												// class
		thread.start();
	}

	// method that constructs game loop
	public void run() {
		// calls init class
		initialization();
		running = true;
		long timer = System.currentTimeMillis();
		long asteroidSpawnTime = System.currentTimeMillis();
		// game loop
		while (running) {
			fps++;
			render(); // runs render method that included drawing of all
						// graphics each time loop runs
			// checks if state of game is in game
			if (state == state.game) {
				// calls game related methods
				// calls collsion methods
				collsionSS();
				collision();
				spaceship.boundaries();
				asteroid.asteroidBound(asteroidList);
				enemySpaceShipCollide();
				enemySpaceShip.enemySpaceShipBound();
				enemySpaceShip.Alive();
				spaceship.movement();

				// every 1 second, if statement runs
				if (System.currentTimeMillis() - timer >= 1000) {
					fps = 0;
					timer = System.currentTimeMillis(); // sets new time
				}
				// checks after 3 seconds spawns asteroid in a random location
				if (System.currentTimeMillis() - asteroidSpawnTime >= asteroidSpawn) {
					counter++;
					averageSpeedSS = 3; // sets back default speed of spaceship
										// after time interval
					// sets enemy space ship speeds back to default
					enemySpaceShip.setxSpeed(1);
					enemySpaceShip.setySpeed(1);
					// gets random position for asteroid to spawn in
					int randomX = random.nextInt(600);
					int randomY = random.nextInt(415);
					int randomPower = random.nextInt(10);
					asteroid.asteroidSpawn(randomX, randomY, randomPower,
							astHealth, asteroidList);
					asteroidSpawnTime = System.currentTimeMillis(); // sets new
																	// time for
																	// asteroid
																	// timer
					// different levels to determine difficulty
					difficulty();
				}
				try {
					thread.sleep(10); // thread pauses for 16 milliseconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// method for collision between the bullets and asteroids and enemy
	// spaceship
	public void collision() {
		for (Bullet bullet : bulletList) {
			for (Asteroid asteroid : asteroidList) {
				// creates new instance of rectangle objects with same
				// measurements as images of entities
				b = new Rectangle(bullet.getX(), bullet.getY(), 30, 30);
				e = new Rectangle(asteroid.getX(), asteroid.getY(), 26, 26);
				es = new Rectangle((int) enemySpaceShip.getX(),
						(int) enemySpaceShip.getY(), 30, 30);
				// checks when the bullet and asteroid intersect
				if (b.intersects(e)) {
					asteroid.setHealth(asteroid.getHealth() - 100);
					spaceship.setPoints(spaceship.getPoints() + 25);
					// checks if asteroid health is less than 0
					if (asteroid.getHealth() <= 0) {
						asteroidListTemp.add(asteroid);
						spaceship.setPoints(spaceship.getPoints() + 10);
						randomPower = random.nextInt(10);
						//adds power up after asteroid is dead
						powerupList.add(new PowerUp(asteroid, this, randomPower)); 
					}
					bulletListTemp.add(bullet); // adds bullet to temp list to
												// be removed later
				}
				// checks if bullet intersects with enemy spaceship
				else if (b.intersects(es)) {
					// checks if enemyspace ship is alive
					if (enemySpaceShip.getIsAlive()) {
						// subtracting health when bullet is in contact
						enemySpaceShip
								.setHealth(enemySpaceShip.getHealth() - 100);
						bulletListTemp.add(bullet); // adds bullet to temp list
						break;
					}
				}
				// removes contents of temp list from the main list
				asteroidList.removeAll(asteroidListTemp);
				bulletList.removeAll(bulletListTemp);
				// checks if enemy space ship is alive
				if (enemySpaceShip.getHealth() <= 0) {
					enemySpaceShip.setIsAlive(false);
				}
				enemySpaceShip.Alive();
			}
		}

	}

	// method for collision between asteroids and player spaceship and power ups
	public void collsionSS() {
		for (Asteroid asteroid : asteroidList) {
			// creates new instance of rectangle objects
			e = new Rectangle(asteroid.getX(), asteroid.getY(), 26, 26);
			s = new Rectangle(spaceship.getX(), spaceship.getY(), 32, 32);
			// if spaceship and asteroid intersect
			if (s.intersects(e)) {
				spaceshipHealth -= 50;
			}
			// if player spaceship is less than 0, set the game state to game
			// over
			ifGameOver();
		}
		// calls method to check for player and power up collision
		powerUpCollsion();

	}

	// render method including all drawings of images and loading of window
	public void render() {
		BufferStrategy bStrat = this.getBufferStrategy();
		if (bStrat == null) {
			createBufferStrategy(3);
			return;
		}
		// creates instance of graphics
		Graphics g = bStrat.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(backgroundImage, 0, 0, null);
		// checks if state is menu, drawing the menu image
		if (state == state.menu) {
			g.drawImage(menuImage, 0, 0, null);
		}
		// checks if state is option, drawing the option menu image
		else if (state == state.option) {
			g.drawImage(optionImage, 0, 0, null);
		}
		// checks if state is game over, draw the game over menu image
		else if (state == state.gameOver) {
			Font f = new Font("Comic Sans MS", Font.BOLD, 25);
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawImage(endGameImage, 0, 0, null);
			try {
				highscore.render(g, 405, 170);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// prints string for game over screen
			g.drawString("POINTS EARNED: " + points, 170, 120);
			g.drawString("HIGHEST SCORE: ", 170, 170);
			g.drawString("AVERAGE SCORE: ", 170, 220);
			highscore.toWrite(spaceship.getPoints());
			reset(); // resets everything back to default
		}
		// checks if game state is in game
		if (state == state.game) {
			inGameState(g);
		}
		// disposes of all graphics
		g.dispose();
		bStrat.show();
	}

	// method to initialize all images and objects being used in the game
	public void initialization() {
		BufferedImageLoader loadImg = new BufferedImageLoader();
		spriteSheet = loadImg.loadImage("/spritesheet5.png");
		backgroundImage = loadImg.loadImage("/background.png");
		menuImage = loadImg.loadImage("/menuPic.png");
		optionImage = loadImg.loadImage("/optionPic.png");
		endGameImage = loadImg.loadImage("/endPic.png");
		spaceship = new SpaceShip(100, 100, spaceshipHealth, this);
		bullet = new Bullet(spaceship, this, 0);
		asteroid = new Asteroid(randomX, randomY, astHealth, 0, this);
		powerup = new PowerUp(asteroid, this, randomPower);
		menu = new MainMenu(this);
		enemySpaceShip = new EnemySpaceShip(-100, -100, enemySpaceShipHealth,
				true, this);
		try {
			highscore = new HighScore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		// only check for key press when game state is in play
		if (state == state.game) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				spaceship.setxSpeed(averageSpeedSS);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				spaceship.setxSpeed(-averageSpeedSS);
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				spaceship.setySpeed(-averageSpeedSS);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				spaceship.setySpeed(averageSpeedSS);
			}
			// if w key is add bullet object with the direction of 1 which is
			// north
			else if (e.getKeyCode() == KeyEvent.VK_W) {
				if (bullet.playerBullet(bulletList)) {
					bulletList.add(new Bullet(spaceship, this, 1));
				}
			}
			// if s key is add bullet object with the direction of 1 which is
			// south
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				if (bullet.playerBullet(bulletList)) {
					bulletList.add(new Bullet(spaceship, this, 2));
				}

			}
			// if a key is add bullet object with the direction of 1 which is
			// left
			else if (e.getKeyCode() == KeyEvent.VK_A) {
				if (bullet.playerBullet(bulletList)) {
					bulletList.add(new Bullet(spaceship, this, 3));
				}

			}
			// if d key is add bullet object with the direction of 4 which is
			// right
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				if (bullet.playerBullet(bulletList)) {
					bulletList.add(new Bullet(spaceship, this, 4));
				}
			}
		}
	}

	// when key is released continue to move spaceship at a constant rate
	public void keyReleased(KeyEvent e) {
		if (state == state.game) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				spaceship.setxSpeed(1);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				spaceship.setxSpeed(-1);
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				spaceship.setySpeed(-1);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				spaceship.setySpeed(1);
			}
		}
	}

	// method that resets all variables being used in game back to default
	public void reset() {
		astHealth = 100;
		spaceshipHealth = 1000;
		speedChange = 1;
		spaceship.setPoints(0);
		counter = 0;
		asteroidList.removeAll(asteroidList);
		bulletList.removeAll(bulletList);
		powerupList.removeAll(powerupList);
		spaceship.setxSpeed(0);
		spaceship.setySpeed(0);
		enemySpaceShip.setIsAlive(true);
		enemySpaceShip.setX(1);
		enemySpaceShip.setY(1);
		enemySpaceShip.setHealth(enemySpaceShipHealth);
	}

	// method for when game is in game state, rendering all entites images
	public void inGameState(Graphics g) {
		// calls method to follow player spaceship
		enemySpaceShip.FollowSS(spaceship);
		// checks if enemy space ship is alive
		if (enemySpaceShip.getIsAlive())
			enemySpaceShip.render(g);
		// sets points
		points = spaceship.getPoints();
		spaceship.render(g); // draws spaceship image
		Font f = new Font("Times New Roman", Font.BOLD, 16);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString("HEALTH REMAINING " + Integer.toString(spaceshipHealth),
				420, 445);
		g.drawString("POINTS " + Integer.toString(points), 20, 445);
		// going through list of asteroids to draw each image
		for (Asteroid asteroid : asteroidList) {
			asteroid.render(g);
		}

		// loops through power up list and draws each power up image
		for (PowerUp powerup : powerupList) {
			powerup.renderH(g);
		}

		// loops through list of Bullets and draws the image
		for (Bullet bullet : bulletList) {
			bullet.render(g);
			// adds bullet to a temporary list if it goes outside the screen
			if (bullet.getY() < 2 || bullet.getY() > 417 || bullet.getX() > 603
					|| bullet.getX() < 0) {
				bulletListTemp.add(bullet);
				// if direction is north
			}
			bullet.Bounds(bullet);
		}
		// removes all instances of bullet that were moved off the screen
		bulletList.removeAll(bulletListTemp);
	}

	// method to check for player and enemy collision
	public void enemySpaceShipCollide() {
		s = new Rectangle(spaceship.getX(), spaceship.getY(), 30, 30);
		es = new Rectangle((int) enemySpaceShip.getX(),
				(int) enemySpaceShip.getY(), 30, 30);
		if (enemySpaceShip.getIsAlive()) {
			if (s.intersects(es)) {
				spaceshipHealth -= 150;
			}
		}
		ifGameOver(); // checks if game is over, calling the method
	}

	// method to determine difficulty of game
	public void difficulty() {
		// if counter is greater than 5 increase speed of asteroids
		if (counter > 5) {
			speedChange = 2;
		}
		// if counter is greater than 10 increase health and spawn time of
		// asteroid
		if (counter > 10) {
			astHealth = 200;
			asteroidSpawn = 2000;
		}
		// if counter is greater than 25 increase health and spawn time of
		// asteroid
		if (counter > 25) {
			astHealth = 300;
			asteroidSpawn = 1000;
		}
	}

	// draw method to display high score
	public void renderHighScore(Graphics g) throws IOException {
		g.setColor(Color.WHITE);
		highscore.render(g, 10, 10);
	}

	// checks if player spaceship health is less than 0, indicating game is over
	public void ifGameOver() {
		if (spaceshipHealth <= 0) {
			state = state.gameOver;
		}
	}

	// method for power up collision between player space ship and enemy space
	// ship
	public void powerUpCollsion() {
		for (PowerUp powerup : powerupList) {
			p = new Rectangle(powerup.getX(), powerup.getY(), 25, 25);
			s = new Rectangle(spaceship.getX(), spaceship.getY(), 30, 30);
			es = new Rectangle(enemySpaceShip.getX(), enemySpaceShip.getY(),30, 30);
			// if intersects add health to player spaceship
			if (p.intersects(s)) {
				// checks what type of power up was dropepd by the asteroid
				if (powerup.getRandomNum() < 2) {
					spaceshipHealth += 1000;
				} else if (powerup.getRandomNum() > 8) {
					spaceship.setPoints(spaceship.getPoints() + 100);
					points += 100;
				} else if (powerup.getRandomNum() == 3) {
					averageSpeedSS = 5;
				} else if (powerup.getRandomNum() == 4) {
					spaceship.setPoints(spaceship.getPoints() + asteroidList.size() * 25);
					points += asteroidList.size() * 25;
					asteroidList.clear(); // clears asteroid list
				}
				// adds power up object to the temp list
				powerupListTemp.add(powerup);
			}
		}
		// removes all instances of the temp list in the main list
		powerupList.removeAll(powerupListTemp);
	}

	public SpaceShip getSpaceShip() {
		return spaceship;
	}

	public int getSpeedChange() {
		return this.speedChange;
	}
}
