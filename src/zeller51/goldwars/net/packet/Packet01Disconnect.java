package zeller51.goldwars.net.packet;

import zeller51.goldwars.net.ClientPacketHandler;
import zeller51.goldwars.net.ServerPacketHandler;

public class Packet01Disconnect extends Packet{

	private String username;
	
	// Unload
	public Packet01Disconnect(byte[] data) {
		super(01, data);
		String[] dataArray = new String(data).split(":");
		username = dataArray[1];
	}

	// Load
	public Packet01Disconnect(String username) {
		super(01, ("01:" + username).getBytes());
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
