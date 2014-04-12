package zeller51.goldwars.net.packet;

public class Packet05BlockChange extends Packet {

	private int x, y, type;

	// Unload
	public Packet05BlockChange(byte[] data) {
		super(02, data);
		String[] dataArray = new String(data).split(":");
		x = Integer.parseInt(dataArray[1]);
		y = Integer.parseInt(dataArray[2]);
		type = Integer.parseInt(dataArray[3]);
	}

	// Load
	public Packet05BlockChange(int x, int y, int type) {
		super(02, ("02:" + x + ":" + y + ":" + type).getBytes());
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getType() {
		return type;
	}
}