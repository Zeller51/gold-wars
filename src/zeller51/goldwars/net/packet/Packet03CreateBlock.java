package zeller51.goldwars.net.packet;

public class Packet03CreateBlock extends Packet {

	private int type, x, y;

	// Unload
	public Packet03CreateBlock(byte[] data) {
		super(03, data);
		String[] dataArray = new String(data).split(":");
		type = Integer.parseInt(dataArray[1]);
		x = Integer.parseInt(dataArray[2]);
		y = Integer.parseInt(dataArray[3]);
	}

	// Load
	public Packet03CreateBlock(int type, int x, int y) {
		super(03, ("03:" + type + ":" + x + ":" + y).getBytes());
	}

	public int getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}