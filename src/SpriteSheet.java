import java.awt.image.BufferedImage;

//sprite sheet class, author Mohid Qureshi
public class SpriteSheet {
	private BufferedImage image;

	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	// grab and crops sprite sheet image
	// column of sub image, row of sub image
	// width of sprite sheet, height of sprite sheet
	// returns sub image of sprite sheet
	public BufferedImage getImage(int x, int y, int width, int height) {
		BufferedImage subImage = image.getSubimage((x - 1) * 32, (y - 1) * 32,
				width, height);
		return subImage;

	}

}
