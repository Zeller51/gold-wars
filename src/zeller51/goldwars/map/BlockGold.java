package zeller51.goldwars.map;

import java.awt.Graphics;

import zeller51.goldwars.Assets;

public class BlockGold extends Block {

	public BlockGold(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Graphics g, int offsetX, int offsetY) {
		g.drawImage(Assets.blockGold.data(), offsetX + x, offsetY + y, null);
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
	
	@Override
	public void destroy(Map map) {
		// TODO Drop gold!
		super.destroy(map);
	}

	@Override
	public int getType() {
		return Block.GOLD;
	}
	
}
