package zeller51.goldwars.net.packet;

public class Packet02CreateMap extends Packet {

	private int width, height;

	// Unload
	public Packet02CreateMap(byte[] data) {
		super(02, data);
		String[] dataArray = new String(data).split(":");
		width = Integer.parseInt(dataArray[1]);
		height = Integer.parseInt(dataArray[2]);
	}

	// Load
	public Packet02CreateMap(int width, int height) {
		super(02, ("02:" + width + ":" + height).getBytes());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}