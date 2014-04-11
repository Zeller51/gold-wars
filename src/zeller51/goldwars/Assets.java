package zeller51.goldwars;

import zeller51.goldwars.graphics.Font;
import zeller51.goldwars.graphics.Sprite;

public class Assets {

	private static Sprite fontCharacters;
	public static Font font;

	public static void load() {
		fontCharacters = new Sprite("chars.png");
		font = new Font(fontCharacters);
	}

}
