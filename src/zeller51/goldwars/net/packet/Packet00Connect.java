package zeller51.goldwars.net.packet;

import zeller51.goldwars.net.ClientPacketHandler;
import zeller51.goldwars.net.ServerPacketHandler;

public class Packet00Connect extends Packet {

	private String username;
	
	// Unload
	public Packet00Connect(byte[] data) {
		super(00, data);
		String[] dataArray = new String(data).split(":");
		username = dataArray[1];
	}

	// Load
	public Packet00Connect(String username) {
		super(00, ("00:" + username).getBytes());
	}

	@Override
	public void writeData(ClientPacketHandler client) {
		client.sendData(data);
	}

	@Override
	public void writeData(ServerPacketHandler server) {
		server.sendDataToClients(data);
	}
	
	public String getUsername() {
		return username;
	}
}