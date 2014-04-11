package zeller51.goldwars.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Font {

	private Sprite sheet;
	private List<FontCharacter> characters = new ArrayList<FontCharacter>();

	public Font(Sprite sheet) {
		this.sheet = sheet;
		process();
	}

	private void process() {
		characters.add(new FontCharacter('A', sheet.create(0, 0, 4, 6), 4));
		characters.add(new FontCharacter('B', sheet.create(6, 0, 4, 6), 4));
		characters.add(new FontCharacter('C', sheet.create(12, 0, 4, 6), 4));
		characters.add(new FontCharacter('D', sheet.create(18, 0, 4, 6), 4));
		characters.add(new FontCharacter('E', sheet.create(24, 0, 4, 6), 4));
		characters.add(new FontCharacter('F', sheet.create(30, 0, 4, 6), 4));
		characters.add(new FontCharacter('G', sheet.create(36, 0, 4, 6), 4));
		characters.add(new FontCharacter('H', sheet.create(42, 0, 4, 6), 4));
		characters.add(new FontCharacter('I', sheet.create(48, 0, 1, 6), 1));
		characters.add(new FontCharacter('J', sheet.create(54, 0, 4, 6), 4));
		characters.add(new FontCharacter('K', sheet.create(60, 0, 4, 6), 4));
		characters.add(new FontCharacter('L', sheet.create(66, 0, 4, 6), 4));
		characters.add(new FontCharacter('M', sheet.create(72, 0, 5, 6), 5));

		characters.add(new FontCharacter('N', sheet.create(0, 6, 5, 6), 5));
		characters.add(new FontCharacter('O', sheet.create(6, 6, 4, 6), 4));
		characters.add(new FontCharacter('P', sheet.create(12, 6, 4, 6), 4));
		characters.add(new FontCharacter('Q', sheet.create(18, 6, 4, 6), 4));
		characters.add(new FontCharacter('R', sheet.create(24, 6, 4, 6), 4));
		characters.add(new FontCharacter('S', sheet.create(30, 6, 4, 6), 4));
		characters.add(new FontCharacter('T', sheet.create(36, 6, 5, 6), 5));
		characters.add(new FontCharacter('U', sheet.create(42, 6, 4, 6), 4));
		characters.add(new FontCharacter('V', sheet.create(48, 6, 5, 6), 5));
		characters.add(new FontCharacter('W', sheet.create(54, 6, 4, 6), 5));
		characters.add(new FontCharacter('X', sheet.create(60, 6, 4, 6), 4));
		characters.add(new FontCharacter('Y', sheet.create(66, 6, 5, 6), 5));
		characters.add(new FontCharacter('Z', sheet.create(72, 6, 4, 6), 4));

		characters.add(new FontCharacter('1', sheet.create(0, 12, 2, 6), 2));
		characters.add(new FontCharacter('2', sheet.create(6, 12, 4, 6), 4));
		characters.add(new FontCharacter('3', sheet.create(12, 12, 4, 6), 4));
		characters.add(new FontCharacter('4', sheet.create(18, 12, 4, 6), 4));
		characters.add(new FontCharacter('5', sheet.create(24, 12, 4, 6), 4));
		characters.add(new FontCharacter('6', sheet.create(30, 12, 4, 6), 4));
		characters.add(new FontCharacter('7', sheet.create(36, 12, 4, 6), 4));
		characters.add(new FontCharacter('8', sheet.create(42, 12, 4, 6), 4));
		characters.add(new FontCharacter('9', sheet.create(48, 12, 4, 6), 4));
		characters.add(new FontCharacter('0', sheet.create(54, 12, 4, 6), 4));
		characters.add(new FontCharacter('$', sheet.create(60, 12, 5, 6), 5));
		characters.add(new FontCharacter('+', sheet.create(66, 12, 3, 6), 3));
		characters.add(new FontCharacter('-', sheet.create(72, 12, 3, 6), 3));

		characters.add(new FontCharacter('=', sheet.create(0, 18, 3, 6), 3));
		characters.add(new FontCharacter(':', sheet.create(6, 18, 1, 6), 1));
		characters.add(new FontCharacter(';', sheet.create(12, 18, 1, 6), 1));
		characters.add(new FontCharacter('.', sheet.create(18, 18, 1, 6), 1));
		characters.add(new FontCharacter(',', sheet.create(24, 18, 1, 6), 1));
		characters.add(new FontCharacter('!', sheet.create(30, 18, 1, 6), 1));
		characters.add(new FontCharacter('?', sheet.create(36, 18, 1, 6), 1));
		characters.add(new FontCharacter('(', sheet.create(42, 18, 2, 6), 2));
		characters.add(new FontCharacter(')', sheet.create(48, 18, 2, 6), 2));
		characters.add(new FontCharacter('<', sheet.create(54, 18, 5, 6), 5));
		characters.add(new FontCharacter('>', sheet.create(60, 18, 5, 6), 5));
		characters.add(new FontCharacter((char) 39, sheet.create(66, 18, 1, 6), 1));
		characters.add(new FontCharacter('"', sheet.create(72, 18, 3, 6), 3));
	}

	public BufferedImage createText(String string) {
		BufferedImage text = new BufferedImage(160, 6,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = text.getGraphics();
		
		char[] chars = string.toUpperCase().toCharArray();

		int offset = 0;
		int spacing = 1;
		int space = 3;

		for (int i = 0; i < chars.length; i++) {
			for (int j = 0; j < characters.size(); j++) {
				if (chars[i] == characters.get(j).c) {
					g.drawImage(characters.get(j).d.data(), offset, 0, null);
					offset += characters.get(j).w + spacing;
				}
			}
			if (chars[i] == ' ') {
				offset += space + spacing;
			}
		}

		/*
		 * try { ImageIO.write(text, "png", ImageIO .createImageOutputStream(new
		 * FileOutputStream("text.png"))); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		return text;
	}
	
}
