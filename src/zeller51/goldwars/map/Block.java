package zeller51.goldwars.map;

import java.awt.Graphics;

public abstract class Block {

	public static final int AIR = 0;
	public static final int STONE = 1;
	public static final int GOLD = 2;
	public static final int BEDROCK = 3;

	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;

	protected int x, y;

	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update(Map map) {
		
	}

	public abstract void render(Graphics g, int offsetX, int offsetY);
	
	public void destroy(Map map) {
		map.changeBlock(x, y, AIR);
	}

	public boolean isCollision(int x, int y) {
		return false;
	}

	public boolean isBlock(int x, int y) {
		if ((x >= this.x && x < this.x + WIDTH)
				&& (y >= this.y && y < this.y + HEIGHT)) {
			return true;
		} else {
			return false;
		}
	}

	public abstract int getType();

}
