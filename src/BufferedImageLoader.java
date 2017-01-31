import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
//BufferedImageLoader class, author Mohid Qureshi
//BufferedImageLoader class to load all buffered images used

public class BufferedImageLoader {
	private BufferedImage image;

	// method that loads a buffered image path given from resource
	public BufferedImage loadImage(String imagePath) {
		try {
			image = ImageIO.read(this.getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}
