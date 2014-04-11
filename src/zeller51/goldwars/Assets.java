package zeller51.goldwars;

import zeller51.goldwars.graphics.Font;
import zeller51.goldwars.graphics.Sprite;
import zeller51.sound.Sound;

public class Assets {

	private static Sprite fontCharacters;
	public static Font font;

	private static Sprite player;
	public static Sprite pUp1, pUp2, pDown1, pDown2, pLeft1, pLeft2, pRight1,
			pRight2;

	private static Sprite blocks;
	public static Sprite blockAir, blockStone, blockBedrock, blockGold;

	private static Sprite breaking;
	public static Sprite break1, break2, break3, break4, break5, break6,
			break7, break8;
	
	public static Sound click, enter;

	public static void load() {
		fontCharacters = new Sprite("chars.png");
		font = new Font(fontCharacters);

		player = new Sprite("playerGuy.png");

		pUp1 = player.create(12, 0, 6, 12);
		pDown1 = player.create(0, 0, 6, 12);
		pLeft1 = player.create(0, 12, 6, 12);
		pRight1 = player.create(12, 12, 6, 12);

		pUp2 = player.create(18, 0, 6, 12);
		pDown2 = player.create(6, 0, 6, 12);
		pLeft2 = player.create(6, 12, 6, 12);
		pRight2 = player.create(18, 12, 6, 12);

		blocks = new Sprite("blocks.png");

		blockAir = blocks.create(8, 0, 8, 8);
		blockStone = blocks.create(0, 0, 8, 8);
		blockBedrock = blocks.create(16, 0, 8, 8);
		blockGold = blocks.create(24, 0, 8, 8);

		breaking = new Sprite("break.png");

		break1 = breaking.create(0, 0, 8, 8);
		break2 = breaking.create(8, 0, 8, 8);
		break3 = breaking.create(16, 0, 8, 8);
		break4 = breaking.create(24, 0, 8, 8);
		break5 = breaking.create(32, 0, 8, 8);
		break6 = breaking.create(40, 0, 8, 8);
		break7 = breaking.create(48, 0, 8, 8);
		break8 = breaking.create(56, 0, 8, 8);
		
		click = new Sound("click.wav");
		enter = new Sound("enter.wav");
	}

}
