package zeller51.goldwars.map;

import java.awt.Graphics;

import zeller51.goldwars.Assets;

public class BlockAir extends Block {

	public BlockAir(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Graphics g, int offsetX, int offsetY) {
		g.drawImage(Assets.blockAir.data(), offsetX + x, offsetY + y, null);
	}

	@Override
	public byte[] getData() {
		return (":" + Block.AIR + ":" + x + ":" + y).getBytes();
	}

	@Override
	public void destroy(Map map) {

	}

}
