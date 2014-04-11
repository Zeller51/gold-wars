package zeller51.goldwars.map;

import java.awt.Graphics;

import zeller51.goldwars.Assets;

public class BlockStone extends Block {

	public BlockStone(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Graphics g, int offsetX, int offsetY) {
		g.drawImage(Assets.blockStone.data(), offsetX + x, offsetY + y, null);
	}

	@Override
	public byte[] getData() {
		return (":" + Block.STONE + ":" + x + ":" + y).getBytes();
	}

	@Override
	public boolean isCollision(int x, int y) {
		if ((x >= this.x && x < this.x + WIDTH)
				&& (y >= this.y && y < this.y + HEIGHT)) {
			return true;
		} else {
			return false;
		}
	}

}
