package zeller51.goldwars.map;

import java.util.ArrayList;
import java.util.List;

import zeller51.goldwars.entities.Entity;

public class Map {

	public static void main(String[] args) {
		Map m = new Map(48, 48);
		m.generateMap();
	}

	private final float CAVEFREQUENCY = 2.0f;
	private final float GOLDFREQUENCY = 5.0f;
	private final float CAVESIZE = 8.0f;
	private final float GOLDSIZE = 4.0f;
	private final float CAVEAMOUNT = 0.1f;
	private final float GOLDAMOUNT = 0.2f;

	public int width, height;
	public long seed;

	private List<Block> blocks = new ArrayList<Block>();
	private List<Entity> entities = new ArrayList<Entity>();

	public Map(int width, int height, long seed) {
		this.width = width;
		this.height = height;
	}

	public Map(int width, int height) {
		this(width, height, System.nanoTime());
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(this);
		}
	}

	public void generateMap() {
		Perlin pCave = new Perlin(seed);
		Perlin pGold = new Perlin(seed * 2);

		for (float h = 0; h < height; h++) {
			for (float w = 0; w < width; w++) {
				float cave = pCave.turbulence2(w / CAVESIZE, h / CAVESIZE,
						CAVEFREQUENCY);
				float gold = pGold.turbulence2(w / GOLDSIZE, h / GOLDSIZE,
						GOLDFREQUENCY);

				int block;

				// Create caves
				if (cave > CAVEAMOUNT) {
					block = Block.AIR;
				} else {
					block = Block.STONE;
				}

				// Create gold
				if ((block == Block.STONE) && (gold >= GOLDAMOUNT)) {
					block = Block.GOLD;
				}

				// Create bedrock surrounding the map
				if ((w == 0) || (w == width - 1)) {
					block = Block.BEDROCK;
				}
				if ((h == 0) || (h == height - 1)) {
					block = Block.BEDROCK;
				}

				System.out.print(" "+block);

				// Create Block
				createBlock(block);
			}
			System.out.println();
		}
	}

	private void createBlock(int block, int index) {
		switch (block) {
		case Block.AIR:
			// TODO BlockAir
			break;
		case Block.STONE:
			// TODO BlockStone
			break;
		case Block.GOLD:
			// TODO BlockGold
			break;
		case Block.BEDROCK:
			// TODO BlockBedrock
			break;
		}
	}

	private void createBlock(int block) {
		createBlock(block, blocks.size());
	}

	public Block getBlock(int x, int y) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).isBlock(x, y)) {
				return blocks.get(i);
			}
		}
		System.err.println("Unable to find block at X:" + x + " Y:" + y + "!");
		return null;
	}

	private int getBlockIndex(int x, int y) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).isBlock(x, y)) {
				return i;
			}
		}
		System.err.println("Unable to find block index at X:" + x + " Y:" + y + "!");
		return -1;
	}

	public void changeBlock(int x, int y, int block) {
		int index = getBlockIndex(x, y);

		blocks.remove(index);
	}

}