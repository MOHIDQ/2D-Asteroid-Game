import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//Main Menu class, author Mohid Qureshi
//main menu class to get mouse inputs to switch between game states when pressed in a given area

public class MainMenu implements MouseListener {
	int mouseX;
	int mouseY;
	private MainGame game;

	public MainMenu(MainGame game) {
		this.game = game;
	}

	public void render(Graphics g) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// when user mouse is pressed
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		// when play button is pressed
		if (game.state == game.state.menu) {
			if (mouseX >= 91 && mouseX <= 554) {
				if (mouseY >= 116 && mouseY <= 197) {
					// changes state to game
					game.state = game.state.game;

				}
			}
		}
		// when option button is pressed
		if (game.state == game.state.menu) {
			if (mouseX >= 91 && mouseX <= 554) {
				if (mouseY >= 230 && mouseY <= 313) {
					// changes state to option
					game.state = game.state.option;
				}
			}
		}
		// when quit button is pressed
		if (game.state == game.state.menu) {
			if (mouseX >= 94 && mouseX <= 562) {
				if (mouseY >= 346 && mouseY <= 427) {
					// quits game
					System.exit(0);
				}
			}
		}
		// when back button is pressed in the option state
		if (game.state == game.state.option) {
			if (mouseX >= 122 && mouseX <= 518) {
				if (mouseY >= 387 && mouseY <= 441) {
					// changes state to menu
					game.state = game.state.menu;
				}
			}
		}
		// when user clicks on the play button in the game over state
		if (game.state == game.state.gameOver) {
			if (mouseX >= 60 && mouseX <= 594) {
				if (mouseY >= 259 && mouseY <= 327) {
					game.getSpaceShip().setPoints(0);
					// changes state to game
					game.state = game.state.game;
				}
			}
		}
		// when user clicks on the main hub button n the game over state
		else if (mouseX >= 60 && mouseX <= 592) {
			if (mouseY >= 374 && mouseY <= 442) {
				// changes state to menu
				game.state = game.state.menu;
			}
		}
		// when user clicks on the main hub button n the game over state
		if (game.state == game.state.gameOver) {
			if (mouseX >= 60 && mouseX <= 592) {
				if (mouseY >= 374 && mouseY <= 442) {
					// changes state to menu
					game.state = game.state.menu;
				}
			}
		}

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
