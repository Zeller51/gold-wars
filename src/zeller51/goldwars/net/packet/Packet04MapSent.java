package zeller51.goldwars.net.packet;

public class Packet04MapSent extends Packet {

	// Unload
	public Packet04MapSent(byte[] data) {
		super(04, data);
	}

	// Load
	public Packet04MapSent() {
		super(04, ("04").getBytes());
	}

}