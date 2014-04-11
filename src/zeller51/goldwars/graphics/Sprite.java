package zeller51.goldwars.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private BufferedImage data;

	public Sprite(BufferedImage data) {
		this.data = data;
	}

	public Sprite(String name) {
		try {
			data = ImageIO.read(new FileInputStream("res/" + name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sprite create(int x, int y, int w, int h) {
		return new Sprite(data.getSubimage(x, y, w, h));
	}

	public BufferedImage data() {
		return data;
	}

}
