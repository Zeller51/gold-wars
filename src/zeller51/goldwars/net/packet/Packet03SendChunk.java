package zeller51.goldwars.net.packet;

public class Packet03SendChunk extends Packet {

	private String dataChunk;

	// Unload
	public Packet03SendChunk(byte[] data) {
		super(03, data);
		dataChunk = new String(data).substring(3);
	}

	// Load
	public Packet03SendChunk(String data) {
		super(03, ("03:" + data).getBytes());
	}

	public String getDataChunk() {
		return dataChunk;
	}

}